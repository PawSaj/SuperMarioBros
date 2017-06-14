package com.project.mario.enviroment;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.project.mario.GameLogic;
import com.project.mario.Handler;
import com.project.mario.entity.CrashedBlock;
import com.project.mario.entity.Entity;
import com.project.mario.enums.PlayerStates;
import com.project.mario.enums.TypeOfPipe;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;

/**
 * Klasa b�d�ca opisem og�lnym wszystkich element�w �rodowiska w grze.
 * 
 * 
 * 
 * @author Pawe� Sajn�g
 *
 */
public abstract class EnviromentObject {
	/**
	 * @param jumpedBlock
	 *            Okre�la czy blok jest w trakcie skoku (ma�y Mario nie ma tyle
	 *            si�y by go zniszczy�, jedynie mo�e zmusi� blok do skoku).
	 * @param activated
	 *            Okre�la czy blok zosta� aktywowany, aktywacja wi��e si� z
	 *            dodatkowym dzia�aniem (np. pojawieniem si� bonusu).
	 * @param tempOfY
	 *            Parametr zapisuj�cy tymczasow� warto�� Y bloku, dla element�w
	 *            pojawiaj�cych si� z tego bloku.
	 * @param isUnderground
	 *            Podobnie jak w {@link Entity} okre�la czy s� to elementy pod
	 *            ziemi� czy na powierzchni
	 */
	protected int x, y;
	protected int width, height;
	protected int velX, velY;
	protected Id id;

	protected boolean destroyed;
	protected boolean activated;

	protected int jumpingAnimationSpeed;
	protected boolean jumpedBlock;
	protected int tempOfY;
	protected TypeOfPipe typeOfPipe;

	protected int frame;
	protected int frameDelay;

	protected int isUnderground;

	protected GameLogic gameLogic;

	public EnviromentObject(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.gameLogic = gameLogic;

		activated = false;
		frame = 0;
		frameDelay = 0;
		tempOfY = y;

		jumpingAnimationSpeed = 8;

		jumpedBlock = true;

		if (y > 14 * 64 || gameLogic.level % 2 == 1)
			isUnderground = 0;
		else
			isUnderground = 1;
	}

	public EnviromentObject(int x, int y, int width, int height, Id id, GameLogic gameLogic, TypeOfPipe typeOfPipe) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.gameLogic = gameLogic;

		activated = false;
		frame = 0;
		frameDelay = 0;
		tempOfY = y;

		jumpingAnimationSpeed = 8;

		jumpedBlock = true;

		if (y > 14 * 64 || gameLogic.level % 2 == 1)
			isUnderground = 0;
		else
			isUnderground = 1;

		this.typeOfPipe = typeOfPipe;
	}

	/**
	 * Metoda odpowiedzialna za wyrenderowanie owego obiektu.
	 * 
	 * @param g
	 *            Parametr grafiki przekazany z obiektu okna.
	 */
	public abstract void render(Graphics g);

	/**
	 * Metoda odpowiedzialna za obliczenie po�o�enia, klatki animacji i innych
	 * element�w logicznych zwi�zanych z elementem w kolejnej klatce.
	 */
	public abstract void update();

	/**
	 * Metoda usuwaj�ca element ze �wiata gry (z listy element�w �rodowiska z
	 * klasy {@link Handler})
	 */
	public void destroy() {
		gameLogic.handler.removeEnviromentObject(this);
	}

	/**
	 * Metoda zwracaj�ca obszar zajmowany przez element
	 * 
	 * @return Obszar zajmowany przez element
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	/**
	 * Metoda zwracaj�ca obszar zajmowany przez element Pipe dost�pny do
	 * interakcji z graczem
	 * 
	 * @return Obszar zajmowany przez element Pipe
	 */
	public Rectangle getBoundsPipe() {
		return new Rectangle(x + 60, y, width - 120, height);
	}

	/**
	 * Metoda zwracaj�ca lew� cz�� obszaru zajmowanego przez element Pipe
	 * dost�pny do interakcji z graczem
	 * 
	 * @return Lewa cz�� obszaru zajmowanego przez element Pipe
	 */
	public Rectangle getBoundsLeft() {
		return new Rectangle(x - 10, y + 120, 20, height - 120);
	}

	/**
	 * Metoda generuj�ca animacj� uderzenia w blok
	 * 
	 * @return Informacj� czy blok zosta� zniszczony
	 */
	protected boolean blockHitAnimation() {
		if (id == Id.wall && (gameLogic.tempOfPlayerState == PlayerStates.big
				|| gameLogic.tempOfPlayerState == PlayerStates.fire)) {
			gameLogic.sounds.breakBlock.play();
			gameLogic.handler.addEntity(new CrashedBlock(x, y, 32, 32, Id.crashedBlock, gameLogic, Facing.left, 10.0));
			gameLogic.handler
					.addEntity(new CrashedBlock(x, y + 32, 32, 32, Id.crashedBlock, gameLogic, Facing.left, 6.0));
			gameLogic.handler
					.addEntity(new CrashedBlock(x + 32, y, 32, 32, Id.crashedBlock, gameLogic, Facing.right, 10.0));
			gameLogic.handler
					.addEntity(new CrashedBlock(x + 32, y + 32, 32, 32, Id.crashedBlock, gameLogic, Facing.right, 6.0));
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

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public TypeOfPipe getTypeOfPipe() {
		return typeOfPipe;
	}

	public int getIsUnderground() {
		return isUnderground;
	}

	public void setIsUnderground(int isUnderground) {
		this.isUnderground = isUnderground;
	}

}
