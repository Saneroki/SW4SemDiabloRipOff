/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.slimeenemyct;

import dk.sdu.mmmi.cbse.common.data.CommonExtractResource;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityAnimator;
import dk.sdu.mmmi.cbse.common.data.EntityEnemy;
import dk.sdu.mmmi.cbse.common.data.EntityTexture;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author soere
 */
@ServiceProvider(service = IEntityProcessingService.class)
public class SlimeProcessSystem implements IEntityProcessingService {

    Random random = new Random();

    EntityTexture walking_sheet;
    EntityTexture attack_sheet;
    EntityTexture dead_sheet;
    EntityAnimator walking;
    EntityAnimator attack;
    EntityAnimator dead;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {

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
            }

        }
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
    }
}
