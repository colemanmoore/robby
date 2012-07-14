/**
 * Population.java
 */
import java.util.*;

/**
 * Represents a robot population.
 */
public class Population {

	private ArrayList<Robot> generationList;
	private double averageFitness;
	private int genSize;
	
	/**
	 * Randomly generates a generation.
	 */
	public Population(int size) {
		genSize = size;
		generationList = new ArrayList<Robot>(genSize);
		for (int i=0; i<genSize; i++) {
			generationList.add(new Robot());
		}
		averageFitness = 0.0;
	}
	
	/**
	 * Breeds a new generation from the old one.
	 */
	public void regenerate() {
		
		// sort the generationList by fitness
		ArrayList<Robot> nextGen = new ArrayList<Robot>(genSize);
		
		while (nextGen.size() < genSize) {
			
			Robot dad, mom;
			
			// 90% chance of picking parents from fitter 20% of population
			int index;
			if (Math.random() > 0.1) {
				index = (int) (Math.random() * (generationList.size()/5)) + 4*generationList.size()/5;
				dad = generationList.get(index);
			}
			else {
				index = (int) (Math.random() * (generationList.size()/5));
				dad = generationList.get(index);
			}
			//System.err.println("Dad at generation index (" + index + ")");
			if (Math.random() > 0.1) {
				index = (int) (Math.random() * (generationList.size()/5)) + 4*generationList.size()/5;
				mom = generationList.get(index);
			}
			else {
				index = (int) (Math.random() * (generationList.size()/5));
				mom = generationList.get(index);
			}
			//System.err.println("Mom at generation index (" + index + ")");
			
			// breed two children from two parents
			nextGen.add(new Robot(dad, mom));
			nextGen.add(new Robot(mom, dad));
		}
		generationList = nextGen;
	}
	
	/**
	 * Processes the lives of all robots in the current generation.
	 */
	public void runAllRobots() {
		int counter = 0;
		for (Robot rob : generationList) {
			rob.runLife();
			counter += rob.getFitness();
		}
		averageFitness = counter / generationList.size();
		
		// sort list by fitness of robot, ascending
		Collections.sort(generationList);
	}
	
	public double averageFitness() {
		return averageFitness;
	}
	
	public double topFitness() {
		return generationList.get(generationList.size()-1).getFitness();
	}
	
	public ArrayList<Robot> getGeneration() {
		return generationList;
	}
	
	public Robot getTopRobot() {
		return generationList.get(generationList.size()-1);
	}
	
	public String toString() {
		String result = "";
		for (Robot rob : generationList) {
			result += rob.toString() + "\n";
		}
		return result;
	}
	/*
	public static void main(String args[]) {
		Population pop = new Population();
		
		int numberOfGenerations = 500;
		for (int i=0; i<numberOfGenerations; i++) {
			pop.regenerate();
			pop.runAllRobots();
			System.out.println(pop.toString());
			System.out.print("Average fitness " + pop.averageFitness() + '\n');
		}
	}
	*/
}
