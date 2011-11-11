package github.tpavels.breakout.entities;

import github.tpavels.constants.Constants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;

public class Paddle {

	private static final int BOTTON_GAP = 15;
	private static final double SPEED = 1;
	
	/**
	 * A gap between paddle and borders
	 */
	private static final double RIGHT_LEFT_GAP = 5;

	private double centerX;
	private double centerY;
	private Color color;
	private int width;
	private int height;
	private int halfWidth;
	private int halfHeight;

	Dimension screenSize;

	public Paddle() {
		this.color = new Color(255, 127, 36);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		screenSize = toolkit.getScreenSize();

		this.width = screenSize.getSize().width / 8;
		this.halfWidth = width/2;
		this.height = this.width/10;
		this.halfHeight = height/2;
		setCoordinates();
	}

	private void setCoordinates() {
		this.centerX = screenSize.getSize().width/2 ;
		this.centerY = screenSize.getSize().height - halfHeight - BOTTON_GAP;
	}

	/**
	 * Shifts paddle to left
	 */
	public void left() {
		centerX = centerX - SPEED*width/14;
		if ((centerX - (halfWidth)) <= Constants.BORDER) {
			centerX = halfWidth + RIGHT_LEFT_GAP;
		}
	}

	/**
	 * Shifts paddle to right
	 */
	public void right() {
		centerX = centerX + SPEED*width/14;
		final double rightBorder = screenSize.getSize().width - Constants.BORDER;
        if ((centerX + halfWidth) >= (rightBorder - RIGHT_LEFT_GAP)) {
			centerX = rightBorder - halfWidth - Constants.BORDER*2;
		}
	}

	@Override public String toString() {
		return "Paddle: centerX="+centerX+" centerY="+centerY+" height="+height+" width="+width+
				" color="+color;
	}

	/**
	 * Draws paddle
	 * @param gfx2d Graphics2D from image
	 */
	public void display(Graphics2D gfx2d) {
        gfx2d.setColor(color);
        double x = centerX - halfWidth;
        double y = centerY - halfHeight;
        gfx2d.fill(new RoundRectangle2D.Double(x, y, width, height, height, height));
    }


	/**
	 * @return the centerX
	 */
	public double getCenterX() {
		return centerX;
	}

	/**
	 * @return the centerY
	 */
	public double getCenterY() {
		return centerY;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

}
