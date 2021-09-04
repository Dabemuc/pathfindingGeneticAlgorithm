import java.util.ArrayList;

public class Genetic{

	public ArrayList<Individuum> indv = new ArrayList<>();
	int startPopCount;
	int goalX = -1;
	int goalY = -1;
	int startX = -1;
	int startY = -1;
	int wayLength;
	double mutWslkeit;
	double kreuzungsStaerke;
	Individuum fittest;
	
	
	
	public Genetic(int startPopCount, int goalX, int goalY, int startX, int startY, int wayLength, double mutWslkeit, double kreuzungsStaerke) {
		this.goalX = goalX;
		this.goalY = goalY;
		this.startPopCount = startPopCount;
		this.startX = startX;
		this.startY = startY;
		this.wayLength = wayLength;
		this.mutWslkeit = mutWslkeit;
		this.kreuzungsStaerke = kreuzungsStaerke;
	}
	
	
	

	public void initialize() {
		for(int i = 0; i < startPopCount; i++) {
			indv.add(new Individuum(startX, startY, goalX, goalY, i, new ArrayList<Object>()));
			indv.get(i).createWay(wayLength);
		}
		fittest = indv.get(0);
	}


	public void selection() {
		for(int i = 0; i < indv.size(); i++) {
			indv.get(i).calcFitness();
		}
		fittest = indv.get(0);
		for(int i = 0; i < startPopCount; i++) {
			if(indv.get(i).getFitness() < fittest.fitness) {
				fittest = indv.get(i);
			}
		}
	}


	public void crossover() {
		for(int i = 0; i < startPopCount; i++) {
			Individuum temp = indv.get(i);
			ArrayList<Object> tempWay = indv.get(i).getWay();
			for(int j = 0; j <  indv.get(i).getWay().size() * kreuzungsStaerke; j++) {
				tempWay.set(j, fittest.getWayAt(j));
			}
			indv.set(i, new Individuum(temp.getX(),temp.getY(), temp.getGoalX(), temp.getGoalY(), temp.getNr(), tempWay));
		}
	}


	public void mutation() {
		for(int i = 0; i < startPopCount; i++) {
			for(int j = 0; j < wayLength; j++) {
				if(Math.random() <= mutWslkeit) {
					indv.get(i).setRndmWayAt(j);
				}
			}	
		}
		
	}


	

	
	
	
	
	
}
