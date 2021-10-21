package dungeonmania;

import java.util.ArrayList;

public class Component {
        private Entity OwningEntity;
        private int updatedOrder;
        private ArrayList<Observer> observingComponents = new ArrayList<Observer>();

        /**
         * Component constructor
         * @param OwningEntity
         * @param updatedOrder
         */
        public Component(Entity OwningEntity, int updatedOrder) {
                this.OwningEntity = OwningEntity;
                this.updatedOrder = updatedOrder;
        }


        /**
         * update order of current component
         * @param newOrder
         */
       /* public void update(int newOrder) {
                this.updatedOrder = newOrder;
                
        }*/

        public void attach(Observer c) {
		if(! observingComponents.contains(c)) { observingComponents.add(c); }
	}

        public void notifyObservers(int newOrder) {
		for(Observer c : observingComponents) {
			c.update(newOrder);
		}
	}


}
