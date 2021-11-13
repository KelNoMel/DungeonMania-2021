package dungeonmania;

public enum GameMode {
    
	PEACEFUL("peaceful"), STANDARD("standard"), HARD("hard");
    
	private String gameMode;
	
    private GameMode(String gameMode) {
    	this.gameMode = gameMode;
	}
    
    public String asString() {
    	return gameMode;
    }

	public static GameMode getGameMode(String gameMode) throws IllegalArgumentException {
    	gameMode = gameMode.toLowerCase();
		switch (gameMode) {
    	case "peaceful":
    		return PEACEFUL;
    	case "standard":
    		return STANDARD;
    	case "hard":
    		return HARD;
    	default:
    		throw new IllegalArgumentException();
    	}
    }
}
