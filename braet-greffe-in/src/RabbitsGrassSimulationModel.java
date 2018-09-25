import java.util.ArrayList;

import java.awt.Color;

import uchicago.src.sim.analysis.DataSource;
import uchicago.src.sim.analysis.OpenSequenceGraph;
import uchicago.src.sim.analysis.Sequence;
import uchicago.src.sim.engine.BasicAction;
import uchicago.src.sim.engine.Schedule;
import uchicago.src.sim.engine.SimInit;
import uchicago.src.sim.engine.SimModelImpl;
import uchicago.src.sim.gui.ColorMap;
import uchicago.src.sim.gui.DisplaySurface;
import uchicago.src.sim.gui.Object2DDisplay;
import uchicago.src.sim.gui.Value2DDisplay;
import uchicago.src.sim.util.SimUtilities;

/**
 * Class that implements the simulation model for the rabbits grass
 * simulation. This is the first class which needs to be setup in
 * order to run Repast simulation. It manages the entire RePast
 * environment and the simulation.
 *
 */


public class RabbitsGrassSimulationModel extends SimModelImpl {		
	private static final int GRIDSIZE = 20;
	private static final int NUMRABBITS = 20;
	private static final int BIRTHTHRESHOLD = 10;
	private static final float GRASSGROWRATE = 0.3f;
	private static final int STARTENERGY = 10;
	
	
	private int gridSize = GRIDSIZE; // TODO: write in report we chose square because rectangles are not interesting 
	private int initNumberRabbits = NUMRABBITS;
	private int birthThreshold = BIRTHTHRESHOLD;
	private float grassGrowthRate = GRASSGROWRATE;
	
	
	private Schedule schedule;
	private RabbitsGrassSimulationSpace rgsSpace;
	private ArrayList<RabbitsGrassSimulationAgent> rabbitList;
	private DisplaySurface displaySurf;
	private OpenSequenceGraph graph;
	
	class amountOfRabbits implements DataSource, Sequence {
		public Object execute() {
			return new Double(getSValue());
		}

		public double getSValue() {
			return (double) rgsSpace.getNumberOfRabbits();
		}
	}
	
	class amountOfGrass implements DataSource, Sequence {
		public Object execute() {
			return new Double(getSValue());
		}

		public double getSValue() {
			return (double) rgsSpace.getNumberOfGrass();
		}
	}
	
	public String getName() {
		return "Rabbits and Grass";
	}
	
	public void setup() {
		System.out.close();  // otherwise Repast will bug, see https://sourceforge.net/p/repast/mailman/message/20323030/
		System.out.println("Running setup");
		rgsSpace = null;
		rabbitList = new ArrayList<RabbitsGrassSimulationAgent>();
		schedule = new Schedule(1);
		
		if (displaySurf != null) {
			displaySurf.dispose();
		}
		displaySurf = null;
		
		if (graph != null) {
			graph.dispose();
		}
		graph = null;
		
		displaySurf = new DisplaySurface(this, "Rabbits Grass Simulation Model Window 1");
		graph = new OpenSequenceGraph("Amount of rabbits and grass", this);
		
		registerDisplaySurface("Rabbits Grass Simulation Model Windows 1", displaySurf);
		this.registerMediaProducer("Plot", graph);
	}
	

	public void begin() {
		buildModel();
	    buildSchedule();
	    buildDisplay();
	    
	    displaySurf.display();
	    graph.display();
	}

	public void buildModel() {
		System.out.println("Running BuildModel");
		rgsSpace = new RabbitsGrassSimulationSpace(gridSize, gridSize);		
		
		for (int i = 0; i < initNumberRabbits; i++) {
			addNewRabbit(STARTENERGY);
		}
		
		for (int i = 0; i < rabbitList.size(); i++) {
			RabbitsGrassSimulationAgent rgsa = (RabbitsGrassSimulationAgent) rabbitList.get(i);
			rgsa.report();
		}
	}
	
