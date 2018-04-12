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
      private final Class service;
      
      public TexturePath(String resource, Class module, Class service){
          this.resource = resource;
          this.module = module;
          this.service = service;
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
    
    public Class getService() {
        return service;
    }
      
}
