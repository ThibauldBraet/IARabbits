import java.util.ArrayList;
import java.util.Collections;

import uchicago.src.sim.space.Object2DGrid;

/**
 * Class that implements the simulation space of the rabbits grass simulation.
 * @author 
 */

public class RabbitsGrassSimulationSpace {
	private Object2DGrid space;
	
	public RabbitsGrassSimulationSpace(int gridSize, int initNumberRabbits) {
		space = new Object2DGrid(gridSize, gridSize);
		
		// if there is more than a rabbit in 1 out of 2 locations, we place randomly the
		// empty locations rather than the rabbits for speed
		Integer defaultValue, alternativeValue;
		int numberAlternatives;
		
		if (initNumberRabbits < gridSize*gridSize/2) {
			defaultValue = new Integer(0);
			alternativeValue = new Integer(1);
			numberAlternatives = initNumberRabbits;
		} else {
			defaultValue = new Integer(1);
			alternativeValue = new Integer(0);
			numberAlternatives = gridSize*gridSize - initNumberRabbits;
		}
		
		for(int i = 0; i < gridSize; i++) {
			for(int j = 0; j < gridSize; j++) {
				space.putObjectAt(i, j, defaultValue);
			}
		}
		
		for(int i = 0; i < numberAlternatives;) {
			int x = (int) (Math.random() * gridSize);
			int y = (int) (Math.random() * gridSize);
			if (((Integer) space.getObjectAt(x, y)).equals(defaultValue)) { // cannot be null
				space.putObjectAt(x, y, alternativeValue);
				i++;
			}
		}
	}

	public Object2DGrid getCurrentAgentSpace() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean moveRabbitAt(int x, int y, int newX, int newY) {
		// TODO Auto-generated method stub
		return false;
	}

}
