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
import dk.sdu.mmmi.cbse.commonenemy.services.ISpawnEnemy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Sadik
 */
@ServiceProvider(service = IGamePluginService.class)
public class EnemyPlugin implements IGamePluginService, ISpawnEnemy {

    private Random random = new Random();
    private Entity enemy;
    private TexturePath idle = new TexturePath("sprite/Axe_Bandit.png", EnemyPlugin.class, Enemy.class);
    private ArrayList amountOfEnemies = new ArrayList();
    private float deacceleration = 900;
    private float acceleration = 50;
    private float maxSpeed = 10;
    private float rotationSpeed = 2;
    private float radians = 3.1415f / 2;
    
    private List<Entity> getEnemies(GameData gameData) {

        float x;
        float y;
        int enemyAmount = gameData.getEnemyAmount();
        for (int i = 0; i < enemyAmount; i++) {
            Entity entityEnemy = new Enemy();
            entityEnemy.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));

            x = (random.nextInt(gameData.getMapWidth()) + 1);
            y = (random.nextInt(gameData.getMapHeight()) + 1);

            entityEnemy.add(new PositionPart(x, y, radians));
            entityEnemy.add(new LifePart(3, Float.MAX_VALUE));
            entityEnemy.setRadius(20);

            amountOfEnemies.add(entityEnemy);
        }

        return amountOfEnemies;
    }

    @Override
    public void spawnEnemy(GameData gameData, World world) {
        System.out.println("fra enemy interface");
        Entity entityEnemy = new Enemy();
        entityEnemy.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));

        float x = (random.nextInt(gameData.getMapWidth()) + 1);
        float y = (random.nextInt(gameData.getMapHeight()) + 1);

        entityEnemy.add(new PositionPart(x, y, radians));
        entityEnemy.add(new LifePart(3, Float.MAX_VALUE));
        entityEnemy.setRadius(20);
        
        world.addSprite(idle);
        world.addEntity(entityEnemy);
        gameData.setEnemyAmount(gameData.getEnemyAmount() + 1);
    }

    @Override
    public void start(GameData gameData, World world) {
        System.out.println("dk.sdu.mmmi.cbse.enemy.EnemyPlugin.start()");
        gameData.setEnemyAmount(5);
        // Add entities to the world
        for (Object enemyElement : getEnemies(gameData)) {
            enemy = (Entity) enemyElement;
            world.addSprite(idle);
            world.addEntity(enemy);
        }

    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemy);
        System.out.println("dk.sdu.mmmi.cbse.enemy.EnemyPlugin.stop()");
        world.removeSprite(idle);
    }
}
