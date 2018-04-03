/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data;

/**
 * TODO - New
 * @author soere
 */
public class EntityEnemy extends Entity {

    private boolean hasTarget;
    private int hitbox;
    private float hotboxX;
    private float hitboxY;
    private int health;

    private boolean movingLeft;
    private boolean movingRight;
    private boolean movingUp;
    private boolean movingDown;
    private int movingDuration = 0;
    private int random;
    private int walk;

    private boolean dead;
    private boolean playDeath;
    private int deadAnimTimer;

    private int coolDown = 0;

    private float dx;
    private float dy;

    public int getDeadAnimTimer() {
        return deadAnimTimer;
    }

    public void setDeadAnimTimer(int deadAnimTimer) {
        this.deadAnimTimer = deadAnimTimer;
    }

    public boolean isPlayDeath() {
        return playDeath;
    }

    public void setPlayDeath(boolean playDeath) {
        this.playDeath = playDeath;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public int getWalk() {
        return walk;
    }

    public void setWalk(int walk) {
        this.walk = walk;
    }

    private boolean attacking;

    public int getRandom() {
        return random;
    }

    public void setRandom(int random) {
        this.random = random;
    }

    public int getMovingDuration() {
        return movingDuration;
    }

    public void setMovingDuration(int movingDuration) {
        this.movingDuration = movingDuration;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public boolean isMovingDown() {
        return movingDown;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void setTarget(boolean hastarget) {
        this.hasTarget = hastarget;
    }

    public void setTargetFalse() {
        this.hasTarget = false;
    }

    public boolean getHasTarget() {
        return this.hasTarget;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public int getHitbox() {
        return hitbox;
    }

    public void setHitbox(int hitbox) {
        this.hitbox = hitbox;
    }

    public float getHotboxX() {
        return hotboxX;
    }

    public void setHotboxX(float hotboxX) {
        this.hotboxX = hotboxX;
    }

    public float getHitboxY() {
        return hitboxY;
    }

    public void setHitboxY(float hitboxY) {
        this.hitboxY = hitboxY;
    }

}
