package flee_and_catch.robot.communication.command;

public class SynchronizationType {
	protected String name;
	protected Type type;
	
	public enum Type {
		SetData
	}
	
	public SynchronizationType(Type pType){
		this.name = pType.toString();
		this.type = pType;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}
}