package game;

import java.util.ArrayList;

public class CheatPlayer extends Player {
	/**
	 * CheatPlayer is a subclass of the Player class. Objects of the class
	 * will fire three bullets at once.
	 * 
	 * @author Le Qi
	 */
	
	public CheatPlayer() {
		super();
	}
	
	/**
	 * Cheat algorithm fires three bullets, one originating from the
	 * object center and the other two from flanks.
	 * 
	 * @return	List of bullets fired (should have three elements).
	 */
	public ArrayList<Bullet> fire() {
		ArrayList<Bullet> bullets = new ArrayList<Bullet>();
		Bullet b;
		for (int i = -getSize(); i <= getSize(); i += getSize()) {
			if (isHorizontal())
				b = new Bullet(getX(), getY() + i, getDirection());
			else
				b = new Bullet(getX() + i, getY(), getDirection());
			bullets.add(b);
		}
		return bullets;
	}

}
