package dungeonmania.entities.redstone;

public enum ActivationLogic {
	AND("and"), OR("or"), XOR("xor"), NOT("not"), COAND("co_and");
	
	private String logic;
	
	private ActivationLogic(String logic) {
		this.logic = logic;
	}
	
	public String getLogic() {
		return logic;
	}
	
	public static ActivationLogic createLogic(String logic) {
		switch (logic) {
			case "and":    return AND;
			case "or":     return OR;
			case "xor":    return XOR;
			case "not":    return NOT;
			case "co_and": return COAND;
			default: return null;
		}
	}
}
