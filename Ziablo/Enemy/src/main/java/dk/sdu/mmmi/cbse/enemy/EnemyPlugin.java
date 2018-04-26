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
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.texture.TexturePath;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Sadik
 */
@ServiceProvider(service = IGamePluginService.class)
public class EnemyPlugin implements IGamePluginService {

    Random random = new Random();

    private Entity enemy;
    TexturePath idle = new TexturePath("sprite/Axe_Bandit.png", EnemyPlugin.class, Enemy.class);

    ArrayList amountOfEnemies = new ArrayList();

    private List<Entity> createEnemyShip(GameData gameData) {

        float deacceleration = 900;
        float acceleration = 50;
        float maxSpeed = 10;
        float rotationSpeed = 2;
        float x;
        float y;
        float radians = 3.1415f / 2;


        for (int i = 0; i < 10; i++) {
            Entity entityEnemy = new Enemy();
            entityEnemy.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));

            x = gameData.getDisplayWidth() / random.nextInt(50);
            y = gameData.getDisplayHeight() / random.nextInt(50);

            entityEnemy.add(new PositionPart(x, y, radians));
            entityEnemy.add(new LifePart(3, Float.MAX_VALUE));
            entityEnemy.setRadius(8);

            amountOfEnemies.add(entityEnemy);
        }

        return amountOfEnemies;
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        for (Object enemyElement : createEnemyShip(gameData)) {
            enemy = (Entity) enemyElement;
            world.addSprite(idle);
            world.addEntity(enemy);
        }
        System.out.println("dk.sdu.mmmi.cbse.enemy.EnemyPlugin.start()");
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemy);
        System.out.println("dk.sdu.mmmi.cbse.enemy.EnemyPlugin.stop()");
        world.removeSprite(idle);
    }
}
