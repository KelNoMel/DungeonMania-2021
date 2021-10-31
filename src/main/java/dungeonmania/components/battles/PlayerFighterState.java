package dungeonmania.components.battles;

public class PlayerFighterState extends FighterState {
    protected BattleComponent owner;

    public PlayerFighterState(BattleComponent owner) {
        super(owner);
    }
}
