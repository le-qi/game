package game;

import java.util.ArrayList;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * This class represents the player that the game user controls. It
 * has properties consisting of a blue circle image (and thus an x
 * and y coordinate for location), a specific direction it is
 * facing, and an indicator of whether the cheat code is enabled,
 * allowing the player to fire multiple bullets. Getter and setter
 * methods are used to determine and set parameters such as location
 * and direction. Depending on whether the player is cheating, there
 * are also two separate algorithms for firing.
 * 
 * @author Le Qi
 */
public abstract class Player {
	
	private static final int PLAYER_SIZE = 15;
	private static final int PLAYER_SPEED = 10;
	private static final Paint PLAYER_COLOR = Color.BLUE;
	
	private Circle myImage;
	private Direction myDirection;
	
	public Player() {
		int center = GameWorld.getArenaSize() / 2;
		myImage = new Circle(center, center, PLAYER_SIZE, PLAYER_COLOR);
		myDirection = Direction.UP;
	}
	
	public static int getSize() {
		return PLAYER_SIZE;
	}
	
	public boolean isHorizontal() {
		return myDirection == Direction.RIGHT || myDirection == Direction.LEFT;
	}
	
	public double getX() {
		return this.myImage.getCenterX();
	}
	
	public double getY() {
		return this.myImage.getCenterY();
	}
	
	public Direction getDirection() {
		return this.myDirection;
	}
	
	public Circle getImage() {
		return this.myImage;
	}
	
	public void setX(double x) {
		this.myImage.setCenterX(x);
	}

	public void setY(double y) {
		this.myImage.setCenterY(y);
	}
	
	public void setDirection(Direction d) {
		this.myDirection = d;
	}
	
	/**
	 * This function moves the player depending on the arrow key that was
	 * pressed. It also checks to make sure the player does not move out
	 * of the arena.
	 * 
	 * @param keyCode	Key pressed used to move the player.
	 */
	public void move(KeyCode keyCode) {
		if (keyCode == KeyCode.RIGHT) {
			if (getX() < GameWorld.getArenaSize() - getSize())
				setX(getX() + PLAYER_SPEED);
			setDirection(Direction.RIGHT);
		} else if (keyCode == KeyCode.LEFT) {
			if (getX() > Player.getSize())
				setX(getX() - PLAYER_SPEED);
			setDirection(Direction.LEFT);
		} else if (keyCode == KeyCode.UP) {
			if (getY() > Player.getSize())
				setY(getY() - PLAYER_SPEED);
			setDirection(Direction.UP);
		} else if (keyCode == KeyCode.DOWN) {
			if (getY() < GameWorld.getArenaSize() - getSize())
				setY(getY() + PLAYER_SPEED);
			setDirection(Direction.DOWN);
		}
	}
	
	/**
	 * Method invoked in order to fire bullets when the user clicks the
	 * mouse during a game scene.
	 * 
	 * @return	List of all the Bullets fired in one click.
	 */
	abstract public ArrayList<Bullet> fire();

}
