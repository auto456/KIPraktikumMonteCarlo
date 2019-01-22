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

//	public static void main(String[] args) throws InterruptedException {
//		Gui frame = new Gui();
//
//		JPanel panel = new JPanel();
//
//		frame.setTitle("Robot GUI");
//		frame.setSize(750, 300);
//		frame.setResizable(false);
//		frame.setLocation(400, 150);
//
//		Object[] particleList = generateParticle(50);
//		myDrawLine mDL = new myDrawLine(particleList);
//		panel.add(mDL);
//		frame.add(panel);
//		
//		frame.setVisible(true);
////		TimeUnit.SECONDS.sleep(2);
////		panel.remove(mDL);
////		panel.add(new myDrawLine(generateParticle(100)));
////		panel.repaint();
////		panel.updateUI();
////		frame.repaint();	
//	}
	
	public Object[] buildGui(int particles) throws InterruptedException {
		Gui frame = new Gui();

		JPanel panel = new JPanel();

		frame.setTitle("Robot GUI");
		frame.setSize(750, 300);
		frame.setResizable(false);
		frame.setLocation(400, 150);

		Object[] particleList = generateParticle(particles);
		myDrawLine mDL = new myDrawLine(particleList);
		panel.add(mDL);
		frame.add(panel);
		
		frame.setVisible(true);
		
		return particleList;
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
		frame.setSize(750, 300);
		frame.setResizable(false);
		frame.setLocation(400, 150);

		Object[] particleList = newParticleList;
		myDrawLine mDL = new myDrawLine(particleList);
		panel.add(mDL);
		frame.add(panel);
		
		frame.setVisible(true);
		
		return particleList;
//		TimeUnit.SECONDS.sleep(2);
//		panel.remove(mDL);
//		panel.add(new myDrawLine(generateParticle(100)));
//		panel.repaint();
//		panel.updateUI();
//		frame.repaint();		
	}

	static Object[] generateParticle(int size) {
		Random r = new Random();

		Object[] rects = new Rectangle[9];
		Rectangle r0 = new Rectangle(0, 50, 600, 50);
		Rectangle r1 = new Rectangle(100, 0, 50, 50);
		Rectangle r2 = new Rectangle(200, 0, 100, 50);
		Rectangle r3 = new Rectangle(350, 0, 50, 50);
		Rectangle r4 = new Rectangle(450, 0, 100, 50);
		Rectangle r5 = new Rectangle(50, 100, 50, 50);
		Rectangle r6 = new Rectangle(200, 100, 50, 50);
		Rectangle r7 = new Rectangle(300, 100, 100, 50);
		Rectangle r8 = new Rectangle(450, 100, 50, 50);

		rects[0] = r0;
		rects[1] = r1;
		rects[2] = r2;
		rects[3] = r3;
		rects[4] = r4;
		rects[5] = r5;
		rects[6] = r6;
		rects[7] = r7;
		rects[8] = r8;

		Object[] particleList = new Particle[size];
		int particleWanted = 0;
		while (particleWanted < size) {
			int x = r.nextInt(600);
			int y = r.nextInt(150);
			int rotation = r.nextInt(360);
			int collision = 1;
			for (int j = 0; j < rects.length; j++) {
				Rectangle rCur = (Rectangle)rects[j];
				if (rCur.intersects(x, y, 6, 6) == true) {
					collision =0;
				}
			}
			if(collision == 0 ) {
				particleList[particleWanted] = new Particle(x, y, rotation, 0.1, -1, -1, -1);
				particleWanted++;
			}

		}
		return particleList;

	}


}
	@SuppressWarnings("serial")
	class myDrawLine extends JPanel {

		public Object[] myParticleList;

		public myDrawLine(Object[] particleList) {
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
				//Draw Particle 
				if(particle.getWeight()>0.7)
					g.setColor(new Color(255,0,0,255));
				else
					g.setColor(new Color(255,0,0,20));

				g.drawOval(particle.getX(), particle.getY(), 6, 6);
				//Draw orientation Line
				Line2D lineTest = new Line2D.Double(particle.getX() +3 , particle.getY() +3, particle.getX()+23, particle.getY()+3);
				AffineTransform richtung = 
				        AffineTransform.getRotateInstance(
				            Math.toRadians(particle.getOrientation()), lineTest.getX1(), lineTest.getY1());
			    ((Graphics2D) g).draw(richtung.createTransformedShape(lineTest));
			    //Draw lines for right and left
				Line2D lineTest2 = new Line2D.Double(particle.getX() +3 , particle.getY() +3, particle.getX()+13, particle.getY()+3);

			    AffineTransform rechts = AffineTransform.getRotateInstance(
			            Math.toRadians(particle.getOrientation()-90), lineTest.getX1(), lineTest.getY1());
			    
			    AffineTransform links = AffineTransform.getRotateInstance(
			            Math.toRadians(particle.getOrientation()+90), lineTest.getX1(), lineTest.getY1());
			    
			    g.setColor(new Color(0,0,255,80));
			    ((Graphics2D) g).draw(links.createTransformedShape(lineTest2));
			    ((Graphics2D) g).draw(rechts.createTransformedShape(lineTest2));
//			    g.setColor(Color.RED);
				


			}

		}

	}