	public void buildSchedule() {
		System.out.println("Running BuildSchedule");
		ArrayList<BasicAction> list = new ArrayList<BasicAction>();
		
		class RabbitGrassSimulationStep extends BasicAction {
			public void execute() {
				SimUtilities.shuffle(rabbitList);
				for (int i = 0; i < rabbitList.size(); i++) {
					RabbitsGrassSimulationAgent rgsa = (RabbitsGrassSimulationAgent) rabbitList.get(i);
					rgsa.step();
					if (rgsa.getEnergy() > birthThreshold) {
						int energy = rgsa.reproduce();
						addNewRabbit(energy);
					}
				}
				
				rgsSpace.addGrass(grassGrowthRate);
				
				reapDeadRabbits();
				
				displaySurf.updateDisplay();
			}
		}
		list.add(new RabbitGrassSimulationStep());
		
		class UpdateRabbitsGraph extends BasicAction {
			public void execute(){
				graph.step();
			}
		}
		list.add(new UpdateRabbitsGraph());
		
		schedule.scheduleActionBeginning(0, list, BasicAction.class, "execute");
	}
	
	private void addNewRabbit(int startEnergy) {
		RabbitsGrassSimulationAgent rabbit = new RabbitsGrassSimulationAgent(startEnergy);
		boolean placed = rgsSpace.addRabbit(rabbit);
		if (placed) {
			rabbitList.add(rabbit); // If no place left, rabbit won't be placed on the grid
		}
	}
	
	private int reapDeadRabbits(){
	    int count = 0;
	    for (int i = (rabbitList.size() - 1); i >= 0 ; i--) {
	    	RabbitsGrassSimulationAgent rgsa = (RabbitsGrassSimulationAgent)rabbitList.get(i);
	    	if (rgsa.getEnergy() < 1) {
	    		rgsSpace.removeRabbitAt(rgsa.getX(), rgsa.getY(), true);
	    		rabbitList.remove(i);
	    		count++;
	    	}
	    }
	    return count;
	}
	
	public void buildDisplay() {
		System.out.println("Running BuildDisplay");
		
		ColorMap map = new ColorMap();
		
		map.mapColor(0, Color.LIGHT_GRAY);
		map.mapColor(1, Color.GREEN);
		
		Value2DDisplay displayGrass = new Value2DDisplay(rgsSpace.getCurrentGrassSpace(), map);
		
		Object2DDisplay displayRabbits = new Object2DDisplay(rgsSpace.getCurrentRabbitSpace());
		displayRabbits.setObjectList(rabbitList);
		
		displaySurf.addDisplayableProbeable(displayGrass, "Grass");
		displaySurf.addDisplayableProbeable(displayRabbits, "Agents");
		
		graph.addSequence("Number of Rabbits", new amountOfRabbits());
		graph.addSequence("Amount of Grass", new amountOfGrass());
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
		this.gridSize = Math.max(gridSize, 1);
	}

	public int getInitNumberRabbits() {
		return initNumberRabbits;
	}

	public void setInitNumberRabbits(int initNumberRabbits) {
		this.initNumberRabbits = Math.max(initNumberRabbits, 0);
	}

	public int getBirthTreshold() {
		return birthThreshold;
	}

	public void setBirthTreshold(int birthTreshold) {
		this.birthThreshold = Math.max(birthTreshold, 0);
	}

	public float getGrassGrowthRate() {
		return grassGrowthRate;
	}

	public void setGrassGrowthRate(float grassGrowthRate) {
		this.grassGrowthRate = Math.min(Math.max(grassGrowthRate, 0.0f), 1.0f);
	}
	
	public static void main(String[] args) {
	    SimInit init = new SimInit();
	    RabbitsGrassSimulationModel model = new RabbitsGrassSimulationModel();
	    init.loadModel(model, "", false);
	}
}
