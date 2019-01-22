package test1;

public class Particle {
	int orientation;
	private int X, Y;
	private double weight;
	private int sensorFront=-1, sensorLeft=-1, sensorRight=-1;
	
	public Particle(int x, int y, int orientation, double weight, int sensorFront, int sensorLeft, int sensorRight) {
		this.orientation = orientation;
		X = x;
		Y = y;
		this.weight = weight;
		this.sensorFront = sensorFront;
		this.sensorLeft = sensorLeft;
		this.sensorRight = sensorRight;

	}

	public int getOrientation() {
		return orientation;
	}

	public int getSensorFront() {
		return sensorFront;
	}

	public void setSensorFront(int sensorFront) {
		this.sensorFront = sensorFront;
	}

	public int getSensorLeft() {
		return sensorLeft;
	}

	public void setSensorLeft(int sensorLeft) {
		this.sensorLeft = sensorLeft;
	}

	public int getSensorRight() {
		return sensorRight;
	}

	public void setSensorRight(int sensorRight) {
		this.sensorRight = sensorRight;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
}
