package flee_and_catch.robot.configuration;

import lejos.hardware.Sound;

public final class SoundConfig {

	private static final boolean SOUND  = true;
	private static final byte 	 VOLUME = 20;			//Range 0 to 100 (0 = no sound)
	
	public static void applyConfigurations() {
		
		if(SOUND) {
			Sound.setVolume(VOLUME);
		}
		else {
			Sound.setVolume(0);
		}
		
	}
}
