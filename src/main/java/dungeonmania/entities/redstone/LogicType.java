package dungeonmania.entities.redstone;

public enum LogicType {
	AND("and"), OR("or"), XOR("xor"), NOT("not"), COAND("co_and");

	private String type;
	
	LogicType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public static LogicType getLogicType(String logicType) throws IllegalArgumentException {
    	switch (logicType) {
	    	case "and":
	    		return AND;
	    	case "or":
	    		return OR;
	    	case "xor":
	    		return XOR;
	    	case "not":
	    		return NOT;
	    	case "co_and":
	    		return COAND;
	    	default:
	    		throw new IllegalArgumentException();
    	}
    }
}
