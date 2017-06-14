package com.project.mario.entity.mob;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.entity.Entity;
import com.project.mario.enums.Id;
import com.project.mario.enums.PlayerStates;

/**
 * Klasa generuj¹ca i opisuj¹ca przeciwnika Piranha Plant.
 * 
 * 
 */
public class PiranhaPlant extends Entity {

	/**
	 * @param WAIT_TIME
	 *            Czas jaki Piranha Plant ma czekaæ w ukryciu.
	 * @param startingY
	 *            Pocz¹tkowe po³o¿enie elementu
	 */
	private final int WAIT_TIME;
	private int countWaitTime;
	private int startingY;

	public PiranhaPlant(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super(x, y, width, height, id, gameLogic);

		WAIT_TIME = 240;
		countWaitTime = WAIT_TIME;
		setGoingUpPipe(false);
		isUnderground = 1;
		startingY = y;

		FRAMES = 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		g.drawImage(gameLogic.graphics.piranhaPlant[frame].getBufferedImage(), x, y, width, height, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#update()
	 */
	public void update() {
		if (gameLogic.tempOfPlayerState != PlayerStates.dead) {
			y += velY;
		}

		if (velY == 0)
			countWaitTime--;

		if (isUnderground == 1 && countWaitTime == 0) {
			setGoingUpPipe(true);
			isUnderground = 0;
			countWaitTime = WAIT_TIME / 2;
			velY = -1;
		} else if (countWaitTime == 0) {
			velY = 1;
			countWaitTime = WAIT_TIME;
		}

		if (velY == -1 && y <= startingY - height) {
			velY = 0;
		}

		if (velY == 1 && y >= startingY) {
			setGoingUpPipe(false);
			isUnderground = 1;
			velY = 0;
		}

		setNextFrame(DELAY_VALUE, FRAMES);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#entityDieAnimation()
	 */
	public void entityDieAnimation() {
		gameLogic.sounds.hitEnemy.play();
		die();
	}
}
