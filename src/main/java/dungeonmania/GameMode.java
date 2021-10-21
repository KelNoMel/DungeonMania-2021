package dungeonmania;

public enum GameMode {
    PEACEFUL, STANDARD, HARD;
    
    public static GameMode getGameMode(String gamemode) throws IllegalArgumentException {
    	switch (gamemode) {
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
