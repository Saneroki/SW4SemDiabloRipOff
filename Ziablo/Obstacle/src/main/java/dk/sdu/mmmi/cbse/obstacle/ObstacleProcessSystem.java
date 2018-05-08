/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.obstacle;


import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonobstacle.Obstacle;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Sadik
 */

@ServiceProvider(service = IEntityProcessingService.class)

public class ObstacleProcessSystem implements IEntityProcessingService {

    Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {

        for (Entity obstacle : world.getEntities(Obstacle.class)) {
            
            PositionPart positionPart = obstacle.getPart(PositionPart.class);
            MovingPart movingPart = obstacle.getPart(MovingPart.class);
            LifePart lifePart = obstacle.getPart(LifePart.class);
            
            obstacleWrapper(positionPart, gameData);
            
            movingPart.process(gameData, obstacle);
            positionPart.process(gameData, obstacle);
            lifePart.process(gameData, obstacle);

            updateShape(obstacle);
        }

    }
    
    private void obstacleWrapper(PositionPart positionPart, GameData gameData) {
        if (positionPart.getX() > gameData.getMapWidth()) {
            positionPart.setX(0);
        }
        if(positionPart.getX() < 0){
            positionPart.setX(gameData.getMapWidth());
        }
        if (positionPart.getY() > gameData.getMapHeight()) {
            positionPart.setY(0);
        }
        if(positionPart.getY() < 0){
            positionPart.setY(gameData.getMapHeight());
        }
    }
    
    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians + (3.1415f / 5)) * 12);
        shapey[0] = (float) (y + Math.sin(radians + (3.1415f / 5)) * 12);

        shapex[1] = (float) (x + Math.cos(radians - (3.1415f / 5)) * 12);
        shapey[1] = (float) (y + Math.sin(radians - (3.1415f / 5)) * 12);

        shapex[2] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 12);
        shapey[2] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 12);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 12);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 12);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}