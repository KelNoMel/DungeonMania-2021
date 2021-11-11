package dungeonmania.entities.redstone;

import java.util.ArrayList;
import java.util.List;

public interface RedstoneSource {
	
	final List<RedstoneConduit> connectedConduits = new ArrayList<>();
	
	public default void registerObserver(RedstoneConduit obs) {
		if (!connectedConduits.contains(obs)) connectedConduits.add(obs);
	}
	
	public default void removeObserver(RedstoneConduit obs) {
		connectedConduits.remove(obs);
	}
	
	public default void notifyObservers() {
		for (RedstoneConduit conduit : connectedConduits) {
			conduit.updateConduit(this);
		}
	}
}
