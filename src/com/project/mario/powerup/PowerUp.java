package com.project.mario.powerup;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.entity.Entity;
import com.project.mario.enums.Id;
import com.project.mario.enums.PlayerStates;
import com.project.mario.enviroment.EnviromentObject;

/**
 * Klasa generuj¹ca ulepszenia gracza.
 *
 */
public class PowerUp extends Entity {
	/**
	 * @param enviromentObject
	 *            przekazany element z którego siê wydostajedany upgrade
	 * @param isUndergroun
	 *            okreœla czy ulepszenie jest jeszcze schowane w bloku
	 * 
	 */
	private EnviromentObject enviromentObject;

	public PowerUp(int x, int y, int width, int height, Id id, GameLogic gameLogic, EnviromentObject enviromentObject) {
		super(x, y, width, height, id, gameLogic);

		isUnderground = 1;
		falling = false;
		this.enviromentObject = enviromentObject;
		gameLogic.sounds.powerUpAppear.play();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		if (enviromentObject.isJumpedBlock()) {
			if (id == Id.mushroom) {
				g.drawImage(gameLogic.graphics.mushroom.getBufferedImage(), x, y, width, height, null);
			} else {
				g.drawImage(gameLogic.graphics.fireFlow[frame].getBufferedImage(), x, y, width, height, null);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#update()
	 */
	public void update() {
		if (enviromentObject.isJumpedBlock()) {
			if (gameLogic.tempOfPlayerState != PlayerStates.dead) {
				x += velX;
				y += velY;
			}
			if (isUnderground == 1) {
				if (y > enviromentObject.getY() - height) {
					y--;
				} else {
					isUnderground = 0;
					if (id == Id.mushroom)
						velX = 2;
				}
			} else {
				for (EnviromentObject env : gameLogic.handler.enviromentObject) {
					if (env.getId() != Id.coin) {
						if (getBoundsBottom().intersects(env.getBounds())) {
							setVelY(0);
							y = env.getY() - height;
							if (falling)
								falling = false;
							if (!isGround())
								setGround(true);
						} else {
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
			}

			if (falling) {
				gravity += 0.4;
				setVelY((int) gravity);
				if (gravity > 2.4)
					setGround(false);
			}
			if (id == Id.fireflow) {
				frameDelay++;
				if (frameDelay >= 10) {
					frame++;
					frame = frame % 4;
					frameDelay = 0;
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
		;
	}
}
