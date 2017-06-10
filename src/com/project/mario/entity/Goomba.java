package com.project.mario.entity;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.entity.Entity;
import com.project.mario.enums.Id;
import com.project.mario.enums.PlayerStates;
import com.project.mario.enviroment.EnviromentObject;
/**
 * Klasa generuj¹ca i opisuj¹ca  przeciwnika Goomba.
 *
 */
public class Goomba extends Entity {
	/**
	 * @param deadTime zmienna okreœlaj¹ca czas wyœwietlania œmierci
	 */
	private int deadTime;

	public Goomba(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super(x, y, width, height, id, gameLogic);
		frame = 0;
		frameDelay = 0;
		deadTime = 30;
		setVelX(-2);
		setState(null);
		
		FRAMES = 2;
	}

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

	public void update() {
		if (gravity >= 30.0) {
			die();
		}

		if (gameLogic.tempOfPlayerState != PlayerStates.dead) {
			if ((int) gameLogic.getVisibleField().getMaxX() + 200 >= x) {
				if (id != Id.deadGoomba || getState() == PlayerStates.dead) {
					x += velX;
					y += velY;

					if (getState() != PlayerStates.dead) {
						for (EnviromentObject env: gameLogic.handler.enviromentObject) {
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

	public void entityDieAnimation() {
		gameLogic.sounds.hitEnemy.play();
		setState(PlayerStates.dead);
		id = Id.deadGoomba;
		jumping = true;
		falling = false;
		gravity = 10.0;
	}
}
