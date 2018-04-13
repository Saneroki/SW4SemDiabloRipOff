/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.texture.TexturePath;
import dk.sdu.mmmi.cbse.common.texture.TextureType;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Sadik
 */

@ServiceProvider(service = IGamePluginService.class)
public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    private Entity createEnemyShip(GameData gameData) {

        float deacceleration = 900;
        float acceleration = 50;
        float maxSpeed = 10;
        float rotationSpeed = 2;
        float x = gameData.getDisplayWidth() / 3;
        float y = gameData.getDisplayHeight() / 3;
        float radians = 3.1415f / 2;

        Entity enemyShip = new Enemy();
        enemyShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new LifePart(3, Float.MAX_VALUE));
        enemyShip.setRadius(8);

        return enemyShip;
    }

    @Override
    public void start(GameData gameData, World world) {
        TexturePath idle = new TexturePath("sprite/Axe_Bandit.png", EnemyPlugin.class ,Enemy.class);
        world.addSprite(idle);
        
        // Add entities to the world
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
        System.out.println("dk.sdu.mmmi.cbse.enemy.EnemyPlugin.start()");
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemy);
        System.out.println("dk.sdu.mmmi.cbse.enemy.EnemyPlugin.stop()");
    }
}
