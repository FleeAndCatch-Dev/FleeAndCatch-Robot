//### Interpreter.java #####################################################################################################################

package flee_and_catch.robot.communication;

//### IMPORTS ##############################################################################################################################
import java.util.Objects;

import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import flee_and_catch.robot.communication.command.CommandType;
import flee_and_catch.robot.communication.command.Connection;
import flee_and_catch.robot.communication.command.ConnectionType;
import flee_and_catch.robot.communication.command.SzenarioCommand;
import flee_and_catch.robot.communication.command.SzenarioCommandType;
import flee_and_catch.robot.communication.command.device.Device;
import flee_and_catch.robot.communication.command.device.DeviceAdapter;
import flee_and_catch.robot.communication.command.szenario.Control;
import flee_and_catch.robot.communication.command.szenario.ControlType;
import flee_and_catch.robot.communication.command.szenario.Szenario;
import flee_and_catch.robot.communication.command.szenario.SzenarioAdapter;
import flee_and_catch.robot.robot.RobotController;

/* Interpreter [class]: Class that parses and interprets arrived JSON objects *//**
 * 
 * @author Manuel Bothner
 *
 */
public final class Interpreter {

//### STATIC VARIABLES #####################################################################################################################

	private static Gson gson = new Gson();
	private static RobotController robotController = null;
	
//### CONSTRUCTORS #########################################################################################################################
	
	/* Interpreter [constructor]: Private constructor to prevent that objects are created */
	private Interpreter() {}
	
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
		ConnectionType type = ConnectionType.valueOf((String) pCommand.get("type"));
		
		//Serialize the JSON object to a Connection class object:
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Device.class, new DeviceAdapter());
		builder.setPrettyPrinting();
		Gson localgson = builder.create();
		Connection command = localgson.fromJson(pCommand.toString(), Connection.class);
		
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
	private static void szenario(JSONObject pCommand) throws Exception {
		
		if(pCommand == null) throw new NullPointerException();  //???
		
		SzenarioCommandType type = SzenarioCommandType.valueOf((String) pCommand.get("type"));
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Szenario.class, new SzenarioAdapter());
		builder.setPrettyPrinting();
		Gson localgson = builder.create();			
		SzenarioCommand command = localgson.fromJson(pCommand.toString(), SzenarioCommand.class);
		
		switch (type) {
			case Control:
				szenarioControl(command);
				break;
			case Synchron:
			
				break;
			case Follow:
	
				break;
			default:
				break;
		}
		
		//Read out the type of the control command:
		/*ControlType type = ControlType.valueOf((String) pCommand.get("type"));
		//Serialize the JSON object to a Control class object:
		Control command = gson.fromJson(pCommand.toString(), Control.class);
		
		switch(type){
			//Set the flag that indicates that the robot is controlled by an app:
			case Begin:
				Interpreter.robotController.setRobotActive(true);
				return;
			//Set the flag that indicates that the robot is controlled by an app:
			case End:
				Interpreter.robotController.setRobotActive(false);
				return;
			//Turn the steering of the robot on (steering commands get accepted and implemented):
			case Start:
				Interpreter.robotController.setAcceptSteering(true);
				return;
			//Turn the steering of the robot off:
			case Stop:
				Interpreter.robotController.setAcceptSteering(false);
				return;
			//Set a new steering command for the robot:
			case Control:
				Interpreter.robotController.setNewSteering(command.getSteering());
				return;
			default:
				throw new Exception("Argument out of range");
		}*/
	}
	
	private static void szenarioControl(SzenarioCommand pCommand) throws Exception{
		ControlType type = ControlType.valueOf(pCommand.getSzenario().getSzenariotype());
		switch(type){
		case Begin:
		case End:
			Interpreter.robotController.setRobotActive(pCommand.getSzenario().getRobots().get(0).isActive());
			return;
		case Start:
			//TODO: Add functionality!
			return;
		case Stop:
			//TODO: Add functionality!
			return;
		case Control:
			Control control = (Control) pCommand.getSzenario();
			Interpreter.robotController.setNewSteering(control.getSteering());
			//robotController.getLockSteering().lock();
			//Interpreter.robotController.controlRobot(control.getSteering());
			//robotController.getLockSteering().unlock();
			//Interpreter.viewController.showStatus("Control");
			return;
		default:
			throw new Exception("Argument out of range");
			}
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
		if(!Objects.equals((String) jsonCommand.get("apiid"), "@@fleeandcatch@@")) {
			throw new Exception("Wrong apiid of json command");	
		}
		
		//Read out the type of the command:
		CommandType type = CommandType.valueOf((String) jsonCommand.get("id"));
		
		switch(type){
			//Command to initialize a connection to the backend:
			case Connection:
				Interpreter.connection(jsonCommand);
				return;
			//Command to control the robot:
			case Szenario:
				Interpreter.szenario(jsonCommand);
				return;
			//Unkown command:
			default:
				throw new Exception("Argument out of range");
		}			
	}
	
	/* [Method]: Method to set the reference to the robot controller *//*
	 * 
	 */
	public static void setRobotController(RobotController robotController) {
		Interpreter.robotController = robotController;
	}
	
//##########################################################################################################################################
}
//### EOF ##################################################################################################################################
