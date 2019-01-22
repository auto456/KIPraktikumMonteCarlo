package test1;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws Exception {
		Gui myGui = new Gui();
		WorldGenerator world = new WorldGenerator();

		Particle[] pl = (Particle[]) myGui.buildGui(1000);

		Particle[] newPList = zeigeSchnittpunkt(pl, world.getWorld());
		for (Particle pa : newPList) {
			if(pa.getSensorFront() == -1 && pa.getSensorLeft() <60 && pa.getSensorLeft() >10 && pa.getSensorRight() <60 && pa.getSensorRight() >10) {
				pa.setWeight(0.8);
				System.out.println("found");
			}
		}

		myGui.resetGui(pl); 
//		sensor = sensorOut(http.readSensor("all"));
//		System.out.println("Front Distance: " + sensor[0]);
//		System.out.println("Left Distance: " + sensor[1]);
//		System.out.println("Right Distance: " + sensor[2]);
//		
//		myGui.resetGui((Particle[]) myGui.buildGui());

//		ServerTest http = new ServerTest();
//		String[] sensor = sensorOut(http.readSensor("all"));
//		System.out.println("Front Distance: " + sensor[0]);
//		System.out.println("Left Distance: " + sensor[1]);
//		System.out.println("Right Distance: " + sensor[2]);
//		System.out.println("Color: " + sensor[3]);

//		http.drive(600);
	}

	public static String[] sensorOut(String sensorString) {
		sensorString.split(" ");
		String[] allSensor = new String[5];

		allSensor[0] = sensorString.split(" ")[3];
		allSensor[1] = sensorString.split(" ")[6];
		allSensor[2] = sensorString.split(" ")[9];
		allSensor[3] = sensorString.split(" ")[11];

		return allSensor;
	}

	public static Particle[] zeigeSchnittpunkt(Particle[] pList, ArrayList<Line2D> world) {

		Boolean frontCollision = false, leftCollision = false, rightCollision = false;
		
		for (Particle pl: pList) {
			int front = -1, left = -1, right = -1;
			for (int pixelLength = 0; pixelLength < 200; pixelLength++) {
				for (int worldLine = 0; worldLine < 35; worldLine++) {

					Line2D lineTestFront = new Line2D.Double(pl.getX(), pl.getY(),
							pl.getX() + pixelLength, pl.getY());
					AffineTransform rotatedLineFront = AffineTransform.getRotateInstance(
							Math.toRadians(pl.getOrientation()), lineTestFront.getX1(),
							lineTestFront.getY1());
					Shape curLineFront = rotatedLineFront.createTransformedShape(lineTestFront);

					Line2D lineTestLeft = new Line2D.Double(pl.getX(), pl.getY(),
							pl.getX() + pixelLength, pl.getY());
					AffineTransform rotatedLineLeft = AffineTransform.getRotateInstance(
							Math.toRadians(pl.getOrientation() - 90), lineTestLeft.getX1(),
							lineTestLeft.getY1());
					Shape curLineLeft = rotatedLineLeft.createTransformedShape(lineTestLeft);

					Line2D lineTestRight = new Line2D.Double(pl.getX(), pl.getY(),
							pl.getX() + pixelLength, pl.getY());
					AffineTransform rotatedLineRight = AffineTransform.getRotateInstance(
							Math.toRadians(pl.getOrientation() + 90), lineTestRight.getX1(),
							lineTestRight.getY1());
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
}
