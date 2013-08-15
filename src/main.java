import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

//to do's
//work on .json edit code
//work on move file code

public class main {

	static String PAQv;
	static JTextArea textArea;

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

	// install code goes here
	public static void Install() throws Exception {

		// PAQ V check Code

		File srcFolder = new File(System.getenv("APPDATA") + "\\.minecraft");
		File destFolder = new File(System.getenv("APPDATA") + "\\.paq");

		if (!destFolder.exists()) {
			if (!srcFolder.exists()) {
				System.out.println(".minecraft Does not exist");
			} else {
				copyFolder(srcFolder, destFolder);
			}

		}

		File v = new File(destFolder + "//v.txt");
		if (v.exists()) {
			BufferedReader in = new BufferedReader(new FileReader(v));
			while (in.ready()) {
				PAQv = in.readLine();
			}
			in.close();
			if (PAQv == Update()) {
				JOptionPane
						.showMessageDialog(null,
								"you are running the most upto date PAQ v. installer will now exit");
				exit();
			} else {
				File file = new File(destFolder + "//v.txt");
				file.delete();
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(Update());
				bw.close();
			}
		} else {

			File file = new File(destFolder + "//v.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Update());
			bw.close();
		}

		download("http://mage-tech.org/PAQ/config.txt", "PAQ-Temp/config.txt");

		BufferedReader br = new BufferedReader(new FileReader(
				"PAQ-Temp/config.txt"));
		String location, adfy, unzip, savelocation, unziplocation, filename;

		while ((location = br.readLine()) != null) {
			adfy = br.readLine();
			unzip = br.readLine();
			savelocation = br.readLine();
			unziplocation = br.readLine();
			filename = br.readLine();

			if (adfy.equals("true")) {
				boolean exists = false;
				do {
					JOptionPane
							.showMessageDialog(
									null,
									"due to the modauthor not giving us permision to add there mod to the mod pack main download you will need to download thu there site");
					java.awt.Desktop.getDesktop().browse(
							java.net.URI.create(location));
					JOptionPane
							.showMessageDialog(
									null,
									" please close this when the file is downloaded to the PAQ-Temp/Downloads folder at your current location");
					File downloadeditem = new File("PAQ-temp/Downloads/"
							+ filename);
					if (downloadeditem.exists() != true) {
						int reply = JOptionPane.showConfirmDialog(null,
								"is the link broken?", null,
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							java.awt.Desktop
									.getDesktop()
									.browse(java.net.URI
											.create("http://pack.mage-tech.org"));
							exists = true;
						} else {
							exists = false;
						}
					} else {
						exists = true;
					}
				} while (exists != true);
				if (unzip.equals("true")) {
					unzip("/PAQ-Temp/Donwloads/" + filename, unziplocation);
				} else {
					File copylocation = new File("/PAQ-Temp/Downloads/"
							+ filename);
					copylocation.renameTo(new File(savelocation));
				}
			} else {
				download(location, savelocation);
				if (unzip.equals("true")) {
					unzip(savelocation, unziplocation);
				}

			}

		}
		br.close();

		// download forge
		BufferedReader reader = read("http://mage-tech.org/PAQ/forgeinfo.txt");

		String url = reader.readLine();
		String name = reader.readLine();

		reader.close();

		download(url, "PAQ-Temp/" + name);

		// install forge
		// ToDO talk to arkman about msg
		JOptionPane
				.showMessageDialog(
						null,
						"the installer will now launch the forge installer please install forge and do not close the installer");

		Process p = Runtime.getRuntime().exec(("java -jar PAQ-Temp/" + name));
		p.waitFor();
		if (p.exitValue() != 0) {
			JOptionPane.showMessageDialog(null,
					"forge install failed PAQ installer will now exit");
			exit();
		}

		// step three edit launcher_projiles.json and add PAQ at %appdata%/.PAQ

		// move mods folder form PAQ-Temp to %appdata%/.PAQ
		// move config folder form PAQ-Temp to %appdata%/.PAQ
		JOptionPane.showMessageDialog(null, "install done have fun playing");
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
	public static void download(String Url, String Dowloadlocation) {
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

	// copy folder code
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

	// console gui
	public static void consolegui() {
		redirectSystemStreams();
		JFrame frame = new JFrame("JTextArea Test");
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		textArea = new JTextArea("redirecting console output ... ", 10, 50);
		textArea.setPreferredSize(new Dimension(6, 3));
		JScrollPane scrollPane = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		textArea.setLineWrap(true);
		frame.add(scrollPane);
		frame.pack();
		frame.setVisible(true);
		System.out.println("console output redirected");
	}

	// update console gui
	private static void updateTextArea(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				textArea.append(text);
			}
		});
	}

	// redirect system.out
	private static void redirectSystemStreams() {
		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				updateTextArea(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				updateTextArea(new String(b, off, len));
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};

		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}

	public static void main(String[] args) throws IOException {
		consolegui();
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
							try {
								Install();
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
							System.out.println("button2");

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
