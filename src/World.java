/**
 * World.java
 */

/**
 * Represents a two-dimensional world of Sites, some of which are littered with cans.
 */
public class World {
	
	// publicly available action-constants
	static final int move_north = 0;
	static final int move_east = 1;
	static final int move_south = 2;
	static final int move_west = 3;
	static final int stay_put = 4;
	static final int pickup_can = 5;
	
	// World properties
	static final int HEIGHT = 10;
	static final int WIDTH = 10;
	
	// rewards and punishments
	private final int WALL_FINE = 5;
	private final int CAN_FINE = 1;
	private final int CAN_REWARD = 10;
	
	private Site current;
	private int curX;
	private int curY;
	private Site[][] grid;
	
	/**
	 * Constructs a new World of <code>HEIGHT</code> by <code>WIDTH</code> Sites.
	 */
	public World() {
		grid = new Site[HEIGHT][WIDTH];
		
		for (int i=0; i<HEIGHT; i++) {
			for (int j=0; j<WIDTH; j++) {
				grid[i][j] = new Site(j, i);
			}
		}
		setCurrentSite(0,0);
	}
	
	/**
	 * Returns the current Site.
	 */
	public Site getCurrentSite() {
		return current;
	}
	
	/**
	 * Returns the local details of the current location - the situation.
	 */
	public int[] getCurrentSituation() {
		int[] situation = new int[5];
		
		// observe cell to the north
		if (current.isAgainstNorthWall()) {
			situation[0] = Site.WALL;
		}
		else {
			if (grid[curY - 1][curX].hasCan()) {
				situation[0] = Site.CAN;
			}
			else {
				situation[0] = Site.EMPTY;
			}
		}
		
		// observe cell to the south
		if (current.isAgainstSouthWall()) {
			situation[1] = Site.WALL;
		}
		else {
			if (grid[curY + 1][curX].hasCan()) {
				situation[1] = Site.CAN;
			}
			else {
				situation[2] = Site.EMPTY;
			}
		}
		
		// observe cell to the east
		if (current.isAgainstEastWall()) {
			situation[2] = Site.WALL;
		}
		else {
			if (grid[curY][curX + 1].hasCan()) {
				situation[2] = Site.CAN;
			}
			else {
				situation[2] = Site.EMPTY;
			}
		}
		
		// observe cell to the west
		if (current.isAgainstWestWall()) {
			situation[3] = Site.WALL;
		}
		else {
			if (grid[curY][curX - 1].hasCan()) {
				situation[3] = Site.CAN;
			}
			else {
				situation[3] = Site.EMPTY;
			}
		}
		
		// observe current cell
		if (current.hasCan()) {
			situation[4] = Site.CAN;
		}
		else {
			situation[4] = Site.EMPTY;
		}
		
		return situation;
	}
	
	/**
	 * Perform an action.
	 * @param action - an action constant
	 */
	public int performAction(int action) {
		int result = 0;
		
		switch (action) {
		case move_north: result += moveNorth(); break;
		case move_east: result += moveEast(); break;
		case move_south: result += moveSouth(); break;
		case move_west: result += moveWest(); break;
		case stay_put: result += stayPut(); break;
		case pickup_can: result += pickupCan(); break;
		default: stayPut();
		}
		
		return result;
	}

	private int moveNorth() {
		int penalty = 0;

		if (current.isAgainstNorthWall()) {
			penalty -= WALL_FINE;
		}
		else {
			setCurrentSite(curX, curY - 1);
		}
		return penalty;
	}

	private int moveEast() {
		int penalty = 0;
		
		if (current.isAgainstEastWall()) {
			penalty -= WALL_FINE;
		}
		else {
			setCurrentSite(curX + 1, curY);
		}
		return penalty;
	}
	
	private int moveSouth() {
		int penalty = 0;
		
		if (current.isAgainstSouthWall()) {
			penalty -= WALL_FINE;
		}
		else {
			setCurrentSite(curX, curY + 1);
		}
		return penalty;
	}
	
	private int moveWest() {
		int penalty = 0;
		
		if (current.isAgainstWestWall()) {
			penalty -= WALL_FINE;
		}
		else {
			setCurrentSite(curX - 1, curY);
		}
		return penalty;
	}
	
	private int stayPut() {
		return 0;
	}
	
	private int pickupCan() {
		int penalty = 0;
		
		if (current.hasCan()) {
			current.removeCan();
			penalty += CAN_REWARD;
			//System.err.println("can reward");
		}
		else {
			penalty -= CAN_FINE;
			//System.err.println("can fine");
		}
		return penalty;
	}
	
	private void setCurrentSite(int xx, int yy) {
		curX = xx;
		curY = yy;
		current = grid[curY][curX];
	}
	
	public String toString() {
		int withCan = 0;
		String result = "";
		for (int i=0; i<HEIGHT; i++) {
			for (int j=0; j<WIDTH; j++) {
				if (grid[i][j].hasCan()) {
					result += ".";
					withCan++;
				}
				else {
					result += " ";
				}
			}
			result += "\n";
		}
		return result;
	}
	
}
