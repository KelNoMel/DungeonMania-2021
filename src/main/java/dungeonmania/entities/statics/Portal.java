package dungeonmania.entities.statics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Portal extends Entity {

	private static Map<String, PortalLink> portalLinks = new HashMap<>();
	private class PortalLink {
		private Portal p1;
		private Portal p2;
		
		private PortalLink(Portal p1, Portal p2) {
			this.p1 = p1;
			this.p2 = p2;
		}
	}
	
	private String colour;
	// Entities teleported already this tick
	private List<Entity> teleportedEntities = new ArrayList<>();
	
	public Portal(Dungeon dungeon, Position position, String colour) {
		super(dungeon, "portal-" + colour, position, false);
		this.colour = colour;

		// Create a new portal link, and link this to the other portal of the same colour if link already exists
		if (portalLinks.putIfAbsent(colour, new PortalLink(this, null)) != null) {
			portalLinks.get(colour).p2 = this;
		}
	}

	protected void inputEntity(InputState inputState) {
		
	}

	protected void updateEntity() {
		List<Entity> entitiesOnThisPortal = dungeon.getEntitiesAtPosition(getPosition());

		entitiesOnThisPortal.remove(this);
		// Only teleport entities that haven't already been teleported this tick
		entitiesOnThisPortal.removeAll(teleportedEntities);
		
		Portal teleportPortal = getCorrespondingPortal();
		for (Entity e : entitiesOnThisPortal) {
			// Teleport!
			e.setPosition(teleportPortal.getPosition());
			teleportPortal.teleportedEntities.add(e);
		}
		teleportedEntities.clear();
	}
	
	private Portal getCorrespondingPortal() {
		if (getId().equals(portalLinks.get(colour).p1.getId())) {
			return portalLinks.get(colour).p2;
		} else {
			return portalLinks.get(colour).p1;
		}
	}
}