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
 * Klasa bêd¹ca opisem ogólnym wszystkich jednostek w grze.
 * 
 * 
 * 
 * @author Pawe³ Sajnóg
 *
 */
public abstract class Entity {
	/**
	 * @param isUnderground
	 *            Parametr mówi¹cy czy jednostka znajduje siê w podziemiach
	 *            (poziomy w podziemiach) czy nie. Zdefiniowane liczbê, poniewa¿
	 *            stanowi to indeks w tablicy z grafik¹ wielu elementów
	 * @param DELAY_VALUE
	 *            Parametr sta³y mówi¹cy jak bardzo ma byæ opóiana animacja
	 *            elementu
	 * @param FRAMES
	 *            Parametr sta³y, ustalany przez ka¿dy obiekt, mówi¹cy o iloœci
	 *            klatek animacji danego obiektu
	 * @param frame
	 *            Parametr bêd¹cy wynikiem obliczeñ aktualnej klatki animacji
	 *            elementu do wywietlenia
	 * @param frameDelay
	 *            Parametr s³u¿¹cy do ustalenia ile jeszcze fps zosta³o do
	 *            zmiany kaltki animacji obiektu
	 * @param goingDownPipe
	 *            Parametr dla gracza okreœlaj¹cy czy porusza siê po ruroci¹gu
	 *            do do³u
	 * @param goingUpPipe
	 *            Parametr dla gracza okreœlaj¹cy czy porusza siê po ruroci¹gu
	 *            do góry
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
	 * Metoda odpowiedzialna za obliczenie po³o¿enia, klatki animacji i innych
	 * elementów logicznych zwi¹zanych z elementem w kolejnej klatce.
	 */
	public abstract void update();

	/**
	 * Metoda odpowiedzialna za stworzenie animacji niszczonego elementu.
	 */
	public abstract void entityDieAnimation();

	/**
	 * Metoda usuwaj¹ca dany obiekt z listy obiektów w grze.
	 */
	public void die() {
		gameLogic.handler.removeEntity(this);
	}

	/**
	 * Metoda okreœlaj¹ca kolejn¹ klatkê animacji.
	 * 
	 * @param delayValue
	 *            Parametr opóienia animacji.
	 * @param frames
	 *            Parametr iloœci klatek animcji danego elementu.
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
	 * Metoda tworz¹ca posik strzelany przez Mario
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
	 *            Obraz do obrócenia.
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
	 * Metoda zwracaj¹ca obszar zajmowany przez element
	 * 
	 * @return Obszar zajmowany przez element
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	/**
	 * Metoda okreœlaj¹ca górn¹ czêœæ obszaru danego elementu.
	 * 
	 * @return Obszar odpowiadaj¹cy górnej czêœci elementu.
	 */
	public Rectangle getBoundsTop() {
		return new Rectangle(x + 10, y, width - 20, 10);
	}

	/**
	 * Metoda okreœlaj¹ca doln¹ czêœæ obszaru danego elementu.
	 * 
	 * @return Obszar odpowiadaj¹cy dolnej czêœci elementu.
	 */
	public Rectangle getBoundsBottom() {
		return new Rectangle(x + 10, y + height - 10, width - 20, 10);
	}

	/**
	 * Metoda zwracaj¹ca doln¹ czêœæ obszaru zajmowanego przez element Pipe
	 * dostêpny do interakcji z innymi elementami
	 * 
	 * @return Dolna czêœæ obszaru zajmowanego przez element Pipe
	 */
	public Rectangle getBoundsBottomPipe() {
		return new Rectangle(x + 20, y + height, width - 40, 10);
	}

	/**
	 * Metoda okreœlaj¹ca lew¹ czêœæ obszaru danego elementu.
	 * 
	 * @return Obszar odpowiadaj¹cy lewej czêœci elementu.
	 */
	public Rectangle getBoundsLeft() {
		return new Rectangle(x, y + 5, 10, height - 10);
	}

	/**
	 * Metoda okreœlaj¹ca praw¹ czêœæ obszaru danego elementu.
	 * 
	 * @return Obszar odpowiadaj¹cy prawej czêœci lementu.
	 */
	public Rectangle getBoundsRight() {
		return new Rectangle(x + width - 10, y + 5, 10, height - 10);
	}

	/**
	 * Metoda okreœlaj¹ca obszar, bêd¹cy wra¿liwym punktem gracza. Interakcja z
	 * nim powoduje obra¿enia u gracza.
	 * 
	 * @return Obszar odpowiadaj¹cy wra¿liwemu punktowi gracza.
	 */
	public Rectangle getBoundsPlayerHearth() {
		return new Rectangle(x, y, width, height - 32);
	}

	/**
	 * Metoda okreœlaj¹ca lew¹ czêœæ obszaru jednostki Koopa, która wchodzi w
	 * interakcjê z otoczeniem
	 * 
	 * @return Obszar odpowiadaj¹cy lewej czêœci elementu.
	 */
	public Rectangle getBoundsLeftKoopa() {
		if (id == Id.koopaTroopa)
			return new Rectangle(x, y + height / 3, 10, height - height * 2 / 3);
		else
			return getBoundsLeft();
	}

	/**
	 * Metoda okreœlaj¹ca praw¹ czêœæ obszaru jednostki Koopa, która wchodzi w
	 * interakcjê z otoczeniem
	 * 
	 * @return Obszar odpowiadaj¹cy prawej czêœci elementu.
	 */
	public Rectangle getBoundsRightKoopa() {
		if (id == Id.koopaTroopa)
			return new Rectangle(x + width - 10, y + height / 3, 10, height - height * 2 / 3);
		else
			return getBoundsRight();
	}

	/**
	 * Metoda okreœlaj¹ca praw¹ czêœæ obszaru Fireballa strzelanego przez Mario,
	 * która wchodzi w interakcjê z otoczeniem
	 * 
	 * @return Obszar odpowiadaj¹cy prawej czêœci elementu.
	 */
	public Rectangle getBoundsRightFireball() {
		return new Rectangle(x + width, y + 5, 10, height - 10);
	}

	/**
	 * Metoda okreœlaj¹ca lew¹ czêœæ obszaru Fireballa strzelanego przez Mario,
	 * która wchodzi w interakcjê z otoczeniem
	 * 
	 * @return Obszar odpowiadaj¹cy lewej czêœci elementu.
	 */
	public Rectangle getBoundsLeftFireball() {
		return new Rectangle(x - 10, y + 5, 10, height - 10);
	}

	/**
	 * Metoda okreœlaj¹ca górn¹ czêœæ obszaru Fireballa strzelanego przez Mario,
	 * która wchodzi w interakcjê z otoczeniem
	 * 
	 * @return Obszar odpowiadaj¹cy górnej czêœci elementu.
	 */
	public Rectangle getBoundsTopFireball() {
		return new Rectangle(x + 5, y - 10, width - 10, 10);
	}

	/**
	 * Metoda okreœlaj¹ca doln¹ czêœæ obszaru Fireballa strzelanego przez Mario,
	 * która wchodzi w interakcjê z otoczeniem
	 * 
	 * @return Obszar odpowiadaj¹cy dolnejs czêœci elementu.
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
