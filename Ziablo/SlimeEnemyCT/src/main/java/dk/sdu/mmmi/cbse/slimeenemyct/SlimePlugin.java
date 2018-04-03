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
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author soere
 */
@ServiceProvider(service = IGamePluginService.class)
public class SlimePlugin implements IGamePluginService {
    
    private Entity slime;
    Random rand = new Random();

    boolean loaded = false;

    EntityTexture walking_sheet;
    EntityTexture attack_sheet;
    EntityTexture dead_sheet;
    EntityAnimator walking;
    EntityAnimator attack;
    EntityAnimator dead;
    
    private Entity createSlimes(GameData gamedata) {

        int amount = 2;
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
//            world.addEntity(slime);
        }
        return slime;
    }

    @Override
    public void start(GameData gameData, World world) {
        // Add slime to the world.
        slime = createSlimes(gameData);
        world.addEntity(slime);
        System.out.println("dk.sdu.mmmi.cbse.enemy.SlimePlugin.start()");
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(slime);
        System.out.println("dk.sdu.mmmi.cbse.enemy.SlimePlugin.stop()");
    }
    
}
