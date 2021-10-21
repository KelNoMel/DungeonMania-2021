package dungeonmania;

public class MoveComponent extends Component implements Observer {
        
        public MoveComponent(Entity OwningEntity, int updatedOrder) {
                super(OwningEntity, updatedOrder);
        }

        @Override
        public void update(int newOrder) {
                
        }

}
