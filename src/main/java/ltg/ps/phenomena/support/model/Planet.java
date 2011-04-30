package ltg.ps.phenomena.support.model;

import java.util.List;

public class Planet {
	
	private String name = null;
	private String color = null;
	private String colorName = null;
	private int classOrbitalTime = -1;
	private float speed = -1;
	private Degree startPosition = null; 
	private Degree currentPosition = null;
	private Degree predictedPosition = null;
	private HelioroomWindow lastWindowVisited = null;
	
	
	public Planet(String name, String color, String colorName, int classOrbitalTime, int startPosition) {
		this.name = name;
		this.color = color;
		this.colorName = colorName;
		this.classOrbitalTime = classOrbitalTime;
		this.speed = 6 / (float) classOrbitalTime;
		this.startPosition = new Degree(startPosition);
		this.currentPosition = this.startPosition;
	}


	public String getName() {
		return name;
	}


	public String getColor() {
		return color;
	}
	
	
	public String getColorName() {
		return colorName;
	}


	public int getClassOrbitalTime() {
		return classOrbitalTime;
	}	

	
	public float getSpeed() {
		return speed;
	}	

	
	public Degree getStartPosition() {
		return startPosition;
	}
	
	
	public Degree getCurrentPosition() {
		return currentPosition;
	}
	
	
	public Degree getPredictedPosition() {
		return predictedPosition;
	}
	
	
	public HelioroomWindow getWindow() {
		return lastWindowVisited;
	}


	public void computeCurrentPosition(float timeDelta) {
		currentPosition = new Degree(startPosition.getValue() - speed * timeDelta);
	}


	public void computeDistanceTraveled(float howManySecondsInAdvance) {
		predictedPosition = new Degree(currentPosition.getValue() - speed * howManySecondsInAdvance);
	}


	public boolean findWindow(List<HelioroomWindow> clientWins) {
		for (HelioroomWindow w : clientWins)
			if(predictedPosition.insideCCWArc(new Degree(w.getViewAngleBegin()), new Degree(w.getViewAngleEnd()))) {
				if (lastWindowVisited == null || lastWindowVisited != w) {
					lastWindowVisited = w;
					return true;
				}
			}
		return false;
	}
}
