/*
This work is licensed under the Creative Commons
Attribution-NonCommercial 3.0 Unported License. 
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc/3.0/.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JOptionPane;

public class Install {
	static String PAQv;

	public static void main() throws Exception {

		// PAQ V check Code

		File srcFolder = new File(System.getenv("APPDATA") + "\\.minecraft");
		File destFolder = new File(System.getenv("APPDATA") + "\\.paq"); ///change to Mod Pack Name

		if (!destFolder.exists()) {
			if (!srcFolder.exists()) {
				System.out.println(".minecraft Does not exist");
				JOptionPane
						.showMessageDialog(null,
								".minecraft does not exits please run minecraft atleast once");
				main.exit();
			} else {
				System.out.println("copying .minecraft this could take a bit");
				FileControl.copyFolder(srcFolder, destFolder);
				System.out.println("finshed coping .minecraft");
			}

		}

		System.out.println("checking if v.txt exits");
		File v = new File(srcFolder + "/v.txt");
		if (v.exists()) {
			System.out.println("v.txt does exits comparing to web PAQ v"); ///change PAQ to mod pack name
			BufferedReader in = new BufferedReader(new FileReader(v));

			while (in.ready()) {
				PAQv = in.readLine();
			}
			in.close();
			if (PAQv.equals(main.Update())) {
				System.out.println("web PAQ v and v.txt mach");  /// Change PAQ to Mod pack name
				JOptionPane
						.showMessageDialog(null,
								"you are running the most upto date PAQ v. installer will now exit");  ///change PAQ to mod pack name
				main.exit();
			} else {
				System.out
						.println("web PAQ v and v.txt do not mach updating v.txt"); /// Change PAQ to Mod pack name
				File file = new File(srcFolder + "/v.txt");
				file.delete();
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(main.Update());
				bw.close();
			}
		} else {
			System.out.println("v.txt does not exist makeing");
			File file = new File(srcFolder + "//v.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(main.Update());
			bw.close();
		}
		System.out.println("Downloading config");
		// Change this txt to change Config download location
		main.downloadtxt("http://mage-tech.org/PAQ/config.txt", 
				"PAQ-Temp/config.txt"); /// Change to Config web location 
		System.out.println("config downloaded ... reading config");
		BufferedReader br = new BufferedReader(new FileReader(
				"PAQ-Temp/config.txt")); /// Change To config Downloaded location
		String location, adfy, unzip, savelocation, unziplocation, filename;

		while ((location = br.readLine()) != null) {
			adfy = br.readLine();
			unzip = br.readLine();
			savelocation = br.readLine();
			unziplocation = br.readLine();
			filename = br.readLine();
			System.out.println("checking if " + filename
					+ " requres adfly download");
			if (adfy.equals("true")) {
				System.out.println("it does");
				boolean exists = false;
				do {
					System.out.println("showing modauthor msg");
					JOptionPane
							.showMessageDialog(
									null,
									"due to the modauthor not giving us permision to add there mod to the mod pack main download you will need to download thu there site");
					System.out.println("opening users defult browser");
					java.awt.Desktop.getDesktop().browse(
							java.net.URI.create(location));
					System.out.println("showing wait msg");
					JOptionPane
							.showMessageDialog(
									null,
									" please close this when the file is downloaded to the PAQ-Temp/Downloads folder at your current location"); /// Change to <Modpack Name>-temp/Downloads
					System.out.println("Checking if " + filename + " exits");
					File downloadeditem = new File("PAQ-temp/Downloads/"
							+ filename); ///chang PAQ-Temp/Downloads to <Modpack name>-Temp/Downloads
					if (downloadeditem.exists() != true) {
						System.out
								.println("File missing asking if link is broken");
						int reply = JOptionPane.showConfirmDialog(null,
								"is the link broken?", null,
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							System.out
									.println("link Broken sending user to broken link reporting forum");
							java.awt.Desktop
									.getDesktop()
									.browse(java.net.URI
											.create("http://pack.mage-tech.org")); ///change to broken link reporting forum web location
							exists = true;
						} else {
							exists = false;
						}
					} else {
						exists = true;
					}
					System.out.println("does file exist?");
				} while (exists != true);
				System.out.println("File does exist");
				System.out.println("Checking if needs upziping");
				if (unzip.equals("true")) {
					System.out.println("does need unziping");
					main.unzip("/PAQ-Temp/Donwloads/" + filename, unziplocation);  ///change PAQ-Temp/Downloads to <modpack name>-Temp/Downloads
					System.out.println("file unziped");
				} else {
					System.out
							.println("file does not need unziping copying to requred location");
					File copylocation = new File("/PAQ-Temp/Downloads/"
							+ filename);
					copylocation.renameTo(new File(savelocation));
					System.out.println("File copyed");
				}
			} else {
				System.out.println("not adfly downloading " + filename);
				main.download(location, savelocation);
				System.out.println("File downloaded");
				System.out.println("Checking if needs unziping");
				if (unzip.equals("true")) {
					System.out.println("file does need unziping");
					main.unzip(savelocation, unziplocation);
					System.out.println("File unziped");
				}
				System.out.println("dose not need unziping");

			}

		}
		br.close();

		// download forge
		System.out.println("getting forge config info");
		BufferedReader reader = main
				.read("http://mage-tech.org/PAQ/forgeinfo.txt");

		String url = reader.readLine();
		String name = reader.readLine();
		String lastVersionId = reader.readLine();

		reader.close();
		System.out.println("downloading config");
		main.download(url, "PAQ-Temp/" + name);
		System.out.println("Fished downloading forge");

		// install forge
		System.out.println("launching forge installer");
		JOptionPane
				.showMessageDialog(
						null,
						"the installer will now launch the forge installer please install forge and do not close the installer");

		Process p = Runtime.getRuntime().exec(("java -jar PAQ-Temp/" + name));
		p.waitFor();
		if (p.exitValue() != 0) {
			JOptionPane.showMessageDialog(null,
					"forge install failed PAQ installer will now exit");
			main.exit();
		}

		JsonEditCode.Main(srcFolder.toString(), main.Update(),
				destFolder.toString(), lastVersionId);

		// move mods folder form PAQ-Temp to %appdata%/.PAQ
		File modsfolder = new File(destFolder + "/mods");
		if (modsfolder.exists()) {
			System.out.println("deleteing old mod folder");
			FileControl.delete(modsfolder);
			System.out.println("old mods folder deleted");
		}
		FileControl.copyFolder(new File("PAQ-Temp/mods"), modsfolder);
		// move config folder form PAQ-Temp to %appdata%/.PAQ
		File configfolder = new File(destFolder + "/config");
		if (configfolder.exists()) {
			System.out.println("deleteing old config folder");
			FileControl.delete(configfolder);
			System.out.println("old config Folder deleted");
		}
		FileControl.copyFolder(new File("PAQ-Temp/config"), configfolder);
		System.out.println("what are we going to do today?");
		JOptionPane.showMessageDialog(null, "install done have fun playing");
		System.out
				.println("we are going to take over the world block by block");
	}
}
