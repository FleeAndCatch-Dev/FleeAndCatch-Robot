package flee_and_catch.robot.view;

import flee_and_catch.robot.communication.command.component.RobotType;
import flee_and_catch.robot.communication.command.device.robot.Position;
import flee_and_catch.robot.robot.Robot;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public final class ViewController {

//### ATTRIBUTES ###########################################################################################################################
	
	private static View view = new View();

//### PRIVATE METHODS ######################################################################################################################
	
	private static void clearScreen() {
		
		//              12345678901234567
		LCD.drawString("                  ", 0, 0);
		LCD.drawString("                  ", 0, 1);
		LCD.drawString("                  ", 0, 2);
		LCD.drawString("                  ", 0, 3);
		LCD.drawString("                  ", 0, 4);
		LCD.drawString("                  ", 0, 5);
		LCD.drawString("                  ", 0, 6);
		LCD.drawString("              v1.1", 0, 7);		
	}
	
	private static void showSelectRobot() {
		clearScreen();
		
		//              12345678901234567
		LCD.drawString(" F&C              ", 0, 0);
		LCD.drawString("                  ", 0, 1);
		LCD.drawString("     Press a      ", 0, 2);
		LCD.drawString("    button to     ", 0, 3);
		LCD.drawString("     select a     ", 0, 4);
		LCD.drawString("      robot!      ", 0, 5);
		LCD.drawString("                  ", 0, 6);
		LCD.drawString("              v1.1", 0, 7);		
	}
	
//### PUBLIC METHODS #######################################################################################################################	
	
	public static void showStartScreen() {
		clearScreen();
		
		//              12345678901234567
		LCD.drawString(" F&C              ", 0, 0);
		LCD.drawString("                  ", 0, 1);
		LCD.drawString("                  ", 0, 2);
		LCD.drawString("    Welcome to    ", 0, 3);
		LCD.drawString("   FleeAndCatch   ", 0, 4);
		LCD.drawString("                  ", 0, 5);
		LCD.drawString("                  ", 0, 6);
		LCD.drawString("              v1.1", 0, 7);
		
		Button.waitForAnyPress();
	}
	
	public static void showInitScreen() {
		clearScreen();
		
		//              12345678901234567
		LCD.drawString(" F&C              ", 0, 0);
		LCD.drawString("                  ", 0, 1);
		LCD.drawString("                  ", 0, 2);
		LCD.drawString(" Wait initialize  ", 0, 3);
		LCD.drawString("      Robot!      ", 0, 4);
		LCD.drawString("                  ", 0, 5);
		LCD.drawString("                  ", 0, 6);
		LCD.drawString("              v1.1", 0, 7);
		
	}
	
	public static void showErrorScreen(String msg) {
		clearScreen();
		
		//              12345678901234567
		LCD.drawString(" F&C              ", 0, 0);
		LCD.drawString("                  ", 0, 1);
		LCD.drawString("                  ", 0, 2);
		LCD.drawString("      Error:      ", 0, 3);
		LCD.drawString(" "   +  msg  +  " ", 0, 4);
		LCD.drawString("                  ", 0, 5);
		LCD.drawString("                  ", 0, 6);
		LCD.drawString("              v1.1", 0, 7);
		
	}
	
	public static void showExit() {
		clearScreen();
		
		//              12345678901234567
		LCD.drawString(" F&C              ", 0, 0);
		LCD.drawString("                  ", 0, 1);
		LCD.drawString("      Press       ", 0, 2);
		LCD.drawString("    escape to     ", 0, 3);
		LCD.drawString("      exit!       ", 0, 4);
		LCD.drawString("                  ", 0, 5);
		LCD.drawString("                  ", 0, 6);
		LCD.drawString("              v1.1", 0, 7);
	
	}
	
	public static void noConnection() {
		clearScreen();
		
		//              12345678901234567
		LCD.drawString(" F&C              ", 0, 0);
		LCD.drawString("                  ", 0, 1);
		LCD.drawString("  No connection   ", 0, 2);
		LCD.drawString("  could create.   ", 0, 3);
		LCD.drawString("                  ", 0, 4);
		LCD.drawString("    Try again!    ", 0, 5);
		LCD.drawString("                  ", 0, 6);
		LCD.drawString("              v1.1", 0, 7);
		
		Button.waitForAnyPress();
	}
	
	public static void showStatus(String status, Position position, double speed) {
		clearScreen();
		
		String x = Double.toString(((double) ((int) (position.getX() * 100))) / 100);
		String y = Double.toString(((double) ((int) (position.getY() * 100))) / 100);
		String o = Double.toString(((double) ((int) (position.getOrientation() * 100))) / 100);
		String s = Double.toString(((double) ((int) (speed * 100))) / 100);
		
		//              12345678901234567
		LCD.drawString(" F&C              ", 0, 0);
		LCD.drawString("     Status:      ", 0, 1);
		LCD.drawString("  " + status + "  ", 0, 2);
		LCD.drawString(" X: " + x + "     ", 0, 3);
		LCD.drawString(" Y: " + y + "     ", 0, 4);
		LCD.drawString(" O: " + o + "     ", 0, 5);
		LCD.drawString(" Speed: " + s + " ", 0, 6);
		LCD.drawString("              v1.1", 0, 7);
	}
	
	public static Robot getSelectedRobot() {
		
		//Show a start/info screen for robot selection:
		showSelectRobot();
		
		//Represents the selected robot:
		RobotType selectedRobot = null;
		
		//Saves the pressed button:
		int pressedBtn  = -1;
		//Pointer to the current robot:
		int pointer = 0;
		//Max number of robots:
		int numberOfRobots = RobotType.values().length;
		

		
		//Wait until user confirm by press a button:
		Button.waitForAnyPress();
		
		clearScreen();
		
		//Show a list of the possible robots to select one (for first button is pressed):
		view.showRobotSelectionScreen(pointer);
		
		//loop that handles the user interaction:
		do {
			
			//Read wait until a button is pressed an read out the event:
			pressedBtn = Button.waitForAnyPress();
			//Button sound:
			Sound.playTone(1500, 100);

			//Exploit the button event:
			switch(pressedBtn) {
			
				//Scroll up (if possible):
				case Button.ID_UP:
					if(pointer > 0) {
						pointer--;
					}
					break;
					
				//Scroll down (if possible):
				case Button.ID_DOWN:
					if(pointer < numberOfRobots - 1) {
						pointer++;
					}
					break;
					
				//Confirm selection:
				case Button.ID_ENTER:
					//Set the selected robot:
					selectedRobot = RobotType.values()[pointer];
					break;
					
				//If any other button was pressed:
				default:
					//nothing to do wait until a other button is pressed:
					break;
			}
			
			//Update view:
			view.showRobotSelectionScreen(pointer);
			
		//Keep in the loop until the enter or escape button is pressed:	
		} while(!(pressedBtn == Button.ID_ENTER || pressedBtn == Button.ID_ESCAPE));
		
		return selectedRobot.getRobot();
	}
	
}
