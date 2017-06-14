package com.project.mario.enviroment;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.entity.Coin;
import com.project.mario.enums.Id;
import com.project.mario.enums.PlayerStates;
import com.project.mario.powerup.PowerUp;
import com.project.mario.powerup.Starman;

/**
 * klasa odpowiadaj¹ca za generowanie bloków zawieraj¹cych bonusy lub monety.
 * Generuje zarówno ukryte (wygl¹daj¹ce jak blok œciany) oraz widoczne bloki z
 * bonusem.
 ** 
 * @author Pawe³ Sajnóg
 *
 */
public class BonusBlock extends EnviromentObject {

	/**
	 * @param gettedPlayerState
	 *            zmienna pobieraj¹ca statu gracza w momencie, gdy uderza on o
	 *            ten element, by wygenerowaæ odpowiednie ulepszenie
	 * @param hiddenCoinsTime
	 *            Okreœla jak d³ugo bêdzie mo¿na "wybijac" ukryte money z bloku
	 */
	private int frame;
	private int frameDelay;
	private int hiddenCoinsTime;
	private boolean startCountingHiddenCoinsTime;
	private PlayerStates gettedPlayerState;

	private boolean poppedUp;

	private int spriteY;

	public BonusBlock(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super(x, y, width, height, id, gameLogic);
		this.frame = 0;
		this.frameDelay = 0;

		this.poppedUp = false;
		this.spriteY = y;
		velY = 0;

		hiddenCoinsTime = 240;
		startCountingHiddenCoinsTime = false;
		gettedPlayerState = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.project.mario.enviroment.EnviromentObject#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		if (!activated) {
			if (id == Id.powerUpBlock || id == Id.coinBlock)
				g.drawImage(gameLogic.graphics.block[frame][1].getBufferedImage(), x, y, width, height, null);
			else if (id == Id.starmanBlock || id == Id.hiddenCoinsBlock)
				g.drawImage(gameLogic.graphics.block[9][isUnderground].getBufferedImage(), x, y, width, height, null);
		} else
			g.drawImage(gameLogic.graphics.block[6][isUnderground].getBufferedImage(), x, y, width, height, null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.enviroment.EnviromentObject#update()
	 */
	public void update() {

		if (startCountingHiddenCoinsTime && hiddenCoinsTime > 0) {
			hiddenCoinsTime--;
		} else {
			startCountingHiddenCoinsTime = false;
		}
		if (activated && !poppedUp || !jumpedBlock) {
			jumpedBlock = blockHitAnimation();
		}

		if (activated && !poppedUp) {
			if (gettedPlayerState == null) {
				gettedPlayerState = gameLogic.tempOfPlayerState;
			}

			if (id == Id.powerUpBlock) {
				if (gettedPlayerState == PlayerStates.small || gettedPlayerState == PlayerStates.immortal
						|| (gameLogic.tempOfPlayerState == PlayerStates.small
								&& gettedPlayerState == PlayerStates.starman)) {
					gameLogic.handler.addEntity(new PowerUp(x, y - 10, width, height, Id.mushroom, gameLogic, this));
				} else {
					gameLogic.handler.addEntity(new PowerUp(x, y - 10, width, height, Id.fireflow, gameLogic, this));
				}
			} else if (id == Id.starmanBlock) {
				gameLogic.handler.addEntity(new Starman(x, spriteY, width, height, Id.superMario, gameLogic, this));
			}

			if (id == Id.coinBlock) {
				gameLogic.handler.addEntity(new Coin(x + 8, y, 48, 48, Id.coinFromBlock, gameLogic, true));
			} else if (id == Id.hiddenCoinsBlock) {
				gameLogic.handler.addEntity(new Coin(x + 8, y, 48, 48, Id.coinFromBlock, gameLogic, true));
				if (!startCountingHiddenCoinsTime) {
					startCountingHiddenCoinsTime = true;
				}
				activated = false;
			}
			if (id != Id.hiddenCoinsBlock || hiddenCoinsTime <= 0) {
				poppedUp = true;
				activated = true;
			}
		}

		frameDelay++;
		if (frameDelay >= 10) {
			frame++;
			frame = frame % 6;
			frameDelay = 0;
		}
	}
}
