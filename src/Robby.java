
public class Robby {

	private Population currentPop;
	
	static int GENS;
	static int SIZE;
	static int SESSIONS;
	static int MOVES;

	public Robby(int count, int popsize, int sessions, int moves) {
		GENS = count;
		SIZE = popsize;
		SESSIONS = sessions;
		MOVES = moves;
		currentPop = new Population(SIZE);
	}
	
	public void runSimulation() {
		currentPop.runAllRobots();
		for (int i=0; i<GENS; i++) {
			System.out.println("(Gen " + i + ") " + currentPop.topFitness() 
					+ "\t\t" + currentPop.getTopRobot().toString());
			currentPop.regenerate();
			currentPop.runAllRobots();
		}
		System.out.println(currentPop.getTopRobot().toString());
	}
	
	public static void main(String[] args) {
	    if (args.length != 4) { 
	    	System.out.println("Usage: Robby [generations] [size] [sessions] [moves]\n");
	    	return;
	    }
		int gens = Integer.parseInt(args[0]);
		int size = Integer.parseInt(args[1]);
		int sessions = Integer.parseInt(args[2]);
		int moves = Integer.parseInt(args[3]);
		Robby robby = new Robby(gens, size, sessions, moves);
		robby.runSimulation();
	}
	
	

}
