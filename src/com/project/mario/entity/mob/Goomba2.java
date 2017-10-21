package com.project.mario.entity.mob;

import java.awt.Graphics;
import java.util.Random;

import com.project.mario.GameLogic;
import com.project.mario.entity.BowserFire;
import com.project.mario.entity.Entity;
import com.project.mario.entity.MarioFireball;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;
import com.project.mario.enums.PlayerStates;
import com.project.mario.enviroment.EnviromentObject;

/**
 * Klasa generuj¹ca i opisuj¹ca przeciwnika Goomba2. Wykonana w ramach zadania
 * podczas oddawania projektu. Jest to kopia goomba z dodaniem strzelania.
 * Goomba2 strzela kule MarioFireball, wywo³ane z klasy Entity.
 *
 */
public class Goomba2 extends Entity {
	/**
	 * @param BLASTING_PREPARING_TIME
	 *            czas, po którym Bowser mo¿e ponownie zion¹æ p³omieniem
	 * @param deadTime
	 *            zmienna okreœlaj¹ca czas wyœwietlania œmierci
	 */
	private final int BLASTING_PREPARING_TIME;
	private int timeToFireCounter;
	private int blastingPreparingCounter;
	private boolean blasting;

	
	private int deadTime;

	public Goomba2(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super(x, y, width, height, id, gameLogic);
		frame = 0;
		frameDelay = 0;
		deadTime = 30;
		setVelX(-2);
		setState(null);
		facing = Facing.left;

		FRAMES = 2;

		BLASTING_PREPARING_TIME = 60;

		timeToFireCounter = 0;
		blastingPreparingCounter = 0;
		blasting = false;
		random = new Random();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		if (id == Id.goomba) {
			g.drawImage(gameLogic.graphics.goomba[frame + 1].getBufferedImage(), x, y, width, height, null);
		} else {
			if (getState() != PlayerStates.dead)
				g.drawImage(gameLogic.graphics.goomba[0].getBufferedImage(), x, y, width, height, null);
			else {
				drawRotatedImage(g, gameLogic.graphics.goomba[frame + 1].getBufferedImage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#update()
	 */
	public void update() {
		if (gravity >= 30.0) {
			die();
		}
		generateRandomBlasting();

		if (gameLogic.tempOfPlayerState != PlayerStates.dead) {
			if ((int) gameLogic.getVisibleField().getMaxX() + 200 >= x) {
				if (id != Id.deadGoomba || getState() == PlayerStates.dead) {
					x += velX;
					y += velY;

					if (getState() != PlayerStates.dead) {
						for (EnviromentObject env : gameLogic.handler.enviromentObject) {
							if (env.getId() != Id.coin) {

								if (getBoundsBottom().intersects(env.getBounds())) {
									if (!env.isJumpedBlock()) {
										entityDieAnimation();
									} else {
										setVelY(0);
										y = env.getY() - height;
										if (falling)
											falling = false;
									}
								} else if (getState() != PlayerStates.dead) {
									if (!falling) {
										gravity = 0.0;
										falling = true;
									}
								}
								if (getBoundsLeft().intersects(env.getBounds())) {
									setVelX(2);
								}
								if (getBoundsRight().intersects(env.getBounds())) {
									setVelX(-2);
								}
							}

						}

						setNextFrame(DELAY_VALUE, FRAMES);
					}

					if (jumping) {
						gravity -= 0.4;
						setVelY((int) -gravity);
						if (gravity <= 0.0) {
							jumping = false;
							falling = true;
						}
					}

					if (falling) {
						gravity += 0.4;
						setVelY((int) gravity);
					}
				} else {
					if (deadTime <= 0) {
						die();
					}
					deadTime--;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#entityDieAnimation()
	 */
	public void entityDieAnimation() {
		gameLogic.sounds.hitEnemy.play();
		setState(PlayerStates.dead);
		id = Id.deadGoomba;
		jumping = true;
		falling = false;
		gravity = 10.0;
	}

	/**
	 * Metoda tworzaca pociski wystrzelane przez Goomba2. Jest to wywo³anie metody torz¹cej kule Mario.
	 */
	private void fire() {
		createMarioFireball();
	}

	/**
	 * Metoda wyznaczaj¹ca losowy czas wystrzeliwanych pocisków.
	 */
	private void generateRandomBlasting() {
		if (timeToFireCounter <= 0) {
			blasting = true;
			timeToFireCounter = random.nextInt(60) + 60;
		}

		if (blasting) {
			if (blastingPreparingCounter <= 0) {
				fire();
				blasting = false;
				blastingPreparingCounter = BLASTING_PREPARING_TIME;
			} else {
				blastingPreparingCounter--;
			}
		}
		timeToFireCounter--;
	}
}
