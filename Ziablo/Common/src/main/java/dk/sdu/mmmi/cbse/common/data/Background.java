/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data;

/**
 * TODO - new
 * @author soere
 */
public class Background {
    
    private EntityTexture texture;
    private float worldSize;
    
    public Background(EntityTexture texture, float worldSize){
        this.texture = texture;
        this.worldSize = worldSize;
    }
    
    public EntityTexture getTexture(){
        return this.texture;
    }
        
    public float getScale(){
        return this.worldSize;
    }
}
