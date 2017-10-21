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

/**
 * Klasa b�d�ca opisem og�lnym wszystkich jednostek w grze.
 * 
 * 
 * 
 * @author Pawe� Sajn�g
 *
 */
public abstract class Entity {
	/**
	 * @param isUnderground
	 *            Parametr m�wi�cy czy jednostka znajduje si� w podziemiach
	 *            (poziomy w podziemiach) czy nie. Zdefiniowane liczb�, poniewa�
	 *            stanowi to indeks w tablicy z grafik� wielu element�w
	 * @param DELAY_VALUE
	 *            Parametr sta�y m�wi�cy jak bardzo ma by� op�iana animacja
	 *            elementu
	 * @param FRAMES
	 *            Parametr sta�y, ustalany przez ka�dy obiekt, m�wi�cy o ilo�ci
	 *            klatek animacji danego obiektu
	 * @param frame
	 *            Parametr b�d�cy wynikiem oblicze� aktualnej klatki animacji
	 *            elementu do wywietlenia
	 * @param frameDelay
	 *            Parametr s�u��cy do ustalenia ile jeszcze fps zosta�o do
	 *            zmiany kaltki animacji obiektu
	 * @param goingDownPipe
	 *            Parametr dla gracza okre�laj�cy czy porusza si� po ruroci�gu
	 *            do do�u
	 * @param goingUpPipe
	 *            Parametr dla gracza okre�laj�cy czy porusza si� po ruroci�gu
	 *            do g�ry
	 */
	protected int x;
	protected int y;
	protected int width, height;
	protected int velX, velY;
	protected Id id;
	protected Facing facing;

	protected GameLogic gameLogic;
	protected boolean falling;
	protected boolean jumping;
	private boolean ground;
	protected double gravity;
	protected int isUnderground; // 0 false, 1 true
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
		ground = false;

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
	 * Metoda odpowiedzialna za stworzenie animacji niszczonego elementu.
	 */
	public abstract void entityDieAnimation();

	/**
	 * Metoda usuwaj�ca dany obiekt z listy obiekt�w w grze.
	 */
	public void die() {
		gameLogic.handler.removeEntity(this);
	}

	/**
	 * Metoda okre�laj�ca kolejn� klatk� animacji.
	 * 
	 * @param delayValue
	 *            Parametr op�ienia animacji.
	 * @param frames
	 *            Parametr ilo�ci klatek animcji danego elementu.
	 */
	protected void setNextFrame(int delayValue, int frames) {
		frameDelay++;
		if (frameDelay >= delayValue) {
			frame++;
			frame = frame % frames;
			frameDelay = 0;
		}
	}

	/**
	 * Metoda tworz�ca posik strzelany przez Mario
	 */
	public void createMarioFireball() {
		if (facing == Facing.left)
			gameLogic.handler
					.addEntity(new MarioFireball(x-32, y + height * 2 / 3, 24, 24, Id.marioFireball, gameLogic, facing));
		else
			gameLogic.handler.addEntity(
					new MarioFireball(x + width+32, y + height * 2 / 3, 24, 24, Id.marioFireball, gameLogic, facing));
		gameLogic.sounds.fireball.play();
		isShooting = true;
	}

	/**
	 * Metoda odpowiedzialna za obracanie elementu graficznego
	 * 
	 * @param g
	 *            Parametr grafiki przekazany z obiektu okna.
	 * @param image
	 *            Obraz do obr�cenia.
	 */
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

	/**
	 * Metoda zwracaj�ca obszar zajmowany przez element
	 * 
	 * @return Obszar zajmowany przez element
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	/**
	 * Metoda okre�laj�ca g�rn� cz�� obszaru danego elementu.
	 * 
	 * @return Obszar odpowiadaj�cy g�rnej cz�ci elementu.
	 */
	public Rectangle getBoundsTop() {
		return new Rectangle(x + 10, y, width - 20, 10);
	}

	/**
	 * Metoda okre�laj�ca doln� cz�� obszaru danego elementu.
	 * 
	 * @return Obszar odpowiadaj�cy dolnej cz�ci elementu.
	 */
	public Rectangle getBoundsBottom() {
		return new Rectangle(x + 10, y + height - 10, width - 20, 10);
	}

