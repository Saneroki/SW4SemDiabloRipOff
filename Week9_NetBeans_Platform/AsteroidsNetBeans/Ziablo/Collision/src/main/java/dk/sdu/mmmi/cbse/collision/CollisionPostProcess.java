/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collision;


import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Sadik
 */
@ServiceProvider(service = IPostEntityProcessingService.class)

public class CollisionPostProcess implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities()){
            collision(e, world);
        }

    }

    private void collision(Entity entity1, World world) {

        PositionPart positionPart = entity1.getPart(PositionPart.class);
        
        for (Entity entity2 : world.getEntities()) {
            
            if (entity1.equals(entity2)){
                break;
            }
            PositionPart positionPart2 = entity2.getPart(PositionPart.class);
            float dx = positionPart.getX() - positionPart2.getX();
            float dy = positionPart.getY() - positionPart2.getY();
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance < entity1.getRadius() + entity2.getRadius()) {
//                if ((entity1 instanceof Bullet && entity2 instanceof Player)||(entity2 instanceof Bullet && entity1 instanceof Player)){
//                    break;
//                }
//                if ((entity1 instanceof Bullet && entity2 instanceof Bullet)||(entity2 instanceof Bullet && entity1 instanceof Bullet)){
//                    break;
//                }
                world.removeEntity(entity1);
                world.removeEntity(entity2);
                System.out.println(entity1.getClass().toString() + " COLLIDED WITH: "+entity2.getClass().toString());
            } 
        }
    }

}