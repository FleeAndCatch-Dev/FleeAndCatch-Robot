package flee_and_catch.robot.communication.command.identification;

import flee_and_catch.robot.communication.command.device.robot.RoleType;

public class RobotIdentification extends Identification {
	private String subtype;
	private String roletype;
	
	public RobotIdentification(int pId, String pType, String pSubtype, String pRoletype){
		this.id = pId;
		this.type = IdentificationType.valueOf(pType).toString();
		this.subtype = pSubtype;
		this.roletype = RoleType.valueOf(pRoletype).toString();
	}
	
	public String getSubtype() {
		return this.subtype;
	}

	public String getRole() {
		return this.roletype;
	}
}
