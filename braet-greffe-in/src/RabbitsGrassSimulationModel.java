import java.util.ArrayList;

import java.awt.Color;
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
	private static final int GRASSGROWRATE = 60;
	private static final int STARTENERGY = 10;
	
	
	private int gridSize = GRIDSIZE; // TODO: write in report we chose square because rectangles are not interesting 
	private int initNumberRabbits = NUMRABBITS; // TODO: should be smaller than gridSize^2
	private int birthThreshold = BIRTHTHRESHOLD;
	private int grassGrowthRate = GRASSGROWRATE;
	private int startEnergy = STARTENERGY;
	
	
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
		
		displaySurf = new DisplaySurface(this, "Rabbits Grass Simulation Model Window 1");
		
		registerDisplaySurface("Rabbits Grass Simulation Model Windows 1", displaySurf);
	}
	

	public void begin() {
		buildModel();
	    buildSchedule();
	    buildDisplay();
	    
	    displaySurf.display();
	}

	public void buildModel() {
		System.out.println("Running BuildModel");
		rgsSpace = new RabbitsGrassSimulationSpace(gridSize, gridSize);		
		
		for (int i = 0; i < initNumberRabbits; i++) {
			addNewRabbit();
		}
		
		for (int i = 0; i < rabbitList.size(); i++) {
			RabbitsGrassSimulationAgent rgsa = (RabbitsGrassSimulationAgent) rabbitList.get(i);
			rgsa.report();
		}
	}
	
	public void buildSchedule() {
		System.out.println("Running BuildSchedule");
		
		class RabbitGrassSimulationStep extends BasicAction {
			public void execute() {
				SimUtilities.shuffle(rabbitList);
				for (int i = 0; i<rabbitList.size(); i++) {
					RabbitsGrassSimulationAgent rgsa = (RabbitsGrassSimulationAgent) rabbitList.get(i);
					rgsa.step();
					if (rgsa.getEnergy()> birthThreshold) {
						rgsa.reproduce();
						addNewRabbit();
					}
				}
				
				rgsSpace.addGrass(grassGrowthRate);
				
				reapDeadRabbits();
				
				displaySurf.updateDisplay();
			}
		}
		
		schedule.scheduleActionBeginning(0, new RabbitGrassSimulationStep());
	}
	
	private void addNewRabbit() {
		RabbitsGrassSimulationAgent rabbit = new RabbitsGrassSimulationAgent(startEnergy);
		boolean placed = rgsSpace.addRabbit(rabbit);
		if (placed) {
			rabbitList.add(rabbit); //If no place left, rabbit won't be placed in grid
		}
	}
	
	private int reapDeadRabbits(){
	    int count = 0;
	    for(int i = (rabbitList.size() - 1); i >= 0 ; i--){
	     RabbitsGrassSimulationAgent rgsa = (RabbitsGrassSimulationAgent)rabbitList.get(i);
	      if(rgsa.getEnergy() < 1){
	        rgsSpace.removeRabbitAt(rgsa.getX(), rgsa.getY());
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
		return birthThreshold;
	}

	public void setBirthTreshold(int birthTreshold) {
		this.birthThreshold = birthTreshold;
	}

	public int getGrassGrowthRate() {
		return grassGrowthRate;
	}

	public void setGrassGrowthRate(int grassGrowthRate) {
		this.grassGrowthRate = grassGrowthRate;
	}
	
	public static void main(String[] args) {
	    SimInit init = new SimInit();
	    RabbitsGrassSimulationModel model = new RabbitsGrassSimulationModel();
	    init.loadModel(model, "", false);
	}
}
