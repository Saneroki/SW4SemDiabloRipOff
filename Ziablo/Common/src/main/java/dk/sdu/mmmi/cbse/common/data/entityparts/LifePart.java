/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author Someone
 */
public class LifePart implements EntityPart {
    
    private int life;
    private boolean isHit = false;
    private float expiration;
    private float lifeTimer;

    public LifePart(int life, float expiration) {
        this.life = life;
        this.expiration = expiration;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isIsHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    public float getExpiration() {
        return expiration;
    }

    public void setExpiration(float expiration) {
        this.expiration = expiration;
    }

    public void reduceExpiration(float delta) {
        this.expiration -= delta;
    }

    /**
     * We implement process for the life of an entity. For example the life time
     * of a bullet. So that we know when it is time for it to disappear.
     * 
     * The removal of the entity after lifeTimer exceeds the expiration should 
     * be done in the individual component that utilizes LifePart (see Bullet process)
     * @param gameData
     * @param entity 
     */
    @Override
    public void process(GameData gameData, Entity entity) {
        lifeTimer += gameData.getDelta();
        if (lifeTimer > expiration) {
            isHit = true;
            lifeTimer = 0;
            life -= 1;
            
        }
    }
}
