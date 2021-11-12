package dungeonmania.entities;

public enum EntityUpdateOrder {
	
	PLAYER(1),
	OTHER(2),
	SPAWNER(3),
	BATTLERESOLVER(4);
	
	private int updateOrder;
	
	EntityUpdateOrder(int d) {
		updateOrder = d;
	}
	
	public int updateOrder() {
		return updateOrder;
	}
	
}
