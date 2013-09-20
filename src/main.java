/*
This work is licensed under the Creative Commons
Attribution-NonCommercial 3.0 Unported License. 
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc/3.0/.
 */

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.io.*;

import javax.swing.JOptionPane;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class main {

	static String PAQv;
	static PrintWriter out;
	
	
	// web page read
	public static BufferedReader read(String url) throws Exception {

		return new BufferedReader(

		new InputStreamReader(

		new URL(url).openStream()));
	}

	// web page get
	public static String webget(String URL1) throws Exception {
		BufferedReader reader = read(URL1);

		String line = reader.readLine();

		return line;
	}

	// Get v. Statuses
	public static String Update() throws Exception {
		// Update code goes here
		String PAQV = null;
			PAQV = webget("http://mage-tech.org/PAQ/PAQv.txt"); 
		return PAQV;
	}

	// exit code
	public static void exit() throws IOException {
		// work on code to clean up PAQ-Temp folder
		out.close();
		FileControl.deletetempDir();
		System.exit(0);
	}

	// Unzip Code
	public static void unzip(String Zipfile, String Directry) throws ZipException {
			ZipFile zipFile = new ZipFile(Zipfile);
			zipFile.extractAll(Directry);
	}

	// Download code
	public static void download(String Url, String Downloadlocation) throws IOException {
		print("Checking if link is valid", false);
		if (exists(Url)) {
			main.print("Downloading file from " + Url + " to "
				+ Downloadlocation, false);
			URL website;
			website = new URL(Url);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos;
			File outputfile = new File(Downloadlocation);
			fos = new FileOutputStream(outputfile);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		} else {
			print("FileServer appears down please try again later", true);
			exit();
		}
		
	}

	//Check if File Exists on Webserver
	public static boolean exists(String URLName) throws MalformedURLException, IOException{
	      HttpURLConnection.setFollowRedirects(false);
	      HttpURLConnection con =
	         (HttpURLConnection) new URL(URLName).openConnection();
	      con.setRequestMethod("HEAD");
	      return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
	  }
	
	// Download txt
	public static void downloadtxt(String Url, String Downloadlocation) throws IOException {
			URL url = new URL(Url);
			URLConnection conn = url.openConnection();
			InputStream in = conn.getInputStream();
			FileOutputStream out = new FileOutputStream(Downloadlocation);

			byte[] b = new byte[1024];
			int count;
			while ((count = in.read(b)) >= 0) {
				out.write(b, 0, count);
			}
			out.flush();
			out.close();
			in.close();
	}

	public static File GetMcFilepath() {

		String os = System.getProperty("os.name").toLowerCase();
		boolean isWindows, isMac;

		isWindows = (os.indexOf("win") >= 0);
		isMac = (os.indexOf("mac") >= 0);

		if (isWindows) {
			File mclocation = new File(System.getenv("APPDATA")
					+ "\\.minecraft");
			return mclocation;
		} else if (isMac) {
			File mclocatoin = new File(System.getProperty("user.home")
					+ "/Library/Application Support/minecraft");
			return mclocatoin;
		} else {
			return null;
		}

	}

	public static File GetPAQPath() {

		String os = System.getProperty("os.name").toLowerCase();
		boolean isWindows, isMac;

		isWindows = (os.indexOf("win") >= 0);
		isMac = (os.indexOf("mac") >= 0);

		if (isWindows) {
			File PAQlocation = new File(System.getenv("APPDATA")
					+ "\\.PAQ");
			return PAQlocation;
		} else if (isMac) {
			File mclocatoin = new File(System.getProperty("user.home")
					+ "/Library/Application Support/PAQ");
			return mclocatoin;
		} else {
			return null;
		}
		}
	
	// Text Printing Method
	public static void print(String Text, boolean alert){
		System.out.println(Text);
		if (alert == true){
			JOptionPane.showMessageDialog(null, Text);
		}
			out.println(Text);
			out.flush();
	}
		
	// start code
	public static void main(String[] args) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		out = new PrintWriter(new FileWriter("PAQlog " + dateFormat.format(date) + ".txt"),true);
		Gui.consolegui();
		FileControl.createtempDir();
		Gui.main();
		boolean programloop = true;
		while (programloop == true) {

			boolean doLoop = true;
			while (doLoop == true) {
				double x;
				double y;

				if (StdDraw.mousePressed()) {
					x = StdDraw.mouseX();
					y = StdDraw.mouseY();
					// Install
					if (x >= .3 - .3 && x <= .3 + .3) {
						if (y >= .25 - .2 && y <= .25 + .2) {
							System.out.println("Install Button Clicked");
							System.out.println("Begining Install");
							try {
								Install.main();
							} catch (Exception e) {
								e.printStackTrace();
								print("a error has hapend sorry about this but please report this to the fourms and be sure to inclued the PAQlog.txt file", true);
								exit();
							}
							doLoop = false;
						}
					}
					// Force Update
					//if (x >= .49 - .3 && x <= .49 + .3) {
						//if (y >= .25 - .2 && y <= .25 + .2) {
							
							//try {
								//Install.main();
							//} catch (Exception e) {
								//e.printStackTrace();
							//}
							
							//}
							//doLoop = false;
						//}
					//}
					// Exit
					if (x >= .7 - .3 && x <= .7 + .3) {
						if (y >= .25 - .2 && y <= .25 + .2) {
							main.print("Exit Button Clicked",false);
							main.print("Exiting",false);
							exit();
							doLoop = false;
						}
					}
				}

			}
		}

		while (StdDraw.mousePressed())
			;
	}

}
