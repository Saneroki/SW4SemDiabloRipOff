/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.wall;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.ICreateWall;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonwall.Wall;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Ander
 */
@ServiceProviders(value = {
    @ServiceProvider(service = ICreateWall.class)
    ,
    @ServiceProvider(service = IGamePluginService.class)}
)

public class WallPlugin implements IGamePluginService, ICreateWall {

    private Entity wall;
    private ArrayList amountOfWalls = new ArrayList();
//    private int wallamount = 27; // Is hardcoded becuase we have not found a solution to get it from libgdx and send it from the core module.
    private int iWalls = 0;
    
    @Override
    public void start(GameData gameData, World world) {
        int i = 0;
        for (Object wallElement : getWalls(gameData)) {
            wall = (Entity) wallElement;
            world.addEntity(wall);
            i++;
            System.out.println("hej fra start i væg " + i);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(wall);
    }
    
    private List<Entity> getWalls(GameData gameData) {
        float x = 0;
        float y = 0;
        float height = 0;
        float width = 0;

        for (int i = 0; i < gameData.getWallAmount(); i++) {
            Entity wall = new Wall();
            wall.add(new PositionPart(x, y, width, height));
            wall.add(new LifePart(Integer.MAX_VALUE, Float.MAX_VALUE));
            amountOfWalls.add(wall);
        }
        return amountOfWalls;
    }
    
    @Override
    public void createWalls(float x, float y, float width, float height, GameData gameData) {

        PositionPart posPart;
        Entity wall = (Wall) amountOfWalls.get(iWalls);
        posPart = wall.getPart(PositionPart.class);
        posPart.setX(x);
        posPart.setY(y);
        posPart.setWidth(width);
        posPart.setHeight(height);

        iWalls++;

        if (iWalls == gameData.getWallAmount()) {
            iWalls = 0;
        }

    }
}