	/**
	 * Metoda zwracaj�ca doln� cz�� obszaru zajmowanego przez element Pipe
	 * dost�pny do interakcji z innymi elementami
	 * 
	 * @return Dolna cz�� obszaru zajmowanego przez element Pipe
	 */
	public Rectangle getBoundsBottomPipe() {
		return new Rectangle(x + 20, y + height, width - 40, 10);
	}

	/**
	 * Metoda okre�laj�ca lew� cz�� obszaru danego elementu.
	 * 
	 * @return Obszar odpowiadaj�cy lewej cz�ci elementu.
	 */
	public Rectangle getBoundsLeft() {
		return new Rectangle(x, y + 5, 10, height - 10);
	}

	/**
	 * Metoda okre�laj�ca praw� cz�� obszaru danego elementu.
	 * 
	 * @return Obszar odpowiadaj�cy prawej cz�ci lementu.
	 */
	public Rectangle getBoundsRight() {
		return new Rectangle(x + width - 10, y + 5, 10, height - 10);
	}

	/**
	 * Metoda okre�laj�ca obszar, b�d�cy wra�liwym punktem gracza. Interakcja z
	 * nim powoduje obra�enia u gracza.
	 * 
	 * @return Obszar odpowiadaj�cy wra�liwemu punktowi gracza.
	 */
	public Rectangle getBoundsPlayerHearth() {
		return new Rectangle(x, y, width, height - 32);
	}

	/**
	 * Metoda okre�laj�ca lew� cz�� obszaru jednostki Koopa, kt�ra wchodzi w
	 * interakcj� z otoczeniem
	 * 
	 * @return Obszar odpowiadaj�cy lewej cz�ci elementu.
	 */
	public Rectangle getBoundsLeftKoopa() {
		if (id == Id.koopaTroopa)
			return new Rectangle(x, y + height / 3, 10, height - height * 2 / 3);
		else
			return getBoundsLeft();
	}

	/**
	 * Metoda okre�laj�ca praw� cz�� obszaru jednostki Koopa, kt�ra wchodzi w
	 * interakcj� z otoczeniem
	 * 
	 * @return Obszar odpowiadaj�cy prawej cz�ci elementu.
	 */
	public Rectangle getBoundsRightKoopa() {
		if (id == Id.koopaTroopa)
			return new Rectangle(x + width - 10, y + height / 3, 10, height - height * 2 / 3);
		else
			return getBoundsRight();
	}

	/**
	 * Metoda okre�laj�ca praw� cz�� obszaru Fireballa strzelanego przez Mario,
	 * kt�ra wchodzi w interakcj� z otoczeniem
	 * 
	 * @return Obszar odpowiadaj�cy prawej cz�ci elementu.
	 */
	public Rectangle getBoundsRightFireball() {
		return new Rectangle(x + width, y + 5, 10, height - 10);
	}

	/**
	 * Metoda okre�laj�ca lew� cz�� obszaru Fireballa strzelanego przez Mario,
	 * kt�ra wchodzi w interakcj� z otoczeniem
	 * 
	 * @return Obszar odpowiadaj�cy lewej cz�ci elementu.
	 */
	public Rectangle getBoundsLeftFireball() {
		return new Rectangle(x - 10, y + 5, 10, height - 10);
	}

	/**
	 * Metoda okre�laj�ca g�rn� cz�� obszaru Fireballa strzelanego przez Mario,
	 * kt�ra wchodzi w interakcj� z otoczeniem
	 * 
	 * @return Obszar odpowiadaj�cy g�rnej cz�ci elementu.
	 */
	public Rectangle getBoundsTopFireball() {
		return new Rectangle(x + 5, y - 10, width - 10, 10);
	}

	/**
	 * Metoda okre�laj�ca doln� cz�� obszaru Fireballa strzelanego przez Mario,
	 * kt�ra wchodzi w interakcj� z otoczeniem
	 * 
	 * @return Obszar odpowiadaj�cy dolnejs cz�ci elementu.
	 */
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
