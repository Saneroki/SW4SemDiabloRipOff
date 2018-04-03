/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.services.IExtractResource;

/**
 * TODO - New
 * @author soere
 */
public class CommonExtractResource {
    
    private static IExtractResource extracter;
    
    public static void setResourceExtracter(IExtractResource extracter){
        CommonExtractResource.extracter = extracter;
    }
    
    public static EntityTexture loadLIBGDXTexture(Class cls, String filePath){
        return extracter.loadTexture(cls, filePath);
    }
    
    public static EntityAnimator loadAnimation(EntityTexture spritesheet, int rows, int columns, float rate) {
        return extracter.loadAnimation(spritesheet, rows, columns, rate);
    }
    
    public static void disposeTexture(EntityTexture texture){
        extracter.disposeTexture(texture);
    }
    
    public static void disposeAnimationTexture(EntityAnimator animation){
        extracter.disposeAnimationTexture(animation);
    }
    
}
