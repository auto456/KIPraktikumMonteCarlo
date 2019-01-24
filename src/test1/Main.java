package test1;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) throws Exception {
		Gui myGui = new Gui();
		WorldGenerator world = new WorldGenerator();
		ServerTest http = new ServerTest();

		// Erstelle neue GUI mit Partikelanzahl
		Particle[] pl = (Particle[]) myGui.buildGui(10000);

		// Monte Carlo
		for (int i = 0; i < 50; i++) {
			nextStep(http, pl, world, i);
			// Gui aktualisieren
			pl = (Particle[]) myGui.resetGui(pl);
		}
	}

	public static void nextStep(ServerTest roboServer, Particle[] pList, WorldGenerator world, int count)
			throws IOException {
		Random r = new Random();
		Particle[] newPList;
		int driveDistance = r.nextInt(100);

		System.out.println("Distance is: " + driveDistance);
		int turn[] = roboServer.drive(driveDistance);
		if (turn[0] == 1) {
			for (Particle p : pList) {
				p.setOrientation(p.getOrientation() + 180);
			}
		}
		float[] sensor = sensorOut(roboServer.readSensor("all"));
		newPList = berechneNeuePosition(pList, driveDistance);
		newPList = berechneSchnittpunkt(newPList, world.getWorld());
		for (Particle pa : newPList) {

			float diffLeft = Math.abs((float) pa.getSensorLeft() / (float) 85 - (float) sensor[1] / (float) 85);
			float diffRight = Math.abs((float) pa.getSensorRight() / (float) 85 - (float) sensor[2] / (float) 85);

			double weightLeft = calculateWeight(diffLeft);
			double weightRight = calculateWeight(diffRight);
			double weightTotal = weightLeft * weightRight;

			pa.setWeight(weightTotal);
		}
	}

	public static float[] sensorOut(String sensorString) {
		sensorString.split(" ");
		float[] allSensor = new float[5];

		allSensor[0] = Float.parseFloat(sensorString.split(" ")[3]) * 100;
		allSensor[1] = Float.parseFloat(sensorString.split(" ")[6]) * 100;
		allSensor[2] = Float.parseFloat(sensorString.split(" ")[9]) * 100;
		allSensor[3] = Float.parseFloat(sensorString.split(" ")[11]) * 100;

		return allSensor;
	}

	public static Particle[] berechneNeuePosition(Particle[] pList, int driveLength) {
		for (Particle p : pList) {
			Line2D lineTestFront = new Line2D.Double(p.getX(), p.getY(), p.getX() + driveLength, p.getY());
			AffineTransform rotatedLineFront = AffineTransform.getRotateInstance(Math.toRadians(p.getOrientation()),
					lineTestFront.getX1(), lineTestFront.getY1());
			Shape curLineFront = rotatedLineFront.createTransformedShape(lineTestFront);

			int curLineY1 = (int) curLineFront.getBounds().getMinY();
			int curLineY2 = (int) curLineFront.getBounds().getMaxY();
			int curLineX1 = (int) curLineFront.getBounds().getMinX();
			int curLineX2 = (int) curLineFront.getBounds().getMaxX();

			if (p.getX() <= curLineX1) {
				p.setX(curLineX2);
			} else {
				p.setX(curLineX1);
			}

			if (p.getY() <= curLineY1) {
				p.setY(curLineY2);
			} else {
				p.setY(curLineY1);
			}
		}
		return pList;
	}

	public static Particle[] berechneSchnittpunkt(Particle[] pList, ArrayList<Line2D> world) {

		Boolean frontCollision = false, leftCollision = false, rightCollision = false;
		for (Particle pl : pList) {
			int front = -1, left = -1, right = -1;
			for (int pixelLength = 0; pixelLength < 200; pixelLength++) {
				for (int worldLine = 0; worldLine < 35; worldLine++) {

					Line2D lineTestFront = new Line2D.Double(pl.getX(), pl.getY(), pl.getX() + pixelLength, pl.getY());
					AffineTransform rotatedLineFront = AffineTransform.getRotateInstance(
							Math.toRadians(pl.getOrientation()), lineTestFront.getX1(), lineTestFront.getY1());
					Shape curLineFront = rotatedLineFront.createTransformedShape(lineTestFront);

					Line2D lineTestLeft = new Line2D.Double(pl.getX(), pl.getY(), pl.getX() + pixelLength, pl.getY());
					AffineTransform rotatedLineLeft = AffineTransform.getRotateInstance(
							Math.toRadians(pl.getOrientation() - 90), lineTestLeft.getX1(), lineTestLeft.getY1());
					Shape curLineLeft = rotatedLineLeft.createTransformedShape(lineTestLeft);

					Line2D lineTestRight = new Line2D.Double(pl.getX(), pl.getY(), pl.getX() + pixelLength, pl.getY());
					AffineTransform rotatedLineRight = AffineTransform.getRotateInstance(
							Math.toRadians(pl.getOrientation() + 90), lineTestRight.getX1(), lineTestRight.getY1());
					Shape curLineRight = rotatedLineRight.createTransformedShape(lineTestRight);

					frontCollision = new Boolean(world.get(worldLine).intersectsLine(
							curLineFront.getBounds2D().getMinX(), curLineFront.getBounds2D().getMinY(),
							curLineFront.getBounds2D().getMaxX(), curLineFront.getBounds2D().getMaxY()));
					leftCollision = new Boolean(world.get(worldLine).intersectsLine(curLineLeft.getBounds2D().getMinX(),
							curLineLeft.getBounds2D().getMinY(), curLineLeft.getBounds2D().getMaxX(),
							curLineLeft.getBounds2D().getMaxY()));
					rightCollision = new Boolean(world.get(worldLine).intersectsLine(
							curLineRight.getBounds2D().getMinX(), curLineRight.getBounds2D().getMinY(),
							curLineRight.getBounds2D().getMaxX(), curLineRight.getBounds2D().getMaxY()));

					if (frontCollision && front == -1) {
//						System.out.println("Front collision at: " + pixelLength + " on Line: " + worldLine);
						pl.setSensorFront(pixelLength);
						front = 1;
					}
					if (leftCollision && left == -1) {
//						System.out.println("Left collision at: " + pixelLength + " on Line: " + worldLine);
						pl.setSensorLeft(pixelLength);
						left = 1;
					}
					if (rightCollision && right == -1) {
//						System.out.println("Right collision at: " + pixelLength + " on Line: " + worldLine);
						pl.setSensorRight(pixelLength);
						right = 1;
					}
				}
			}
		}

		return pList;

	}

	public static double calculateWeight(double sensor) {
//		return 1-sensor;
		return 1 / Math.exp(3 * Math.pow(sensor, 2));
	}
}
