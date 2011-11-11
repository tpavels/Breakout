package github.tpavels.breakout;

import github.tpavels.breakout.entities.Ball;
import github.tpavels.breakout.entities.Paddle;
import github.tpavels.breakout.entities.Wall;
import github.tpavels.breakout.utils.FrameUtils;
import github.tpavels.breakout.utils.KeyHandler;
import github.tpavels.breakout.utils.TextUtils;
import github.tpavels.constants.Constants;
import github.tpavels.constants.Constants.Key;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

public class GameField extends JPanel implements Runnable {

	private static final long serialVersionUID = -6384464023813347210L;
	private static final String FONT = "Arial";

	private boolean running = false;
	private Wall wall = null;
	private Paddle paddle = null;
	private Ball ball = null;
	private Score score = null;
	private KeyHandler keyHandler = null;
	private Image image = null;
	private TextUtils drawText = null;

	public GameField(JPanel container, Dimension frameSize) {
		FrameUtils.addUIComponent(container, this, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new int[] {0,0,1,1}, new Insets(Constants.BORDER, Constants.BORDER, 0, Constants.BORDER));
		setFieldSizes(frameSize);

		wall = new Wall();
		paddle = new Paddle();
		score = new Score();
		drawText = new TextUtils();
		keyHandler = new KeyHandler(this);
		ball = new Ball(paddle, wall, score);


		setBackground(Color.BLACK);
		setFocusable(true);
		requestFocus();
		addKeyListener(keyHandler);

		wall.create();
	}

	public void addNotify(){
		super.addNotify();
		startGame();
	}

	@Override public void paint(Graphics g) {
		paintComponent(g);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, null);
		}
	}

	public void run() {
		long period = 17;
		long beforeTime, timeDiff, sleepTime;
		beforeTime = System.currentTimeMillis();
		running = true;
		while(running) {
			gameUpdate();
			gameRender();
			paintScreen();

			// adjust sleep time
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleepTime = period - timeDiff;
			if (sleepTime <= 0) {
				sleepTime = 2;
			}
			try {
				TimeUnit.MILLISECONDS.sleep(sleepTime);
			}
			catch(InterruptedException e){
				System.err.println("sleep was interrupted");
			}
			beforeTime = System.currentTimeMillis();
		}
	}

	public void stopGame() {
		running = false;
	}

	private Graphics2D gfx2d = null;

	private boolean win = false;
	private boolean gameOver = false;
	private boolean pause = false;
	private int iWidth = 1;
	private int iHeight = 1;

	private void gameRender() {
		if (image == null){
			image = createImage(iWidth, iHeight);
			if (image == null) {
				System.out.println("image is null");
				return;
			} else {
				gfx2d = (Graphics2D) image.getGraphics();
			}
		}
		gfx2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		displayBackground();
		paddle.display(gfx2d);
		wall.display(gfx2d);
		ball.display(gfx2d);
		drawText.displayScore(gfx2d, score);

		if (pause) {
			drawText.displayPause(gfx2d, this.getSize());
		}
		if (gameOver) {
			drawText.displayGameOver(gfx2d, score, this.getSize());
			stopGame();
		}
		if (win) {
			drawText.displayWin(gfx2d, score, this.getSize());
			stopGame();
		}

		//renderFPS();
	}


	private boolean startPosition = true;

	/**
	 * @return the startPosition
	 */
	public boolean isStartPosition() {
		return startPosition;
	}

	private void gameUpdate(){
		Key keyPressed = keyHandler.getKeyPressed();

		// movement keys
		if (!gameOver && !pause){
			switch (keyPressed) {
			case LEFT:
				paddle.left();
				break;
			case RIGHT:
				paddle.right();
				break;
			case SPACE:
				if (startPosition) {
					ball.start();
					startPosition = false;
				}
				keyHandler.resetKey(); // not sure it's needed here
				break;
			default:
				// do nothing
				break;
			}

			ball.move();

			// game over
			if (ball.offScreen()){
				gameOver = true;
			}

			// win
			if (wall.isDestroyed()){
				win = true;
			}
		}
	}

	private void paintScreen() {
		Graphics g;
		try {
			g = this.getGraphics();
			if ((g != null) && (image != null)) {
				g.drawImage(image, 0, 0, null);
			}
			g.dispose();
		} catch (Exception e) {
			System.err.println("Graphics context error: " + e);
		}
	}

	public void pause() {
		if (pause) {
			pause = false;
		} else {
			pause = true;
		}
	}

	private void displayBackground() {
		gfx2d.setColor(Color.BLACK);
		gfx2d.fill(new Rectangle2D.Double(0, 0, iWidth, iHeight));
	}

	private long nextSecond = System.currentTimeMillis() + 1000;
	private int frameInLastSecond = 25;
	private int framesInCurrentSecond = 0;
	private void displayFPS() {
		long currentTime = System.currentTimeMillis();
		if (currentTime > nextSecond) {
			nextSecond += 1000;
			frameInLastSecond = framesInCurrentSecond;
			framesInCurrentSecond = 0;
		}
		framesInCurrentSecond++;
		gfx2d.setColor(Color.WHITE);
		Font font = new Font(FONT, Font.PLAIN, 20);
		gfx2d.setFont(font);
		gfx2d.drawString(frameInLastSecond + " fps", this.getSize().width - 60, 20);
	}

	private void setFieldSizes(Dimension frameSize) {
		iWidth = frameSize.getSize().width - Constants.BORDER*2;
		iHeight = frameSize.getSize().height - Constants.BORDER*2;
	}

	private Thread animator;
	private void startGame() {
		if (animator == null || !running) {
			animator = new Thread(this);
			animator.start();
		}
	}

}
