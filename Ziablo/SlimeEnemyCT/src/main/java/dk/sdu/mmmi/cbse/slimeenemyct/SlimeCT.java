/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.slimeenemyct;

import dk.sdu.mmmi.cbse.common.data.CommonExtractResource;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityAnimator;
import dk.sdu.mmmi.cbse.common.data.EntityDirection;
import dk.sdu.mmmi.cbse.common.data.EntityEnemy;
import dk.sdu.mmmi.cbse.common.data.EntityTexture;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.InfcEntityInstantiateService;
import dk.sdu.mmmi.cbse.common.services.InfcEntityUpdateService;
import java.util.Random;

/**
 * TODO - New
 *
 * @author soere
 *
 * @ServiceProviders(value = {
 * @ServiceProvider(service = InfcEntityUpdateService.class) ,
 * @ServiceProvider(service = InfcEntityInstantiateService.class)} )
 *
 */
public class SlimeCT implements InfcEntityUpdateService, InfcEntityInstantiateService {

    Random rand = new Random();

    boolean loaded = false;

    EntityTexture walking_sheet;
    EntityTexture attack_sheet;
    EntityTexture dead_sheet;
    EntityAnimator walking;
    EntityAnimator attack;
    EntityAnimator dead;

    private void createSlimes(int amount, World world) {

        for (int i = 0; i < amount; i++) {
            Entity slime = new EntityEnemy();
            slime.setDirection(EntityDirection.LEFT);

            if (loaded == false) {
                walking_sheet = CommonExtractResource.loadLIBGDXTexture(this.getClass(), "slime_spritesheets/blobsheet_0.png");
                walking = CommonExtractResource.loadAnimation(walking_sheet, 4, 12, 8f);
                attack_sheet = CommonExtractResource.loadLIBGDXTexture(this.getClass(), "slime_spritesheets/blobsheet_0.png");
                attack = CommonExtractResource.loadAnimation(attack_sheet, 5, 7, 5f);
                dead_sheet = CommonExtractResource.loadLIBGDXTexture(this.getClass(), "slime_spritesheets/blobsheet_0.png");
                dead = CommonExtractResource.loadAnimation(dead_sheet, 8, 6, 1f);
                loaded = true;
            }

            slime.setEntityAnimator(walking);

            slime.setPosition(rand.nextInt(World.WORLD_SIZE), rand.nextInt(World.WORLD_SIZE));
            slime.setRadius(6);
            ((EntityEnemy) slime).setHealth(60);
            ((EntityEnemy) slime).setHitbox(12);
            ((EntityEnemy) slime).setDead(false);
            ((EntityEnemy) slime).setPlayDeath(false);
            ((EntityEnemy) slime).setDeadAnimTimer(4000);
            slime.setColX(slime.getX() + 25);
            slime.setColY(slime.getY() + 7);
            slime.setDirection(EntityDirection.LEFT);
            world.addEntity(slime);
        }
    }

    @Override
    public void updateEntity(GameData data, World world) {
        
        System.out.println("HEJ");
        
        for (Event event : data.getEvents()) {
            if (event.getSource().equals(EventType.ADD_SLIME)) {
                createSlimes(event.getAmount(), world);
                data.removeEvent(event);
            }
//            if (event.getType().equals(EventType.ADD_NIGHTGUARD)) {
//                createNightGuards(event.getAmount(), world);
//                data.removeEvent(event);
//            }
        }

        for (Entity e : world.getEntities()) {
            for (Entity p : world.getEntities()) {
                if (e instanceof EntityEnemy) {
                    int timer = ((EntityEnemy) e).getCoolDown();

                    if (timer > 0) {
                        timer -= 1;
                    }
                    ((EntityEnemy) e).setCoolDown(timer);

                    if (((EntityEnemy) e).getHealth() <= 0) {
                        e.setEntityAnimator(dead);
                        ((EntityEnemy) e).setDead(true);
                        if (!((EntityEnemy) e).isPlayDeath()) {
                            die(((EntityEnemy) e), world);
                        }
                    }
                    e.setColX(e.getX() + 25);
                    e.setColY(e.getY() + 7);
                    ((EntityEnemy) e).setHotboxX(e.getX() + 25);
                    ((EntityEnemy) e).setHitboxY(e.getY() + 25);

//                    if (!((EntityEnemy) e).isDead() && ((EntityEnemy) e).getHealth() > 0) {
//                        if (p instanceof EntityPlayer) {
//
//                            float x1 = e.getColX();
//                            float y1 = e.getColY();
//                            float x2 = p.getColX();
//                            float y2 = p.getColY();
//
//                            double distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
//
//                            if (distance < 120 && distance > 15) {
//                                ((EntityEnemy) e).setTarget(true);
//                                e.setEntityAnimator(walking);
//                            } else if (distance < 18) {
//                                e.setEntityAnimator(attack);
//                                if (((EntityEnemy) e).getCoolDown() < 1) {
//                                    ((EntityEnemy) e).setAttacking(true);
//                                    ((EntityEnemy) e).setCoolDown(5000);
//                                } else {
//                                    ((EntityEnemy) e).setAttacking(false);
//                                }
//                                ((EntityEnemy) e).setTargetFalse();
//                            } else {
//                                e.setEntityAnimator(walking);
//                                ((EntityEnemy) e).setAttacking(false);
//                                ((EntityEnemy) e).setTargetFalse();
//                            }
//                        }
//                    } else {
//                        ((EntityEnemy) e).setAttacking(false);
//                        ((EntityEnemy) e).setTargetFalse();
//                    }
                    e.wrap(world);
                }
            }
        }

//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void die(EntityEnemy e, World world) {
        int deathAnimTimer = e.getDeadAnimTimer();

        deathAnimTimer--;

        e.setDeadAnimTimer(deathAnimTimer);

        if (e.getDeadAnimTimer() < 0) {

            world.removeKilledEntity(e);
            e.setPlayDeath(true);
        }
    }

    @Override
    public void entityRender(GameData data, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(GameData data, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose(GameData data, World world) {

        for (Entity e : world.getEntities()) {
            if (e instanceof EntityEnemy) {
                world.removeEntity(e);
            }
        }

        CommonExtractResource.disposeAnimationTexture(attack);
        CommonExtractResource.disposeAnimationTexture(walking);
        CommonExtractResource.disposeAnimationTexture(dead);
        CommonExtractResource.disposeTexture(walking_sheet);
        CommonExtractResource.disposeTexture(attack_sheet);
        CommonExtractResource.disposeTexture(dead_sheet);

//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
