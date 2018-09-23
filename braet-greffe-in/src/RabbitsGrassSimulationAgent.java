import java.awt.Color;
import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;
import uchicago.src.sim.space.Object2DGrid;


/**
 * Class that implements the simulation agent for the rabbits grass simulation.
 */



public class RabbitsGrassSimulationAgent implements Drawable {
	private static int MOVINGCOST = 1;
	
	private int energy; // energy left
	private int x; // current x-coordinate
	private int y; // current y-coordinate
	private static int IDNumber = 0;
	private int ID;
	private RabbitsGrassSimulationSpace rgsSpace;
	
	
	// Create Rabbit with given energy and random direction
	public RabbitsGrassSimulationAgent(int initEnergy) {	
		energy = initEnergy;
		x = -1;
		y = -1;
		IDNumber++;
		ID = IDNumber;
	}
		
	// Change coordinates
	public void setXY(int newX, int newY) {
		x = newX;
		y = newY;
	}
	
	// Set the grass-space
	public void setRabbitsGrassSimulationSpace(RabbitsGrassSimulationSpace rgss) {
		rgsSpace = rgss;
	}
	
	// Get ID
	public String getID() {
		return "R-" + ID;
	}
	
	// Get energy left
	public int getEnergy() {
		return energy;
	}
	
	// prints a resume of the rabbit's characteristics
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
		G.drawFastOval(Color.WHITE);		
	}
	
	
	// Try to move and eat grass on next position if there is grass there
	public void step() {
		int newX = x;
		int newY = y;
		switch((int) (Math.random() * 4)) {
		case 0: // North
			newY--;
			break;
		case 1: // East
			newX++;
			break;
		case 2: // South
			newY++;
			break;
		case 3: // West
			newX--;
			break;
		}
		
		Object2DGrid grid = rgsSpace.getCurrentRabbitSpace();
		newX = (newX + grid.getSizeX()) % grid.getSizeX();
		newY = (newY + grid.getSizeY()) % grid.getSizeY();
		
		if (tryMove(newX, newY)) {
			energy += rgsSpace.eatGrassAt(newX, newY);
		} else {
			energy += rgsSpace.eatGrassAt(x, y);
		}
		energy -= MOVINGCOST; // each step costs energy
	}
	
	private boolean tryMove(int newX, int newY) {
		return rgsSpace.moveRabbitAt(x, y, newX, newY);
	}

	// divides the rabbit's energy by 2, return the amount of energy "given" to the child rabbit
	public int reproduce() {
		energy /= 2;
		return energy;
	}


}
