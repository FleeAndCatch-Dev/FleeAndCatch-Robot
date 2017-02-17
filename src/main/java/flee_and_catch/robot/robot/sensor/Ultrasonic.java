package flee_and_catch.robot.robot.sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.AbstractFilter;

public class Ultrasonic {

	private EV3UltrasonicSensor ultrasonicSensor;
	private SampleProvider provider;
	private boolean enable;
	
	public Ultrasonic(Port port){
		ultrasonicSensor = new EV3UltrasonicSensor(port);
		provider = ultrasonicSensor.getDistanceMode();
	}
	
	public float getDistance(){
		float[] values = new float[provider.sampleSize()];
		provider.fetchSample(values, 0);
		return values[0];
	}
	
	public void enable(){
		enable = true;
		ultrasonicSensor.enable();
	}
	public void disable(){
		enable = false;
		ultrasonicSensor.disable();
	}
	public boolean isEnable() {
		return enable;
	}
	
}
