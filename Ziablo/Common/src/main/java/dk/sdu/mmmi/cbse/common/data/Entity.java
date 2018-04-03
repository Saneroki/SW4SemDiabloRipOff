package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();

    private float[] shapeX = new float[4];
    private float[] shapeY = new float[4];

    // TODO - New. 
    private EntityAnimator entityAnimator = null;
    private EntityTexture texture = null;
    private EntityAnimator attack = null;

    private float x;
    private float y;
    private int dx = 0;
    private int dy = 0;

    private float colX;
    private float colY;

    private EntityDirection direction;
    private float radius;
    // til her

    private Map<Class, EntityPart> parts;

    public Entity() {
        parts = new ConcurrentHashMap<>();
    }

    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }

    public void remove(Class partClass) {
        parts.remove(partClass);
    }

    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }

    public void setRadius(float r) {
        this.radius = r;
    }

    public float getRadius() {
        return radius;
    }

    public String getID() {
        return ID.toString();
    }

    public float[] getShapeX() {
        return shapeX;
    }

    public void setShapeX(float[] shapeX) {
        this.shapeX = shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }

    public void setShapeY(float[] shapeY) {
        this.shapeY = shapeY;
    }

    // TODO - New Fra her
    public EntityDirection getDirection() {
        return this.direction;
    }

    public void setDirection(EntityDirection direction) {
        this.direction = direction;
    }
    
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void wrap(World world) {
        if (x < -20) {
            x = (World.WORLD_SIZE * world.getBackgroundScale()) - 20;
        }
        if (x > (World.WORLD_SIZE * world.getBackgroundScale()) - 20) {
            x = -20;
        }
        if (y < 0) {
            y = (World.WORLD_SIZE * world.getBackgroundScale());
        }
        if (y > (World.WORLD_SIZE * world.getBackgroundScale())) {
            y = 0;
        }
    }

    public EntityAnimator getAttack() {
        return attack;
    }

    public void setAttack(EntityAnimator attack) {
        this.attack = attack;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    public float getColX() {
        return colX;
    }

    public void setColX(float colX) {
        this.colX = colX;
    }

    public float getColY() {
        return colY;
    }

    public void setColY(float colY) {
        this.colY = colY;
    }

    public EntityAnimator getEntityAnimator() {
        return entityAnimator;
    }

    public void setEntityAnimator(EntityAnimator entityAnimator) {
        this.entityAnimator = entityAnimator;
    }

    public EntityTexture getEntityTexture() {
        return texture;
    }

    public void setEntityTexture(EntityTexture texture) {
        this.texture = texture;
    }

}
