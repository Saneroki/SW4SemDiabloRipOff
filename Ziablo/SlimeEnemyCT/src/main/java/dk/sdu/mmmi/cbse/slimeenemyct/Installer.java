/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.slimeenemyct;

import org.openide.modules.ModuleInstall;

/**
 * TODO - New
 * @author soere
 */
public class Installer extends ModuleInstall{
    @Override
    public void restored(){
        System.out.println("HI FROM SLIME INSTALLER");
    }
    
    @Override
    public void uninstalled(){
        
    }
    
}
