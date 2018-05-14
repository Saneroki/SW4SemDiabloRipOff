package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.SPACE;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.ICreateBullet;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.Lookup;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.W;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.A;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.D;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.S;

@ServiceProvider(service = IEntityProcessingService.class)

/**
 *
 * @author jcs
 */
public class PlayerControlSystem implements IEntityProcessingService {

    private float difX;
    private float difY;

    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movement = player.getPart(MovingPart.class);

            positionPart.setRadians(setPlayerRotation(gameData, player));

            if (gameData.getKeys().isDown(A)) {
                movement.setDx(-1000f);
            }

            if (gameData.getKeys().isDown(D)) {
                movement.setDx(1000f);
            }

            if (gameData.getKeys().isDown(W)) {
                movement.setDy(1000f);
            }
            if (gameData.getKeys().isDown(S)) {
                movement.setDy(-1000f);
            }
            // to fix player stuttering around when not moving.
            if (!gameData.getKeys().isDown(A) && !gameData.getKeys().isDown(D) && !gameData.getKeys().isDown(W) && !gameData.getKeys().isDown(S)) {
                movement.setDx(0);
                movement.setDy(0);
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
        }
    }

    private float setPlayerRotation(GameData gameData, Entity entity) {

        PositionPart positionPart = entity.getPart(PositionPart.class);

        difX = positionPart.getX() - (gameData.getDisplayWidth() / 2);
        difY = positionPart.getY() - (gameData.getDisplayHeight() / 2);

        float angle = (float) Math.atan2((gameData.getMousePositionY() + difY) - positionPart.getY(), (gameData.getMousePositionX() + difX) - positionPart.getX());

        return angle;
    }
}
