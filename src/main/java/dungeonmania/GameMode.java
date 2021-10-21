package dungeonmania;

public enum GameMode {
    PEACEFUL, STANDARD, HARD;
    
    public static GameMode getGameMode(String gameMode) throws IllegalArgumentException {
    	switch (gameMode) {
    	case "Peaceful":
    		return PEACEFUL;
    	case "Standard":
    		return STANDARD;
    	case "Hard":
    		return HARD;
    	default:
    		throw new IllegalArgumentException();
    	}
    }
}
