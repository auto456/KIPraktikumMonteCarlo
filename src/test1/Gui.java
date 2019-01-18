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

	public static void main(String[] args) throws InterruptedException {
		Gui frame = new Gui();

		JPanel panel = new JPanel();

		frame.setTitle("Robot GUI");
		frame.setSize(750, 300);
		frame.setResizable(false);
		frame.setLocation(400, 150);

		Object[] particleList = generateParticle(1000);
		myDrawLine mDL = new myDrawLine(particleList);
		panel.add(mDL);
		frame.add(panel);
		
		frame.setVisible(true);
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
				particleList[particleWanted] = new Particle(x, y, rotation, 0.1);
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
//		g.setColor(Color.black);

			Line2D line0 = new Line2D.Double(0, 50, 100, 50);
			Line2D line1 = new Line2D.Double(100, 50, 100, 0);
			Line2D line2 = new Line2D.Double(100, 0, 150, 0);
			Line2D line3 = new Line2D.Double(150, 0, 150, 50);
			Line2D line4 = new Line2D.Double(150, 50, 200, 50);
			Line2D line5 = new Line2D.Double(200, 50, 200, 0);
			Line2D line6 = new Line2D.Double(200, 0, 300, 0);
			Line2D line7 = new Line2D.Double(300, 0, 300, 50);
			Line2D line8 = new Line2D.Double(300, 50, 350, 50);
			Line2D line9 = new Line2D.Double(350, 50, 350, 0);
			Line2D line10 = new Line2D.Double(350, 0, 400, 0);
			Line2D line11 = new Line2D.Double(400, 0, 400, 50);
			Line2D line12 = new Line2D.Double(400, 50, 450, 50);
			Line2D line13 = new Line2D.Double(450, 50, 450, 0);
			Line2D line14 = new Line2D.Double(450, 0, 550, 0);
			Line2D line15 = new Line2D.Double(550, 0, 550, 50);
			Line2D line16 = new Line2D.Double(550, 50, 600, 50);
			Line2D line17 = new Line2D.Double(600, 50, 600, 100);
			Line2D line18 = new Line2D.Double(600, 100, 500, 100);
			Line2D line19 = new Line2D.Double(500, 100, 500, 150);
			Line2D line20 = new Line2D.Double(500, 150, 450, 150);
			Line2D line21 = new Line2D.Double(450, 150, 450, 100);
			Line2D line22 = new Line2D.Double(450, 100, 400, 100);
			Line2D line23 = new Line2D.Double(400, 100, 400, 150);
			Line2D line24 = new Line2D.Double(400, 150, 300, 150);
			Line2D line25 = new Line2D.Double(300, 150, 300, 100);
			Line2D line26 = new Line2D.Double(300, 100, 250, 100);
			Line2D line27 = new Line2D.Double(250, 100, 250, 150);
			Line2D line28 = new Line2D.Double(250, 150, 200, 150);
			Line2D line29 = new Line2D.Double(200, 150, 200, 100);
			Line2D line30 = new Line2D.Double(200, 100, 100, 100);
			Line2D line31 = new Line2D.Double(100, 100, 100, 150);
			Line2D line32 = new Line2D.Double(100, 150, 50, 150);
			Line2D line33 = new Line2D.Double(50, 150, 50, 100);
			Line2D line34 = new Line2D.Double(50, 100, 0, 100);
			Line2D line35 = new Line2D.Double(0, 100, 0, 50);

			ArrayList<Line2D> lines = new ArrayList();

			lines.add(line0);
			lines.add(line1);
			lines.add(line2);
			lines.add(line3);
			lines.add(line4);
			lines.add(line5);
			lines.add(line6);
			lines.add(line7);
			lines.add(line8);
			lines.add(line9);
			lines.add(line10);
			lines.add(line11);
			lines.add(line12);
			lines.add(line13);
			lines.add(line14);
			lines.add(line15);
			lines.add(line16);
			lines.add(line17);
			lines.add(line18);
			lines.add(line19);
			lines.add(line20);
			lines.add(line21);
			lines.add(line22);
			lines.add(line23);
			lines.add(line24);
			lines.add(line25);
			lines.add(line26);
			lines.add(line27);
			lines.add(line28);
			lines.add(line29);
			lines.add(line30);
			lines.add(line31);
			lines.add(line32);
			lines.add(line33);
			lines.add(line34);
			lines.add(line35);

			g.setColor(Color.black);

			for (Line2D lineX : lines) {
				((Graphics2D) g).draw(lineX);
			}

			g.setColor(Color.RED);

//        g.drawLine(13, 10, 13, 16);
			for (Particle particle : (Particle[]) myParticleList) {

				int orientationX = 0, orientationY = 0;

				if (particle.getOrientation() < 22 && particle.getOrientation() > -22) {
					orientationX = 0;
					orientationY = -9;
				} else if (particle.getOrientation() < 64 && particle.getOrientation() >= 22) {
					orientationX = 6;
					orientationY = -6;
				} else if (particle.getOrientation() < 115 && particle.getOrientation() >= 64) {
					orientationX = 9;
					orientationY = 0;
				} else if (particle.getOrientation() < 150 && particle.getOrientation() >= 115) {
					orientationX = 6;
					orientationY = 6;
				} else if (particle.getOrientation() < 200 && particle.getOrientation() >= 150) {
					orientationX = 0;
					orientationY = 9;
				} else if (particle.getOrientation() < 245 && particle.getOrientation() >= 200) {
					orientationX = -6;
					orientationY = 6;
				} else if (particle.getOrientation() < 290 && particle.getOrientation() >= 245) {
					orientationX = -9;
					orientationY = 0;
				} else if (particle.getOrientation() < 335 && particle.getOrientation() >= 290) {
					orientationX = -6;
					orientationY = -6;
				} else if (particle.getOrientation() <= 360 && particle.getOrientation() >= 335) {
					orientationX = 0;
					orientationY = -9;
				}

				g.drawOval(particle.getX(), particle.getY(), 6, 6);
				g.drawLine(particle.getX() + 3, particle.getY() + 3, particle.getX() + orientationX,
						particle.getY() + orientationY);
				


			}

		}

	}