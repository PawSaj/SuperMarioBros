package com.project.mario.entity;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;

public abstract class Entity {
	protected int x;
	protected int y;
	protected int width, height;
	protected int velX, velY;
	protected Id id;
	protected Facing facing;
	
	protected GameLogic gameLogic;
	protected boolean falling;
	protected boolean jumping;
	protected double gravity;
	protected int isUnderground; //0 false, 1 true
	
	protected boolean goingDownPipe;
	protected boolean goingUpPipe;
	
	public int DELAY_VALUE;
	public int FRAMES;
	
	protected int frame;
	protected int frameDelay;
	

	public Entity(int x, int y, int width, int height, Id id, GameLogic gameLogic, Facing facing) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.gameLogic = gameLogic;
		this.facing = facing;
		
		isUnderground = 0;
		falling = true;
		jumping = false;
		
	}

	public abstract void render(Graphics g);
	
	public abstract void update();
	
	public void die() {
		gameLogic.handler.removeEntity(this);
	}
	
	protected void setNextFrame (int delayValue, int frames) {
		frameDelay++;
		if (frameDelay >= delayValue) {
			frame++;
			frame = frame % frames;
			frameDelay = 0;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public double getGravity() {
		return gravity;
	}

	public void setGravity(double gravity) {
		this.gravity = gravity;
	}

	public Facing getFacing() {
		return facing;
	}

	public void setFacing(Facing facing) {
		this.facing = facing;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public int getIsUnderground() {
		return isUnderground;
	}

	public void setIsUnderground(int isUnderground) {
		this.isUnderground = isUnderground;
	}
	
	
}
