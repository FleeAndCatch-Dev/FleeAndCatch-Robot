package flee_and_catch.robot.localisation;

//### IMPORTS ##############################################################################################################################

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import flee_and_catch.robot.communication.command.device.robot.Position;



public class PositionTest {

//### CONSTANTS ############################################################################################################################

//### ATTRIBUTES ###########################################################################################################################
	
	private Position pos;

//### METHODS #############################################################################################################################
	
	@Before
	public void setUp() {
		this.pos = new Position();
	}
	
	@Test
	public void calculateNewOrientationTest() {
		
		Assert.assertEquals("The method calculateNewOrientation has a failure!", 0.0f, this.pos.getOrientation(), 0);
		
		this.pos.calculateNewOrientation(90.0f);
		Assert.assertEquals("The method calculateNewOrientation has a failure!", 90.0f, this.pos.getOrientation(), 0);
		this.pos.calculateNewOrientation(-90.0f);
		Assert.assertEquals("The method calculateNewOrientation has a failure!", 0.0f, this.pos.getOrientation(), 0);
		this.pos.calculateNewOrientation(400.0f);
		Assert.assertEquals("The method calculateNewOrientation has a failure!", 40.0f, this.pos.getOrientation(), 0);
		this.pos.calculateNewOrientation(-400.0f);
		Assert.assertEquals("The method calculateNewOrientation has a failure!", 0.0f, this.pos.getOrientation(), 0);
		
	}
	
	@Test
	public void calculateNewPositionTest() {
		
		Assert.assertEquals("The method calculateNewPosition has a failure!", 0.0f, this.pos.getX(), 0.0000001);
		Assert.assertEquals("The method calculateNewPosition has a failure!", 0.0f, this.pos.getY(), 0.0000001);
		
		this.pos.calculateNewPosition(40.0f);
		Assert.assertEquals("The method calculateNewPosition has a failure!", 40.0f, this.pos.getX(), 0.0000001);
		Assert.assertEquals("The method calculateNewPosition has a failure!", 0.0f, this.pos.getY(), 0.0000001);
		
		this.pos.setOrientation(90.0f);
		this.pos.calculateNewPosition(50.0f);
		Assert.assertEquals("The method calculateNewPosition has a failure!", 40.0f, this.pos.getX(), 0.0000001);
		Assert.assertEquals("The method calculateNewPosition has a failure!", 50.0f, this.pos.getY(), 0.0000001);
		
		this.pos.setX(0.0f);
		this.pos.setY(0.0f);
		this.pos.setOrientation(270);
		this.pos.calculateNewPosition(70.0f);
		Assert.assertEquals("The method calculateNewPosition has a failure!", 0.0f, this.pos.getX(), 0.0000001);
		Assert.assertEquals("The method calculateNewPosition has a failure!", -70.0f, this.pos.getY(), 0.0000001);
		
		this.pos.setX(0.0f);
		this.pos.setY(0.0f);
		this.pos.setOrientation(180);
		this.pos.calculateNewPosition(14.7f);
		Assert.assertEquals("The method calculateNewPosition has a failure!", -14.7f, this.pos.getX(), 0.0000001);
		Assert.assertEquals("The method calculateNewPosition has a failure!", 0.0f, this.pos.getY(), 0.0000001);
		
		this.pos.setX(0.0f);
		this.pos.setY(0.0f);
		this.pos.setOrientation(0.0f);
		this.pos.calculateNewPosition(250.0f);
		this.pos.calculateNewOrientation(90.0f);
		this.pos.calculateNewPosition(300.0f);
		Assert.assertEquals("The method calculateNewPosition has a failure!", 250.0f, this.pos.getX(), 0.0000001);
		Assert.assertEquals("The method calculateNewPosition has a failure!", 300.0f, this.pos.getY(), 0.0000001);
		
	}

	
//##########################################################################################################################################
}
//### EOF ##################################################################################################################################