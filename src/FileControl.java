/*
This work is licensed under the Creative Commons
Attribution-NonCommercial 3.0 Unported License. 
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc/3.0/.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FileControl {

	// Create Temp dir
		public static void createtempDir() {
			File theDir = new File("PAQ-Temp/mods");
			File theDir2 = new File("PAQ-Temp/config");
			File theDir3 = new File("PAQ-Temp/Downloads");

			if (!theDir.exists()) {
				boolean result = theDir.mkdirs();
				if (result) {
					main.print("PAQ-Temp/mods Folder Created", false);
				}
			}

			if (!theDir2.exists()) {

				boolean result = theDir2.mkdirs();

				if (result) {
					main.print("PAQ-Temp/config Folder Created", false);
				}
			}

			if (!theDir3.exists()) {

				boolean result = theDir3.mkdirs();

				if (result) {
					main.print("PAQ-Temp/Downloads Folder Created", false);
				}
			}
		}

		// delete directory code
		public static void delete(File file) throws IOException {
			if (file.isDirectory()) {

				// directory is empty, then delete it
				if (file.list().length == 0) {

					file.delete();
					main.print("Directory is deleted : "
							+ file.getAbsolutePath(), false);

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
						main.print("Directory is deleted : "
								+ file.getAbsolutePath(), false);
					}
				}

			} else {
				// if file, then delete it
				file.delete();
				main.print("File is deleted : " + file.getAbsolutePath(), false);
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
					main.print("Directory copied from " + src + "  to "
							+ dest , false);
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
				main.print("File copied from " + src + " to " + dest, false);
			}
		}


}
