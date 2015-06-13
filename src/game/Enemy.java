package game;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public abstract class Enemy {
	/**
	 * The class represents a single enemy, consisting of red circle shape
	 * with a specific location in the arena, as well as values for the
	 * x-velocity and y-velocity of the circle. Speeds and locations are
	 * adjusted using setter and getter methods.
	 * 
	 * @author Le Qi
	 */

	private static final int ENEMY_SIZE = 10;
	private static final Paint ENEMY_COLOR = Color.RED;
	private static final double ENEMY_SPEED = 1.5;

	private double myXSpeed;
	private double myYSpeed;
	private Circle myImage;
	private Random myGenerator = new Random();

	public Enemy() {
		myImage = new Circle(generateLocation(), generateLocation(), ENEMY_SIZE, ENEMY_COLOR);
	}
	
	public static double getEnemySpeed() {
		return ENEMY_SPEED;
	}

	public double getXSpeed() {
		return this.myXSpeed;
	}

	public double getYSpeed() {
		return this.myYSpeed;
	}

	public double getX() {
		return this.myImage.getCenterX();
	}

	public double getY() {
		return this.myImage.getCenterY();
	}

	public Circle getImage() {
		return this.myImage;
	}

	public Random getRandomGenerator() {
		return this.myGenerator;
	}

	public void setX(double x) {
		this.myImage.setCenterX(x);
	}

	public void setY(double y) {
		this.myImage.setCenterY(y);
	}

	public void setXSpeed(double x) {
		this.myXSpeed = x;
	}

	public void setYSpeed(double y) {
		this.myYSpeed = y;
	}

	/**
	 * Repositions the enemy for a subsequent frame.
	 */
	public void move() {
		setX(getX() + getXSpeed());
		setY(getY() + getYSpeed());
	}
	
	/**
	 * Generates a single x or y coordinate of a location where the enemy
	 * is to appear. Enemies should appear randomly in the arena at a
	 * specific distance (4/5 the arena size) away from the player at the
	 * beginning of the game.
	 * 
	 * @return	Random coordinate where enemy should appear.
	 */
	private int generateLocation() {
		int location;
		do {
			location = myGenerator.nextInt(GameWorld.getArenaSize());
		} while (location > GameWorld.getArenaSize() / 5 && location < GameWorld.getArenaSize() * 4 / 5);
		return location;
	}

	/**
	 * This abstract method updates the speed of the enemy. Both the
	 * BasicEnemy and SmartEnemy classes inherit the method, and both have
	 * different algorithms for setting the incremental speed.
	 * 
	 * @param p	The player unit the enemies are trying to attack.
	 */
	abstract void updateSpeed(Player p);

}