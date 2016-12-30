package flee_and_catch.robot;

//### IMPORTS ##############################################################################################################################
import lejos.hardware.Sound;

public class Configuration {

	
//### CONSTANTS ############################################################################################################################
	
	public static final String address = "192.168.100.100";
	public static final int port = 5000;
	private static final boolean SOUND  = true;
	private static final byte 	 VOLUME = 20;			//Range 0 to 100 (0 = no sound)
	
//### ATTRIBUTES ###########################################################################################################################

//### COMPONENTS ###########################################################################################################################

//### CONSTRUCTORS #########################################################################################################################

//### INITIAL METHODS ######################################################################################################################

//### INNER CLASSES ########################################################################################################################

//### GETTER/SETTER ########################################################################################################################

//### STATIC METHODS #######################################################################################################################
	
	static void applyConfigurations() {
		
		if(Configuration.SOUND) {
			Sound.setVolume(Configuration.VOLUME);
		}
		else {
			Sound.setVolume(0);
		}
		
	}

//##########################################################################################################################################	
}
//### EOF ##################################################################################################################################