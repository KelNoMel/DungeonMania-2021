package dungeonmania.entities.bosses;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.BattleComponent;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Position;
import org.json.JSONObject;

public class Hydra extends Entity {

    public AIComponent aiComponent = new AIComponent(this, 1);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);
	public BattleComponent battleComponent = new BattleComponent(this, 3, 30, 10);

    
    public Hydra(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "hydra", position, true, EntityUpdateOrder.OTHER, entitySpecificData);
    }

    @Override
    protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {
        
    }

    @Override
    protected void inputEntity(InputState inputState) {
        
    }

    @Override
    protected void updateEntity() {
        List<Entity> entities = getDungeon().getEntitiesInRadius(getPosition(), 2.0);
        for (Entity e : entities) {
            if (e instanceof Player
        
    }

    @Override
    public void addJSONEntitySpecific(JSONObject baseJSON) {
        
        
    }
}
