package dungeonmania.components.battles;

public class AllyFighterState extends FighterState {
    protected BattleComponent owner;

    public AllyFighterState(BattleComponent owner) {
        super(owner);
    }
}
