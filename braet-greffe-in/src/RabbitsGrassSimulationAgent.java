import java.awt.Color;
import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;
import uchicago.src.sim.space.Object2DGrid;


/**
 * Class that implements the simulation agent for the rabbits grass simulation.

 * @author
 */



public class RabbitsGrassSimulationAgent implements Drawable {
	private static int MOVINGCOST = 1;
	private static int REPRODUCTIONCOST = 10;
	
	private int energy; //energy left
	private int x; //current x-coordinate
	private int y; //current y-coordinate
	private char direction; //direction to go next
	private static int IDNumber = 0;
	private int ID;
	private RabbitsGrassSimulationSpace rgsSpace;
	
	
	//Create Rabbit with default energy and random direction
	public RabbitsGrassSimulationAgent(int initEnergy) {	
		energy = initEnergy;
		x=-1;
		y=-1;
		setDirection();
		IDNumber++;
		ID = IDNumber;
	}
	
	//Set the direction at random
	private void setDirection() {
		int rand = (int) Math.floor(Math.random() * 4);
		switch(rand) {
			case 0:
				direction = 'N';
				break;
			case 1:
				direction = 'E';
				break;
			case 2:
				direction = 'S';
				break;
			case 3:
				direction = 'W';
				break;
		}
		
	}
		
	//Change coordinates
	public void setXY(int newX, int newY) {
		x = newX;
		y = newY;
	}
	
	//Set the grass-space
	public void setRabbitsGrassSimulationSpace(RabbitsGrassSimulationSpace rgss) {
		rgsSpace = rgss;
	}
	
	//Get ID
	public String getID() {
		return "R-" + ID;
	}
	
	//Get energy left
	public int getEnergy() {
		return energy;
	}
	
	public void report() {
		System.out.println(getID() + " at " + x + ", " + y + " has " + getEnergy() + "energy");
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public void draw(SimGraphics G) {
		G.drawFastRoundRect(Color.WHITE);		
	}
	
	
	//Try to move and eat grass on next position if there is still grass present there
	public void step() {
		int newX = x;
		int newY = y;
		switch(direction) {
		case 'N':
			newY--;
			break;
		case 'E':
			newX++;
			break;
		case 'S':
			newY++;
			break;
		case 'W':
			newX--;
			break;
		}
		
		Object2DGrid grid = rgsSpace.getCurrentRabbitSpace();
		newX = (newX + grid.getSizeX()) % grid.getSizeX();
		newY = (newY + grid.getSizeY()) % grid.getSizeY();
		
		if(tryMove(newX, newY)) {
			energy += rgsSpace.eatGrassAt(newX, newY);
		}			
		energy -= MOVINGCOST; //each step costs energy
		
		
		
		
	}
	
	private boolean tryMove(int newX, int newY) {
		return rgsSpace.moveRabbitAt(x, y, newX, newY);
	}

	
	public void reproduce() {
		energy -= REPRODUCTIONCOST;
	}


}
