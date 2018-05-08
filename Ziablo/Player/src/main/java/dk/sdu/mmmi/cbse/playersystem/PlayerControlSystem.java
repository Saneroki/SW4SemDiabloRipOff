package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.SPACE;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.DOWN;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.ICreateBullet;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.Lookup;

@ServiceProvider(service = IEntityProcessingService.class)

/**
 *
 * @author jcs
 */
public class PlayerControlSystem implements IEntityProcessingService {

    private PositionPart positionPart;
    private boolean test = true;
    private float xFirst;
    private float yFirst;
    private float difX;
    private float difY;
    
    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            positionPart = player.getPart(PositionPart.class);
            MovingPart movement = player.getPart(MovingPart.class);

            
            positionPart.setRadians(setPlayerRotation(gameData));
            
            
            if (gameData.getKeys().isDown(LEFT)){
                movement.setDx(-100f);
            }
            
            if (gameData.getKeys().isDown(RIGHT)){
                movement.setDx(100f);
            }
            
            if (gameData.getKeys().isDown(UP)){
                movement.setDy(100f);
            }            
            if (gameData.getKeys().isDown(DOWN)){
                movement.setDy(-100f);
            }
            
            if (gameData.getKeys().isDown(SPACE)) {
                ICreateBullet bullet = Lookup.getDefault().lookup(ICreateBullet.class);
                if (bullet != null) {
                    bullet.createBullet(gameData, world, player);
                }
            }
            //positionPart.setRadians(180);
            movement.process(gameData, player);
            positionPart.process(gameData, player);
            
            updateShape(player);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians + (3.1415f / 4)) * 15);
        shapey[0] = (float) (y + Math.sin(radians + (3.1415f / 4)) * 15);

        shapex[1] = (float) (x + Math.cos(radians - (3.1415f / 4)) * 15);
        shapey[1] = (float) (y + Math.sin(radians - (3.1415f / 4)) * 15);

        shapex[2] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 15);
        shapey[2] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 15);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 15);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 15);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
    
    private float setPlayerRotation(GameData gameData) {
        if (test) {
            xFirst = positionPart.getX();
            yFirst = positionPart.getY();
            test = false;
        }
        
        difX = positionPart.getX() - xFirst;
        difY = positionPart.getY() - yFirst;

        float angle = (float) Math.atan2((gameData.getMousePositionY() + difY) - positionPart.getY(), (gameData.getMousePositionX() + difX) - positionPart.getX());

        return angle;
    }


}
