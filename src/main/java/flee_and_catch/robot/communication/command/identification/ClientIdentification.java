package flee_and_catch.robot.communication.command.identification;

public class ClientIdentification extends Identification {

	private String address;
	private int port;
	
	public ClientIdentification(int pId, String pType, String pAddress, int pPort){
		this.id = pId;
		this.type = IdentificationType.valueOf(pType).toString();
		this.address = pAddress;
		this.port = pPort;
	}
	
	public ClientIdentification(ClientIdentification pClientIdentification){
		this.id = pClientIdentification.getId();
		this.type = pClientIdentification.getType();
		this.address = pClientIdentification.getAddress();
		this.port = pClientIdentification.getPort();
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}
}
