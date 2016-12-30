package flee_and_catch.robot.communication.command;

import flee_and_catch.robot.communication.identification.ClientIdentification;

public abstract class Command {
	protected String id;
	protected String type;
	protected String apiid;
	protected String errorhandling;
	protected ClientIdentification identification;


	/**
	 * <h1>Constructor</h1>
	 * Create new command object.
	 * 
	 * @param pId Id as command type.
	 * @param pType Type as command sub type.
	 * 
	 * @author ThunderSL94
	 */
	protected Command(String pId, String pType, ClientIdentification pIdentification){
		this.id = pId;
		this.type = pType;
		this.errorhandling = "ignoreerrors";
		this.apiid = "@@fleeandcatch@@";
		this.identification = pIdentification;
	}
	
	/**
	 * <h1>Get command</h1>
	 * Get command as json string.
	 * 
	 * @return Json string.
	 * 
	 * @author ThunderSL94
	 */
	public abstract String getCommand();

	public String getId() {
		return id;
	}
	
	public String getType() {
		return type;
	}

	public String getApiid() {
		return apiid;
	}

	public String getErrorhandling() {
		return errorhandling;
	}
	
	public ClientIdentification getIdentification() {
		return identification;
	}
}
