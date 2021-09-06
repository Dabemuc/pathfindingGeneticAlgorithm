package Tests;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Kreuzungsstaerke1 extends Kaestchen{
	
	//set window size:
	static int pxlsize = 6; static int wndwsize = 100;
	
	//variables:
	int startPopCount = 100;
	int wayLength = 200;
	double mutWslkeit = 0.03;				
	double kreuzungsStaerke = 1;			
	
	int goalX = 175;
	int goalY = 50;
	int startX = 25;
	int startY = 50;
	
	
	int genDauer = 1;
	double rndmWallWsl = 0.03;
	Genetic g;
	public ArrayList<Wall> walls = new ArrayList<>();
	boolean goalSet, startSet, ready, running;
	int genAkt = 0;
	Timer t = new Timer();
	
	
	
	public static void main(String[] args) {
		new Kreuzungsstaerke1();
	}
	
	public Kreuzungsstaerke1() {
		super(pxlsize, pxlsize, wndwsize *2, wndwsize, true);
		createWalls();
		drawWalls();
		farbeSetzen(startX, startY, "blau");
		farbeSetzen(goalX, goalY, "cyan");
	}

	
	
	public void tasteClick(String taste) {
		if(!running) 
		if((taste.equals("S") && ready) || startSet == false) { 
			g = new Genetic(startPopCount, goalX, goalY, startX, startY, wayLength, mutWslkeit, kreuzungsStaerke);
			g.initialize();
			running = true;
			startSet = true;
			goalSet = true;
			t.schedule(new TimerTask() {
				
				@Override
                public void run() {
                	genAkt++;
                    g.selection();
                    g.crossover();
                    g.mutation();
                    System.out.println(g.fittest.fitness);
                    draw();
                    if(checkForHit() == true) {
                    	cancel();
                    }
                    drawFittest();
                    System.out.println("akt. Generation: " + genAkt);
                }
            }, 0, genDauer);
		}
		
		if(taste.equals("B") && running)
			t.cancel();
	}
	
	public void mausLeftClick(int x, int y) {
		if(startSet == false) {
			farbeSetzen(startX, startY, "wei�");
			startX = x; startY = y;
			startSet = true;
			farbeSetzen(startX, startY, "blau");
		}
		
		else if(goalSet == false) {
			farbeSetzen(goalX, goalY, "wei�");
			goalX = x; goalY = y;
			goalSet = true;
			farbeSetzen(goalX, goalY, "cyan");
			
			ready = true;
			
		}
	}

	private void draw() {
		for(int i = 0; i < wndwsize * 2; i++) {
			for(int j = 0; j < wndwsize; j++) {
				if(checkForWall(i, j) == false && (i != goalX || j != goalY) && (i != startX || j != startY))
					farbeSetzen(i, j, "wei�");
			}
		}
		for(int i = 0; i < startPopCount; i++) {
			int tempSteps = 0;
			int tempX = startX, tempY = startY;
			for(int j = 0; j < wayLength; j++) {
				if(((tempX != goalX) || (tempY != goalY)) && checkForWall(tempX, tempY) == false) {
					switch(g.indv.get(i).getWayAt(j)) {
					case 'L':
						tempX++;
						break;
					case 'O':
						tempY--;
						break;
					case 'R':
						tempX--;
						break;
					case 'U':
						tempY++;
						break;
					}
					
					tempSteps++;
				}
				farbeSetzen(tempX, tempY, "rot");
			}

			g.indv.get(i).setStepsUsed(tempSteps);
			g.indv.get(i).setX(tempX);
			g.indv.get(i).setY(tempY);
		}
		drawWalls();
		farbeSetzen(startX, startY, "blau");
		farbeSetzen(goalX, goalY, "cyan");
	}
	
	public void drawFittest(){
		int tempSteps = 0;
		int tempX = startX, tempY = startY;
		for(int j = 0; j < wayLength; j++) {
			if((tempX != goalX || tempY != goalY) && checkForWall(tempX, tempY) == false) {
				switch(g.fittest.getWayAt(j)) {
				case 'L':
					tempX++;
					break;
				case 'O':
					tempY--;
					break;
				case 'R':
					tempX--;
					break;
				case 'U':
					tempY++;
					break;
				}

				tempSteps++;
			}
			farbeSetzen(tempX, tempY, "gr�n");
		}
		g.fittest.setStepsUsed(tempSteps);
		farbeSetzen(tempX, tempY, "gr�n");
		g.fittest.setX(tempX);
		g.fittest.setY(tempY);
		
		
	}
	
	public void drawWalls(){
		for(int i = 0; i < walls.size(); i++)
			farbeSetzen(walls.get(i).x, walls.get(i).y, "schwarz");
	}
	
	public boolean checkForWall(int checkX, int checkY) {
		for(int i = 0; i < walls.size(); i++) {
			if(walls.get(i).x == checkX && walls.get(i).y == checkY)
				return true;
		}
		return false;
	}

	public void createWalls() {
		
		
		//Zuf�llige Mauern
		//drawRndm();
		
		//Kreuz:
		/*drawVert(100, 0, 20);
		drawVert(100, 25, 45);
		drawVert(100, 55, 75);
		drawVert(100, 80, 100);
		drawHor(50, 75, 95);
		drawHor(50, 105, 125);
		
		drawVert(70, 40, 60);
		drawVert(130, 40, 60);*/
		
		//Untersuchungfl�che:
		drawVert(70, 43, 57);
		drawVert(130, 43, 57);
		
		
		//Begrenzung:
		drawHor(1, 1, 201);
		drawHor(100, 1, 201);
		drawVert(1, 1, 101);
		drawVert(200, 1, 101);
	}

	private void drawVert(int x, int yStart, int yEnd) {
		for(int i = yStart; i <= yEnd; i++)
			walls.add(new Wall(x, i));
	}
	
	private void drawHor(int y, int xStart, int xEnd) {
		for(int i = xStart; i <= xEnd; i++)
			walls.add(new Wall(i, y));
	}
	
	public void drawRndm() {
		for(int i = 0; i < wndwsize * 2; i++) {
			for(int j = 0; j < wndwsize; j++) {
				if(Math.random() < rndmWallWsl) walls.add(new Wall(i, j));
			}
		} 
	}
	
	public boolean checkForHit() {
		for(int i = 0; i < g.indv.size(); i++) {
			if(g.indv.get(i).x == goalX && g.indv.get(i).y == goalY) {
				g.fittest = g.indv.get(i);
				return true;
			}
		}
		return false;
	}
	
}
