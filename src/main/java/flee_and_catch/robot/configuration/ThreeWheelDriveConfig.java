package flee_and_catch.robot.configuration;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

public class ThreeWheelDriveConfig {
	public static final Port PORT_MOTOR_RIGHT = MotorPort.C;
	public static final Port PORT_MOTOR_LEFT = MotorPort.B;
	public static final Port PORT_ULTRASONIC = SensorPort.S4;
}
