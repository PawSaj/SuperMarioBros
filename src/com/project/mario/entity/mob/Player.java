package com.project.mario.entity.mob;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.project.mario.GameLogic;
import com.project.mario.entity.Entity;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;
import com.project.mario.enums.PlayerStates;
import com.project.mario.enums.TypeOfPipe;
import com.project.mario.enviroment.EnviromentObject;

/**
 * Klasa generuj¹ca i opisuj¹ca zachowanie Gracza.
 * 
 * 
 */
public class Player extends Entity {

	/**
	 * @param IMMORTAL_TIME
	 *            sta³a mówi¹ca i tym jak d³ugo mario jest niesmiertelny po
	 *            olizji.
	 * @param pixelsTraveled
	 *            okreœla ile gracz przeby³ pixeli w czasie wchodzenia do
	 *            ruroci¹gu.
	 * @param tempOfPlayerDiffrentInHeight
	 *            zmienna mówi¹ca o wiekoœci gracza w momencie zetkniêcia siê z
	 *            flag¹.
	 * @param shootingTime
	 *            zmienna aktywuj¹ca na okreœlony czas animacjê strza³u, gdy
	 *            gracz. siê nie porusza
	 * @param tempOfPlayerDiffrentInHeight
	 *            Okreœla ró¿nicê miêdzy du¿ym, a ma³ym Mario.
	 * @param STARMAN_TIME
	 *            Czas w jakim Mario przyjmuje postaæ Starmana.
	 * @param rowOfPlayerGraphics
	 *            Parametr wyznaczania odpowiedniego rzêdu w siatce z grafik¹
	 *            gracza.
	 */
	private boolean move;
	private final int IMMORTAL_TIME;
	private int immortalTimeCounter;
	private int pixelsTraveled;
	private int jumpingFromPole;
	private int tempOfPlayerDiffrentInHeight;
	private int starmanFrame;
	private int starmanFrameDelay;
	private int maxOfStarmanFrameDelay;
	private final int STARMAN_TIME;
	private int starmanTimeCounter;
	private int rowOfPlayerGraphics;
	private int shootingTime;

