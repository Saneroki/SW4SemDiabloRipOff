/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;


import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import static java.lang.Math.random;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Sadik
 */

@ServiceProvider(service = IEntityProcessingService.class)

public class EnemyProcessSystem implements IEntityProcessingService {

    Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemy : world.getEntities(Enemy.class)) {
            int i = random.nextInt(100);

            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            
            for (Entity p : world.getEntities(Player.class)) {
                PositionPart posE = enemy.getPart(PositionPart.class);

                PositionPart posP = p.getPart(PositionPart.class);

                float EColX = posE.getX() + gameData.getDisplayWidth() / 2;
                float EColY = posE.getY() + gameData.getDisplayHeight() / 2;

                float PColX = posP.getX() + gameData.getDisplayWidth() / 2;
                float PColY = posP.getY() + gameData.getDisplayHeight() / 2;
                
                float x1 = EColX;
                float y1 = EColY;
                float x2 = PColX;
                float y2 = PColY;

                double distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
//                System.out.println(distance);
                if(distance < 300 && distance > 15){
                    enemy.setFind(true);
                }else if(distance < 18){
                    enemy.setFindFalse();
                }else{
                    enemy.setFindFalse();
                }
//                enemy.setFindFalse();
                
            }
            
//            if (i % 2 == 0) {
//                movingPart.setRight(true);
//                movingPart.setLeft(false);
//
//            } 
//            if (i % 3 == 0) {
//                movingPart.setRight(false);
//                movingPart.setLeft(true);
//            }
//
//            //acceleration/deacceleration
//            if (i % 5 == 0) {
//                movingPart.setUp(true);
//            }

            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);

            updateShape(enemy);
        }

    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
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