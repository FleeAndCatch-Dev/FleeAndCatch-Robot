package flee_and_catch.robot.communication.command.identification;

public abstract class Identification {
	protected int id;
	protected String type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
}
