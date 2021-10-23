package dungeonmania;

public class MoveComponent extends Component implements Observer {

    /**
     * constructor for Move Component
     * @param OwningEntity
     * @param updatedOrder
     */
    public MoveComponent(Entity OwningEntity, int updatedOrder) {
        super(OwningEntity, updatedOrder);
    }

    @Override
    public void update(Subject componentA) {
        if(componentA instanceof Component) {
        	this.updatedOrder = ((Component) componentA).getUpdatedOrder();
        }	
    }

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}
}
