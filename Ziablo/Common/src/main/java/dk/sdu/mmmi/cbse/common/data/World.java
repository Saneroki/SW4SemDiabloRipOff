package dk.sdu.mmmi.cbse.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.scene.layout.Background;

/**
 *
 * @author jcs
 */
public class World {

    // TODO - New
    public static int WORLD_SIZE = 1000;
    private Background background = null;
    private int deaths;

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();

    public String addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);
        return entity.getID();
    }

    public void removeEntity(String entityID) {
        entityMap.remove(entityID);
    }

    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());
    }
    
    public void removeKilledEntity(Entity entity){
        entityMap.remove(entity.getID());
    }

    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
                    r.add(e);
                }
            }
        }
        return r;
    }

    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }

    // TODO - Nyt
    public void setBackground(Background background) {
        this.background = background;
    }

    public EntityTexture getBackground() {
        if (background == null) {
            return null;
        } else {
            return null;//this.background.getTexture();
        }
    }

    public void disposeBackground(EntityTexture background) {
        CommonExtractResource.disposeTexture(background);
    }

    public float getBackgroundScale() {
        if (background == null) {
            return 1f;
        } else {
            return 2f; //this.background.getScale();
        }
    }

}
