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
import flee_and_catch.robot.robot.RobotController;

//### IMPORTS ##############################################################################################################################

/* Interpreter [class]: Class that parses and interprets arrived JSON objects *//**
 * 
 * @author Simon Lang
 * @author Manuel Bothner
 *
 */
public final class Interpreter {

//### STATIC VARIABLES #####################################################################################################################
	
	private static Gson gson = new Gson();
	
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
		
		//Read out the type of the connection command:
		ConnectionCommandType type = ConnectionCommandType.valueOf((String) pCommand.get("type"));
		
		//Serialize the JSON object to a Connection class object:
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Device.class, new DeviceAdapter());
		builder.setPrettyPrinting();
		Gson localgson = builder.create();
		ConnectionCommand command = localgson.fromJson(pCommand.toString(), ConnectionCommand.class);
		
		switch(type){
			//Set the id of this client:
			case Connect:
				Client.getClientIdentification().setId(command.getIdentification().getId());
				return;
			//Disconnect the client:
			case Disconnect:
				Client.disconnect();
				return;
			//Unknown connection type:
			default:
				throw new Exception("Argument out of range");
		}
	}
	
	/* control [Method]: Processes a JSON object of the type control for steering the robot *//**
	 * 
	 * @param pCommand
	 * @throws Exception
	 */
	private static void control(JSONObject pCommand) throws Exception {
		
		//Read out the type of the control command:
		ControlCommandType type = ControlCommandType.valueOf((String) pCommand.get("type"));
		//Serialize the JSON object to a Control class object:
		ControlCommand command = gson.fromJson(pCommand.toString(), ControlCommand.class);
		
		switch(type){
		//Set the flag that indicates that the robot is controlled by an app:
		case Begin:
			RobotController.getRobot().setActive(true);
			RobotController.getSteeringThread().start();
			RobotController.getSynchronizeThread().start();
			return;
		//Set the flag that indicates that the robot is controlled by an app:
		case End:
			//RobotController.setRobotActive(false);
			//TODO
			return;
		//Turn the steering of the robot on (steering commands get accepted and implemented):
		case Start:
			//RobotController.setAcceptSteering(true);
			//TODO
			return;
		//Turn the steering of the robot off:
		case Stop:
			//RobotController.setAcceptSteering(false);
			//TODO
			return;
		//Set a new steering command for the robot:
		case Control:
			RobotController.setSteering(command.getSteering());
			return;
		default:
			throw new Exception("Argument out of range");
		}
	}
	
	private static void exception(JSONObject pCommand){
		//Read out the type of the connection command:
		ExceptionCommandType type = ExceptionCommandType.valueOf((String) pCommand.get("type"));
		
		//Deserialize the JSON object to a Connection class object:
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Device.class, new DeviceAdapter());
		builder.setPrettyPrinting();
		Gson localgson = builder.create();
		ExceptionCommand command = localgson.fromJson(pCommand.toString(), ExceptionCommand.class);
		
		switch (type) {
			case Undefined:
				break;
			case UnhandeldDisconnection:
				//Set active of robot false
				break;
			default:
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
				//Unkown command:
				default:
					throw new Exception("Argument out of range");
			}
		}
		return;
	}
	
//##########################################################################################################################################
}
//### EOF ##################################################################################################################################
