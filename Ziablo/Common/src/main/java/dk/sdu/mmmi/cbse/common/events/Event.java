package dk.sdu.mmmi.cbse.common.events;

import dk.sdu.mmmi.cbse.common.data.Entity;
import java.io.Serializable;

/**
 * TODO - New
 * @author Mads
 */
public class Event implements Serializable{
    private int amount;
    private final Entity source;

    public Event(int amount, Entity source) {
        this.amount = amount;
        this.source = source;
    }

    public int getAmount(){
        return amount;
    }
    
    public Entity getSource() {
        return source;
    }
}
