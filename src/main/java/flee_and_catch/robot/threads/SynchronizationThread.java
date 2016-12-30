package flee_and_catch.robot.threads;

import flee_and_catch.robot.communication.Client;
import flee_and_catch.robot.communication.command.CommandType;
import flee_and_catch.robot.communication.command.Synchronization;
import flee_and_catch.robot.communication.command.SynchronizationType;
import flee_and_catch.robot.robot.Robot;

//### IMPORTS ##############################################################################################################################
public class SynchronizationThread implements Runnable {

//### ATTRIBUTES ###########################################################################################################################
	
	private Robot robot;
	
//### CONSTRUCTORS #########################################################################################################################

	public SynchronizationThread(Robot robot) {
		this.robot = robot;
	}

//### METHODS ##############################################################################################################################

	@Override
	public void run() {
		
		//Create synchronization object:
		Synchronization sync = new Synchronization(CommandType.Synchronization.toString(),SynchronizationType.SetData.toString(), Client.getCompleteIdentification(), robot);
		
		try {
			Client.sendCmd(sync.getCommand());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
		
	}

}
//### EOF ##################################################################################################################################