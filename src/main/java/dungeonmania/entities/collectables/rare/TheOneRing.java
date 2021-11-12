package dungeonmania.entities.collectables.rare;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityState;
import dungeonmania.entities.Player;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.util.Position;

public class TheOneRing extends Entity {
	private CollectableComponent collectableComp;

	public TheOneRing(Dungeon dungeon, Position position, CollectableState itemState, JSONObject entitySpecificData) {
		super(dungeon, "the_one_ring", position, false, entitySpecificData);
		collectableComp = new CollectableComponent(this, 1, itemState);
	}

	protected void inputEntity(InputState inputState) {

	}

	protected void updateEntity() {
		Player p = getDungeon().getPlayer();
		// Automatically revives the player at hp <= 0, should override battleComponent saying player is dead
		if (p.getHealth() <= 0 || p.getState() == EntityState.DEAD 
			&& collectableComp.getCollectableState() == CollectableState.INVENTORY) {
			
			p.setHealth(100);
			p.setState(EntityState.ACTIVE);
			// Item gets used
			this.setState(EntityState.DEAD);
		}

	}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
	
}
