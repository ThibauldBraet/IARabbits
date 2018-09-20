import uchicago.src.sim.engine.Schedule;
import uchicago.src.sim.engine.SimInit;
import uchicago.src.sim.engine.SimModelImpl;

/**
 * Class that implements the simulation model for the rabbits grass
 * simulation.  This is the first class which needs to be setup in
 * order to run Repast simulation. It manages the entire RePast
 * environment and the simulation.
 *
 * @author 
 */


public class RabbitsGrassSimulationModel extends SimModelImpl {		
	private int gridSize = 20; // TODO: write in report we chose square because rectangles are not interesting 
	private int initNumberRabbits = 20; // TODO: should be smaller than gridSize^2
	private int birthTreshold = 10;
	private double grassGrowthRate = 0.005;
	
	private int grassEnergy = 5; // TODO: decide if they are variables or not, constants atm
	private int movingCost = 1;
	
	private Schedule schedule;
	private RabbitsGrassSimulationSpace rgsSpace;
	
	
	public static void main(String[] args) {
	    SimInit init = new SimInit();
	    RabbitsGrassSimulationModel model = new RabbitsGrassSimulationModel();
	    init.loadModel(model, "", false);
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

	public String getName() {
		return "Rabbits and Grass";
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setup() {
		System.out.println("Running Setup");
		rgsSpace = null;
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
}
