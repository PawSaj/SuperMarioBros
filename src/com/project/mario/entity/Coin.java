package com.project.mario.entity;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Id;

/**
 * Klasa generuj¹ca monety.
 * 
 * 
 */
public class Coin extends Entity {

	/**
	 * @param frameGoldDelay
	 *            Parametr s³u¿¹cy do wyd³u¿ania klatki animacji monety
	 *            obracaj¹cej siê z bloku
	 * @param fromBlock
	 *            Parametr okreœlaj¹cy czy moneta jest z bloku czy wolnostoj¹ca
	 */
	private int frameGoldDelay;
	public boolean fromBlock;
	private int coinFrames;
	private int coinFrameDelay;

	public Coin(int x, int y, int width, int height, Id id, GameLogic gameLogic, boolean fromBlock) {
		super(x, y, width, height, id, gameLogic);
		this.frameGoldDelay = 0;
		this.fromBlock = fromBlock;
		if (fromBlock) {
			coinFrames = 4;
			coinFrameDelay = 2;
		} else {
			coinFrames = 5;
			coinFrameDelay = 10;
		}
		velY = 0;
		jumping = true;
		falling = false;
		gravity = 18.0;

		if (fromBlock) {
			gameLogic.coins++;
			gameLogic.sounds.coinSound.play();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		if (!fromBlock) {
			g.drawImage(gameLogic.graphics.coin[frame].getBufferedImage(), x, y, width, height, null);
		} else {
			g.drawImage(gameLogic.graphics.coin[frame + 5].getBufferedImage(), x, y, width, height, null);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#update()
	 */
	public void update() {
		y += velY;

		if (!fromBlock) {
			if (frameGoldDelay == 0)
				frameDelay++;
			if (frame == 4) {
				frameGoldDelay++;
				frameGoldDelay = frameGoldDelay % 10;
			}
		} else {

			if (jumping) {
				gravity -= 0.6;
				setVelY((int) -gravity);
				if (gravity <= 0.0) {
					jumping = false;
					falling = true;
				}
			}

			if (falling) {
				gravity += 0.6;
				setVelY((int) gravity);
				if (gravity > 14.0)
					die();
			}
		}

		frameDelay++;
		if (frameDelay >= coinFrameDelay) {
			frameGoldDelay = 0;
			frame++;
			frame = frame % coinFrames;
			frameDelay = 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#entityDieAnimation()
	 */
	public void entityDieAnimation() {
		;
	}

}
