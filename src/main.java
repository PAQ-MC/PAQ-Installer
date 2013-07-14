import java.awt.Dimension;
import java.awt.Toolkit;


public class main {

	

	public static void Gui() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		StdDraw.setCanvasSize(600, 300);
		StdDraw.frame.setLocation(dim.width/2-StdDraw.frame.getSize().width/2, dim.height/2-StdDraw.frame.getSize().height/2 - 130);
		
		StdDraw.picture(0.5, 0.8, "PAQLogo.png");
		StdDraw.picture(0.18, 0.25, "Install1.png", .3,.2 );
		StdDraw.picture(0.49, 0.25, "update1.png", .3,.2 );
		StdDraw.picture(0.8, 0.25, "exit1.png", .3,.2 );
		// StdDraw.
	}
	
	public static void Install() {
		//install code goes here
	}
	
	public static void Update() {
		//Update code goes here
	}
	
	public static void exit() {
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
