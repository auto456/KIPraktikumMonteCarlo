package test1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.*;

import lejos.robotics.geometry.Line;

public class Gui extends javax.swing.JFrame {

	public Particle[] buildGui(Particle p) {
		Gui frame = new Gui();

		JPanel panel = new JPanel();

		frame.setTitle("Robot GUI");
		frame.setSize(750, 600);
		frame.setResizable(false);
		frame.setLocation(400, 150);

		Particle[] particleList = new Particle[1];
		particleList[0] = p;
		myDrawLine mDL = new myDrawLine(particleList);
		panel.add(mDL);
		frame.add(panel);

		frame.setVisible(true);

		return particleList;
	}

	public Object[] buildGui(int particles) throws InterruptedException {
		Gui frame = new Gui();

		JPanel panel = new JPanel();

		frame.setTitle("Robot GUI");
		frame.setSize(750, 600);
		frame.setResizable(false);
		frame.setLocation(400, 150);

		Object[] particleList = generateParticle(particles);
		myDrawLine mDL = new myDrawLine(particleList);
		panel.add(mDL);
		frame.add(panel);
		frame.setVisible(true);

		return particleList;
	}

	public Particle[] resetGui(Particle[] newParticleList, int turn) {
		Gui frame = new Gui();

		JPanel panel = new JPanel();

		frame.setTitle("Robot GUI");
		frame.setSize(750, 600);
		frame.setResizable(false);
		frame.setLocation(400, 150);

		Object[] particleList = newParticleList;
		Particle[] cleanList = generateParticle(particleList.length, (Particle[]) particleList);

		myDrawLine mDL = new myDrawLine(cleanList);
		panel.add(mDL);
		frame.add(panel);

		frame.setVisible(true);

		return (Particle[]) particleList;
//		TimeUnit.SECONDS.sleep(2);
//		panel.remove(mDL);
//		panel.add(new myDrawLine(generateParticle(100)));
//		panel.repaint();
//		panel.updateUI();
//		frame.repaint();		

	}

	public Object[] resetGui(Particle[] newParticleList) throws InterruptedException {
		Gui frame = new Gui();

		JPanel panel = new JPanel();

		frame.setTitle("Robot GUI");
		frame.setSize(750, 600);
		frame.setResizable(false);
		frame.setLocation(400, 150);

		Object[] particleList = newParticleList;
		Particle[] cleanList = generateParticle(particleList.length, (Particle[]) particleList);
		myDrawLine mDL = new myDrawLine(cleanList);
		panel.add(mDL);
		frame.add(panel);

		frame.setVisible(true);

		return cleanList;
//		TimeUnit.SECONDS.sleep(2);
//		panel.remove(mDL);
//		panel.add(new myDrawLine(generateParticle(100)));
//		panel.repaint();
//		panel.updateUI();
//		frame.repaint();		
	}

	private Particle[] generateParticle(int length, Particle[] particleList) {
		Particle[] cleanList = new Particle[length];

		Rectangle rMid = new Rectangle(0, 65, 600, 20);

		Random r = new Random();
		for (int i = 0; i < particleList.length; i++) {
			int collision = 1;
			Rectangle rCur = rMid;
			if (rCur.intersects(particleList[i].getX(), particleList[i].getY(), 6, 6) == true) {
				cleanList[i] = particleList[i];
			} else {
				do {
					int x = r.nextInt(600);
					int y = r.nextInt(150);
					int rotation = r.nextInt(2);
					cleanList[i] = new Particle(x, y, rotation * 180, 1, -1, -1, -1);
				} while (rCur.intersects(cleanList[i].getX(), cleanList[i].getY(), 6, 6) == false);
			}

		}

		return cleanList;

	}

	static Object[] generateParticle(int size) {
		Random r = new Random();

		Rectangle rMid = new Rectangle(0, 65, 600, 20);

		Object[] particleList = new Particle[size];
		int particleWanted = 0;
		while (particleWanted < size) {
			int x = r.nextInt(600);
			int y = r.nextInt(150);
			int rotation = r.nextInt(2);
			int collision = 1;
				Rectangle rCur = rMid;
				if (rCur.intersects(x, y, 6, 6) == true) {
					collision = 0;
				}
			
			if (collision == 0) {
				particleList[particleWanted] = new Particle(x, y, rotation * 180, 1, -1, -1, -1);
				particleWanted++;
			}

		}
		return particleList;

	}
}

@SuppressWarnings("serial")
class myDrawLine extends JPanel {

	public Object[] myParticleList;
	public Particle bestParticle;

	public myDrawLine(Object[] particleList) {
		double max = 0;
		for (Particle p : (Particle[]) particleList) {
			if (p.getWeight() >= max) {
				max = p.getWeight();
				bestParticle = p;
			}
		}
		myParticleList = particleList;
	}

	public Dimension getPreferredSize() {

		return new Dimension(750, 300);
	}

	@SuppressWarnings("unchecked")
	protected void paintComponent(Graphics g) {

		((Graphics2D) g).setStroke(new BasicStroke(1));

		// X Start, Y Start, X End, Y End
		// X = <---------->
		g.setColor(Color.black);
		WorldGenerator world = new WorldGenerator();
		for (Line2D lineX : world.getWorld()) {
			((Graphics2D) g).draw(lineX);
		}

		((Graphics2D) g).setStroke(new BasicStroke(4));
		g.setColor(Color.YELLOW);

		g.drawLine(20, 75, 580, 75);

		((Graphics2D) g).setStroke(new BasicStroke(1));

		g.setColor(Color.RED);

//        g.drawLine(13, 10, 13, 16);
		for (Particle particle : (Particle[]) myParticleList) {
			if (bestParticle == particle) {
				g.setColor(new Color(0, 255, 0, (int) (particle.getWeight() * 255)));
				((Graphics2D) g).setStroke(new BasicStroke(1.2f));
			} else {
				g.setColor(new Color(255, 0, 0, (int) (particle.getWeight() * 255)));
				((Graphics2D) g).setStroke(new BasicStroke(1));
			}
			// Draw Particle
			if (particle != null) {
//				g.setColor(new Color(255,0,0,(int) (particle.getWeight()*255)));

				g.drawOval(particle.getX(), particle.getY(), 6, 6);
				// Draw orientation Line
				Line2D lineTest = new Line2D.Double(particle.getX() + 3, particle.getY() + 3, particle.getX() + 13,
						particle.getY() + 3);
				AffineTransform richtung = AffineTransform.getRotateInstance(Math.toRadians(particle.getOrientation()),
						lineTest.getX1(), lineTest.getY1());
				((Graphics2D) g).draw(richtung.createTransformedShape(lineTest));
				// Draw lines for right and left
				Line2D lineTest2 = new Line2D.Double(particle.getX() + 3, particle.getY() + 3, particle.getX() + 10,
						particle.getY() + 3);

				AffineTransform rechts = AffineTransform.getRotateInstance(
						Math.toRadians(particle.getOrientation() - 90), lineTest.getX1(), lineTest.getY1());

				AffineTransform links = AffineTransform.getRotateInstance(
						Math.toRadians(particle.getOrientation() + 90), lineTest.getX1(), lineTest.getY1());

				g.setColor(new Color(0, 0, 255, (int) (particle.getWeight() * 255)));
				((Graphics2D) g).draw(links.createTransformedShape(lineTest2));
				((Graphics2D) g).draw(rechts.createTransformedShape(lineTest2));
//			    g.setColor(Color.RED);

			}
		}

	}

}