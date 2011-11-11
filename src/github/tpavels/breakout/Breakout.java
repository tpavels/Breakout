package github.tpavels.breakout;

import javax.swing.SwingUtilities;


public class Breakout {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GameFrame game = new GameFrame();
				game.initialize();
			}
		});
	}
}
