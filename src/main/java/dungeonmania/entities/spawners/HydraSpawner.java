package dungeonmania.entities.spawners;

import dungeonmania.Dungeon;
import dungeonmania.Gamemode;
import dungeonmania.entities.bosses.Hydra;
import dungeonmania.util.Position;

public class HydraSpawner extends Spawner {

    public HydraSpawner(Dungeon dungeon, Position position) {
        super(dungeon, "hydra_spawner", position, 50);
    }

    public boolean spawnEntity() {
        if (getDungeon().getGamemode() == Gamemode.HARD) {
            new Hydra(getDungeon(), getPosition());
            return true;
        }
        return false;
    }

}
