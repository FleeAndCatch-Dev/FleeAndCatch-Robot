package flee_and_catch.robot.robot.sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;

public class Gyro {

	private EV3GyroSensor gyroSensor;
	private SampleProvider provider;
	
	public Gyro(Port port){
		gyroSensor = new EV3GyroSensor(port);
		provider = gyroSensor.getAngleMode();
		gyroSensor.reset();
	}
	
	/**
	 * Get the angle started at the last reset
	 * @return
	 */
	public float getAngle(){
		float[] values = new float[provider.sampleSize()];
		provider.fetchSample(values, 0);
		return values[0];
	}
	
	/**
	 * Reset the gyro sensor
	 */
	public void reset(){
		gyroSensor.reset();
	}
}
