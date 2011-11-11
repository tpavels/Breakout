package github.tpavels.breakout.entities;

import java.awt.Color;

/**
 * @author pavel
 *
 */
public class Brick  {

	private Color color = null;
	private int centerX = 0;
	private int centerY = 0;
	private double width = 0;
	private double height = 0;
	private boolean exists = false; 


	public Brick(Color color, double width, double height) {
		this.color = color;
		this.width = width;
		this.height = height;
		exists = true;
	}

	/**
	 * Sets brick center coordinates
	 * @param startX x 
	 * @param startY y
	 */
	public void setCenterXY(double startX, double startY) {
		centerX = (int) (startX + width/2);
		centerY = (int) (startY + height/2);
	}

	/**
	 * @return true if brick is visible
	 */
	public boolean isExists() {
		return exists;
	}

	/**
	 * Destroy brick from wall, makes it invisible
	 */
	public void desroy() {
		exists = false;
	}

	/**
	 * @return brick center coordinate x value
	 */
	public int getCenterX() {
		return centerX;
	}

	/**
	 * @return brick center coordinate y value
	 */
	public int getCenterY() {
		return centerY;
	}

	public String toString() {
		return "Brick: centerX="+centerX+" centerY="+centerY+" height="+height+" width="+width+" color="+color;
	}

	/**
	 * @return brick width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @return brick height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @return brick color
	 */
	public Color getColor() {
		return color;
	}



}
