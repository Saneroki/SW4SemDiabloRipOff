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
import dk.sdu.mmmi.cbse.common.services.ICreateObstacle;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.texture.TexturePath;
import dk.sdu.mmmi.cbse.commonobstacle.Obstacle;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Sadik
 */

@ServiceProvider(service = IGamePluginService.class)
public class ObstaclePlugin implements IGamePluginService, ICreateObstacle {

    private Entity obstacle;

    @Override
    public void createObstacle(GameData gameData) {

        float deacceleration = 0;
        float acceleration = 0;
        float maxSpeed = 0;
        float rotationSpeed = 0;
        Random rand = new Random();
        float x = (rand.nextInt(gameData.getMapWidth()) + 1);
        float y = (rand.nextInt(gameData.getMapHeight()) + 1);
        float radians = 3.1415f / 2;
        int life = Integer.MAX_VALUE;
        float lifeExpiration = Float.MAX_VALUE;
        
        Obstacle obstacle = new Obstacle();
        
        obstacle.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        obstacle.add(new PositionPart(x, y, radians));
        obstacle.add(new LifePart(life, lifeExpiration));
        obstacle.setRadius(30);
        
        this.obstacle = obstacle;
        
    }

    @Override
    public void start(GameData gameData, World world) {
        int i = 0;
        
        TexturePath idle = new TexturePath("sprite/boulder.png", ObstaclePlugin.class, Obstacle.class);
        world.addSprite(idle);
        
        // Add entities to the world
        while(i < 20){
            createObstacle(gameData);
            world.addEntity(obstacle);
            i++;
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity obstacle : world.getEntities(Obstacle.class)) {
            world.removeEntity(obstacle);
        }
    }
}
