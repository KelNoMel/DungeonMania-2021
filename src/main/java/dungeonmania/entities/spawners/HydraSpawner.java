package dungeonmania.entities.spawners;

import dungeonmania.Dungeon;
import dungeonmania.Gamemode;
import dungeonmania.entities.bosses.Hydra;
import dungeonmania.util.Position;

public class HydraSpawner extends Spawner {

    public HydraSpawner(Dungeon dungeon, String type, Position position, int tickSpawnRate) {
        super(dungeon, type, position, tickSpawnRate);
        if (dungeon.getGamemode() == Gamemode.HARD) changeSpawnRate(50);
    }

    @Override
    public boolean spawnEntity() {
        if (getDungeon().getGamemode() == Gamemode.HARD) {
            new Hydra(getDungeon(), getPosition());
            return true;
        }
        return false;
    }

}
