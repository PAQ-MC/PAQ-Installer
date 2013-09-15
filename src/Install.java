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
File srcFolder = null, destFolder = null;
		if (main.GetMcFilepath()== null){
			main.print("Your OS is currently not supported Exiting Installer and Opening fourms", true);
			java.awt.Desktop.getDesktop().browse(java.net.URI.create("http://paq.mage-tech.org"));
			main.exit();
		}else{
		srcFolder	 = main.GetMcFilepath();
		destFolder	 = main.GetPAQPath();
		}
		
		

		if (!destFolder.exists()) {
			if (!srcFolder.exists()) {
				main.print(".minecraft does not exits please run minecraft atleast once", true);
				main.exit();
			} else {
				main.print("copying .minecraft this could take a bit", false);
				FileControl.copyFolder(srcFolder, destFolder);
				main.print("finshed coping .minecraft",false);
			}

		}

		System.out.println("checking if v.txt exits");
		File v = new File(srcFolder + "/v.txt");
		if (v.exists()) {
			main.print("v.txt does exits comparing to web PAQ v",false); ///change PAQ to mod pack name
			BufferedReader in = new BufferedReader(new FileReader(v));

			while (in.ready()) {
				PAQv = in.readLine();
			}
			in.close();
			if (PAQv.equals(main.Update())) {
				main.print("web PAQ v and v.txt mach",false);  /// Change PAQ to Mod pack name
				main.print("you are running the most upto date PAQ v. installer will now exit",true);  ///change PAQ to mod pack name
				main.exit();
			} else {
				main.print("web PAQ v and v.txt do not mach updating v.txt",false); /// Change PAQ to Mod pack name
				File file = new File(srcFolder + "/v.txt");
				file.delete();
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(main.Update());
				bw.close();
			}
		} else {
			main.print("v.txt does not exist makeing",false);
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
		main.print("Downloading config",false);
		// Change this txt to change Config download location
		main.downloadtxt("http://mage-tech.org/PAQ/config.txt", 
				"PAQ-Temp/config.txt"); /// Change to Config web location 
		main.print("config downloaded ... reading config",false);
		BufferedReader br = new BufferedReader(new FileReader(
				"PAQ-Temp/config.txt")); /// Change To config Downloaded location
		String location, adfy, unzip, savelocation, unziplocation, filename;

		while ((location = br.readLine()) != null) {
			adfy = br.readLine();
			unzip = br.readLine();
			savelocation = br.readLine();
			unziplocation = br.readLine();
			filename = br.readLine();
			main.print("checking if " + filename
					+ " requres adfly download",false);
			if (adfy.equals("true")) {
				System.out.println("it does");
				boolean exists = false;
				do {
					main.print("showing modauthor msg",false);
					main.print("due to the modauthor not giving us permision to add there mod to the mod pack main download you will need to download thu there site",false);
					main.print("opening users defult browser",false);
					java.awt.Desktop.getDesktop().browse(
							java.net.URI.create(location));
					main.print("showing wait msg",false);
					main.print(" please close this when the file is downloaded to the PAQ-Temp/Downloads folder at your current location",true); /// Change to <Modpack Name>-temp/Downloads
					main.print("Checking if " + filename + " exits",true);
					File downloadeditem = new File("PAQ-temp/Downloads/"
							+ filename); ///chang PAQ-Temp/Downloads to <Modpack name>-Temp/Downloads
					if (downloadeditem.exists() != true) {
						main.print("File missing asking if link is broken",false);
						int reply = JOptionPane.showConfirmDialog(null,
								"is the link broken?", null,
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							main.print("link Broken sending user to broken link reporting forum",false);
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
					main.print("does file exist?",false);
				} while (exists != true);
				main.print("File does exist",false);
				main.print("Checking if needs upziping",false);
				if (unzip.equals("true")) {
					main.print("does need unziping",false);
					main.unzip("/PAQ-Temp/Donwloads/" + filename, unziplocation);  ///change PAQ-Temp/Downloads to <modpack name>-Temp/Downloads
					main.print("file unziped",false);
				} else {
					main.print("file does not need unziping copying to requred location",false);
					File copylocation = new File("/PAQ-Temp/Downloads/"
							+ filename);
					copylocation.renameTo(new File(savelocation));
					main.print("File copyed",false);
				}
			} else {
				main.print("not adfly downloading " + filename,false);
				main.download(location, savelocation);
				main.print("File downloaded",false);
				main.print("Checking if needs unziping",false);
				if (unzip.equals("true")) {
					main.print("file does need unziping",false);
					main.unzip(savelocation, unziplocation);
					main.print("File unziped",false);
				}
				main.print("dose not need unziping",false);

			}

		}
		br.close();

		// download forge
		main.print("getting forge config info",false);
		BufferedReader reader = main
				.read("http://mage-tech.org/PAQ/forgeinfo.txt");

		String url = reader.readLine();
		String name = reader.readLine();
		String lastVersionId = reader.readLine();

		reader.close();
		main.print("downloading config",false);
		main.download(url, "PAQ-Temp/" + name);
		main.print("Fished downloading forge",false);

		// install forge
		main.print("launching forge installer",false);
		main.print("the installer will now launch the forge installer please install forge and do not close the installer",true);

		Process p = Runtime.getRuntime().exec(("java -jar PAQ-Temp/" + name));
		p.waitFor();
		if (p.exitValue() != 0) {
			main.print("forge install failed PAQ installer will now exit",true);
			main.exit();
		}

		JsonEditCode.Main(srcFolder.toString(), main.Update(),
				destFolder.toString(), lastVersionId);

		// move mods folder form PAQ-Temp to %appdata%/.PAQ
		File modsfolder = new File(destFolder + "/mods");
		if (modsfolder.exists()) {
			main.print("deleteing old mod folder",false);
			FileControl.delete(modsfolder);
			main.print("old mods folder deleted",false);
		}
		FileControl.copyFolder(new File("PAQ-Temp/mods"), modsfolder);
		// move config folder form PAQ-Temp to %appdata%/.PAQ
		File configfolder = new File(destFolder + "/config");
		if (configfolder.exists()) {
			main.print("deleteing old config folder",false);
			FileControl.delete(configfolder);
			main.print("old config Folder deleted",false);
		}
		FileControl.copyFolder(new File("PAQ-Temp/config"), configfolder);
		main.print("what are we going to do today?",false);
		main.print("install done have fun playing",true);
		main.print("we are going to take over the world block by block",false);
	}
}
