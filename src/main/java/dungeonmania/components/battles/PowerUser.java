package dungeonmania.components.battles;

public enum PowerUser {
    PLAYER(10), ENEMY(5);

    private int modifier;

    PowerUser(int modifier) {
        this.modifier = modifier;
    }

    public int getModifier() { return modifier; }

}
