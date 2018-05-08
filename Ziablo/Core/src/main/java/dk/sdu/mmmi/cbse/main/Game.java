package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.commonbullet.Bullet;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import dk.sdu.mmmi.cbse.commonobstacle.Obstacle;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import dk.sdu.mmmi.cbse.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.map.MapLoader;
import dk.sdu.mmmi.cbse.textureloader.TextureLoader;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

public class Game
        implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private SpriteBatch batch;
    private TextureLoader TL;
    private MapLoader ML;
    //So we replace the "SPILocator" with this Lookup. This will be our whiteboard register in netbeans modules
    private final Lookup lookup = Lookup.getDefault();
    
    private List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private Lookup.Result<IGamePluginService> result;
    
    private final GameData gameData = new GameData();

    private World world = new World();

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();
        batch = new SpriteBatch();
        TL = new TextureLoader();
        ML = new MapLoader();
        
        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );
        
        ML.loadMap(gameData);
        
        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();
        
        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : result.allInstances()) {
            iGamePlugin.start(gameData, world);
            gamePlugins.add(iGamePlugin);
        }
        ML.createWalls(gameData);
    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        draw();

        gameData.getKeys().update();

        TL.loadRenderingMaterial(world);

        if (world.getSprites().isEmpty()) {
            update();
            drawEntities();
            drawCamera();
        }
    }

    private void update() {

        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        for (IPostEntityProcessingService entityProcessorService : getPostEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
    }

    private void drawCamera() {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        for (int i = 0; i <= world.getSortedListOfEntities().size() - 1; i++) {
            Entity e = world.getSortedListOfEntities().get(i);
            if (e.getClass() == Player.class) {
                PositionPart p = e.getPart(PositionPart.class);
                cam.position.set(p.getX(), p.getY(), 0);
            }
        }
    }

    private void drawEntities() {

        batch.begin();
        for (int i = 0; i <= world.getSortedListOfEntities().size() - 1; i++) {
            Entity e = world.getSortedListOfEntities().get(i);

            if (e.getClass() == Enemy.class) {
                drawTexture(TL.getEnemy_idle(), e);

            } else if (e.getClass() == Player.class) {
                drawTexture(TL.getPlayer_idle(), e);

            } else if (e.getClass() == Bullet.class) {
                drawTexture(TL.getProjectile(), e);

            } else if (e.getClass() == Obstacle.class) {
                drawTexture(TL.getObstacle(), e);
            }

        }
        batch.end();
    }

    private void drawTexture(Animation a, Entity e) {
        int width = getTextureRegion(a).getRegionWidth();
        int height = getTextureRegion(a).getRegionHeight();
        PositionPart p = e.getPart(PositionPart.class);
        batch.draw(getTextureRegion(a), p.getX() - (width / 2), p.getY() - (height / 2));
    }

    private TextureRegion getTextureRegion(Animation component_animation) {
        TextureRegion texture = component_animation.getKeyFrame(gameData.getDelta(), true);

        return texture;
    }

    private void draw() {

        ML.renderCam(cam);

        sr.setProjectionMatrix(cam.combined);

//        for (Entity entity : world.getEntities()) {
//
//            sr.setColor(1, 1, 1, 1);
//
//            sr.begin(ShapeRenderer.ShapeType.Line);
//
//            float[] shapex = entity.getShapeX();
//            float[] shapey = entity.getShapeY();
//
//            for (int i = 0, j = shapex.length - 1;
//                    i < shapex.length;
//                    j = i++) {
//
//                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
//            }
//
//            sr.end();
//        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return lookup.lookupAll(IEntityProcessingService.class);
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return lookup.lookupAll(IPostEntityProcessingService.class);
    }
    
    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {

            Collection<? extends IGamePluginService> updated = result.allInstances();

            for (IGamePluginService us : updated) {
                // Newly installed modules
                if (!gamePlugins.contains(us)) {
                    us.start(gameData, world);
                    gamePlugins.add(us);
                }
            }

            // Stop and remove module
            for (IGamePluginService gs : gamePlugins) {
                if (!updated.contains(gs)) {
                    gs.stop(gameData, world);
                    gamePlugins.remove(gs);
                }
            }
        }

    };
    
}
