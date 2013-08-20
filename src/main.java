import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;


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
	//Get v. Statuses 
	public static String Update() {
		// Update code goes here
		String PAQV = null;
		try {
			PAQV = webget("http://mage-tech.org/PAQ/PAQv.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return PAQV;

		// webcheck to check that v.txt in %appdata%/.PAQ matches
		// http://mage-tech.org/pack/PAQv.txt if not return "update needed"
	}
	//exit code
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
		UnZip unZip = new UnZip();
		unZip.unZipIt(Zipfile, Directry);
	}
	// Download code
	public static void download(String Url, String Dowloadlocation) {
		try {
			
			File outputfile = new File(Dowloadlocation);
			outputfile.getParentFile().mkdirs();
			outputfile.createNewFile();
			
			System.out.println("Downloading " + Url + " to " + outputfile);
			
			HttpURLConnection connect = (HttpURLConnection) (new URL(Url)).openConnection();
			connect.setInstanceFollowRedirects(true);
			
			InputStream inStream = connect.getInputStream();
			OutputStream outStream = new FileOutputStream(outputfile);
			
			int data = inStream.read();
			System.out.print(data);
			while (data != -1);
			{
				System.out.print(data);
				outStream.write(data);
				System.out.print(data);
				data = inStream.read();
			}
			
			inStream.close();
			outStream.flush();
			outStream.close();
			
			System.out.println("Download complete");
		

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
	
	public static void main(String[] args) throws IOException {
		//Gui.consolegui();
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
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							doLoop = false;
						}
					}
					// Website
					if (x >= .49 - .3 && x <= .49 + .3) {
						if (y >= .25 - .2 && y <= .25 + .2) {
							System.out.println("Website Button Clicked");
							System.out.println("Opening The PAQ Fourms");
							try {
								java.awt.Desktop
										.getDesktop()
										.browse(java.net.URI
												.create("http://paq.mage-tech.org"));
							} catch (IOException e) {
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
