//### View.java ############################################################################################################################

package flee_and_catch.robot.view;

//### IMPORTS ##############################################################################################################################
import flee_and_catch.robot.robot.Robots;
import lejos.hardware.lcd.LCD;

/* View [Class]: Class that represents the graphical interface via LCD of the robot *//**
 * 
 * 
 * <u>Visibility:</u> package-wide
 * @author Manuel Bothner
 *
 */
class View {
	
//### PRIVATE METHODS ######################################################################################################################

	private void printFrame() {
		
		LCD.drawString("#F&C##########0.9#", 0, 0);
		LCD.drawChar('#', 0, 1);
		LCD.drawChar('#', 17, 1);
		LCD.drawChar('#', 0, 2);
		LCD.drawChar('#', 17, 2);
		LCD.drawChar('#', 0, 3);
		LCD.drawChar('#', 17, 3);
		LCD.drawChar('#', 0, 4);
		LCD.drawChar('#', 17, 4);
		LCD.drawChar('#', 0, 5);
		LCD.drawChar('#', 17, 5);
		LCD.drawChar('#', 0, 6);
		LCD.drawChar('#', 17, 6);
		LCD.drawString("##################", 0, 7);
		
	}
	
//### PACKAGEWIDE METHODS ##################################################################################################################
	
	void showRobotSelectionScreen(int pointer) {
		
		//Read out all elements of the enum:
		Robots[] possibleRobots = Robots.values();
		
		//Determine the current page on that the element on this the pointer points is:
		int curPage = pointer / 4;
		//Set array pointer on the start of the page:
		int arrayPointer = curPage * 4;
		//Saves the index of the pointed element on the page (0-3):
		int pagePointer = pointer % 4;	
		
		//Max number of shown elements:
		int max = 4;		//Are enough elements in the array the standard value is 4!
		//If there are not enough elements in the array cause it's the last page: 
		if(arrayPointer + 3 > possibleRobots.length) {
			max = possibleRobots.length % 4;	//Number of the remaining elements (of the last page)!
		}
		
		//Clear the display first:
		LCD.clear();
		
		//Draw the current elements of the enum:
		for(int i = 0; i < max; i++) {
			
			if(i == pagePointer) {
				//Draw als marked:
				LCD.drawString(possibleRobots[arrayPointer].getName(), 2, i + 2, true);
			}
			else {
				//Draw normal:
				LCD.drawString(possibleRobots[arrayPointer].getName(), 2, i + 2, false);
			}
			
			arrayPointer++;
		}
		
		//Draw the frame:
		this.printFrame();
		
		//Print info to what the current page is and how many pages exists:
		String pageInfo = "Page" + (curPage + 1) + "/" + (int) Math.ceil(possibleRobots.length / 4.0);
		LCD.drawString(pageInfo, 1, 7);
		
	}

//##########################################################################################################################################
}
//### EOF ##################################################################################################################################