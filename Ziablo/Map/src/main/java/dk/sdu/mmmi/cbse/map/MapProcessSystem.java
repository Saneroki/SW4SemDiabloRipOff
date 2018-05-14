/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.map;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonenemy.services.ISpawnEnemy;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.Lookup;

/**
 *
 * @author Ander
 */
@ServiceProvider(service = IEntityProcessingService.class)

public class MapProcessSystem implements IEntityProcessingService {

    private int lastProcessEnemyAmount = 5;
    private int enemyCap = 100;
    
    @Override
    public void process(GameData gameData, World world) {
        spawnTwoEnemiesIfOneEnmemyDies(gameData, world);
    }

    public void spawnNewEnemy(GameData gameData, World world) {
        ISpawnEnemy newEnemy = Lookup.getDefault().lookup(ISpawnEnemy.class);
        if (newEnemy != null) {
            newEnemy.spawnEnemy(gameData, world);
        }
    }

    public void spawnTwoEnemiesIfOneEnmemyDies(GameData gameData, World world) {
        int i = 0;
        if (lastProcessEnemyAmount > gameData.getEnemyAmount() && lastProcessEnemyAmount < enemyCap - 1) {
            while (i < 2) {
                spawnNewEnemy(gameData, world);
                i++;
            }
            lastProcessEnemyAmount = gameData.getEnemyAmount();
        }
    }
}
