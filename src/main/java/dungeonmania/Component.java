package dungeonmania;

import java.util.ArrayList;

public class Component implements Subject {
        private Entity OwningEntity;
        protected int updatedOrder;
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
         * getter for order of component
         * @return updated order
         */
        public int getUpdatedOrder() {
                return updatedOrder;
        }


        /**
         * getter of owning entity
         * @return owning entity
         */
        public Entity getEntity() {
                return OwningEntity;
        }

        @Override
        public void attach(Observer c) {
		if(! observingComponents.contains(c)) { observingComponents.add(c); }
	}


        @Override
        public void detach(Observer c) {
                observingComponents.remove(c);
        }


        @Override
        public void notifyObservers() {
                for(Observer c : observingComponents) {
			c.update(this);
		}   
        }


}
