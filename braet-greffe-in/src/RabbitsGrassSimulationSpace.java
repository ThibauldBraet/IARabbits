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
		for(int i = 0; i < gridSize; i++) {
			for(int j = 0; j < gridSize; j++) {
				space.putObjectAt(i, j, new Integer(0));
			}
		}
		placeRabbits(gridSize, initNumberRabbits);
	}

	/*
	 * the locations of the rabbits are given by the initNumberRabbits first elements of a permutation
	 * of integers from 0 to gridSize^2
	 */
	private void placeRabbits(int gridSize, int initNumberRabbits) {
		int listSize = gridSize * gridSize;
		ArrayList<Integer> list = new ArrayList<Integer>(listSize);
		for(int i = 0; i < listSize; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		
		for(int i = 0; i < initNumberRabbits; i++) {
			int x = list.get(i) / gridSize;
			int y = list.get(i) % gridSize;
			space.putObjectAt(x, y, new Integer(1));
		}
	}

}
