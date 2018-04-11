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
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.Lookup;

@ServiceProvider(service = IEntityProcessingService.class)

/**
 *
 * @author jcs
 */
public class PlayerControlSystem implements IEntityProcessingService {

    private ICreateBullet createBullet;

    /**
     * Use SPILocator (or netbeans Lookup) to find all implementations of the
     * ICreateBullet interface. This way we don't have to add a physical Bullet
     * JAR file. Instead we use whiteboard model to our advantage.
     */
    private void instantiateBullet() {
        for (ICreateBullet bullet : Lookup.getDefault().lookupAll(ICreateBullet.class)) {
            createBullet = (ICreateBullet) bullet;
        }
    }

    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(LEFT));
            movingPart.setRight(gameData.getKeys().isDown(RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(UP));
            movingPart.setDown(gameData.getKeys().isDown(DOWN));
            
            //Potentially working
            if (gameData.getKeys().isDown(SPACE)) {
                System.out.println("pew pew");
                if (createBullet == null) {
                    instantiateBullet();
                }
                try {
                    createBullet.createBullet(gameData, world, player);
                } catch (NullPointerException e) {
                    System.out.println("Null bullets" + e);
                }
            }

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);

            updateShape(player);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class
        );
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

}
