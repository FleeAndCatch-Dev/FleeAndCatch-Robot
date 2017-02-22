//### Interpreter.java #####################################################################################################################

package flee_and_catch.robot.communication;

import java.util.Objects;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import flee_and_catch.robot.communication.command.CommandType;
import flee_and_catch.robot.communication.command.ConnectionCommand;
import flee_and_catch.robot.communication.command.ConnectionCommandType;
import flee_and_catch.robot.communication.command.ControlCommand;
import flee_and_catch.robot.communication.command.ControlCommandType;
import flee_and_catch.robot.communication.command.ExceptionCommand;
import flee_and_catch.robot.communication.command.ExceptionCommandType;
import flee_and_catch.robot.communication.command.device.Device;
import flee_and_catch.robot.communication.command.device.DeviceAdapter;
import flee_and_catch.robot.communication.command.device.robot.Robot;
import flee_and_catch.robot.communication.command.identification.Identification;
import flee_and_catch.robot.communication.command.identification.IdentificationAdapter;
import flee_and_catch.robot.robot.RobotController;
import flee_and_catch.robot.view.ViewController;

//### IMPORTS ##############################################################################################################################

/* Interpreter [class]: Class that parses and interprets arrived JSON objects *//**
 * 
 * @author Simon Lang
 * @author Manuel Bothner
 *
 */
public final class Interpreter {

//### STATIC VARIABLES #####################################################################################################################
	
	private static boolean syncThread;
	
//### PRIVATE STATIC METHODS ###############################################################################################################

	/* connection [Method]: Processes a JSON object of the type connection to initialize the connection to the backend *//**
	 * <h1>Connection</h1>
	 * Parse connection command.
	 * 
	 * @param pCommand Command as json object.
	 * @throws Exception
	 * 
	 * ThunderSL94
	 */
	private static void connection(JSONObject pCommand) throws Exception {
		
		//Serialize the JSON object to a Connection class object:
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Identification.class, new IdentificationAdapter());
		builder.registerTypeAdapter(Device.class, new DeviceAdapter());
		builder.setPrettyPrinting();
		Gson localgson = builder.create();
		ConnectionCommand command = localgson.fromJson(pCommand.toString(), ConnectionCommand.class);
		
		//Read out the type of the connection command:
		ConnectionCommandType type = ConnectionCommandType.valueOf(command.getType());
		
		switch(type){
			//Set the id of this client:
			case Connect:
				Client.getClientIdentification().setId(command.getIdentification().getId());
				((Robot)Client.getDevice()).getIdentification().setId(command.getIdentification().getId());
				RobotController.getRobot().getIdentification().setId(command.getIdentification().getId());
				return;
			//Disconnect the client:
			case Disconnect:
				Client.disconnect();
				return;
		}
	}
	
	/* control [Method]: Processes a JSON object of the type control for steering the robot *//**
	 * 
	 * @param pCommand
	 * @throws Exception
	 */
	private static void control(JSONObject pCommand) throws Exception {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Identification.class, new IdentificationAdapter());
		builder.setPrettyPrinting();
		Gson localgson = builder.create();
		
		//Serialize the JSON object to a Control class object:
		ControlCommand command = localgson.fromJson(pCommand.toString(), ControlCommand.class);
		
		//Read out the type of the control command:
		ControlCommandType type = ControlCommandType.valueOf(command.getType());
		
		switch(type){
		//Set the flag that indicates that the robot is controlled by an app:
		case Begin:
			RobotController.intitComponents();
			RobotController.changeActive(true);
			syncThread = true;
			RobotController.setAccept(true);
			break;
		//Set the flag that indicates that the robot is controlled by an app:
		case End:
			RobotController.getRobot().stop();
			RobotController.changeActive(false);
			break;
		//Turn the steering of the robot on (steering commands get accepted and implemented):
		case Start:
			RobotController.setAccept(true);
			break;
		//Turn the steering of the robot off:
		case Stop:
			RobotController.getRobot().stop();
			RobotController.setAccept(false);
			break;
		//Set a new steering command for the robot:
		case Control:
			RobotController.setSteering(command.getSteering());
			if(syncThread){
				RobotController.getSteeringThread().start();
				RobotController.getSynchronizeThread().start();
				syncThread = false;
			}
			break;
		}
		
		if(type != ControlCommandType.End){
			ViewController.showStatus(type.toString(), RobotController.getRobot().getPosition(), RobotController.getRobot().getRealSpeed());
		}
		else {
			ViewController.showExit();
		}
	}
	
	private static void exception(JSONObject pCommand) throws Exception{
				
		//Deserialize the JSON object to a Connection class object:
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Identification.class, new IdentificationAdapter());
		builder.registerTypeAdapter(Device.class, new DeviceAdapter());
		builder.setPrettyPrinting();
		Gson localgson = builder.create();
		ExceptionCommand command = localgson.fromJson(pCommand.toString(), ExceptionCommand.class);
		
		//Read out the type of the connection command:
		ExceptionCommandType type = ExceptionCommandType.valueOf(command.getType());
		
		switch (type) {
			case Undefined:
				throw new Exception(command.getException().getMessage());
			case UnhandeldDisconnection:
				RobotController.changeActive(false);
				RobotController.getRobot().stop();
				break;
		}
		
		//Get new exception
		//TODO
	}
	
//### PUBLIC STATIC METHODS ################################################################################################################

	/* parse [Method]: Method to parse a command (JSON object as string): *//**
	 * <h1>Parse command</h1>
	 * Parse json command and run it, when the parsing is correct. Sort it in different id.
	 * 
	 * @param pCommand Command as json command.
	 * @throws Exception
	 * 
	 * @author ThunderSL94
	 */
	public static void parse(String pCommand) throws Exception {
		
		//Convert string to JSON object:
		JSONObject jsonCommand = new JSONObject(pCommand);
		
		//If the command has not the right identification:
		if(Objects.equals((String) jsonCommand.get("apiid"), "@@fleeandcatch@@")){
			//Read out the type of the command:
			CommandType type = CommandType.valueOf((String) jsonCommand.get("id"));
			
			switch(type){
				//Command to initialize a connection to the backend:
				case Connection:
					connection(jsonCommand);
					return;
				//Command to control the robot:
				case Control:
					control(jsonCommand);
					return;
				case Exception:
					exception(jsonCommand);
					return;
				case Synchronization:
					return;
			default:
				break;
			}
		}
		return;
	}
	
//##########################################################################################################################################
}
//### EOF ##################################################################################################################################
