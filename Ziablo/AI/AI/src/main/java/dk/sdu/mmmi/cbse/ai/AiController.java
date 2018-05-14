/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.ai;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Fidde
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)
    ,
    @ServiceProvider(service = IGamePluginService.class)}
)
public class AiController implements IEntityProcessingService, IGamePluginService {

    private Random r = new Random();
    private int getRandom;
    private int movingDuration;
    private int Walk;
    private float idleMoveSpeed = 100;
    private float chargeMoveSpeed = 150;

    @Override
    public void process(GameData data, World world) {
        for (Entity e : world.getEntities(Enemy.class)) {
            if (!world.getEntities(Player.class).isEmpty()) {
                for (Entity p : world.getEntities(Player.class)) {
                    if (e instanceof Enemy) {
                        if (e.getFind()) {
                            chargeTarget(e, p, data);
                        } else {
                            walkWithoutPurpose((Enemy) e, data);
                        }
                    }
                }
            } else {
                walkWithoutPurpose((Enemy)e, data);
            }
        }
    }

    private void walkWithoutPurpose(Enemy e, GameData data) {

        PositionPart pe = e.getPart(PositionPart.class);
        MovingPart me = e.getPart(MovingPart.class);

        float x = pe.getX();
        float y = pe.getY();

        if (movingDuration == 0) {
            getRandom = r.nextInt(101);
        }

        if (getRandom > 50) {
            Walk = 20;
        }
        if (getRandom <= 50) {
            Walk = 40;
        }
        if (getRandom <= 25 && !me.isRight() && !me.isUp() && !me.isDown()) {
            me.setLeft(true);
//            me.setDirection(EntityDirection.LEFT);
            me.setDx(-idleMoveSpeed);

            if (getRandom > 50) {
                me.setDy(0f);
            }
        }
        if (getRandom > 25 && getRandom <= 50 && !me.isLeft() && !me.isUp() && !me.isDown()) {
            me.setRight(true);
//            e.setDirection(EntityDirection.RIGHT);
            me.setDx(idleMoveSpeed);
            if (getRandom < 50) {
                me.setDy(0);
            }
        }
        if (getRandom > 50 && getRandom <= 75 && !me.isLeft() && !me.isRight() && !me.isDown()) {
            me.setUp(true);
            me.setDy(idleMoveSpeed);
            if (getRandom < 50) {
                me.setDx(0);
            }
        }
        if (getRandom > 75 && getRandom <= 101 && !me.isLeft() && !me.isRight() && !me.isUp()) {
            me.setDown(true);
            me.setDy(-idleMoveSpeed);
            if (getRandom > 50) {
                me.setDx(0);
            }
        }
        if (movingDuration >= Walk) {
            me.setLeft(false);
            me.setRight(false);
            me.setUp(false);
            me.setDown(false);
            movingDuration = 0;
        }
        x += me.getDx() * data.getDelta();
        y += me.getDy() * data.getDelta();
        movingInDirection(e);
        pe.setX(x);
        pe.setY(y);
    }

    private void movingInDirection(Entity e) {
//        PositionPart pe = e.getPart(PositionPart.class);
        MovingPart me = e.getPart(MovingPart.class);

        if (me.isLeft() || me.isRight() || me.isDown() || me.isUp()) {
            int m = movingDuration;
            m += 1;
            movingDuration = m;
        }
    }

    private void chargeTarget(Entity enemy, Entity player, GameData data) {
        PositionPart posE = enemy.getPart(PositionPart.class);
        MovingPart movE = enemy.getPart(MovingPart.class);

        PositionPart posP = player.getPart(PositionPart.class);
        //       MovingPart movP = player.getPart(MovingPart.class);

        float EColX = posE.getX() + data.getDisplayWidth() / 2;
        float EColY = posE.getY() + data.getDisplayHeight() / 2;

        float PColX = posP.getX() + data.getDisplayWidth() / 2;
        float PColY = posP.getY() + data.getDisplayHeight() / 2;

//        float PColX = 
        float xLocation = posE.getX();
        float yLocation = posE.getY();

        //       for (Entity p : world.getEntities()) { // think this loop will make it itterate two times in all process's
        if (EColX < PColX) {
//                    enemy.setDirection(EntityDirection.RIGHT);
            movE.setRight(true);
            xLocation += chargeMoveSpeed * data.getDelta();
            posE.setX(xLocation);
//                    enemy.setX(xLocation);
        }
        if (EColX > PColX) {
//                    enemy.setDirection(EntityDirection.LEFT);
            movE.setLeft(true);
            xLocation -= chargeMoveSpeed * data.getDelta();
//                    enemy.setX(xLocation);
            posE.setX(xLocation);
        }
        if (EColY < PColY) {
            yLocation += chargeMoveSpeed * data.getDelta();
//                    enemy.setY(yLocation);
            posE.setY(yLocation);
        }
        if (EColY > PColY) {
            yLocation -= chargeMoveSpeed * data.getDelta();
//                    enemy.setY(yLocation);
            posE.setY(yLocation);
        }
//        }
    }

    @Override
    public void start(GameData gd, World world) {

    }

    @Override
    public void stop(GameData gd, World world) {

    }

}
