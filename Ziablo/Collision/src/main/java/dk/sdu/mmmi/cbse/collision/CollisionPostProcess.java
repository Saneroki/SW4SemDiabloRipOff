/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Wall;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.commonbullet.Bullet;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import dk.sdu.mmmi.cbse.commonobstacle.Obstacle;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Sadik
 */
@ServiceProvider(service = IPostEntityProcessingService.class)

public class CollisionPostProcess implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {
                collision(entity1, entity2, world);
                testLife(entity1, entity2, world);
            }
        }
    }

    private void collision(Entity entity1, Entity entity2, World world) {

        float dx;
        float dy;
        float distance;

        PositionPart positionPart1 = entity1.getPart(PositionPart.class);
        PositionPart positionPart2 = entity2.getPart(PositionPart.class);

        if (entity1.equals(entity2)) {
            return;
        }

        if (entity1 instanceof Wall && entity2 instanceof Wall) {
            return;
        }

        if (entity1 instanceof Wall) {
            if (RectangleAndCircleCollision(positionPart1.getX(), positionPart1.getY(),
                    positionPart1.getHeight(), positionPart1.getWidth(), positionPart2.getX(), positionPart2.getY(), entity2.getRadius())) {

                if (entity2 instanceof Bullet) {
                    world.removeEntity(entity2);
                    return;
                }
                collisionWall(entity2);
                return;
            }
        }

        if (entity2 instanceof Wall) {
//            if (RectangleAndCircleCollision(positionPart2.getX(), positionPart2.getY(),
//                    positionPart2.getHeight(), positionPart2.getWidth(), positionPart1.getX(), positionPart1.getY(), entity1.getRadius())) {
//
//                if (entity1 instanceof Bullet) {
//                    world.removeEntity(entity1);
//                    return;
//                }
//                collisionWall(entity1);
                return;
//            }
        }

        dx = positionPart1.getX() - positionPart2.getX();
        dy = positionPart1.getY() - positionPart2.getY();
        distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance <= entity1.getRadius() + entity2.getRadius()) {

            LifePart entityOneLifePart = entity1.getPart(LifePart.class);
            LifePart entityTwoLifePart = entity2.getPart(LifePart.class);

            if (entity1 instanceof Player) {
                if (entity2 instanceof Enemy) {
                    entityOneLifePart.setIsHit(true);
                    entityOneLifePart.setLife(entityOneLifePart.getLife() - 1);
                    System.out.println(entity1.getClass().toString() + " COLLIDED WITH: " + entity2.getClass().toString());
                    return;
                }
            }
            if (entity2 instanceof Player) {
                if (entity1 instanceof Enemy) {
                    entityTwoLifePart.setIsHit(true);
                    entityTwoLifePart.setLife(entityTwoLifePart.getLife() - 1);
                    System.out.println(entity1.getClass().toString() + " COLLIDED WITH: " + entity2.getClass().toString());
                    return;
                }
            }
            if (entity1 instanceof Bullet) {
                if (entity2 instanceof Enemy) {
                    entityTwoLifePart.setIsHit(true);
                    entityTwoLifePart.setLife(entityTwoLifePart.getLife() - 1);
                    world.removeEntity(entity1);
                    System.out.println(entity1.getClass().toString() + " COLLIDED WITH: " + entity2.getClass().toString());
                    return;
                }
            }
            if (entity2 instanceof Bullet) {
                if (entity1 instanceof Enemy) {
                    entityOneLifePart.setIsHit(true);
                    entityOneLifePart.setLife(entityOneLifePart.getLife() - 1);
                    world.removeEntity(entity2);
                    System.out.println(entity1.getClass().toString() + " COLLIDED WITH: " + entity2.getClass().toString());
                    return;
                }
            }
            if (entity1 instanceof Obstacle) {
                if(entity2 instanceof Player) {
                    collisionOverlap(entity1, entity2);
                    System.out.println(entity1.getClass().toString() + " COLLIDED WITH: " + entity2.getClass().toString());
                    return;
                }
            }
            if (entity2 instanceof Obstacle) {
                if(entity1 instanceof Player) {
                    collisionOverlap(entity2, entity1);
                    System.out.println(entity1.getClass().toString() + " COLLIDED WITH: " + entity2.getClass().toString());
                    return;
                }
            }
        }
    }

    private void testLife(Entity entity1, Entity entity2, World world) {

        LifePart entityOneLifePart = entity1.getPart(LifePart.class);
        LifePart entityTwoLifePart = entity2.getPart(LifePart.class);
        if (entityOneLifePart.getLife() == 0) {
            world.removeEntity(entity1);
        }
        if (entityTwoLifePart.getLife() == 0) {
            world.removeEntity(entity2);
        }
    }

    private boolean RectangleAndCircleCollision(float rectX, float rectY, float rectHeight, float rectWidth, float circX, float circY, float circRadius) {

        float deltaX = circX - Math.max(rectX, Math.min(circX, rectX + rectWidth));
        float deltaY = circY - Math.max(rectY, Math.min(circY, rectY + rectHeight));

        return (deltaX * deltaX + deltaY * deltaY) < (circRadius * circRadius);
    }

    private void collisionWall(Entity entityNotWall) {

        PositionPart posAnything = entityNotWall.getPart(PositionPart.class);
        MovingPart movingPartAnything = entityNotWall.getPart(MovingPart.class);

        if (entityNotWall instanceof Asteroid) {
            posAnything.setX(posAnything.getX() - (movingPartAnything.getDx() * (float) 0.5));
            posAnything.setY(posAnything.getY() - (movingPartAnything.getDy() * (float) 0.5));
            return;
        }

        posAnything.setX(posAnything.getX() - (movingPartAnything.getDx() * (float) 0.02));
        posAnything.setY(posAnything.getY() - (movingPartAnything.getDy() * (float) 0.02));
    }

    private void collisionOverlap(Entity entityIsPushing, Entity entityGetPushed) {

        if (entityIsPushing.equals(entityGetPushed)) {

        } else {

            PositionPart pentity1 = entityIsPushing.getPart(PositionPart.class);
            PositionPart pentity2 = entityGetPushed.getPart(PositionPart.class);

            float dx = (pentity1.getX() + entityIsPushing.getRadius() / 2) - (pentity2.getX() + entityGetPushed.getRadius() / 2);
            float dy = (pentity1.getY() + entityIsPushing.getRadius() / 2) - (pentity2.getY() + entityGetPushed.getRadius() / 2);

            boolean check = Math.sqrt((dx * dx) + (dy * dy)) < (entityIsPushing.getRadius() + entityGetPushed.getRadius());

            if (check) {
                pentity2.setX((pentity2.getX() - dx / (entityGetPushed.getRadius() - 1)));
                pentity2.setY((pentity2.getY() - dy / (entityGetPushed.getRadius() - 1)));
            }
        }
    }
}
