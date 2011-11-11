package github.tpavels.breakout.entities;

import github.tpavels.constants.Constants;
import github.tpavels.constants.Constants.Collision;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;

public class Wall  {

	private static final int COLUMNS = 40;
    private static final int ROWS = 20;
    
    /**
     * It is needed to add a black(field background color) 'borders' around bricks
     */
    private static final int INVISIBLE_BRICK_BORDER = 6;

	private double topGap;
	private double brickHeight;
	private double brickWidth;
	private int columns;
	private int layers;
	private double rightShift;
	private Brick[][] wall = null;

	public Wall() {
		layers = ROWS;
		columns = COLUMNS;
		wall = new Brick[layers][columns];

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		brickHeight = screenSize.getSize().height / (2*layers);
		brickWidth = screenSize.getSize().width / columns/1.2;
		// it is needed to ensure that wall is in the center of screen
		rightShift = (screenSize.getSize().width - brickWidth * columns) / 2;
		// creates gap top bottom border and wall
		topGap = screenSize.getSize().width/10;
	}

	/**
	 * Detects collision with ball and brick
	 * @param ball
	 * @return Collision enumeration of possible types of collisions
	 */
	public Collision collision(Ball ball) {
		double x = ball.getCenterX();
		double y = ball.getCenterY();
		int r = ball.getRadius();
		for (int row = 0; row < layers; row++) {
			for (int col = 0; col < columns; col++) {
				Brick brick = wall[row][col];
				if (brick.isExists()) {
                    double halfWidth  = (brick.getWidth()) / 2;
                    double halfHeight = (brick.getHeight()) / 2;
					double distX = x - brick.getCenterX();
					double distY = y - brick.getCenterY();
					double cornerDistance = Math.pow(Math.abs(distX) - halfWidth,2)+ Math.pow(Math.abs(distY) - halfHeight,2);
					if (-halfWidth <= distX && distX <= halfWidth && -halfHeight <= distY && distY <= halfHeight) {
						brick.desroy();
						return Collision.HORIZONTAL;
					} else if (Math.abs(distX)-1 <= halfWidth && Math.abs(distY)-1 <= halfHeight) {
					    brick.desroy();
					    return Collision.VERTICAL;
					} else if (cornerDistance <= (r*r)) {
					    brick.desroy();
					    return Collision.CORNER;
					}
				}
			}
		}
		return Collision.NONE;
	}

	/**
	 * Create wall and sets bricks color
	 */
	public void create() {
		for (int i = 0; i < layers; i++) {
			for (int j = 0; j < columns; j++) {
				wall[i][j] = new Brick(new Color(255, 211, 155), brickWidth, brickHeight);
			}
		}
	}

	/**
	 * Draws wall
	 * @param gfx2d Graphics2D from image
	 */
	public void display(Graphics2D gfx2d) {
        for (int i = 0; i < wall.length; i++) {
            for (int j = 0; j < wall[i].length; j++) {
                Brick brick = wall[i][j];
                    if (brick.isExists()) {
                    gfx2d.setColor(brick.getColor());
                    double startX = j*brick.getWidth() + rightShift - Constants.BORDER/2;
                    double startY = i*brick.getHeight() + topGap;
                    brick.setCenterXY(startX, startY);
                    gfx2d.fill(new Rectangle2D.Double(startX, startY,
                            brick.getWidth()-Wall.INVISIBLE_BRICK_BORDER, brick.getHeight()-Wall.INVISIBLE_BRICK_BORDER));
                }
            }
        }
    }

    /**
     * Checks if the wall is destroyed or not
     * @return true if the whole wall is destroyed
     */
    public boolean isDestroyed() {
        for (int i = 0; i < layers; i++) {
            for (int j = 0; j < columns; j++) {
                if (wall[i][j].isExists()) {
                    return false;
                }
            }
        }
        return true;
    }
}
