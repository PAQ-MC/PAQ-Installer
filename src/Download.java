import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Download {
	
	private static int tryGetFileSize(URL url) {
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("HEAD");
			conn.getInputStream();
			return conn.getContentLength();
		} catch (IOException e) {

			return -1;
		} finally {
			conn.disconnect();
		}
	}

	// Download code
	public static void download(String Url, String Downloadlocation)
			throws IOException {
		int cont = 0;
		do {
			main.print("Checking if link is valid", false);
			if (exists(Url)) {
				main.print("Downloading file from " + Url + " to "
						+ Downloadlocation, false);
				URL website;
				website = new URL(Url);
				ReadableByteChannel rbc = Channels.newChannel(website
						.openStream());
				FileOutputStream fos;
				File outputfile = new File(Downloadlocation);
				fos = new FileOutputStream(outputfile);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
			} else {
				main.print("FileServer appears down please try again later",
						true);
				main.exit();
			}
			cont = cont + 1;
		} while ((CheckDownload(new URL(Url), Downloadlocation) == true) & cont < 3);

	}

	public static boolean CheckDownload(URL url, String Filepath) {
		long webFileSize = tryGetFileSize(url);
		java.io.File file = new java.io.File(Filepath);
		long FileSize = file.length();
		main.print("Checking That The Download is vaild", false);
		if (webFileSize == FileSize) {
			return false;
		} else {
			return true;
		}

	}

	// Check if File Exists on Webserver
	public static boolean exists(String URLName) throws MalformedURLException,
			IOException {
		HttpURLConnection.setFollowRedirects(false);
		HttpURLConnection con = (HttpURLConnection) new URL(URLName)
				.openConnection();
		con.setRequestMethod("HEAD");
		return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
	}

	// Download txt
	public static void downloadtxt(String Url, String Downloadlocation)
			throws IOException {
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

}
