package Tests;
import java.util.ArrayList;

public class Individuum {

	public int x, y;
	public int goalX, goalY;
	public int startX = x, startY = y;
	ArrayList<Object> way = new ArrayList<>();
	double distance;
	public double fitness;
	float maxDistance = (float) Math.sqrt(((goalX)-(startX))*((goalX)-(startX)) + ((goalY)-(startY))*((goalY)-(startY)));
	int stepsUsed;
	int nr;
	boolean colided;
	
	public Individuum(int x, int y, int goalX, int goalY, int nr, ArrayList<Object> way) {
		this.x = x;
		this.y = y;
		this.goalX = goalX;
		this.goalY = goalY;
		this.nr = nr;
		this.way = way;
	}
	
	public void createWay(int wayLength){
		stepsUsed = 0;
		for(int i = 0; i < wayLength; i++) {
			way.add(getRndmDir());
			stepsUsed++;
		}
	}
	
	public char getRndmDir() {
		int temp = (int) ((Math.random() * 10));
		if (temp != 0 && temp <= 4) {
			switch(temp) {
			case 1:
				return 'O';
			case 2:
				return 'R';
			case 3:
				return 'L';
			case 4:
				return 'U';
			}
		}
			
		return getRndmDir();
	}

	public void calcFitness() {
		distance = Math.sqrt(((x)-(goalX))*((x)-(goalX)) + ((y)-(goalY))*((y)-(goalY)));
		fitness = distance;
	}

	public void setFitness(double d) {
		this.fitness = d;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setStepsUsed(int s) {
		this.stepsUsed = s;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getGoalX() {
		return goalX;
	}

	public int getGoalY() {
		return goalY;
	}

	public char getWayAt(int pos) {
		return (char) way.get(pos);
	}
	
	public void setWayAt(int pos, char dir) {
		way.set(pos, dir);
	}
	
	public void setRndmWayAt(int pos) {
		way.set(pos, getRndmDir());
	}

	public double getDistance() {
		return distance;
	}

	public ArrayList<Object> getWay() {
		return way;
	}

	public double getFitness() {
		return fitness;
	}

	public int getNr() {
		return nr;
	}

	public void setWay(ArrayList<Object> tempWay) {
		way = tempWay;
		
	}
	

}
