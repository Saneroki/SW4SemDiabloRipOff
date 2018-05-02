/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Sadik
 */
@ServiceProvider(service = IEntityProcessingService.class)
public class AsteroidProcessingSystem implements IEntityProcessingService {

    Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            int i = random.nextInt(100);

            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);

            if (i % 3 == 0) {

                movingPart.setRight(true);
                movingPart.setLeft(false);
            }
            if (i % 2 == 0) {
                movingPart.setRight(false);
                movingPart.setLeft(true);
            }

            //acceleration/deacceleration
            if (i % 5 == 0) {
                movingPart.setUp(true);
            }

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);

            updateShape(asteroid);
        }
    }

    /**
     * For setting the shape of asteroid asteroid.
     *
     * @param asteroid provides data of asteroid in game world.
     */
    private void updateShape(Entity asteroid) {
        float[] shapex = new float[6];
        float[] shapey= new float[6];
        PositionPart positionPart = asteroid.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 15);
        shapey[0] = (float) (y + Math.sin(radians) * 15);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 15);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1415f / 5) * 15);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 17);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 17);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 15);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 15);

        shapex[4] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5 - 3.1415f) * 17);
        shapey[4] = (float) (y + Math.sin(radians - 4 * 3.1415f / 5 - 3.1415f) * 17);

        shapex[5] = (float) (x + Math.cos(radians + 6 * 3.1415f / 5 - 3.1415f) * 17);
        shapey[5] = (float) (y + Math.sin(radians + 6 * 3.1415f / 5 - 3.1415f) * 17);

        asteroid.setShapeX(shapex);
        asteroid.setShapeY(shapey);
    }

}