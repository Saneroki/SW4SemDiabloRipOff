/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Sadik
 */
@ServiceProvider(service = IEntityProcessingService.class)

public class EnemyProcessSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemy : world.getEntities(Enemy.class)) {

            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);

            enemyWrapper(positionPart, gameData);

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

                if (distance < 300 && distance > 15) {
                    enemy.setFind(true);
                } else if (distance < 18) {
                    enemy.setFind(false);
                } else {
                    enemy.setFind(false);
                }
            }
            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
        }

    }

    private void enemyWrapper(PositionPart positionPart, GameData gameData) {
        if (positionPart.getX() > gameData.getMapWidth()) {
            positionPart.setX(0);
        }
        if (positionPart.getX() < 0) {
            positionPart.setX(gameData.getMapWidth());
        }
        if (positionPart.getY() > gameData.getMapHeight()) {
            positionPart.setY(0);
        }
        if (positionPart.getY() < 0) {
            positionPart.setY(gameData.getMapHeight());
        }
    }
}
