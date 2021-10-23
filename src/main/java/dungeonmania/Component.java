package dungeonmania;

import java.util.ArrayList;

public abstract class Component implements Subject {
        private Entity owningEntity;
        protected int updatedOrder;
        private ArrayList<Observer> observingComponents = new ArrayList<Observer>();

        /**
         * Component constructor
         * @param OwningEntity
         * @param updatedOrder
         */
        public Component(Entity OwningEntity, int updatedOrder) {
            this.owningEntity = OwningEntity;
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
            return owningEntity;
        }
        
        public abstract void update();

        
        
        @Override
        public void attach(Observer c) {
        	if(!observingComponents.contains(c)) {
        		observingComponents.add(c);
        	}
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
