package ltg.ps.phenomena.support.model;

public class Planet {
	
	private String name = null;
	private String color = null;
	private String colorName = null;
	private int classOrbitalTime = -1;
	private float speed = -1;
	private Degree startPosition = null; 
	private Degree currentPosition = null;
	
	
	public Planet(String name, String color, String colorName, int classOrbitalTime, int startPosition) {
		this.name = name;
		this.color = color;
		this.colorName = colorName;
		this.classOrbitalTime = classOrbitalTime;
		this.speed = 6 / classOrbitalTime;
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


	public void computeCurrentPosition(long timeDelta) {
		currentPosition = new Degree(startPosition.getValue() - speed * timeDelta);
	}
}
