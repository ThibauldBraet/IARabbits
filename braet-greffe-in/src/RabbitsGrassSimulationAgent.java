import java.awt.Color;
import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;
import uchicago.src.sim.space.Object2DGrid;


/**
 * Class that implements the simulation agent for the rabbits grass simulation.

 * @author
 */



public class RabbitsGrassSimulationAgent implements Drawable {
	private int energy;
	private int x;
	private int y;
	private char direction;
	private static int IDNumber = 0;
	private int ID;
	private RabbitsGrassSimulationSpace rgsSpace;
	
	
	
	public RabbitsGrassSimulationAgent(int initEnergy) {
		energy = initEnergy;
		x=-1;
		y=-1;
		setDirection();
		IDNumber++;
		ID = IDNumber;
	}
	
	private void setDirection() {
		int rand = (int) Math.floor(Math.random() * 4);
		switch(rand) {
			case 0:
				direction = 'N';
			case 1:
				direction = 'E';
			case 2:
				direction = 'S';
			case 3:
				direction = 'W';
		}
		
	}
		
	public void setXY(int newX, int newY) {
		x = newX;
		y = newY;
	}
	
	public void setRabbitsGrassSimulationSpace(RabbitsGrassSimulationSpace rgss) {
		rgsSpace = rgss;
	}
	
	public String getID() {
		return "R-" + ID;
	}
	
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
	
	public void step() {
		int newX = x;
		int newY = y;
		
		switch(direction) {
		case 'N':
			newY--;
		case 'E':
			newX++;
		case 'S':
			newY++;
		case 'W':
			newX--;
		}
		
		Object2DGrid grid = rgsSpace.getCurrentAgentSpace();
		newX = (newX + grid.getSizeX()) % grid.getSizeX();
		newY = (newY + grid.getSizeY()) % grid.getSizeY();
		
		
	}
	
	private boolean tryMove(int newX, int newY) {
		return rgsSpace.moveRabbitAt(x, y, newX, newY);
	}
	
	public void eatGrass(int amount) {
		energy += amount;
	}
	
	private void reprodruce() {
		
	}


}
