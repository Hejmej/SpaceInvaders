
public abstract class Invader {

	int x;
	int y;
	int health;
	
	public Invader(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Accessor method
	 * gets the current x-coordinate
	 * @returns x
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * Accessor method
	 * gets the current y-coordinate
	 * @returns y
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * Mutator method
	 * sets the x-coordinate to a new value
	 * @param x2
	 */
	public void setX(int x2){
		x = x2;
	}
	
	/**
	 * Mutator method
	 * sets the y-coordinate to a new value
	 * @param y2
	 */
	public void setY(int y2){
		x = y2;
	}
	
	/**
	 * Mutator method
	 * subtracts the health of an enemy if the enemy is hit
	 * @param hit
	 */
	public void decreaseHealth(boolean hit){
		health --;
	}
	
	/**
	 * accessor method
	 * returns the health of the invader
	 * @return health
	 */
	public int getHealth(){
		return health;
	}
}
