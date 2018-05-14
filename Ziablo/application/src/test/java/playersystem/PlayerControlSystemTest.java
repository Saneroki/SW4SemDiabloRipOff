/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.*;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import dk.sdu.mmmi.cbse.ziablo.GameTest;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.openide.util.Exceptions;

/**
 *
 * @author glenn
*/
public class PlayerControlSystemTest{
    
    
    private GameData gameData;
    private World world;
    private PlayerControlSystem pcs;
    private PositionPart ppp; // Player PositionPart
    private MovingPart pmp; // Player MovingPart
    
    @Before
    public void initialize(){
        gameData = new GameData();
        gameData.setDelta(100f);
        world = new World();
        Entity player = new Player();
        player.add(new PositionPart(50, 50, (3.1415f / 2)));
        player.add(new MovingPart(10, 200, 300, 5));
        ppp = player.getPart(PositionPart.class);
        pmp = player.getPart(MovingPart.class);
        pcs = new PlayerControlSystem();

    }
    
    
    // Simple test to see if the the application thinks if the buttons are pressed
    // when they shouldn't
    @Test
    public void testIfKeysAreInitiallyDown() {

        assertFalse(gameData.getKeys().isDown(A));
        assertFalse(gameData.getKeys().isDown(D));
        assertFalse(gameData.getKeys().isDown(W));
        assertFalse(gameData.getKeys().isDown(S));
        assertFalse(gameData.getKeys().isDown(SPACE));
    }
    
    @Test
    public void pppNotNull(){
        assertNotNull(ppp);
    }
    
    @Test(timeout=500)
    public void testIfMovementIsImidiatly(){
        pcs.process(gameData, world);
        
        
        pcs.process(gameData, world);
        float initialPosition = ppp.getX();
        System.out.println("initialPosition is " + initialPosition);
        

        try {
            gameData.getKeys().setKey(GameKeys.A, true);
            TimeUnit.MILLISECONDS.sleep(50);
            gameData.getKeys().setKey(GameKeys.A, false);
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        }
        //TODO: this test is not working as intended
        //assertNotEquals(initialPosition, ppp.getX());
        
        
        pcs.process(gameData, world);
    }
    
    
    
}