package dungeonmania.entities.bosses;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.AIState;
import dungeonmania.components.BattleComponent;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.components.aistates.AIMercAlly;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.entities.Player;
import dungeonmania.entities.moving.Mercenary;
import dungeonmania.util.Position;

import java.util.List;

import org.json.JSONObject;

public class Hydra extends Entity {

    
    public AIComponent aiComponent = new AIComponent(this, 1);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);
	public BattleComponent battleComponent = new BattleComponent(this, 3, 30, 10);

    
    public Hydra(Dungeon dungeon, Position position, JSONObject entitySpecificData) {
		super(dungeon, "hydra", position, true, EntityUpdateOrder.OTHER, entitySpecificData);
    }

    @Override
    protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}

    @Override
    protected void inputEntity(InputState inputState) {}

    @Override
    protected void updateEntity() {
        List<Entity> entities = getDungeon().getEntitiesInRadius(getPosition(), 2.0);
        for (Entity e : entities) {
            if (e instanceof Player) {
                int percent = (int) Math.ceil(Math.random() * 100);
			    if (percent >= 50) {
                    battleComponent.setHealth(100);
                } else {
                    battleComponent.dealDamage(15);
                }
            }
            // check to see if assassins/mercenary are allies
            if (e instanceof Mercenary || e instanceof Assassin) {
                if (aiComponent.getAISate() instanceof AIMercAlly) {
                    int percent = (int) Math.ceil(Math.random() * 100);
                    if (percent >= 50) {
                        battleComponent.setHealth(100);
                    } else {
                        battleComponent.dealDamage(15);
                    } 
                }
            }
        }
        
    }

    @Override
    public void addJSONEntitySpecific(JSONObject baseJSON) {}
}
