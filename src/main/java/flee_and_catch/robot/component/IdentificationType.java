package flee_and_catch.robot.component;

public class IdentificationType {

	private String name;
	private Typ typ;
	
	public enum Typ{
		Robot;
	}
	
	public IdentificationType(Typ pTyp){
		this.name = pTyp.toString();
		this.typ = pTyp;
	}

	public String getName() {
		return name;
	}

	public Typ getTyp() {
		return typ;
	}
}
