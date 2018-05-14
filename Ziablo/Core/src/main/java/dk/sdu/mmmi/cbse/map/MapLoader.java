/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.services.ICreateWall;
import java.util.Collection;
import java.util.Random;
import org.openide.util.Lookup;

/**
 *
 * @author Ander
 */
public class MapLoader {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private final Lookup lookup = Lookup.getDefault();
    private String loadMapRandom;
    
    public void loadMap(GameData gameData) {
        Random rand = new Random();
        int i = (rand.nextInt(2) + 1);
        
        if(i == 1) {
            loadMapRandom = "maps/map_black_stone_done.tmx";
        } else {
            loadMapRandom = "maps/woodMap.tmx";
        }
        map = new TmxMapLoader().load(loadMapRandom);
        renderer = new OrthogonalTiledMapRenderer(map);
        MapProperties prop = map.getProperties();
        
        gameData.setWallAmount(map.getLayers().get("Object Layer 1").getObjects().getCount());
        gameData.setMapWidth(prop.get("width", Integer.class));
        gameData.setMapHeight(prop.get("height", Integer.class));
    }

    public void createWalls(GameData gameData){
        for (ICreateWall iCreateWall : getWallServices()) {
            for (MapObject object : map.getLayers().get("Object Layer 1").getObjects()) {
                if (object instanceof RectangleMapObject) {
                    RectangleMapObject rectObject = (RectangleMapObject) object;
                    Rectangle rect = rectObject.getRectangle();
                    iCreateWall.createWalls(rect.x, rect.y - 99200, rect.width, rect.height, gameData);
                }
            }
        }
    }
    
    public void renderCam(OrthographicCamera cam) {
        renderer.setView(cam);
        renderer.render();
    }

    private Collection<? extends ICreateWall> getWallServices() {
        return lookup.lookupAll(ICreateWall.class);
    }

}
