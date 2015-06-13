package game;

public class BasicEnemy extends Enemy {
	/**
	 * The BasicEnemy is a subclass of the Enemy class. The enemies move
	 * by initializing an initial speed in a diagonal direction and
	 * bouncing off the edges of the arena.
	 * 
	 * @author Le Qi
	 */

	public BasicEnemy() {
		super();
		setXSpeed(getRandomGenerator().nextInt(1)
				* 2 * getEnemySpeed() - getEnemySpeed());
		setYSpeed(getRandomGenerator().nextInt(1)
				* 2 * getEnemySpeed() - getEnemySpeed());
	}

	/**
	 * Algorithm for determining the speed of the basic enemies. In the
	 * event of an arena edge collision, the speed of the corresponding
	 * coordinate changes sign and flips the enemy around, reversing its
	 * direction.
	 * 
	 * @param	p	Player targeted by enemies.
	 */
	void updateSpeed(Player p) {
		if (this.getX() >= GameWorld.getArenaSize() || this.getX() <= 0)
			this.setXSpeed(this.getXSpeed() * -1);
		if (this.getY() >= GameWorld.getArenaSize() || this.getY() <= 0)
			this.setYSpeed(this.getYSpeed() * -1);
	}

}
