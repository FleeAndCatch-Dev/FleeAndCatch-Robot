//### ViewController.java ##################################################################################################################

package flee_and_catch.robot.view;

import flee_and_catch.robot.component.RobotType;
//### IMPORTS ##############################################################################################################################
import flee_and_catch.robot.robot.Robot;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

/* ViewController [Class]: Class that handles the view (robot display) and interacts with the user *//**
 * 
 * @author Manuel Bothner
 *
 */
public class ViewController {

	//Display = 18 Chars in 8 rows

//### ATTRIBUTES ###########################################################################################################################
	
	private View view;

//### CONSTRUCTORS #########################################################################################################################

	public ViewController() {
		this.view = new View();
	}
	
//### PRIVATE METHODS ######################################################################################################################

	private void showSelectRobotStartScreen() {
		
		//              12345678901234567
		LCD.drawString("#F&C#########0.9#", 0, 0);
		LCD.drawString("#               #", 0, 1);
		LCD.drawString("#    Press a    #", 0, 2);
		LCD.drawString("#   button to   #", 0, 3);
		LCD.drawString("#   select a    #", 0, 4);
		LCD.drawString("#     robot!    #", 0, 5);
		LCD.drawString("#               #", 0, 6);
		LCD.drawString("#################", 0, 7);
		
	}

//### PUBLIC METHODS #######################################################################################################################	
	
	public Robot getSelectedRobot() {
		
		//Represents the selected robot:
		RobotType selectedRobot = null;
		
		//Saves the pressed button:
		int pressedBtn  = -1;
		//Pointer to the current robot:
		int pointer = 0;
		//Max number of robots:
		int numberOfRobots = RobotType.values().length;
		
		//Show a start/info screen for robot selection:
		this.showSelectRobotStartScreen();
		
		//Wait until user confirm by press a button:
		Button.waitForAnyPress();
		
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
		
		return null; //selectedRobot.getRobot();
	}
	
//##########################################################################################################################################
}
//### EOF ##################################################################################################################################

