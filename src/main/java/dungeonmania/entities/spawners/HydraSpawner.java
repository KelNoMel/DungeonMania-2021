package dungeonmania.entities.spawners;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.bosses.Hydra;
import dungeonmania.util.Position;

public class HydraSpawner extends Spawner {

    public HydraSpawner(Dungeon dungeon, String type, Position position, int tickSpawnRate,
            JSONObject entitySpecificData) {
        super(dungeon, type, position, tickSpawnRate, entitySpecificData);
        if (dungeon.getGamemode() == Gamemode.HARD) changeSpawnRate(50);
    }

    @Override
    public void spawnEntity() {
        if (getDungeon().getGamemode() == Gamemode.HARD) {
            new Hydra(getDungeon(), getPosition(), new JSONObject());
        }
        
    }

    @Override
    protected void loadJSONEntitySpecific(JSONObject entitySpecificData) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addJSONEntitySpecific(JSONObject baseJSON) {
        // TODO Auto-generated method stub
        
    }
    
}
