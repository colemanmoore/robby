/**
 * Robot.java
 */
import java.util.*;

/**
 * A rule based robot designed to clean up hypothetical messes.
 */
public class Robot implements Comparable<Robot> {
	
	private final int situation_count = 243;
	private final double mutation_prob = 0.1;

	private double fitness; // initialized in runLife()
	private int[][][][][] strategyTable;
	
	/**
	 * Generates a robot with a random strategy.
	 */
	public Robot() {
		
		strategyTable = new int[3][3][3][3][3];
		for (int north=0; north<3; north++) {
			for (int south=0; south<3; south++) {
				for (int east=0; east<3; east++) {
					for (int west=0; west<3; west++) {
						for (int here=0; here<3; here++) {
							strategyTable[north][south][east][west][here] = (int)(Math.random()*6);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Breeds a robot from the strategy DNA of two Robots.
	 * @param dad
	 * @param mom
	 */
	public Robot(Robot dad, Robot mom) {
		strategyTable = new int[3][3][3][3][3];
		ArrayList<Integer> list = new ArrayList<Integer>(situation_count);
		
		double random = Math.random() * situation_count;
		int splitPoint = (int) Math.floor(random);
		ArrayList<Integer> dadsStrategy = new ArrayList<Integer>(dad.getStrategyBetween(0, splitPoint));
		ArrayList<Integer> momsStrategy = new ArrayList<Integer>(mom.getStrategyBetween(splitPoint, situation_count));
		
		// take dad's DNA
		for (Integer i : dadsStrategy) {
			
			// provide chance for mutation
			if (Math.random() < mutation_prob) {
				list.add( (int) (Math.random()*6) );
			}
			else {
				list.add(i);
			}
		}
		// take mom's DNA
		for (Integer i : momsStrategy) {
			
			// provide chance for mutation
			if (Math.random() < mutation_prob) {
				list.add( (int) (Math.random()*6) );
			}
			else {
				list.add(i);
			}
		}
		listToTable(list);
	}
	
	/**
	 * Rolls an ArrayList (of a strategy) into a five-dimensional table
	 * @param aList
	 */
	private void listToTable(ArrayList<Integer> aList) {
		int i = 0;
		for (int north=0; north<3; north++) {
			for (int south=0; south<3; south++) {
				for (int east=0; east<3; east++) {
					for (int west=0; west<3; west++) {
						for (int here=0; here<3; here++) {
							strategyTable[north][south][east][west][here] = aList.get(i++);
						}
					}
				}
			}
		}
	}
	
	/**
	 * The Robot lives its life of cleaning.
	 * Performs cleaning function on <code>session_count</code> World objects.
	 */
	public void runLife() {
		fitness = 0.0;
		
		// create random Worlds and clean them
		for (int i=0; i<Robby.SESSIONS; i++) {
			fitness += cleanWorld(new World());
		}
		
		// average fitness
		fitness /= (double) Robby.SESSIONS;
	}
	
	/**
	 * The Robot does a cleaning session on a World object and returns its fitness for the session.
	 */
	public int cleanWorld(World aWorld) {
		int fitnessResult = 0;
		World world = aWorld;
		
		for (int round=0; round<Robby.MOVES; round++) {
			
			// get the current situation
			int[] situ = world.getCurrentSituation();
			
			// look up the Site's attributes in strategy table
			int action = strategyTable[situ[0]][situ[1]][situ[2]][situ[3]][situ[4]];
			//System.err.println("n:"+situ[0]+"s:"+situ[1]+"e:"+situ[2]+"w:"+situ[3]+"h:"+situ[4]);
			//System.err.println("action: " + action);
			
			// perform action
			fitnessResult += world.performAction(action);
		}
		
		return fitnessResult;
	}

	/**
	 * Retrieves a Robot's strategy string between two indices.
	 */
	private ArrayList<Integer> getStrategyBetween(int start, int end) {
		
		ArrayList<Integer> strategyList = new ArrayList<Integer>(situation_count);
		
		// unravel the strategyTable
		for (int north=0; north<3; north++) {
			for (int south=0; south<3; south++) {
				for (int east=0; east<3; east++) {
					for (int west=0; west<3; west++) {
						for (int here=0; here<3; here++) {
							strategyList.add(strategyTable[north][south][east][west][here]);
						}
					}
				}
			}
		}
		
		ArrayList<Integer> subList = new ArrayList<Integer>(end - start);
		for (int i=start; i<end; i++) {
			subList.add(strategyList.get(i));
		}
		return subList;
	}
	
	/**
	 * Implements the compareTo interface so that lower-fitness robots are sorted before higher-fitness ones.
	 */
	public int compareTo(Robot otherRobot) {
		return (int) Math.signum(fitness - otherRobot.getFitness());
	}
	
	public double getFitness() {
		return fitness;
	}
	
	/**
	 * Returns a string of the Robot's DNA, or unraveled strategy table.
	 */
	public String toString() {
		String result = "";
		for (int north=0; north<3; north++) {
			for (int south=0; south<3; south++) {
				for (int east=0; east<3; east++) {
					for (int west=0; west<3; west++) {
						for (int here=0; here<3; here++) {
							result += strategyTable[north][south][east][west][here];
						}
					}
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		/*
		Robot dad = new Robot();
		System.out.println(dad.toString());
		Robot mom = new Robot();
		System.out.println(mom.toString());
		
		Robot kid = new Robot(dad, mom);
		System.out.println(kid.toString());
		*/
		/*
		World w = new World();
		System.out.println(w.toString());
		Robot r = new Robot();
		System.out.println(r.toString());
		int fitness = r.cleanWorld(w);
		System.out.println(w.toString());
		System.out.println(fitness);
		*/
	}
	
}
