import java.util.ArrayList;
import uchicago.src.sim.space.Object2DGrid;


/**
 * Class that implements the simulation space of the rabbits grass simulation.
 */

public class RabbitsGrassSimulationSpace {
	private static int GRASSENERGY = 5;
	
	
	private Object2DGrid grassSpace;
	private Object2DGrid rabbitSpace;
	private int numberOfRabbits; // used to determine strategy to add a rabbit in a free spot
	private int numberOfGrass;
	private final int numberOfCells;
	
	// Create both spaces
	// Initialize all cells with grass value 0
	public RabbitsGrassSimulationSpace(int xSize, int ySize) {
		grassSpace = new Object2DGrid(xSize, ySize);
		rabbitSpace = new Object2DGrid(xSize, ySize);
		numberOfRabbits = 0;
		numberOfGrass = 0;
		numberOfCells = xSize * ySize;
		
		for(int i = 0; i < xSize; i++) {
			for(int j = 0; j < ySize; j++) {
				grassSpace.putObjectAt(i, j, new Integer(0));
			}
		}
	}
	
	// Check if there is grass on this position
	public boolean isCellOccupiedByGrass(int x, int y) {
		return ((Integer) grassSpace.getObjectAt(x, y)).intValue() > 0;
	}

	// Get the rabbit at this position
	public RabbitsGrassSimulationAgent getRabbitAt(int x, int y) {
		RabbitsGrassSimulationAgent retVal = null;
		if(rabbitSpace.getObjectAt(x, y) != null) {
			retVal = (RabbitsGrassSimulationAgent) rabbitSpace.getObjectAt(x, y);
		}
		return retVal;
	}
	
	public Object2DGrid getCurrentGrassSpace() {
		return grassSpace;
	}
	
	public Object2DGrid getCurrentRabbitSpace() {
		return rabbitSpace;
	}
	
	// Check if there is a rabbit at this position
	public boolean isCellOccupiedByRabbit(int x, int y) {
		boolean retVal = false;
		if (rabbitSpace.getObjectAt(x, y) != null)
			retVal = true;
		return retVal;
	}
	
	// Add rabbit to rabbitSpace by looking for open spot
	public boolean addRabbit(RabbitsGrassSimulationAgent rabbit) {	
		if (numberOfRabbits >= numberOfCells) {
			return false;
		}
		
		if (numberOfRabbits > 0.9 * numberOfCells) { /* 0.9 is arbitrary and represents a tradeoff between the
		 time that is needed to find randomly an empty cell and the time needed to identify all of the empty cells
		 in practice, it is unusual we will have 90% of rabbits but at least that case will be handled correctly */
			class IntPair {
				public final int x;
				public final int y;
				IntPair(int x, int y) {this.x = x; this.y = y;}
			}
			ArrayList<IntPair> freeSpots = new ArrayList<IntPair>();
			for (int i = 0; i < rabbitSpace.getSizeX(); i++) {
				for (int j = 0; j < rabbitSpace.getSizeY(); j++) {
					if (!isCellOccupiedByRabbit(i, j)) {
						freeSpots.add(new IntPair(i, j));
					}
				}
			}
			IntPair spot = freeSpots.get((int) Math.random() * freeSpots.size());
			rabbitSpace.putObjectAt(spot.x, spot.y, rabbit);
			rabbit.setXY(spot.x, spot.y);
			rabbit.setRabbitsGrassSimulationSpace(this);			
		} else {
			boolean found = false;
			while (!found) {
				int x = (int) (Math.random() * (rabbitSpace.getSizeX()));
				int y = (int) (Math.random() * (rabbitSpace.getSizeY()));
				if (!isCellOccupiedByRabbit(x, y)) {
					rabbitSpace.putObjectAt(x, y, rabbit);
					rabbit.setXY(x, y);
					rabbit.setRabbitsGrassSimulationSpace(this);
					found = true;
				}
			}
		}
		numberOfRabbits++;
		return true;
	}
	
	// Delete the rabbit at this position
	public void removeRabbitAt(int x, int y, boolean decrementNumberOfRabbits) {
		rabbitSpace.putObjectAt(x, y, null);
		if (decrementNumberOfRabbits) {
			numberOfRabbits--;
		}
	}
	
	// Set the grass value on 0 for this position (so eat the grass)
	public int eatGrassAt(int x, int y) {
		if (isCellOccupiedByGrass(x, y)) {
			grassSpace.putObjectAt(x, y, new Integer(0));
			numberOfGrass--;
			return GRASSENERGY;
		}
		return 0;
	}
	
	// Check if new cell is occupied and if not move the rabbit
	public boolean moveRabbitAt(int x, int y, int newX, int newY) {
		boolean retVal = false;	
		if(!isCellOccupiedByRabbit(newX, newY)) {
			RabbitsGrassSimulationAgent rgsa = (RabbitsGrassSimulationAgent) rabbitSpace.getObjectAt(x, y);
			removeRabbitAt(x,y, false);
			rgsa.setXY(newX, newY);
			rabbitSpace.putObjectAt(newX, newY, rgsa);
			retVal = true;
		}
		return retVal;
	}
	
	
	// Add certain amount of grass
	public void addGrass(float rate) {
		for (int i = 0; i < rabbitSpace.getSizeX(); i++) {
			for (int j = 0; j < rabbitSpace.getSizeY(); j++) {
				if (!isCellOccupiedByGrass(i, j)) {
					if (rate > Math.random()) {
						grassSpace.putObjectAt(i, j, new Integer(1));
						numberOfGrass++;
					}
				}
			}
		}
	}
	
	// prints the number of rabbits
	public void printNumberOfRabbits() {
		System.out.println("There are currently " + Integer.toString(numberOfRabbits) + " rabbits.");
	}
	
	public int getNumberOfRabbits() {
		return numberOfRabbits;
	}
	
	public int getNumberOfGrass() {
		return numberOfGrass;
	}
}
