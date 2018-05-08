/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.textureloader;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import dk.sdu.mmmi.cbse.common.data.Entity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import dk.sdu.mmmi.cbse.common.texture.TexturePath;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.commonbullet.Bullet;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import dk.sdu.mmmi.cbse.commonobstacle.Obstacle;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import static dk.sdu.mmmi.cbse.textureloader.ComponentAnimator.ComponentAnimator;

/**
 *
 * @author Bruger
 */
public class TextureLoader {

    private Animation player_idle;

    private Animation enemy_idle;

    private Animation projectile;

    private Animation obstacle;

    private static boolean enemy = false;

    private static boolean player = false;

    public void loadRenderingMaterial(World world) {
        if (!world.getSprites().isEmpty()) {
            for (TexturePath sheet : world.getSprites()) {

                if (sheet.getService().equals(Enemy.class)) {
                    enemy_idle = ComponentAnimator(loadTexture(sheet.getModule(), sheet.getResource()), 4, 4, 3f);
                    System.out.println("Texture animation: " + enemy_idle + " " + sheet.getModule());
                    world.removeSprite(sheet);
                }

                if (sheet.getService().equals(Player.class)) {
                    player_idle = ComponentAnimator(loadTexture(sheet.getModule(), sheet.getResource()), 1, 3, 3f);
                    System.out.println("Texture animation: " + player_idle + " " + sheet.getModule());
                    world.removeSprite(sheet);
                }
                
                if (sheet.getService().equals(Bullet.class)) {
                    projectile = ComponentAnimator(loadTexture(sheet.getModule(), sheet.getResource()), 1, 1, 1f);
                    System.out.println("Texture animation: " + projectile + " " + sheet.getModule());
                    world.removeSprite(sheet);
                }
                
                if (sheet.getService().equals(Obstacle.class)) {
                    obstacle = ComponentAnimator(loadTexture(sheet.getModule(), sheet.getResource()), 1, 1, 1f);
                    System.out.println("Texture animation: " + obstacle + " " + sheet.getModule());
                    world.removeSprite(sheet);
                }
            }
        }
    }
    


    private static Texture loadTexture(Class cls, String path) {
        byte[] data;
        Pixmap pixmap;
        Texture temp_texture = null;
        try {
            data = loadAsByteArray(cls, path);
            pixmap = new Pixmap(data, 0, data.length);
            temp_texture = new Texture(pixmap);
            pixmap.dispose();
            System.out.println("Returned texture : " + temp_texture.getHeight());
            return temp_texture;
        } catch (IOException | NullPointerException ex) {
            ex.printStackTrace();
        }
        return temp_texture;
    }

    public static boolean enemyLoaded() {
        if (enemy) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean playerLoaded() {
        if (player) {
            return true;
        } else {
            return false;
        }
    }

    private static byte[] loadAsByteArray(Class cls, String path) throws IOException, NullPointerException {
        final byte[] data;

        try (InputStream is = cls.getClassLoader().getResourceAsStream(path)) {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            data = baos.toByteArray();

            baos.close();
            is.close();
        }

        return data;
    }

     public Animation getPlayer_idle() {
        return player_idle;
    }

    public Animation getEnemy_idle() {
        return enemy_idle;
    }

    public Animation getProjectile() {
        return projectile;
    }

    public Animation getObstacle() {
        return obstacle;
    }

}
