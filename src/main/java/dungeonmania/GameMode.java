package dungeonmania;

public enum GameMode {
    
	PEACEFUL("Peaceful"), STANDARD("Standard"), HARD("Hard");
    
	private String gameMode;
	
    private GameMode(String gameMode) {
    	this.gameMode = gameMode;
	}
    
    public String asString() {
    	return gameMode;
    }

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
