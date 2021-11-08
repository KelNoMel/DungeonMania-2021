package dungeonmania.entities.buildable;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.InputState;
import dungeonmania.util.Position;
import dungeonmania.entities.buildable.Buildable;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildable.Recipe;
import dungeonmania.components.CollectableComponent;
import dungeonmania.components.CollectableState;
import dungeonmania.components.AttackTypeEnum;
import dungeonmania.components.BattleItemComponent;
import dungeonmania.components.WeaponComponent;

/**
 * Bow Entity is a buildable weapon that can attack a extra times to simulate
 * having extra arrows to shoot at an enemy from a distance
 */
public class Bow extends Buildable {
	
	final private int totalDurability = 3;
	final private int damage = 4;

	public CollectableComponent collectableComponent;
	public BattleItemComponent battleItemComponent;
	public WeaponComponent weaponComponent;

	public Bow(Dungeon dungeon, Position position, CollectableState collectableState, JSONObject entitySpecificData) {
		super(dungeon,  BuildableEnum.BOW.getType(), position, false, BuildableEnum.BOW.getRecipes(), entitySpecificData);
		collectableComponent = new CollectableComponent(this, 1, collectableState);
		battleItemComponent = new BattleItemComponent(this, 2, totalDurability);
		weaponComponent = new WeaponComponent(this, 3, damage, AttackTypeEnum.EXTRA);
	}
			
	protected void inputEntity(InputState inputState) {}

	protected void updateEntity() {}
	
	public void addJSONEntitySpecific(JSONObject baseJSON) {}
	protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {}
}
