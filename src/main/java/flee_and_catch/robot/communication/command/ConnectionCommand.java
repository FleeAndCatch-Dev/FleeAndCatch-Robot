package flee_and_catch.robot.communication.command;

import flee_and_catch.robot.communication.command.device.Device;
import flee_and_catch.robot.communication.command.identification.ClientIdentification;

public class ConnectionCommand extends Command {
	private Device device;
	
	/**
	 * <h1>Constructor</h1>
	 * Create new connection object for json command.
	 * 
	 * @param pId Id as command type.
	 * @param pType Type as connection type.
	 * @param pClient Client object.
	 * 
	 * @author ThunderSL94
	 */
	public ConnectionCommand(String pId, String pType, ClientIdentification pIdentification, Device pDevice){
		super(pId, pType, pIdentification);
		this.device = pDevice;
	}

	public Device getDevice() {
		return device;
	}
}
