import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JOptionPane;

//to do's
//1. create perm folder at .minecraft folder location 
//2. create temp folder at start location
//3. Code all install code

public class main {
    //web page read
	public static BufferedReader read(String url) throws Exception{

		return new BufferedReader(

		new InputStreamReader(

		new URL(url).openStream()));}
	
	//web page get
	public static String webget(String URL1) throws Exception{
		BufferedReader reader = read(URL1);

		String line = reader.readLine();
		
		return line;
	}
	//GUI code
	public static void Gui() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		StdDraw.setCanvasSize(600, 300);
		StdDraw.frame.setLocation(dim.width/2-StdDraw.frame.getSize().width/2, dim.height/2-StdDraw.frame.getSize().height/2 - 130);
		
		StdDraw.picture(0.5, 0.7, "PAQLogo.png");
		StdDraw.picture(0.18, 0.25, "Install1.png", .3,.2 );
		StdDraw.picture(0.49, 0.25, "update1.png", .3,.2 );
		StdDraw.picture(0.8, 0.25, "exit1.png", .3,.2 );
		StdDraw.picture(0.5, 0.00, "copywrite1.png");
		// StdDraw.
	}
	
	public static void Install() {
		//install code goes here
		//ToDo work on install code
		//step 0 check that Install needs updating
		//step one download forge
		//step two install forge into minecraft launcher
		//step three edit launcher_projiles.json and add PAQ at %appdata%/.PAQ
		//step four download  config.zip and unzip
		//step five move config folder to %appdata%/.PAQ
		//step six download permission given mods.zip and unzip
		//move permission given mods folder to %appdata%/.PAQ
		//launch users broser to download link for non permission given mod link for user to download to ether C:/PAQ-Temp or %desktop%/PAQ-Temp
		//check to make sure mod was downloaded move on if yes if not repeat last step after asking user if link is not broken if yes provide link to report broken link and move to next step
		//repeat last two steps till all non perm mods are downloaded
		//move mods folder form PAQ-Temp to %appdata%/.PAQ
		//msg box to state install done
	}
	
	public static void Update() {
		//Update code goes here
		String PAQV = null;
		try {
			PAQV = webget("http://mage-tech.org/PAQ/PAQv.txt");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "PAQ V. " + PAQV );
		String PAQLV = null;
		try {
			PAQLV = webget("http://mage-tech.org/PAQ/PAQLauncherV.txt");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "PAQ Launcher V. " + PAQLV);
		
		//webcheck to check that v.txt in %appdata%/.PAQ matches http://mage-tech.org/pack/PAQv.txt if not return "update needed"
	}
	
	public static void exit() {
		//work on code to clean up PAQ-Temp folder
		System.exit(0);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gui();
				
		boolean gameloop2 = true;
		while (gameloop2 == true){
		
			boolean doLoop = true;
			while (doLoop == true) {
				double x;
				double y;
			
				if (StdDraw.mousePressed()) {
					x = StdDraw.mouseX();
					y = StdDraw.mouseY();
					//Install
					if (x >= .18 - .3 && x <= .18 + .3) {
						if (y >= .25 - .2 && y <= .25 + .2) {
							System.out.println("test");
							Install();
							doLoop = false;
						}
					}
					//Update
					if (x >= .49 - .3 && x <= .49 + .3) {
						if (y >= .25 - .2 && y <= .25 + .2) {
							System.out.println("test2");
							Update();
							doLoop = false;
						}
					}
				//Exit
					if (x >= .8 - .3 && x <= .8 + .3) {
						if (y >= .25 - .2 && y <= .25 + .2) {
							System.out.println("test3");
							exit();
							doLoop = false;
						}
					}
				}

			}
		}
		
		while(StdDraw.mousePressed());
	}

}
