package github.tpavels.breakout;

import github.tpavels.constants.Constants;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JPanel implements Constants {

	/**
	 *
	 */
	private static final long serialVersionUID = -1818321739720685072L;

	public void initialize() {
		final JPanel containerPanel = new JPanel(new GridBagLayout());

		JFrame frame = new JFrame(FRAME_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(false);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		createField(containerPanel, screenSize);

		frame.setContentPane(containerPanel);
		frame.setVisible(true);
	}

	private void createField(JPanel container, Dimension dimension) {
		GameField fieldPanel = new GameField(container, dimension);
	}
}
