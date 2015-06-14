package game;

/**
 * The SmartEnemy class is a subclass of the Enemy class. Enemies
 * follow the player throughout the map.
 * 
 * @author Le Qi
 */
public class SmartEnemy extends Enemy {

	public SmartEnemy() {
		super();
		setXSpeed(0);
		setYSpeed(0);
	}

	/**
	 * Calculates new velocities for the advanced enemies that follow the
	 * player. The speeds are always so that the enemy is oriented towards
	 * the player.
	 * 
	 * @param	p	Player targeted by enemies.
	 */
	void updateSpeed(Player p) {
		double xDist = p.getX() - this.getX();
		double yDist = p.getY() - this.getY();
		double totalDist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
		this.setXSpeed(xDist / totalDist * getEnemySpeed());
		this.setYSpeed(yDist / totalDist * getEnemySpeed());	
	}

}
