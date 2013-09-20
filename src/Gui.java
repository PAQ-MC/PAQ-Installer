/*
This work is licensed under the Creative Commons
Attribution-NonCommercial 3.0 Unported License. 
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc/3.0/.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

public class Gui {
	static JTextPane textArea;
	
	public static void main() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		StdDraw.setCanvasSize(600, 300);
		StdDraw.frame.setLocation(dim.width / 2 - StdDraw.frame.getSize().width
				/ 2, dim.height / 2 - StdDraw.frame.getSize().height / 2 - 130);

		StdDraw.picture(0.5, 0.7, "PAQLogo.png");
		StdDraw.picture(0.3, 0.25, "Install1.png", .3, .2);
		//StdDraw.picture(0.49, 0.25, "ForceUpdate1.png", .3, .2);
		StdDraw.picture(0.7, 0.25, "exit1.png", .3, .2);
		StdDraw.picture(0.5, 0.00, "copywrite1.png");
	}

	// console gui
	public static void consolegui() {
		//redirectSystemStreams();
		JFrame frame = new JFrame("Console");
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		textArea = new JTextPane();
		textArea.setPreferredSize(new Dimension(600, 300));
		//JScrollPane scrollPane = new JScrollPane(textArea,
		//		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		//		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textArea.setAutoscrolls(true);
		
		frame.add(new JScrollPane(textArea));
		MessageConsole mc = new MessageConsole(textArea);
		mc.redirectOut(null, System.out);
		mc.redirectErr(Color.RED, System.out);
		
		//frame.add(scrollPane);
		frame.pack();
		frame.setVisible(true);
		main.print("console output redirected", false);
	}

	// update console gui
	private static void updateTextArea(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				//textArea.(text);
				
			
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
}
