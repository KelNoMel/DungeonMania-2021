package dungeonmania.entities.bosses;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.components.AIComponent;
import dungeonmania.components.BattleComponent;
import dungeonmania.components.MoveComponent;
import dungeonmania.components.MovementType;
import dungeonmania.components.aistates.AIRandomHostile;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityUpdateOrder;
import dungeonmania.util.Position;

import org.json.JSONObject;

public class Hydra extends Entity {
	final int maxHealth = 150;
	final int damage = 20;
    
    public AIComponent aiComponent = new AIComponent(this, 1);
	public MoveComponent moveComponent = new MoveComponent(this, 2, MovementType.NORMAL);
	public BattleComponent battleComponent = new BattleComponent(this, 3, maxHealth, damage);
    
    public Hydra(Dungeon dungeon, Position position) {
		super(dungeon, "hydra", position, true, EntityUpdateOrder.OTHER);
		aiComponent.registerState(new AIRandomHostile(aiComponent, moveComponent));
		aiComponent.changeState("RandomHostile");
    }

    protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
    protected void inputEntity(InputState inputState) {}

    protected void updateEntity() {
        /*List<Entity> entities = getDungeon().getEntitiesInRadius(getPosition(), 2.0);
        
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
                if (aiComponent.getAIState() instanceof AIMercAlly) {
                    int percent = (int) Math.ceil(Math.random() * 100);
                    if (percent >= 50) {
                        battleComponent.setHealth(100);
                    } else {
                        battleComponent.dealDamage(15);
                    } 
                }
            }
        }*/
        
    }

    @Override
    public void saveJSONEntitySpecific(JSONObject baseJSON) {}
}
