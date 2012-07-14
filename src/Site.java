/**
 * Site.java
 */

/**
 * A location in a grid world that either is littered with a can or isn't.
 * Also provides information about the sites surrounding it.
 */
public class Site {
	
	static final int EMPTY = 0;
	static final int CAN = 1;
	static final int WALL = 2;
	
	private int x, y;
	private boolean can;
	
	/**
	 * Constructs a new Site, with 50% chance of having a can.
	 * @param xx - the Site's x-coordinate
	 * @param yy - the Site's y-coordinate
	 */
	public Site(int xx, int yy) {
		x = xx;
		y = yy;
		if (Math.random() < 0.5) {
			can = true;
		}
		else {
			can = false;
		}
	}
	
	/**
	 * Returns true if there is a can, false otherwise.
	 * @return the value of <code>can</code>
	 */
	public boolean hasCan() {
		return can;
	}
	
	/**
	 * Remove a can from this site.
	 */
	public void removeCan() {
		can = false;
	}
	
	/**
	 * Checks if a wall is to the north.
	 * @return true if against a northern wall
	 */
	public boolean isAgainstNorthWall() {
		if (y == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Checks if a wall is to the south.
	 * @return true if against a southern wall
	 */
	public boolean isAgainstSouthWall() {
		if (y == World.HEIGHT - 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Checks if a wall is to the east.
	 * @return true if against a eastern wall
	 */
	public boolean isAgainstEastWall() {
		if (x == World.WIDTH - 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Checks if a wall is to the west.
	 * @return true if against a western wall
	 */
	public boolean isAgainstWestWall() {
		if (x == 0) {
			return true;
		}
		else {
			return false;
		}
	}

}
