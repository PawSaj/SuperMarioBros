package com.project.mario.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.project.mario.GameLogic;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;
import com.project.mario.enums.PlayerStates;

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
	private boolean ground = false;
	protected double gravity;
	protected int isUnderground; //0 false, 1 true
	private PlayerStates state;
	protected boolean isShooting;
	
	private boolean goingDownPipe;
	protected boolean goingUpPipe;
	
	public int DELAY_VALUE;
	public int FRAMES;
	
	protected int frame;
	protected int frameDelay;
	
	protected Random random;
	
	public Entity(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.gameLogic = gameLogic;
		this.facing = null;
		
		isUnderground = 0;
		falling = true;
		jumping = false;
		
		DELAY_VALUE = 10;
	}
	
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
		
		DELAY_VALUE = 10;
	}

	public abstract void render(Graphics g);
	
	public abstract void update();
	
	public abstract void entityDieAnimation();
	
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
	
	public void createMarioFireball() {
		if (facing == Facing.left)
			gameLogic.handler.addEntity(new MarioFireball(x, y + height * 2 / 3, 24, 24, Id.marioFireball, gameLogic, facing));
		else
			gameLogic.handler.addEntity(
					new MarioFireball(x + width, y + height * 2 / 3, 24, 24, Id.marioFireball, gameLogic, facing));
		gameLogic.sounds.fireball.play();
		isShooting = true;
	}
	
	protected void drawRotatedImage(Graphics g, BufferedImage image) {
		double rotationRequired = Math.toRadians(180);
		double locationX = width / 2;
		double locationY = height / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		Graphics2D g2d = (Graphics2D) g;
		// Drawing the rotated image at the required drawing locations
		g2d.drawImage(op.filter(image, null), x, y, null);
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
	

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public Rectangle getBoundsTop() {
		return new Rectangle(x + 10, y, width - 20, 10);
	}

	public Rectangle getBoundsBottom() {
		return new Rectangle(x + 10, y + height - 10, width - 20, 10);
	}

	public Rectangle getBoundsBottomPipe() {
		return new Rectangle(x + 20, y + height, width - 40, 10);
	}
	
	public Rectangle getBoundsLeft() {
		return new Rectangle(x, y + 5, 10, height - 10);
	}

	public Rectangle getBoundsRight() {
		return new Rectangle(x + width - 10, y + 5, 10, height - 10);
	}

	public Rectangle getBoundsPlayerHearth() {
		return new Rectangle(x, y, width, height - 32);
	}

	public Rectangle getBoundsLeftKoopa() {
		if (id == Id.koopaTroopa)
			return new Rectangle(x, y + height / 3, 10, height - height * 2 / 3);
		else
			return getBoundsLeft();
	}

	public Rectangle getBoundsRightKoopa() {
		if (id == Id.koopaTroopa)
			return new Rectangle(x + width - 10, y + height / 3, 10, height - height * 2 / 3);
		else
			return getBoundsRight();
	}

	public Rectangle getBoundsRightFireball() {
		return new Rectangle(x + width, y + 5, 10, height - 10);
	}

	public Rectangle getBoundsLeftFireball() {
		return new Rectangle(x - 10, y + 5, 10, height - 10);
	}

	public Rectangle getBoundsTopFireball() {
		return new Rectangle(x + 5, y - 10, width - 10, 10);
	}

	public Rectangle getBoundsBottomFireball() {
		return new Rectangle(x + 5, y + height, width - 10, 10);
	}

	public boolean isGoingUpPipe() {
		return goingUpPipe;
	}

	public void setGoingUpPipe(boolean goingUpPipe) {
		this.goingUpPipe = goingUpPipe;
	}

	public boolean isGoingDownPipe() {
		return goingDownPipe;
	}

	public void setGoingDownPipe(boolean goingDownPipe) {
		this.goingDownPipe = goingDownPipe;
	}

	public PlayerStates getState() {
		return state;
	}

	public void setState(PlayerStates state) {
		this.state = state;
	}

	public boolean isGround() {
		return ground;
	}

	public void setGround(boolean ground) {
		this.ground = ground;
	}
}
