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
		File destFolder = new File(System.getenv("APPDATA") + "\\.paq");

		if (!destFolder.exists()) {
			if (!srcFolder.exists()) {
				System.out.println(".minecraft Does not exist");
			} else {
				FileControl.copyFolder(srcFolder, destFolder);
			}

		}

		File v = new File(destFolder + "//v.txt");
		if (v.exists()) {
			BufferedReader in = new BufferedReader(new FileReader(v));

			while (in.ready()) {
				PAQv = in.readLine();
			}
			in.close();
			if (PAQv == main.Update()) {
				JOptionPane
						.showMessageDialog(null,
								"you are running the most upto date PAQ v. installer will now exit");
				main.exit();
			} else {
				File file = new File(destFolder + "//v.txt");
				file.delete();
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(main.Update());
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
			bw.write(main.Update());
			bw.close();
		}
		//Change this txt to change Config download location
		main.download("http://mage-tech.org/PAQ/config.txt",
				"PAQ-Temp/config.txt");

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
					main.unzip("/PAQ-Temp/Donwloads/" + filename, unziplocation);
				} else {
					File copylocation = new File("/PAQ-Temp/Downloads/"
							+ filename);
					copylocation.renameTo(new File(savelocation));
				}
			} else {
				main.download(location, savelocation);
				if (unzip.equals("true")) {
					main.unzip(savelocation, unziplocation);
				}

			}

		}
		br.close();

		// download forge
		BufferedReader reader = main
				.read("http://mage-tech.org/PAQ/forgeinfo.txt");

		String url = reader.readLine();
		String name = reader.readLine();
		String lastVersionId = reader.readLine();

		reader.close();

		main.download(url, "PAQ-Temp/" + name);

		// install forge
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

		//edit launcher_profiles.json
		//add to the profiles group
		//	"name": "PAQ" + v. number,
		//	"gameDir": destfolder ,
		//  "lastVersionId": lastVersionId,
		//  "useHopperCrashService": false,
		

		// move mods folder form PAQ-Temp to %appdata%/.PAQ
		// move config folder form PAQ-Temp to %appdata%/.PAQ
		JOptionPane.showMessageDialog(null, "install done have fun playing");
	}

}
