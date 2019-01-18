package test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
 
import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class ClientTest {
	public static MovePilot pilot;
	public static SampleProvider distanceFront, distanceLeft, distanceRight, color;
	public static float[] sampleFront, sampleLeft, sampleRight, sampleColor;
	
	public static void hardwareCheck() {
		Wheel wheel1 = WheeledChassis.modelWheel(Motor.A, 70).offset(-100);
		Wheel wheel2 = WheeledChassis.modelWheel(Motor.D, 70).offset(100);
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
//		Chassis morgensternCh = new WheeledChassis(new Wheel[] {morgenstern}, WheeledChassis.TYPE_DIFFERENTIAL);
		//MovePilot
		pilot = new MovePilot(chassis);
		
		//Ultrasonic Sensors
		SensorModes usFront = new EV3UltrasonicSensor(SensorPort.S1);
		SensorModes usLeft = new EV3UltrasonicSensor(SensorPort.S2);
		SensorModes usRight = new EV3UltrasonicSensor(SensorPort.S3);
		//Color Sensors
		SensorModes colorDown = new EV3ColorSensor(SensorPort.S4);
		
		//SampleProviders for Ultrasonic Index0 is Distance
		distanceFront = usFront.getMode(0);
		distanceLeft = usLeft.getMode(0);
		distanceRight = usRight.getMode(0);
		//SampleProvider for Colorsensor Index 012 = RGB
		color = colorDown.getMode(0);
		
		//float arrays for samples
		sampleFront = new float[distanceFront.sampleSize()];
		sampleLeft = new float[distanceLeft.sampleSize()];
		sampleRight = new float[distanceRight.sampleSize()];
		sampleColor = new float[color.sampleSize()];
	}
	

  public static final int PORT = 80;
  private ServerSocket ss;
  private Socket sock;
 
  public ClientTest() throws IOException {
    ss = new ServerSocket(PORT);
  }
 
  public void run() throws IOException {
    for (;;) {
      sock = ss.accept();
      InputStream is = sock.getInputStream();
      OutputStream os = sock.getOutputStream();
 
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      PrintStream ps = new PrintStream(os);
 
      for (;;) {
        String cmd = br.readLine();
        if (cmd == null)
          break;
        String reply = execute(cmd);
        if (reply != null)
          ps.println(reply);
        else {
          br.close();
          ps.close();
          break;
        }
      }
 
    }
  }
  public String sensorValue;

  public String execute(String cmd) {
	
    
	System.out.println("cmd: " + cmd);
    String[] tokens = cmd.split(" ");
    String[] command = tokens[1].split("X");    
    if (tokens.length > 1 && tokens[0].equals("GET")) {
    	distanceFront.fetchSample(sampleFront, 0);
    	distanceLeft.fetchSample(sampleLeft, 0);
    	distanceRight.fetchSample(sampleRight, 0);
    	color.fetchSample(sampleColor, 0);
    	sensorValue = "Dist F: " + sampleFront[0] + " Dist L: " + sampleLeft[0] + " Dist R: " + sampleRight[0] + " Color: " + sampleColor[0];
      if (tokens[1].equals("/Hello")) {
        Sound.beepSequenceUp();
      } else if (tokens[1].equals("/Goodbye")) {
        Sound.beepSequence();
      } else if (tokens[1].equals("/Text")) {
        Sound.beep();
      } else if (command[0].equals("/fahren")) {
    	  String value = command[1];
    	  int distance = Integer.parseInt(value);
    	  pilot.setLinearSpeed(150);
		  pilot.travel(distance*10);
      } else if(command[0].equals("/links")) {
    	  String value = command[1];
    	  int rot = Integer.parseInt(value);
    	  pilot.setAngularSpeed(60);
  		  pilot.rotate(0 - rot);
      } else if(command[0].equals("/rechts")) {
    	  String value = command[1];
    	  int rot = Integer.parseInt(value);
    	  pilot.setAngularSpeed(60);
  		  pilot.rotate(rot);
      } else if(command[0].equals("/sensor")) {
    	  String value = command[1];
    	  if(value.equals("front")) {
    		  distanceFront.fetchSample(sampleFront, 0);
    		  sensorValue = Float.toString(sampleFront[0]);
    	  } else if(value.equals("left")) {
    		  distanceLeft.fetchSample(sampleLeft, 0);
    		  sensorValue = Float.toString(sampleLeft[0]);
    	  } else if(value.equals("right")) {
    		  distanceRight.fetchSample(sampleRight, 0);
    		  sensorValue = Float.toString(sampleRight[0]);
    	  } else if(value.equals("color")) {
    		  color.fetchSample(sampleColor, 0);
    		  sensorValue = String.valueOf(sampleColor[0]);
    	  } else if(value.equals("all")) {
    		  distanceFront.fetchSample(sampleFront, 0);
    		  distanceLeft.fetchSample(sampleLeft, 0);
    		  distanceRight.fetchSample(sampleRight, 0);
    		  color.fetchSample(sampleColor, 0);
    		  sensorValue = "Dist F: " + sampleFront[0] + " Dist L: " + sampleLeft[0] + " Dist R: " + sampleRight[0] + " Color: " + sampleColor[0];
    	  }
      }
      return "HTTP/1.1 200 OK\r\n\r\nOK\r\n " + sensorValue; 
    }
    return null;
  }
 
  public static void main(String[] args) throws IOException {
	  hardwareCheck();	  
	  new ClientTest().run();
  }
}
