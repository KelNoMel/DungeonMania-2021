package dungeonmania.components.battles;

public class EnemyFighterState extends FighterState {
    protected BattleComponent owner;

    public EnemyFighterState(BattleComponent owner) {
        super(owner);
    }
}
