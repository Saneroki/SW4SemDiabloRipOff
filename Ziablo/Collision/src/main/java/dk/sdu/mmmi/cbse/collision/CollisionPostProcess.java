/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
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

    
    private boolean check;
    
    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            collision(e, world);
            collisionOverlap(e, gameData, world);
        }

    }

    private void collision(Entity entity1, World world) {

        PositionPart positionPart = entity1.getPart(PositionPart.class);

        for (Entity entity2 : world.getEntities()) {

            if (entity1.equals(entity2)) {
                break;
            }
            PositionPart positionPart2 = entity2.getPart(PositionPart.class);
            float dx = positionPart.getX() - positionPart2.getX();
            float dy = positionPart.getY() - positionPart2.getY();
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance < entity1.getRadius() + entity2.getRadius()) {
                LifePart firstEntity = entity1.getPart(LifePart.class);
                firstEntity.setIsHit(true);
                firstEntity.setLife(firstEntity.getLife() - 1);

                LifePart secondEntity = entity2.getPart(LifePart.class);
                secondEntity.setIsHit(true);
                secondEntity.setLife(secondEntity.getLife() - 1);

                if (firstEntity.getLife() == 0) {
                    world.removeEntity(entity1);
                }
                if (secondEntity.getLife() == 0) {
                    world.removeEntity(entity2);
                }
                System.out.println(entity1.getClass().toString() + " COLLIDED WITH: " + entity2.getClass().toString());
            }
        }
    }

    private void collisionOverlap(Entity entity_0, GameData gameData, World world) {

        for (Entity entity_1 : world.getEntities()) {
            
            if(entity_0.equals(entity_1)){
                break;
            }
            
            
            PositionPart Pentity0 = entity_0.getPart(PositionPart.class);
            PositionPart Pentity1 = entity_1.getPart(PositionPart.class);

            float ColX0 = Pentity0.getX() + gameData.getDisplayWidth() / 2;
            float ColY0 = Pentity0.getY() + gameData.getDisplayHeight() / 2;

            float ColX1 = Pentity1.getX() + gameData.getDisplayWidth() / 2;
            float ColY1 = Pentity1.getY() + gameData.getDisplayHeight() / 2;

            float dx = (ColX0 + entity_0.getRadius() / 2) - (ColX1 + entity_1.getRadius() / 2);
            float dy = (ColY0 + entity_0.getRadius() / 2) - (ColY1 + entity_1.getRadius() / 2);

            float entity_0Radius = entity_0.getRadius();
            float entity_1Radius = entity_1.getRadius();

            boolean check = Math.sqrt((dx * dx) + (dy * dy)) <= (entity_1Radius + entity_0Radius);

            if (check) {
                Pentity1.setX((Pentity1.getX() - dx / (entity_1.getRadius() - 1)));
                Pentity1.setY((Pentity1.getY() - dy / (entity_1.getRadius() - 1)));
            }
        }
        
    }

//    private boolean hitboundOverlap(EntityEnemy entity_0, EntityPlayer entity_1) {
//        float entity_0_hitbox = entity_0.getHitbox();
//        float entity_1_hitbox = entity_1.getHitbox();
//        float dx = (entity_0.getHotboxX() + entity_0_hitbox / 2) - (entity_1.getHotboxX() + entity_1_hitbox / 2);
//        float dy = (entity_0.getHitboxY() + entity_0_hitbox / 2) - (entity_1.getHitboxY() + entity_1_hitbox / 2);
//
//        boolean check = Math.sqrt((dx * dx) + (dy * dy)) <= (entity_1_hitbox + entity_0_hitbox);
//
//        return check;
//    }
}
