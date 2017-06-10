package com.project.mario.enviroment;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.project.mario.GameLogic;
import com.project.mario.entity.CrashedBlock;
import com.project.mario.enums.PlayerStates;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;

public abstract class EnviromentObject {
	protected int x, y;
	protected int width, height;
	protected int velX, velY;
	protected Id id;
	
	protected boolean destroyed;
	protected boolean activated;
	
	protected int jumpingAnimationSpeed;
	protected boolean jumpedBlock;
	protected int tempOfY;
	
	
	private GameLogic gameLogic;
	
	public EnviromentObject(int x, int y, int width, int height, Id id,  GameLogic gameLogic) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.gameLogic = gameLogic;
	}
	
	public abstract void render(Graphics g);
	
	public abstract void update();

	public void destroy() {
		gameLogic.handler.removeEnviromentObject(this);
	}
	
	public void die() {
		gameLogic.handler.removeEnviromentObject(this);
	}
	
	/**
	 * Metoda zwracaj¹ca obszar zajmowany przez element
	 * 
	 * @return Obszar zajmowany przez element
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	/**
	 * Metoda zwracaj¹ca obszar zajmowany przez element Pipe dostêpny do
	 * interakcji z graczem
	 * 
	 * @return Obszar zajmowany przez element Pipe
	 */
	public Rectangle getBoundsPipe() {
		return new Rectangle(x + 60, y, width - 120, height);
	}

	/**
	 * Metoda zwracaj¹ca lew¹ czêœæ obszaru zajmowanego przez element Pipe
	 * dostêpny do interakcji z graczem
	 * 
	 * @return Lewa czêœæ obszaru zajmowanego przez element Pipe
	 */
	public Rectangle getBoundsLeft() {
		return new Rectangle(x - 10, y + 120, 20, height - 120);
	}

	/**
	 * Metoda generuj¹ca animacjê uderzenia w blok
	 * 
	 * @return Informacjê czy blok zosta³ zniszczony
	 */
	protected boolean blockHitAnimation() {
		if (id == Id.wall
				&& (gameLogic.tempOfPlayerState == PlayerStates.big || gameLogic.tempOfPlayerState == PlayerStates.fire)) {
			gameLogic.sounds.breakBlock.play();
			gameLogic.handler.addEntity(new CrashedBlock(x, y, 32, 32, Id.crashedBlock, gameLogic, Facing.left, 10.0));
			gameLogic.handler.addEntity(new CrashedBlock(x, y + 32, 32, 32, Id.crashedBlock, gameLogic, Facing.left, 6.0));
			gameLogic.handler.addEntity(new CrashedBlock(x + 32, y, 32, 32, Id.crashedBlock, gameLogic, Facing.right, 10.0));
			gameLogic.handler.addEntity(new CrashedBlock(x + 32, y + 32, 32, 32, Id.crashedBlock, gameLogic, Facing.right, 6.0));
			destroyed = true;
			return false;
		} else {
			if (!gameLogic.sounds.bumpBlock.isRunning())
				gameLogic.sounds.bumpBlock.play();
			if (y > tempOfY - height / 4 && (velY == 0 || velY == -1)) {
				y -= jumpingAnimationSpeed;
				if (velY != -1)
					velY = -1;
				return false;
			} else if (y < tempOfY + height / 8 && (velY == 1 || velY == -1)) {
				y += jumpingAnimationSpeed;
				if (velY != 1)
					velY = 1;
				return false;
			} else if (y != tempOfY && (velY == 1 || velY == -2)) {
				y -= jumpingAnimationSpeed;
				velY = -2;
				return false;
			} else {
				velY = 0;
				return true;
			}
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
	
	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public int getJumpingAnimationSpeed() {
		return jumpingAnimationSpeed;
	}

	public void setJumpingAnimationSpeed(int jumpingAnimationSpeed) {
		this.jumpingAnimationSpeed = jumpingAnimationSpeed;
	}

	public boolean isJumpedBlock() {
		return jumpedBlock;
	}

	public void setJumpedBlock(boolean jumpedBlock) {
		this.jumpedBlock = jumpedBlock;
	}

	public int getTempOfY() {
		return tempOfY;
	}

	public void setTempOfY(int tempOfY) {
		this.tempOfY = tempOfY;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

}
