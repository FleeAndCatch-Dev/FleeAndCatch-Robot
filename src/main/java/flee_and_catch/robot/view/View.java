package flee_and_catch.robot.view;

import flee_and_catch.robot.robot.RobotType;
import lejos.hardware.lcd.LCD;

public class View {

	private void printFrame() {
		
		LCD.drawString(" F&C              ", 0, 0);
		LCD.drawChar(' ', 0, 1);
		LCD.drawChar(' ', 17, 1);
		LCD.drawChar(' ', 0, 2);
		LCD.drawChar(' ', 17, 2);
		LCD.drawChar(' ', 0, 3);
		LCD.drawChar(' ', 17, 3);
		LCD.drawChar(' ', 0, 4);
		LCD.drawChar(' ', 17, 4);
		LCD.drawChar(' ', 0, 5);
		LCD.drawChar(' ', 17, 5);
		LCD.drawChar(' ', 0, 6);
		LCD.drawChar(' ', 17, 6);
		LCD.drawString("              v1.1", 0, 7);
		
	}
	
	void showRobotSelectionScreen(int pointer) {
		
		//Read out all elements of the enum:
		RobotType[] possibleRobots = RobotType.values();
		
		//Determine the current page on that the element on this the pointer points is:
		int curPage = pointer / 4;
		//Set array pointer on the start of the page:
		int arrayPointer = curPage * 4;
		//Saves the index of the pointed element on the page (0-3):
		int pagePointer = pointer % 4;	
		
		//Max number of shown elements:
		int max = RobotType.values().length;		//Are enough elements in the array the standard value is 4!
		//If there are not enough elements in the array cause it's the last page: 
		if(arrayPointer + 3 > possibleRobots.length) {
			max = possibleRobots.length % 4;	//Number of the remaining elements (of the last page)!
		}
		
		//Clear the display first:
		LCD.clear();
		
		//Draw the current elements of the enum:
		for(int i = 0; i < max; i++) {
			
			if(i == pagePointer) {
				//Draw as marked:
				LCD.drawString(possibleRobots[arrayPointer].toString(), 2, i + 2, true);
			}
			else {
				//Draw normal:
				LCD.drawString(possibleRobots[arrayPointer].toString(), 2, i + 2, false);
			}
			
			arrayPointer++;
		}
		
		//Draw the frame:
		this.printFrame();
		
		//Print info to what the current page is and how many pages exists:
		String pageInfo = "Page" + (curPage + 1) + "/" + (int) Math.ceil(possibleRobots.length / 4.0);
		LCD.drawString(pageInfo, 1, 7);
		
	}
}
