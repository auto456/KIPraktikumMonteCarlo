package test1;

public class Particle {
	int orientation;
	private int X, Y;
	private double weight;
	
	public Particle(int x, int y, int orientation, double weight) {
		this.orientation = orientation;
		X = x;
		Y = y;
		this.weight = weight;
	}

	public int getOrientation() {
		return orientation;
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
