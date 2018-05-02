package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.texture.TexturePath;
import dk.sdu.mmmi.cbse.commonplayer.Player;


import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGamePluginService.class)

public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        
        TexturePath idle = new TexturePath("sprite/idle.png", PlayerPlugin.class, Player.class);
        world.addSprite(idle);
        
        // Add entities to the world
        player = createPlayer(gameData);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {

        float deacceleration = 1000;
        float acceleration = 150;
        float maxSpeed = 2500;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;
        
        Entity player = new Player();
        player.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        player.add(new PositionPart(x, y, radians));
        player.add(new LifePart(3000000, Float.MAX_VALUE));
        player.setRadius(30);
        
        
        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }

}
