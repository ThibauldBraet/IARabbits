import java.util.ArrayList;
import java.util.Collections;

import uchicago.src.sim.space.Object2DGrid;

/**
 * Class that implements the simulation space of the rabbits grass simulation.
 * @author 
 */

public class RabbitsGrassSimulationSpace {
	private static int GRASSENERGY = 5;
	
	
	private Object2DGrid grassSpace;
	private Object2DGrid rabbitSpace;
	
	//Create both spaces
	//Init all cells with grass value 0
	public RabbitsGrassSimulationSpace(int xSize, int ySize) {
		grassSpace = new Object2DGrid(xSize, ySize);
		rabbitSpace = new Object2DGrid(xSize, ySize);
		
		for(int i = 0; i < xSize; i++) {
			for(int j = 0; j < ySize; j++) {
				grassSpace.putObjectAt(i, j, new Integer(0));
			}
		}
	}
	
	//Add grass to a certain cell (value 1)
	public void addGrass(int x, int y) {
		grassSpace.putObjectAt(x, y, new Integer(1));
	}
	
	//Check if there is grass on this position
	public boolean getGrass(int x, int y) {
		if (((Integer) grassSpace.getObjectAt(x, y)).intValue() > 0) return true;
		return false;
	}

	//Get the rabbit at this position
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
	
	//Check if there is a rabbit at this position
	public boolean isCellOccupied(int x, int y) {
		boolean retVal = false;
		if (rabbitSpace.getObjectAt(x, y) != null)
			retVal = true;
		return retVal;
	}
	
	//Add rabbit to rabbitSpace by looking for open spot
	//We look 10*x*y times for an open spot
	public boolean addRabbit(RabbitsGrassSimulationAgent rabbit) {
		boolean retVal = false;
		int count = 0;
		int countLimit = 10 * rabbitSpace.getSizeX() * rabbitSpace.getSizeY(); //10 is random chosen value. We just need to try enough times
		
		while((retVal == false) && (count < countLimit)) {
			int x = (int) (Math.random() * (rabbitSpace.getSizeX()));
			int y = (int) (Math.random() * (rabbitSpace.getSizeY()));
			if (isCellOccupied(x, y) == false) {
				rabbitSpace.putObjectAt(x, y, rabbit);
				rabbit.setXY(x, y);
				rabbit.setRabbitsGrassSimulationSpace(this);
				retVal = true;
			}
			count++;
		}
		return retVal;
	}
	
	//Delete the rabbit at this position
	public void removeRabbitAt(int x, int y) {
		rabbitSpace.putObjectAt(x, y, null);		
	}
	
	//Set the grass value on 0 for this position (so eat the grass)
	public int eatGrassAt(int x, int y) {
		int grass = getGrass(x, y) ? GRASSENERGY : 0;
		grassSpace.putObjectAt(x, y, new Integer(0));
		return grass;
	}
	
	//Check if new cell is occupied and if not move the rabbit
	public boolean moveRabbitAt(int x, int y, int newX, int newY) {
		boolean retVal = false;	
		if(!isCellOccupied(newX, newY)) {
			RabbitsGrassSimulationAgent rgsa = (RabbitsGrassSimulationAgent)rabbitSpace.getObjectAt(x, y);
			removeRabbitAt(x,y);
			rgsa.setXY(newX, newY);
			rabbitSpace.putObjectAt(newX, newY, rgsa);
			retVal = true;
		}
		return retVal;
	}
	
	
	//Add certain amount of grass
	public void addGrass(int amount) {
		for (int i=0; i<amount; i++) {
			boolean grown = false;
			int count = 0;
			int countLimit = grassSpace.getSizeX() * grassSpace.getSizeY(); 
			
			//Look for a limit amount of time for a space to grow grass
			while((grown == false) && (count < countLimit)) {
				int x = (int) (Math.random() * (grassSpace.getSizeX()));
				int y = (int) (Math.random() * (grassSpace.getSizeY()));
				if (!getGrass(x, y)) {
					addGrass(x,y);
					grown = true;
				}
				count++;
			}
		}
	}
	
	/*
	 * the locations of the rabbits are given by the initNumberRabbits first elements of a permutation
	 * of integers from 0 to gridSize^2
	 */
	/*private void placeRabbits(int initNumberRabbits) {
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
	}*/
	
}
