/*
This work is licensed under the Creative Commons
Attribution-NonCommercial 3.0 Unported License. 
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc/3.0/.
 */

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.io.*;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class main {

	static String PAQv;

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
	public static String Update() {
		// Update code goes here
		String PAQV = null;
		try {
			PAQV = webget("http://mage-tech.org/PAQ/PAQv.txt"); // /change to
																// your v.txt
																// location on
																// the web
		} catch (Exception e) {
			e.printStackTrace();
		}

		return PAQV;
	}

	// exit code
	public static void exit() {
		// work on code to clean up PAQ-Temp folder
		try {
			FileControl.deletetempDir();
		} catch (IOException e) {

			e.printStackTrace();
		}
		System.exit(0);
	}

	// Unzip Code
	public static void unzip(String Zipfile, String Directry) {

		try {

			ZipFile zipFile = new ZipFile(Zipfile);
			zipFile.extractAll(Directry);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}

	// Download code
	public static void download(String Url, String Downloadlocation) {
		System.out.println("Downloading file from " + Url + " to "
				+ Downloadlocation);
		try {
			URL website;
			website = new URL(Url);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos;
			File outputfile = new File(Downloadlocation);
			fos = new FileOutputStream(outputfile);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Download txt
	public static void downloadtxt(String Url, String Downloadlocation) {
		try {
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

		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	// start code
	public static void main(String[] args) throws IOException {
		// Gui.consolegui();
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
					if (x >= .18 - .3 && x <= .18 + .3) {
						if (y >= .25 - .2 && y <= .25 + .2) {
							System.out.println("Install Button Clicked");
							System.out.println("Begining Install");
							try {
								Install.main();
							} catch (Exception e) {
								e.printStackTrace();
							}
							doLoop = false;
						}
					}
					// Force Update
					if (x >= .49 - .3 && x <= .49 + .3) {
						if (y >= .25 - .2 && y <= .25 + .2) {
							FileControl.delete(GetPAQPath());
							FileControl.delete(new File(GetMcFilepath()+"/v.txt"));
							try {
								Install.main();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							doLoop = false;
						}
					}
					// Exit
					if (x >= .8 - .3 && x <= .8 + .3) {
						if (y >= .25 - .2 && y <= .25 + .2) {
							System.out.println("Exit Button Clicked");
							System.out.println("Exiting");
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
