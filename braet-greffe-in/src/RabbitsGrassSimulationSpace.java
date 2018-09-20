import uchicago.src.sim.space.Object2DGrid;

/**
 * Class that implements the simulation space of the rabbits grass simulation.
 * @author 
 */

public class RabbitsGrassSimulationSpace {
	private Object2DGrid space;
	
	public RabbitsGrassSimulationSpace(int gridSize, int initNumberRabbits) {
		space = new Object2DGrid(gridSize, gridSize);
		for(int i = 0; i < gridSize; i++) {
			for(int j = 0; j < gridSize; j++) {
				space.putObjectAt(i, j, new Integer(0));
			}
		}
		placeRabbits(initNumberRabbits);
	}

	private void placeRabbits(int initNumberRabbits) {
		
		
	}

}