	public Player(int x, int y, int width, int height, Id id, GameLogic gameLogic, Facing facing) {
		super(x, y, width, height, id, gameLogic, facing);
		move = false;
		IMMORTAL_TIME = 120;
		immortalTimeCounter = IMMORTAL_TIME;
		STARMAN_TIME = 720;
		starmanTimeCounter = 0;
		setState(gameLogic.tempOfPlayerState);
		pixelsTraveled = 0;
		jumpingFromPole = 0;
		setGoingDownPipe(false);
		setGoingUpPipe(false);
		tempOfPlayerDiffrentInHeight = 0;
		isUnderground = 0;
		random = new Random();
		starmanFrame = 0;
		starmanFrameDelay = 0;
		maxOfStarmanFrameDelay = 2;
		rowOfPlayerGraphics = 0;
		isShooting = false;
		shootingTime = 0;

		DELAY_VALUE = 6;
		FRAMES = 5;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		if (gameLogic.tempOfPlayerState == PlayerStates.fire) {
			if (rowOfPlayerGraphics != 1) {
				rowOfPlayerGraphics = 1;
			}
		} else {
			if (rowOfPlayerGraphics != 0) {
				rowOfPlayerGraphics = 0;
			}
		}

		if (getState() == PlayerStates.dead) {
			g.drawImage(gameLogic.graphics.player[16][rowOfPlayerGraphics].getBufferedImage(), x, y, width, height,
					null);
		} else {
			setStarmanColors(g);

			if (getState() == PlayerStates.slidingOnPole) {
				renderSlidingAnimation(g);
			} else {
				if (immortalTimeCounter != IMMORTAL_TIME && immortalTimeCounter % 8 > 3)
					return;
				renderWalkingAndJumpingAnimation(g);
			}

			g.setPaintMode();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#update()
	 */
	public void update() {
		if (gameLogic.sounds.powerUp.isRunning()) {
			frameDelay++;
			frameDelay %= 3;
			if (frameDelay == 0) {
				if (height == 64) {
					height = 128;
				} else {
					height = 64;
				}
			}
		} else {
			if (x >= -gameLogic.cam.getX())
				x += velX;
			else if (velX > 0)
				x += velX;
			y += velY;

			if (velX != 0)
				move = true;
			else
				move = false;

			if (getState() == PlayerStates.dead) {
				entityDieAnimation();
			} else {
				checkFallDown();

				chceckSizeOfPlayer();

				checkIsImmortalOrStarman();

				checkGoingByPipes();

				entityCollisionaDetect();

				setAnimationFrame();
			}

			jumping();

			falling();
		}
	}

	/**
	 * Metoda do okreœlenia odpowiedniej klatki animacji podczas chodzenia oraz
	 * skakania.
	 * 
	 * @param g
	 *            Parametr grafiki przekazany z obiektu okna.
	 */
	private void renderWalkingAndJumpingAnimation(Graphics g) {
		if (facing == Facing.left) {
			if (frame < 0)
				frame = 0;
			if (!jumping && isGround())
				if (isShooting && !move) {
					g.drawImage(gameLogic.graphics.player[11][rowOfPlayerGraphics].getBufferedImage(), x, y, width,
							height, null);
					if (shootingTime >= 20) {
						isShooting = false;
						shootingTime = 0;
					}
					shootingTime++;
				} else {
					g.drawImage(gameLogic.graphics.player[frame + 9][rowOfPlayerGraphics].getBufferedImage(), x, y,
							width, height, null);
				}
			else
				g.drawImage(gameLogic.graphics.player[7][rowOfPlayerGraphics].getBufferedImage(), x, y, width, height,
						null);
		} else if (facing == Facing.right) {
			if (frame > 0)
				frame = 0;
			if (!jumping && isGround()) {
				if (isShooting && !move) {
					g.drawImage(gameLogic.graphics.player[3][rowOfPlayerGraphics].getBufferedImage(), x, y, width,
							height, null);
					if (shootingTime >= 20) {
						isShooting = false;
						shootingTime = 0;
					}
					shootingTime++;
				} else {
					g.drawImage(gameLogic.graphics.player[frame + 4][rowOfPlayerGraphics].getBufferedImage(), x, y,
							width, height, null);
				}
			} else
				g.drawImage(gameLogic.graphics.player[6][rowOfPlayerGraphics].getBufferedImage(), x, y, width, height,
						null);
		}
	}

	/**
	 * Metoda wyznaczaj¹cy aktualny kolor gracza, gdy jest one Starmanem.
	 * 
	 * @param g
	 *            Parametr grafiki przekazany z obiektu okna.
	 */
	private void setStarmanColors(Graphics g) {
		if (getState() == PlayerStates.starman) {
			if (starmanFrame == 0) {
				g.setXORMode(Color.GREEN);
			} else if (starmanFrame == 1) {
				g.setXORMode(Color.RED);
			} else if (starmanFrame == 2) {
				g.setXORMode(Color.BLUE);
			} else if (starmanFrame == 3) {
				;
			}
		}
	}

	/**
	 * Metoda ustalaj¹ca klatkê animacji gracza wchodz¹cego do ruroc¹gu.
	 * 
	 * @param g
	 *            Parametr grafiki przekazany z obiektu okna.
	 */
	private void renderSlidingAnimation(Graphics g) {
		if (jumpingFromPole >= 60)
			g.drawImage(gameLogic.graphics.player[14][rowOfPlayerGraphics].getBufferedImage(), x, y, width, height,
					null);
		else
			g.drawImage(gameLogic.graphics.player[15][rowOfPlayerGraphics].getBufferedImage(), x, y, width, height,
					null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#entityDieAnimation()
	 */
	public void entityDieAnimation() {
		if (!gameLogic.sounds.marioDieSound.isRunning()) {
			gameLogic.coins = 0;
			gameLogic.lives--;
			gameLogic.showDeathScreen = true;
			if (gameLogic.lives == 0) {
				gameLogic.gameOverScreen = true;
				gameLogic.level = 0;
				gameLogic.tempOfCoins = 0;
				gameLogic.sounds.gameOverSound.play();
			}
			gameLogic.tempOfPlayerState = PlayerStates.small;
			die();
		}
	}

	/**
	 * Metoda mówi¹ca o tym jakiej wysokoœci powienien byæ gracz dla danego
	 * stanu.
	 * 
	 * @return Wysokoœæ przypisan¹ do danego stanu gracza.
	 */
	private int diffrentInHeight() {
		if (getState() == PlayerStates.small)
			return 192;
		else
			return 256;
	}

	/**
	 * Metoda sprawdzaj¹ca czy gracz nie spad³ w przepaœæ.
	 */
	private void checkFallDown() {
		if (y > 31 * 64 || (isUnderground == 0 && y > 15 * 64)) {
			setState(PlayerStates.dead);
			gameLogic.tempOfPlayerState = getState();
			gameLogic.sounds.mainThemeSong.stop();
			gameLogic.sounds.underworldSong.stop();
			gameLogic.sounds.marioDieSound.play();
			y += 64;
			velX = 0;
			velY = 0;
			jumping = false;
			falling = false;
			setGround(true);
			gravity = 0.0;
		}
	}

	/**
	 * Metoda sprawdzaj¹ca czy gracz jest odpowiednich rozmiarów. Jeœli nie
	 * dokonuje korekty.
	 */
	private void chceckSizeOfPlayer() {
		if ((getState() == PlayerStates.big || getState() == PlayerStates.fire) && height == 64)
			height *= 2;
		if (getState() == PlayerStates.small && height != 64)
			height = 64;
	}

	/**
	 * Mateoda odpowiedzialna za animacjê, gdy Mario idzie do zamku po
	 * ukoñczeniu poziomu.
	 * 
	 * @param enviromentObject
	 *            Obiekt docelowy - zamek.
	 */
	private void goingToCastel(EnviromentObject enviromentObject) {
		if (y > enviromentObject.getY() + enviromentObject.getHeight() - tempOfPlayerDiffrentInHeight
				&& !gameLogic.sounds.flagPole.isRunning()) {
			setState(PlayerStates.goingToCastel);
			if (velY != 0)
				setVelY(0);
			if (velX == 0)
				setVelX(2);
			if (!gameLogic.sounds.stageClear.isRunning())
				gameLogic.sounds.stageClear.play();
		} else if (y > enviromentObject.getY() + enviromentObject.getHeight() - tempOfPlayerDiffrentInHeight) {
			if (x == enviromentObject.getX() - 24)
				x = enviromentObject.getX() + 24;
			if (velY != 0)
				setVelY(0);
			jumpingFromPole--;
		}
	}

	/**
	 * Metoda odpowiedzialna za animacjê Mario zje¿d¿aj¹cego na fladze.
	 * 
	 * @param enviromentObject
	 *            Obiekt flagi.
	 */
	private void slidingOnPole(EnviromentObject enviromentObject) {
		if (velX != 0) {
			if (!gameLogic.sounds.flagPole.isRunning())
				gameLogic.sounds.flagPole.play();
			tempOfPlayerDiffrentInHeight = diffrentInHeight();
			gravity = 0.0;
			jumping = false;
			falling = false;
			enviromentObject.setActivated(true);
			setVelY(4);
			setVelX(0);
			x = enviromentObject.getX() - 24;
			jumpingFromPole = 60;
		}
		enviromentObject.setVelY(y + height - 16);

	}

	/**
	 * Metoda wykrywania kolizji gracza z flag¹.
	 * 
	 * @param enviromentObject
	 *            Obiekt flagi.
	 */
	private void flagCollision(EnviromentObject enviromentObject) {
		if (getState() != PlayerStates.goingToCastel) {
			if (y < enviromentObject.getY() + enviromentObject.getHeight() - 128) {
				if (getState() != PlayerStates.slidingOnPole)
					gameLogic.tempOfPlayerState = getState();
				slidingOnPole(enviromentObject);
				if (getState() != PlayerStates.slidingOnPole)
					setState(PlayerStates.slidingOnPole);
			} else {
				setVelX(0);
				x = enviromentObject.getX() - width;
			}
		}
		goingToCastel(enviromentObject);
	}

	/**
	 * Metoda wykrywaj¹ca kolizjê gracza i elementami œrodowiska.
	 */
	private void enviromentObjectCollisionDetect() {
		for (EnviromentObject env : gameLogic.handler.enviromentObject) {

			if (env.getId() == Id.flag && getBounds().intersects(env.getBounds())) {
				gameLogic.sounds.mainThemeSong.stop();
				gameLogic.sounds.underworldSong.stop();
				flagCollision(env);
			} else if (env.getId() == Id.castel && getBounds().intersects(env.getBounds())) {
				setVelX(0);
				if (!gameLogic.sounds.stageClear.isRunning())
					gameLogic.switchLevel();
			} else if (env.getId() == Id.fireball && getBounds().intersects(env.getBounds())) {
				hurting();
			} else if (env.getId() == Id.hammer && getBounds().intersects(env.getBounds())) {
				if (x > env.getX() + 16) {
					env.setActivated(true);
					goingToPrinces();
				}
			} else if (env.getId() != Id.trunk) {
				if (getState() == PlayerStates.goingToPrinces) {
					if (env.getId() == Id.princes && getBounds().intersects(env.getBounds())) {
						env.setActivated(true);
						velX = 0;
					} else
						velX = 2;
				}
				if (getBoundsTop().intersects(env.getBounds())) {
					setVelY(0);
					y = env.getY() + env.getHeight();
					if (jumping) {
						jumping = false;
						gravity = 7.0;
						falling = true;
					}
					if (env.getId() == Id.powerUpBlock || env.getId() == Id.starmanBlock || env.getId() == Id.coinBlock
							|| env.getId() == Id.hiddenCoinsBlock) {
						if (getState() != PlayerStates.starman)
							gameLogic.tempOfPlayerState = getState();
					}
					env.setActivated(true);
				}
				if (getBoundsBottom().intersects(env.getBounds())) {
					setVelY(0);
					y = env.getY() - height;
					if (falling)
						falling = false;
					if (!isGround())
						setGround(true);
				} else {
					if (!falling && !jumping && getState() != PlayerStates.slidingOnPole) {
						gravity = 0.0;
						falling = true;
					}
				}
				if (getBoundsLeft().intersects(env.getBounds())) {
					setVelX(0);
					x = env.getX() + env.getWidth();
				}
				if (getBoundsRight().intersects(env.getBounds())) {
					setVelX(0);
					x = env.getX() - width;
				}
			}
		}
	}

	/**
	 * Metoda ustawiaj¹ca odpowiednie parametry w momencie gdy Mario idzie do
	 * ksiê¿niczki.
	 */
	private void goingToPrinces() {
		setState(PlayerStates.goingToPrinces);
		velX = 2;
		gameLogic.sounds.underworldSong.stop();
		gameLogic.sounds.stageClear.play();

	}

	/**
	 * Metoda okreœlaj¹ca rekacjê gdy staniemy na przeciwniku.
	 * 
	 * @param e
	 *            Przeciwnik.
	 */
	private void stepOnEnemy(Entity e) {
		if (getState() != PlayerStates.starman) {
			if (e.getId() == Id.goomba) {
				e.setId(Id.deadGoomba);
			} else if (e.getId() == Id.koopaTroopa) {
				e.setVelX(0);
				e.setY(e.getY() + 32);
				e.setHeight(64);
				e.setId(Id.deadKoopa);
			} else if (e.getId() == Id.deadKoopa) {
				if (e.getVelX() != 0) {
					e.setVelX(0);
				} else {
					if (x + width / 2 > e.getX() + e.getWidth() / 2) {
						e.setVelX(8);
					} else {
						e.setVelX(8);
					}
				}

			}
			y -= 64;
			gravity = 0.0;
			if (e.getId() != Id.deadKoopa)
				gameLogic.sounds.hitEnemy.play();
			else
				gameLogic.sounds.kickDeadKoopa.play();
		} else {
			e.entityDieAnimation();
		}
	}

	/**
	 * Metoda odpowiedzialna za analizê obra¿eñ jakie otrzymuje Mario.
	 */
	private void hurting() {
		if (getState() == PlayerStates.big || getState() == PlayerStates.fire) {
			setState(PlayerStates.immortal);
			gameLogic.tempOfPlayerState = getState();
			height /= 2;
			x += width;
		} else if (getState() == PlayerStates.small) {
			setState(PlayerStates.dead);
			gameLogic.tempOfPlayerState = getState();
			gameLogic.sounds.mainThemeSong.stop();
			gameLogic.sounds.underworldSong.stop();
			gameLogic.sounds.marioDieSound.play();
			velX = 0;
			velY = 0;
			gravity = 10.0;
			jumping = true;
			falling = false;
			setGround(false);
		}
	}

	/**
	 * Metoda wykrywania kolizji z grzybem wzrostu.
	 * 
	 * @param e
	 *            Obiekt do weryfikacji.
	 */
	private void mushroomCollision(Entity e) {
		if (getBounds().intersects(e.getBounds())) {
			setX(getX() - 20);
			setY(getY() - 20);

			if (getState() == PlayerStates.small || getState() == PlayerStates.immortal
					|| getState() == PlayerStates.starman) {
				height *= 2;
				y -= 32;
				if (getState() != PlayerStates.starman || getState() == PlayerStates.immortal) {
					setState(PlayerStates.big);
					gameLogic.tempOfPlayerState = getState();
				} else
					gameLogic.tempOfPlayerState = PlayerStates.big;
			}
			gameLogic.sounds.powerUp.play();
			e.die();
		}
	}

	/**
	 * Metoda wykrywaj¹ca kolizjê z innymi elementami, gdy Mario przeobrazi³ siê
	 * w Starmana.
	 * 
	 * @param e
	 *            Obiekt do weryfikacji.
	 */
	private void starmanCollision(Entity e) {
		if (getBounds().intersects(e.getBounds())) {
			if (getState() != PlayerStates.starman)
				gameLogic.tempOfPlayerState = getState();
			setState(PlayerStates.starman);
			e.die();
			if (gameLogic.sounds.mainThemeSong.isRunning())
				gameLogic.sounds.mainThemeSong.stop();
			else if (gameLogic.sounds.underworldSong.isRunning())
				gameLogic.sounds.underworldSong.stop();
			gameLogic.sounds.starmanSound.play();
		}
	}

	/**
	 * Metoda odpowiadaj¹ca za wykrywanie kolizji z kwiatkiem wzrostu.
	 * 
	 * @param e
	 *            Obiekt do weryfikacji.
	 */
	private void fireFlowCollision(Entity e) {
		if (getBounds().intersects(e.getBounds())) {
			if (getState() == PlayerStates.small) {
				if (getState() != PlayerStates.starman)
					setState(PlayerStates.big);
				gameLogic.tempOfPlayerState = PlayerStates.big;
			} else {
				if (getState() != PlayerStates.starman)
					setState(PlayerStates.fire);
				gameLogic.tempOfPlayerState = PlayerStates.fire;
			}
			gameLogic.sounds.powerUp.play();
			e.die();
		}
	}

	/**
	 * Metoda odpowiadaj¹ca za wykrywanie kolizji z przeciwnikiem Goomba.
	 * 
	 * @param e
	 *            Obiekt do weryfikacji.
	 */
	private void goombaCollision(Entity e) {
		if (getBounds().intersects(e.getBounds())) {
			if (!getBoundsPlayerHearth().intersects(e.getBounds()) || getState() == PlayerStates.starman) {
				stepOnEnemy(e);
			} else {
				if (getState() != PlayerStates.immortal)
					hurting();
			}
		}
	}

	/**
	 * Metoda odpowiadaj¹ca za wykrywanie kolizji z Bowserem.
	 * 
	 * @param e
	 *            Obiekt do weryfikacji.
	 */
	private void bowserCollision(Entity e) {
		if (getBounds().intersects(e.getBounds()) && getState() != PlayerStates.immortal) {
			hurting();
		}
	}

	/**
	 * Metoda odpowiadaj¹ca za wykrywanie kolizji z pomieniami Bowsera lub
	 * Piranha Plants.
	 * 
	 * @param e
	 *            Obiekt do weryfikacji.
	 */
	private void bowserFireOrPiranhaPlandCollision(Entity e) {
		if (getBounds().intersects(e.getBounds()) && getState() != PlayerStates.immortal) {
			if (getState() == PlayerStates.starman) {
				e.entityDieAnimation();
			} else {
				hurting();
			}
		}
	}

	/**
	 * Metoda odpowiadaj¹ca za wykrywanie kolizji z Koopa.
	 * 
	 * @param e
	 *            Obiekt do weryfikacji.
	 */
	private void koopaCollision(Entity e) {
		if (getBounds().intersects(e.getBounds()) && getState() != PlayerStates.immortal) {
			if (getState() != PlayerStates.starman) {
				if (!getBoundsPlayerHearth().intersects(e.getBounds())) {
					stepOnEnemy(e);
				} else {
					hurting();
				}
			} else {
				e.entityDieAnimation();
			}

		}
	}

	/**
	 * Metoda odpowiadaj¹ca za wykrywanie kolizji z muszl¹ Koopa.
	 * 
	 * @param e
	 *            Obiekt do weryfikacji.
	 */
	private void deadKoopaCollision(Entity e) {
		if (getBounds().intersects(e.getBounds()) && getState() != PlayerStates.immortal) {
			if (getState() != PlayerStates.starman) {
				if (!getBoundsPlayerHearth().intersects(e.getBounds())) {
					stepOnEnemy(e);
				} else if (e.getVelX() == 0) {
					if (getBoundsRight().intersects(e.getBounds())) {
						e.setX(e.getX() + 8);
						e.setVelX(8);
					} else if (getBoundsLeft().intersects(e.getBounds())) {
						e.setX(e.getX() - 8);
						e.setVelX(-8);
					}
					gameLogic.sounds.kickDeadKoopa.play();
				} else {
					if (e.getState() != PlayerStates.dead)
						hurting();
				}
			} else {
				e.entityDieAnimation();
			}

		}
	}

	/**
	 * Metoda odpowiadaj¹ca za wykrywanie kolizji z elementami wywo³uj¹ca
	 * odpowiednie podmetody.
	 */
	private void entityCollisionaDetect() {
		for (Entity e : gameLogic.handler.entity) {
			if (e.getState() != PlayerStates.dead) {
				if (e.getId() == Id.coin && getBounds().intersects(e.getBounds())
						&& e.getState() != PlayerStates.dead) {
					gameLogic.coins++;
					gameLogic.sounds.coinSound.play();
					e.die();
				} else if (e.getId() == Id.mushroom) {
					mushroomCollision(e);
				} else if (e.getId() == Id.superMario) {
					starmanCollision(e);
				} else if (e.getId() == Id.fireflow) {
					fireFlowCollision(e);
				} else if (e.getId() == Id.goomba) {
					goombaCollision(e);
				} else if (e.getId() == Id.bowser) {
					bowserCollision(e);
				} else if (e.getId() == Id.bowserFire
						|| (!isGoingDownPipe() && !isGoingUpPipe() && e.getId() == Id.piranhaPlant)) {
					bowserFireOrPiranhaPlandCollision(e);
				} else if (e.getId() == Id.koopaTroopa) {
					koopaCollision(e);
				} else if (e.getId() == Id.deadKoopa) {
					deadKoopaCollision(e);
				}
			}
		}
	}

	/**
	 * Metoda wyznaczaj¹ca kolejn¹ klatkê animacji Mario
	 */
	private void setAnimationFrame() {
		starmanFrameDelay++;

		if (starmanFrameDelay >= maxOfStarmanFrameDelay) {
			starmanFrame++;
			starmanFrame %= 4;
			starmanFrameDelay = 0;
		}

		if (move) {
			frameDelay++;
			if (frameDelay >= DELAY_VALUE) {
				if (facing == Facing.right)
					frame--;
				else
					frame++;

				if (frame >= FRAMES) {
					frame = 1;
				} else if (frame <= -FRAMES) {
					frame = -1;
				}
				frameDelay = 0;
			}
		} else {
			frame = 0;
		}
	}

	/**
	 * Metoda sprawdzaj¹ca czy Mario jest w stanie nieœmiertelnoœci lub Starman
	 * (co te¿ mu daje niesmiertelnoœæ).
	 */
	private void checkIsImmortalOrStarman() {
		if (getState() == PlayerStates.immortal && immortalTimeCounter != IMMORTAL_TIME) {
			immortalTimeCounter++;
		} else if (getState() == PlayerStates.immortal) {
			setState(PlayerStates.small);
			gameLogic.tempOfPlayerState = getState();
			immortalTimeCounter = 0;
		} else
			immortalTimeCounter = 0;

		if (getState() == PlayerStates.starman && starmanTimeCounter != STARMAN_TIME) {
			starmanTimeCounter++;
			if (starmanTimeCounter == 600) {
				maxOfStarmanFrameDelay = 6;
			}
		} else if (getState() == PlayerStates.starman) {
			setState(gameLogic.tempOfPlayerState);
			starmanTimeCounter = 0;
			maxOfStarmanFrameDelay = 2;
			if (gameLogic.level % 2 == 0 && isUnderground == 0)
				gameLogic.sounds.mainThemeSong.play();
			else
				gameLogic.sounds.underworldSong.play();
			gameLogic.sounds.starmanSound.stop();

		} else {
			starmanTimeCounter = 0;
		}
	}

	/**
	 * Metoda weryfikuj¹ca, czy Mario porysza siê w ruroci¹gach, jeœli tak
	 * przenosi go w odpowiednie miejsce za pomoc¹ (@link
	 * {@link #teleportByPipe()}.
	 */
	private void checkGoingByPipes() {
		if (isGoingDownPipe()) {
			pixelsTraveled += velY;
			if (pixelsTraveled > height) {
				teleportByPipe();
			} else {
				setVelX(0);
				setVelY(2);
			}
		} else if (isGoingUpPipe()) {
			pixelsTraveled += velX;
			if (pixelsTraveled > height) {
				teleportByPipe();
			} else {
				setVelX(2);
				setVelY(0);
			}
		} else {
			enviromentObjectCollisionDetect();
		}
	}

	/**
	 * Metoda formalnie odpowiadaj¹ca za teleportacjê Mario.
	 */
	private void teleportByPipe() {
		if (isGoingDownPipe()) {
			isUnderground = 1;
			for (EnviromentObject env : gameLogic.handler.enviromentObject) {
				if (env.getId() == Id.teleportPlace) {
					x = env.getX();
					y = env.getY();
				}
			}
			gameLogic.sounds.underworldSong.play();
		} else {
			for (EnviromentObject env : gameLogic.handler.enviromentObject) {
				if (env.getId() == Id.pipe && env.getTypeOfPipe() == TypeOfPipe.verticalOpenOutput
						&& Math.abs(x - env.getX()) < 30 * 64) {
					x = env.getX() + width / 2;
					y = env.getY() - height;
				}
			}
			isUnderground = 0;
			if (gameLogic.level % 2 == 0)
				gameLogic.sounds.mainThemeSong.play();
			else
				gameLogic.sounds.underworldSong.play();

			if (x > 186 * 64) {
				for (EnviromentObject env : gameLogic.handler.enviromentObject) {
					env.setIsUnderground(1);
				}
			}
		}

		setVelY(0);
		setGoingDownPipe(false);
		setGoingUpPipe(false);
		pixelsTraveled = 0;
		gravity = 0.0;
		jumping = false;
		falling = false;
		setGround(false);
	}

	/**
	 * Metoda odpowiadaj¹ca za skok Mario w górê.
	 */
	private void jumping() {
		if (jumping && !isGoingDownPipe() && !isGoingUpPipe()) {
			gravity -= 0.45;
			setVelY((int) -gravity);
			if (gravity <= 0.0) {
				jumping = false;
				falling = true;
				setGround(false);
			}
		}
	}

	/**
	 * Metoda odpowiadaj¹ca za opadanie Mario.
	 */
	private void falling() {
		if (falling && !isGoingDownPipe() && !isGoingUpPipe()) {
			gravity += 0.45;
			setVelY((int) gravity);
			if (gravity > 2.4)
				setGround(false);
		}
	}

}