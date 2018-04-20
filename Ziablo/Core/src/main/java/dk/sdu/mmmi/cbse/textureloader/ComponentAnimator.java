/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.textureloader;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author User
 */
public class ComponentAnimator {

    public static Animation ComponentAnimator(Texture spritesheet, int rows, int columns, float rate) {
        float animation_speed = 1.0f / rate;
        TextureRegion[][] temp_frames = TextureRegion.split(spritesheet, spritesheet.getWidth() / columns, spritesheet.getHeight() / rows);
        TextureRegion[] animation_frames = new TextureRegion[columns * rows];

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                animation_frames[index++] = temp_frames[i][j];
            }
        }

        Animation comp_animation = new Animation(animation_speed, animation_frames);

        return comp_animation;
    }
    
    public static int getTextureWidth(Texture spritesheet){
        return spritesheet.getWidth();
    }
    
    public static int getTextureHeight(Texture spritesheet){
        return spritesheet.getHeight();
    }
    
}
