/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.ICreateBullet;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.texture.TexturePath;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import dk.sdu.mmmi.cbse.commonbullet.Bullet;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Sadik
 */

//important to register the interfaces the module uses
@ServiceProviders(value={
    @ServiceProvider(service = IGamePluginService.class),
    @ServiceProvider(service = ICreateBullet.class)}
)

public class BulletPlugin implements IGamePluginService, ICreateBullet {

    private Entity bullet;
    private final long periodMilliseconds = 500;

    private long lastTick = 0L; // 1970 will be long before the current time :)

    @Override
    public void start(GameData gameData, World world) {
        TexturePath idle = new TexturePath("sprite/orb.png", BulletPlugin.class, Bullet.class);
        world.addSprite(idle);
    }

    @Override
    public void createBullet(GameData gameData, World world, Entity entity) {

        float deacceleration = 0;
        float acceleration = 100000;
        float maxSpeed = 300;
        float rotationSpeed = 0;
        PositionPart position = entity.getPart(PositionPart.class);
        //https://stackoverflow.com/questions/11697364/how-do-i-align-bullets-with-a-gun
        //https://en.wikipedia.org/wiki/Rotation_%28mathematics%29
        float x = (float) (position.getX() + (18 * Math.cos(position.getRadians())) - 1 * Math.sin(position.getRadians()));
        float y = (float) (position.getY() + (18 * Math.sin(position.getRadians())) + 1 * Math.cos(position.getRadians()));

        if (timerCheck()) {
            Entity projectile = new Bullet();

            projectile.add(new PositionPart(x, y, position.getRadians()));

            projectile.setRadius(2);

            //cery important to add these parts to the entity, BEFORE calling the entity for getters and setters
            projectile.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
            projectile.add(new LifePart(1, 1));
            world.addEntity(projectile);
        }

    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            world.removeEntity(bullet);
        }
    }
    
    private boolean timerCheck(){
        long now = System.nanoTime();
        long diffNanos = now - lastTick;
        long diffMilliseconds = TimeUnit.NANOSECONDS.toMillis(diffNanos);
        if (diffMilliseconds < periodMilliseconds) {
            return false;
        }
        lastTick = now;
        return true;
    }

}
