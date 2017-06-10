package com.project.mario.entity;

import java.awt.Graphics;
import java.util.Random;

import com.project.mario.GameLogic;
import com.project.mario.entity.Entity;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;
import com.project.mario.enums.PlayerStates;
import com.project.mario.enviroment.EnviromentObject;
/**
 * Klasa generuj¹ca i opisuj¹ca  przeciwnika Bowser.
 *
 */
public class Bowser extends Entity {
	/**
	 * @param BLASTING_PREPARING_TIME
	 *            czas, po którym Bowser mo¿e ponownie zion¹æ p³omieniem
	 * @param MAX_X_CHANGE
	 * @param MIN_X_CHANGE
	 *            Zakres, po ktorym mo¿e poruszaæ siê Bowser
	 * @param bowserShift
	 *            zmienna o losowej wartoœci s³u¿¹ca do wydu¿enia w czasie
	 *            wygenerowanego ruchu
	 *
	 */
	private final int BLASTING_PREPARING_TIME;
	private final int MAX_X_CHANGE;
	private final int MIN_X_CHANGE;
	private int bowserShift;
	private int timeToFireCounter;
	private int blastingPreparingCounter;
	private boolean blasting;
	private int jumpingDelay;

	public Bowser(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super(x, y, width, height, id, gameLogic);

		random = new Random();
		BLASTING_PREPARING_TIME = 60;
		MAX_X_CHANGE = x + 64 * 2;
		MIN_X_CHANGE = x - 64 * 8;
		bowserShift = random.nextInt(240);
		timeToFireCounter = 0;
		blastingPreparingCounter = 0;
		blasting = false;
		facing = Facing.left;
		jumpingDelay = random.nextInt(240);
		falling = true;
		setGround(false);
		jumping = false;
		
		FRAMES = 2;
	}

	public void render(Graphics g) {
		if (facing == Facing.left) {
			if (blasting) {
				g.drawImage(gameLogic.graphics.bowser[2].getBufferedImage(), x, y, width, height, null);
			} else {
				g.drawImage(gameLogic.graphics.bowser[frame].getBufferedImage(), x, y, width, height, null);
			}
		} else {
			if (blasting) {
				g.drawImage(gameLogic.graphics.bowser[3].getBufferedImage(), x, y, width, height, null);
			} else {
				g.drawImage(gameLogic.graphics.bowser[frame + 4].getBufferedImage(), x, y, width, height, null);
			}
		}
	}

	public void update() {
		if (gravity >= 40.0) {
			die();
		}
		generateRandomBowserShift();
		generateRandomJumping();
		generateRandomBlasting();

		if (gameLogic.tempOfPlayerState != PlayerStates.dead) {
			if (x > MIN_X_CHANGE && x < MAX_X_CHANGE)
				x += velX;
			y += velY;
		}

		for (EnviromentObject env: gameLogic.handler.enviromentObject) {
			if (env.getId() != Id.coin) {

				if (getBoundsBottom().intersects(env.getBounds())) {
					setVelY(0);
					y = env.getY() - height;
					if (falling)
						falling = false;
					if (!isGround())
						setGround(true);
				} else {
					if (!falling && !jumping) {
						gravity = 0.0;
						falling = true;
					}
				}
			}
		}

		if (jumping) {
			gravity -= 0.1;
			setVelY((int) -gravity);
			if (gravity <= 0.0) {
				jumping = false;
				falling = true;
				setGround(false);
			}
		}

		if (falling) {
			gravity += 0.1;
			setVelY((int) gravity);
			if (gravity > 2.4)
				setGround(false);
		}

		setNextFrame(DELAY_VALUE, FRAMES);
		
	}

	public void entityDieAnimation() {
		;
	}
	
	private void fire() {
		if (facing == Facing.left)
			gameLogic.handler.addEntity(new BowserFire(x - 64, y + 44, 128, 32, Id.bowserFire, gameLogic, facing));
		else
			gameLogic.handler.addEntity(new BowserFire(x + 64, y + 44, 128, 32, Id.bowserFire, gameLogic, facing));
		gameLogic.sounds.bowserFireSound.play();
	}

	private void generateRandomBowserShift() {
		if (x <= MIN_X_CHANGE + 100) {
			velX = 1;
			bowserShift = random.nextInt(240);
		} else if (x >= MAX_X_CHANGE - 100) {
			velX = -1;
			bowserShift = random.nextInt(240);
		}
		if (bowserShift <= 0 && random.nextBoolean()) {
			velX = -1;
			bowserShift = random.nextInt(240);
		} else if (bowserShift <= 0) {
			velX = +1;
			bowserShift = random.nextInt(240);
		}
		bowserShift--;
	}

	private void generateRandomJumping() {
		if (jumpingDelay <= 0 && !jumping && isGround()) {
			jumping = true;
			setGround(false);
			if ((gravity = random.nextDouble() * 6.0) < 2.0)
				jumping = false;
			jumpingDelay = random.nextInt(240);
		}
		jumpingDelay--;
	}

	private void generateRandomBlasting() {
		if (timeToFireCounter <= 0) {
			blasting = true;
			timeToFireCounter = random.nextInt(480) + 240;
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
