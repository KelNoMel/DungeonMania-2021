package dungeonmania.entities.redstone;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.components.RedstoneSourceComponent;

public interface RedstoneConduit {

	final List<RedstoneSourceComponent> poweringComponents = new ArrayList<>();
	
	public void updateConduit(RedstoneSource src);
	
}
