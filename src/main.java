import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;

import javax.swing.JOptionPane;

//to do's
//1. create perm folder at .minecraft folder location 
//2. create temp folder at start location
//3. Code all install code

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

	// GUI code
	public static void Gui() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		StdDraw.setCanvasSize(600, 300);
		StdDraw.frame.setLocation(dim.width / 2 - StdDraw.frame.getSize().width
				/ 2, dim.height / 2 - StdDraw.frame.getSize().height / 2 - 130);

		StdDraw.picture(0.5, 0.7, "PAQLogo.png");
		StdDraw.picture(0.18, 0.25, "Install1.png", .3, .2);
		StdDraw.picture(0.49, 0.25, "website1.png", .3, .2);
		StdDraw.picture(0.8, 0.25, "exit1.png", .3, .2);
		StdDraw.picture(0.5, 0.00, "copywrite1.png");
		// StdDraw.
	}

	public static void Install() {
		// install code goes here
		// ToDo work on install code
		// PAQ V check Code

		File srcFolder = new File(System.getenv("APPDATA") + "\\.minecraft");
		File destFolder = new File(System.getenv("APPDATA") + "\\.paq");

		if (!destFolder.exists()) {
			if (!srcFolder.exists()) {
				System.out.println(".minecraft Does not exist");
			} else {
				try {
					copyFolder(srcFolder, destFolder);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		File v = new File(destFolder + "//v.txt");
		if (v.exists()) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(v));
				while (in.ready()) {
					PAQv = in.readLine();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (PAQv != Update()) {
				JOptionPane
						.showMessageDialog(null,
								"you are running the most upto date PAQ v. installer will now exit");
				exit();
			}
		} else {
			try {

				String content = Update();

				File file = new File(destFolder + "//v.txt");

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(content);
				bw.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// step one download forge
		// step two install forge into minecraft launcher
		// step three edit launcher_projiles.json and add PAQ at %appdata%/.PAQ
		// step four download config.zip and unzip
		// step five move config folder to %appdata%/.PAQ
		// step six download permission given mods.zip and unzip
		// move permission given mods folder to %appdata%/.PAQ
		// launch users broser to download link for non permission given mod
		// link for user to download to ether C:/PAQ-Temp or %desktop%/PAQ-Temp
		// check to make sure mod was downloaded move on if yes if not repeat
		// last step after asking user if link is not broken if yes provide link
		// to report broken link and move to next step
		// repeat last two steps till all non perm mods are downloaded
		// move mods folder form PAQ-Temp to %appdata%/.PAQ
		// msg box to state install done
	}

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

	public static void exit() {
		// work on code to clean up PAQ-Temp folder
		try {
			deletetempDir();
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
	public static void downloadZipFile(String Url, String Dowloadlocation) {
		try {
			URL url = new URL(Url);
			URLConnection conn = url.openConnection();
			InputStream in = conn.getInputStream();
			FileOutputStream out = new FileOutputStream(Dowloadlocation);
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

	// Create Temp dir
	public static void createtempDir() {
		File theDir = new File("PAQ-Temp/mods");
		File theDir2 = new File("PAQ-Temp/config");
		File theDir3 = new File("PAQ-Temp/Downloads");

		if (!theDir.exists()) {
			boolean result = theDir.mkdirs();
			if (result) {
				System.out.println("PAQ-Temp/mods Folder Created");
			}
		}

		if (!theDir2.exists()) {

			boolean result = theDir2.mkdirs();

			if (result) {
				System.out.println("PAQ-Temp/config Folder Created");
			}
		}

		if (!theDir3.exists()) {

			boolean result = theDir3.mkdirs();

			if (result) {
				System.out.println("PAQ-Temp/Downloads Folder Created");
			}
		}
	}

	// delete directory code
	public static void delete(File file) throws IOException {
		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {

				file.delete();
				System.out.println("Directory is deleted : "
						+ file.getAbsolutePath());

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					delete(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					System.out.println("Directory is deleted : "
							+ file.getAbsolutePath());
				}
			}

		} else {
			// if file, then delete it
			file.delete();
			System.out.println("File is deleted : " + file.getAbsolutePath());
		}
	}

	// delete temp directory code
	public static void deletetempDir() throws IOException {
		File file = new File("PAQ-Temp");
		delete(file);
	}

	public static void copyFolder(File src, File dest) throws IOException {

		if (src.isDirectory()) {

			// if directory not exists, create it
			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory copied from " + src + "  to "
						+ dest);
			}

			// list all the directory contents
			String files[] = src.list();

			for (String file : files) {
				// construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// recursive copy
				copyFolder(srcFile, destFile);
			}

		} else {
			// if file, then copy it
			// Use bytes stream to support all file types
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
			System.out.println("File copied from " + src + " to " + dest);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		createtempDir();
		Gui();

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
							System.out.println("button1");
							Install();
							doLoop = false;
						}
					}
					// Website
					if (x >= .49 - .3 && x <= .49 + .3) {
						if (y >= .25 - .2 && y <= .25 + .2) {
							System.out.println("button2");

							try {
								java.awt.Desktop
										.getDesktop()
										.browse(java.net.URI
												.create("http://pack.mage-tech.org"));
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
							System.out.println("button3");
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
