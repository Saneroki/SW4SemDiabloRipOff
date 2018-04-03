/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.services;

/**
 * TODO - New
 * @author soere
 */
public interface IExtractResource {
    
    dk.sdu.mmmi.cbse.common.data.EntityTexture loadTexture(Class cls, String path);
    
    dk.sdu.mmmi.cbse.common.data.EntityAnimator loadAnimation(dk.sdu.mmmi.cbse.common.data.EntityTexture spritesheet, int rows, int columns, float rate);
    
    void disposeTexture(dk.sdu.mmmi.cbse.common.data.EntityTexture texture);
    
    void disposeAnimationTexture(dk.sdu.mmmi.cbse.common.data.EntityAnimator animation);
    
}
