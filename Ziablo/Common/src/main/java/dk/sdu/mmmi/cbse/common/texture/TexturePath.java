/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.texture;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Bruger
 */
public class TexturePath implements Serializable{
    
      private final UUID ID = UUID.randomUUID();
      
      private final String resource;
      private final Class module;
      private final TextureType type;
      
      public TexturePath(String resource, Class module, TextureType type){
          this.resource = resource;
          this.module = module;
          this.type = type;
      }

    public String getID() {
        return ID.toString();
    }

    public String getResource() {
        return resource;
    }

    public Class getModule() {
        return module;
    }
      
    public TextureType getType(){
        return type;
    }
}
