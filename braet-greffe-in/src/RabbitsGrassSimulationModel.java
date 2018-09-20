import java.util.ArrayList;

import uchicago.src.sim.engine.Schedule;
import uchicago.src.sim.engine.SimInit;
import uchicago.src.sim.engine.SimModelImpl;
import uchicago.src.sim.gui.DisplaySurface;

/**
 * Class that implements the simulation model for the rabbits grass
 * simulation.  This is the first class which needs to be setup in
 * order to run Repast simulation. It manages the entire RePast
 * environment and the simulation.
 *
 * @author 
 */


public class RabbitsGrassSimulationModel extends SimModelImpl {		
	private static final int GRIDSIZE = 20;
	private static final int NUMRABBITS = 20;
	private static final int BIRTHTHRESHOLD = 10;
	private static final double GRASSGROWRATE = 0.005;
	private static final int GRASSENERGY = 5;
	private static final int MOVINGCOST = 1;
	
	
	private int gridSize = GRIDSIZE; // TODO: write in report we chose square because rectangles are not interesting 
	private int initNumberRabbits = NUMRABBITS; // TODO: should be smaller than gridSize^2
	private int birthTreshold = BIRTHTHRESHOLD;
	private double grassGrowthRate = GRASSGROWRATE;
	
	private int grassEnergy = GRASSENERGY; // TODO: decide if they are variables or not, constants atm
	private int movingCost = MOVINGCOST;
	
	private Schedule schedule;
	
	private RabbitsGrassSimulationSpace rgsSpace;
	
	private ArrayList<RabbitsGrassSimulationAgent> rabbitList;

	private DisplaySurface displaySurf;
	
	public String getName() {
		return "Rabbits and Grass";
	}
	
	public void setup() {
		System.out.println("Running setup");
		rgsSpace = null;
		rabbitList = new ArrayList<RabbitsGrassSimulationAgent>();
		schedule = new Schedule(1);
		
		if (displaySurf != null) {
			displaySurf.dispose();
		}
		
		displaySurf = null;
		
		displaySurf = new DisplaySurface(this, "Carry Drop Model Window 1");
		
		registerDisplaySurface("Carry Drop Model Windows 1", displaySurf);
	}
	

	public void begin() {
		buildModel();
	    buildSchedule();
	    buildDisplay();
	}

	public void buildModel() {
		System.out.println("Running BuildModel");
		rgsSpace = new RabbitsGrassSimulationSpace(gridSize, initNumberRabbits);
	}
	
	public void buildSchedule() {
		System.out.println("Running BuildSchedule");
	}
	
	public void buildDisplay() {
		System.out.println("Running BuildDisplay");
	}

	public String[] getInitParam() {
		String[] initParams = {"GridSize", "InitNumberRabbits", "BirthTreshold", "GrassGrowthRate"};
		return initParams;
	}

	

	public Schedule getSchedule() {
		return schedule;
	}

	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	public int getInitNumberRabbits() {
		return initNumberRabbits;
	}

	public void setInitNumberRabbits(int initNumberRabbits) {
		this.initNumberRabbits = initNumberRabbits;
	}

	public int getBirthTreshold() {
		return birthTreshold;
	}

	public void setBirthTreshold(int birthTreshold) {
		this.birthTreshold = birthTreshold;
	}

	public double getGrassGrowthRate() {
		return grassGrowthRate;
	}

	public void setGrassGrowthRate(double grassGrowthRate) {
		this.grassGrowthRate = grassGrowthRate;
	}
	
	public static void main(String[] args) {
	    SimInit init = new SimInit();
	    RabbitsGrassSimulationModel model = new RabbitsGrassSimulationModel();
	    init.loadModel(model, "", false);
	}
}
