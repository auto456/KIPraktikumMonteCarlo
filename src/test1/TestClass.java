package test1;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.TachoMotorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class TestClass
{
	public static void main(String[] args) {
//		Not needed because Chassis
//		RegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
//		RegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.D);		
		
		//Wheels and Chassis 2 Wheels
		Wheel wheel1 = WheeledChassis.modelWheel(Motor.A, 70).offset(-103);
		Wheel wheel2 = WheeledChassis.modelWheel(Motor.D, 70).offset(103);
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
//		Chassis morgensternCh = new WheeledChassis(new Wheel[] {morgenstern}, WheeledChassis.TYPE_DIFFERENTIAL);
		//MovePilot
		MovePilot pilot = new MovePilot(chassis);
		
		//Ultrasonic Sensors
		SensorModes usFront = new EV3UltrasonicSensor(SensorPort.S1);
		SensorModes usLeft = new EV3UltrasonicSensor(SensorPort.S2);
		SensorModes usRight = new EV3UltrasonicSensor(SensorPort.S3);
		//Color Sensors
		SensorModes colorDown = new EV3ColorSensor(SensorPort.S4);
		
		//SampleProviders for Ultrasonic Index0 is Distance
		SampleProvider distanceFront = usFront.getMode(0);
		SampleProvider distanceLeft = usLeft.getMode(0);
		SampleProvider distanceRight = usRight.getMode(0);
		//SampleProvider for Colorsensor Index 012 = RGB
		SampleProvider color = colorDown.getMode(2);
		
		//float arrays for samples
		float[] sampleFront = new float[distanceFront.sampleSize()];
		float[] sampleLeft = new float[distanceLeft.sampleSize()];
		float[] sampleRight = new float[distanceRight.sampleSize()];
		float[] sampleColor = new float[color.sampleSize()];
		
		//##########################################################
		//##########################################################
		//##########################################################		
		//Sensorwerte ausgeben
		distanceFront.fetchSample(sampleFront, 0);
		distanceLeft.fetchSample(sampleLeft, 0);
		distanceRight.fetchSample(sampleRight, 0);
		color.fetchSample(sampleColor, 0);
		System.out.println("Dist F: " + sampleFront[0]);
		System.out.println("Dist L: " + sampleLeft[0]);
		System.out.println("Dist R: " + sampleRight[0]);
		System.out.println("Color R: " + sampleColor[0] + " G: " + sampleColor[1] + " B: " + sampleColor[2]);
		Delay.msDelay(2000);
		
		//50cm geradeaus
		pilot.setLinearSpeed(150);
		pilot.travel(500);
		
		//Sensorwerte ausgeben
		distanceFront.fetchSample(sampleFront, 0);
		distanceLeft.fetchSample(sampleLeft, 0);
		distanceRight.fetchSample(sampleRight, 0);
		color.fetchSample(sampleColor, 0);			
		System.out.println("Dist F: " + sampleFront[0]);
		System.out.println("Dist L: " + sampleLeft[0]);
		System.out.println("Dist R: " + sampleRight[0]);
		System.out.println("Color R: " + sampleColor[0] + " G: " + sampleColor[1] + " B: " + sampleColor[2]);
		Delay.msDelay(2000);
		
		//90 Grad nach links
		pilot.setAngularSpeed(60);
		pilot.rotate(-90);
		Delay.msDelay(1000);

		//270 Grad nach rechts
		pilot.rotate(270);
		Delay.msDelay(1000);

		//50cm geradeaus
		pilot.travel(500);
		Delay.msDelay(1000);

		//Sensorwerte ausgeben
		distanceFront.fetchSample(sampleFront, 0);
		distanceLeft.fetchSample(sampleLeft, 0);
		distanceRight.fetchSample(sampleRight, 0);
		color.fetchSample(sampleColor, 0);			
		System.out.println("Dist F: " + sampleFront[0]);
		System.out.println("Dist L: " + sampleLeft[0]);
		System.out.println("Dist R: " + sampleRight[0]);
		System.out.println("Color R: " + sampleColor[0] + " G: " + sampleColor[1] + " B: " + sampleColor[2]);
		Delay.msDelay(2000);
			
		//180 Grad nach links
		pilot.rotate(-180);
		Delay.msDelay(1000);
		
		//Sensorwerte ausgeben
		distanceFront.fetchSample(sampleFront, 0);
		distanceLeft.fetchSample(sampleLeft, 0);
		distanceRight.fetchSample(sampleRight, 0);
		color.fetchSample(sampleColor, 0);			
		System.out.println("Dist F: " + sampleFront[0]);
		System.out.println("Dist L: " + sampleLeft[0]);
		System.out.println("Dist R: " + sampleRight[0]);
		System.out.println("Color R: " + sampleColor[0] + " G: " + sampleColor[1] + " B: " + sampleColor[2]);		
		
//		//Morgenstern ausrasten
//		@SuppressWarnings("resource")
//		RegulatedMotor morgenStern = new EV3MediumRegulatedMotor(MotorPort.C);
//
//		morgenStern.rotate(360);

	}
}
