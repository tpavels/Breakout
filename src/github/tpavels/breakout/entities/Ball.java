package github.tpavels.breakout.entities;

import github.tpavels.breakout.Score;
import github.tpavels.constants.Constants;
import github.tpavels.constants.Constants.Collision;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Ball {

	private static final Color BALL_COLOR = Color.WHITE;
    private static final int MAX_SPEED = 6;

	private double centerX, centerY;
	private double vX, vY;
	private int radius;

	private boolean isGlued;

	private Dimension screenSize;
	private Color color;
	private Paddle paddle;
	private Score score;
	private Wall wall;

	public Ball(Paddle paddle, Wall wall, Score score) {
		this.color = BALL_COLOR;
		this.isGlued = true;
		this.vY = -MAX_SPEED;
		this.vX = 0;

		this.paddle = paddle;
		this.wall = wall;
		this.score = score;

		setDefaultRadius();
		setDefaultCoordinates();
	}

    /**
     * Draw ball
     * @param gfx2d Graphics2D of field image
     */
    public void display(Graphics2D gfx2d) {
        gfx2d.setColor(color);
        double x = centerX - radius;
        double y = centerY - radius;
        gfx2d.fill(new Ellipse2D.Double(x, y, radius*2, radius*2));
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
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * @return the vX
	 */
	public double getvX() {
		return vX;
	}

	/**
	 * @return the vY
	 */
	public double getvY() {
		return vY;
	}

	/**
	 * Move ball on the field, then check if there is a collision with Paddle, Border or Wall(brick),
	 */
	public void move() {
		if (isGlued) {
			this.centerX = paddle.getCenterX();
			return;
		}
		centerX += vX;
		centerY += vY;
		bounceOfPaddle();
		bounceOfBorder();
		bounceOfWall();
	}

	/**
	 * @return true if ball is off screen (not visible)
	 */
	public boolean offScreen() {
		if  ((centerY + radius) > (screenSize.getSize().height)) {
			return true;
		} else {
			return false;
		}
	}

    /**
     * Takes off the ball from paddle
     */
    public void start() {
		isGlued = false;
	}

	@Override public String toString() {
		return "Ball: centerX="+centerX+" centerY="+centerY+" radius="+radius+" color="+color;
	}

	private void bounceOfBorder() {
	    if ((centerY - radius) <= 0) { // top
	        vY = Math.abs(vY);
	        score.resetMultipier();
	    } else if ((centerX - radius) <= 0) { // right
	        vX = Math.abs(vX);
	        score.resetMultipier();
	    } else if ((centerX + radius) >= screenSize.getSize().width - Constants.BORDER - radius) { // left
	        vX = Math.abs(vX) * -1;
	        score.resetMultipier();
	    }
//	    } else if ((centerY + radius) >= screenSize.getSize().height - Constants.BORDER - radius) { // bottom
//	        vY = Math.abs(vY) * -1;
//	    }
	}

	private void bounceOfPaddle() {
		double distX = Math.abs(centerX - paddle.getCenterX());
		double distY = Math.abs(centerY - paddle.getCenterY());
		if ((distX <= paddle.getWidth()/2+radius && distY <= paddle.getHeight()/2+radius)){
		    score.resetMultipier();
			vY = -vY;
			centerY = paddle.getCenterY() - paddle.getHeight();
			// it is more interesting if the ball will bounce from paddle different way
			if (vX < 0){
				vX = (distX % MAX_SPEED + 1) * -1;
			} else {
				vX = (distX % MAX_SPEED + 1);
			}
		}
		double cornerDistance = Math.pow(distX - paddle.getWidth()/2,2) + Math.pow(distY - paddle.getHeight()/2,2);
		if (cornerDistance <= (radius*radius)) {
		    score.resetMultipier();
			centerY = paddle.getCenterY() - paddle.getHeight();
			// rare and not tested
			if (vX < 0){
				vX = -5;
			} else {
				vX = 5;
			}
		}
	}

	private void bounceOfWall() {
	    Collision collision = wall.collision(this);
	    Random rYDirection = new Random();
	    Random rXDirection = new Random();
	    switch (collision) {
	        case VERTICAL:
	            vX = -vX;
	            score.incrementScore();
	            break;
	        case HORIZONTAL:
	            vY = -vY;
	            score.incrementScore();
	            break;
	        case CORNER:
	            //TODO elaborate
	        	boolean bY = rYDirection.nextBoolean();
	        	boolean bX = rXDirection.nextBoolean();
	        	//randomize behavior
	        	vY = vY * (bY ? -1 : 1);
	            vX = -vX * (bX ? -1 : 1);
	            score.incrementScore();
	            break;
	        case NONE:
	            // do nothing
	            break;
	        default:
	            System.err.println("ERROR - bounceOfWall: incorrect collision type");
	            break;
	    }
	}

	private void setDefaultCoordinates() {
        this.centerX = paddle.getCenterX();
        this.centerY = paddle.getCenterY()-paddle.getHeight()/2-radius;
    }

	private void setDefaultRadius() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
		this.screenSize = toolkit.getScreenSize();
		this.radius = screenSize.getSize().width / 250;
    }

}
