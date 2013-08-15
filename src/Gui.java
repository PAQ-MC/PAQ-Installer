import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Gui {
	static JTextArea textArea;
	
	public static void main() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		StdDraw.setCanvasSize(600, 300);
		StdDraw.frame.setLocation(dim.width / 2 - StdDraw.frame.getSize().width
				/ 2, dim.height / 2 - StdDraw.frame.getSize().height / 2 - 130);

		StdDraw.picture(0.5, 0.7, "PAQLogo.png");
		StdDraw.picture(0.18, 0.25, "Install1.png", .3, .2);
		StdDraw.picture(0.49, 0.25, "website1.png", .3, .2);
		StdDraw.picture(0.8, 0.25, "exit1.png", .3, .2);
		StdDraw.picture(0.5, 0.00, "copywrite1.png");
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
}
