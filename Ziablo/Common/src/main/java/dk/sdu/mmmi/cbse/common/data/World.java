package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.texture.TexturePath;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author jcs
 */
public class World {

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();
    private final Map<String, TexturePath> sprites = new ConcurrentHashMap<>();

    public Collection<TexturePath> getSprites() {
        synchronized (sprites) {
            return sprites.values();
        }
    }

    public void addSprite(TexturePath texture) {
        synchronized (sprites) {
            sprites.put(texture.getID(), texture);
        }
    }

    public void removeSprite(TexturePath texture) {
        synchronized (sprites) {
            sprites.remove(texture.getID());
        }
    }

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

    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    public List<Entity> getSortedListOfEntities() {
        List<Entity> r = getListOfEntities();
        r.sort((Entity ent1, Entity ent2) -> {
            PositionPart pos1 = ent1.getPart(PositionPart.class);
            PositionPart pos2 = ent2.getPart(PositionPart.class);

            final float y1 = pos1.getY(), y2 = pos2.getY();
            if (y2 < y1) {
                return -1;
            } else if (y2 > y1) {
                return 1;
            } else {
                return 0;
            }
        });
        return r;
    }

    private ArrayList<Entity> getListOfEntities() {
        ArrayList<Entity> al = new ArrayList<>();
        for (Entity e : getEntities()) {
            al.add(e);
        }
        return al;
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

}
