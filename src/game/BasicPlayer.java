package game;

import java.util.ArrayList;

public class BasicPlayer extends Player {
	/**
	 * The BasicPlayer class is a subclass of the Player class. A
	 * BasicPlayer only shoots one bullet at a time.
	 * 
	 * @author Le Qi
	 */
	
	public BasicPlayer() {
		super();
	}
	
	/**
	 * Non cheating algorithm fires one simple bullet.
	 * 
	 * @return	List of bullets fired (should have one element).
	 */
	public ArrayList<Bullet> fire() {
		ArrayList<Bullet> bullets = new ArrayList<Bullet>();
		Bullet b = new Bullet(getX(), getY(), getDirection());
		bullets.add(b);
		return bullets;
	}

}
