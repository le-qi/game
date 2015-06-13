package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bullet {
	/**
	 * The class represents a bullet object, with a specific rectangle
	 * shape on the image, and also firing at a specific bullet velocity
	 * in a specific direction. Getter and setter methods can be used to
	 * obtain bullet velocities and locations from the GameWorld class.
	 * 
	 * @author Le Qi
	 */
	
	private static final int BULLET_SPEED = 10;
	private static final int BULLET_LENGTH = 10;
	private static final int BULLET_WIDTH = 2;
	
	private int myDirection;
	private int myXSpeed;
	private int myYSpeed;
	private Rectangle myImage;
	
	public Bullet(double x, double y, Direction d) {
		if (d == Direction.RIGHT) {
			myXSpeed = Bullet.BULLET_SPEED;
			myYSpeed = 0;
		}
		else if (d == Direction.LEFT) {
			myXSpeed = -Bullet.BULLET_SPEED;
			myYSpeed = 0;
		}
		else if (d == Direction.UP) {
			myXSpeed = 0;
			myYSpeed = -Bullet.BULLET_SPEED;
		}
		else if (d == Direction.DOWN) {
			myXSpeed = 0;
			myYSpeed = Bullet.BULLET_SPEED;
		}
		
		if (d == Direction.RIGHT || d == Direction.LEFT)
			this.myImage = new Rectangle(x, y, BULLET_LENGTH, BULLET_WIDTH);
		else
			this.myImage = new Rectangle(x, y, BULLET_WIDTH, BULLET_LENGTH);
		
		this.myImage.setFill(Color.YELLOW);
	}
	
	public int getDirection() {
		return this.myDirection;
	}
	
	public double getXSpeed() {
		return this.myXSpeed;
	}
	
	public double getYSpeed() {
		return this.myYSpeed;
	}
	
	public double getX() {
		return this.myImage.getX();
	}
	
	public double getY() {
		return this.myImage.getY();
	}
	
	public Rectangle getImage() {
		return this.myImage;
	}
	
	public void setX(double x) {
		this.myImage.setX(x);
	}

	public void setY(double y) {
		this.myImage.setY(y);
	}
	
	/**
	 * Repositions the bullet for subsequent frames.
	 */
	public void move() {
		setX(getX() + getXSpeed());
		setY(getY() + getYSpeed());
	}
	
}
