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
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonbullet.Bullet;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Sadik
 */

@ServiceProvider(service = IEntityProcessingService.class)//important to register the interfaces the module uses

public class BulletProcessingSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity projectile : world.getEntities(Bullet.class)) {

            PositionPart position = projectile.getPart(PositionPart.class);
            LifePart life = projectile.getPart(LifePart.class);
            MovingPart movement = projectile.getPart(MovingPart.class);

            //making sure bullet moves straight up
            movement.setUp(true);

            movement.process(gameData, projectile);
            position.process(gameData, projectile);
            life.process(gameData, projectile);

            /**
             * We remove the entity from the world in this method (and not in life.process),
             * because we want the entity component to be responsible for its
             * own entities in the World.
             */
            if (life.getLife() == 0) {
                world.removeEntity(projectile);
            }

            updateShape(projectile);

        }

    }


    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class
        );
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 8);
        shapey[0] = (float) (y + Math.sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
