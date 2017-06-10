package com.project.mario.powerup;

import java.awt.Graphics;
import java.util.Random;

import com.project.mario.GameLogic;
import com.project.mario.entity.Entity;
import com.project.mario.enums.Id;
import com.project.mario.enums.PlayerStates;
import com.project.mario.enviroment.EnviromentObject;
/**
 * Klasa generuj¹ca element daj¹cy graczowi niezniszczalnoœæ.
 *
 */
public class Starman extends Entity {

	/**
	 * @param jumpingDelay
	 *            zmianna o wartoœci losowej wyzwalaj¹ca skok w obiekcie
	 * @param enviromentObject
	 *            to samo co dla atrybutu klasy PowerUp
	 */
	private int jumpingDelay;
	private EnviromentObject enviromentObject;

	public Starman(int x, int y, int width, int height, Id id, GameLogic gameLogic, EnviromentObject enviromentObject) {
		super(x, y, width, height, id, gameLogic);

		random = new Random();
		jumpingDelay = random.nextInt(240);
		setGround(false);
		jumping = false;

		isUnderground = 1;
		falling = false;

		this.enviromentObject = enviromentObject;

		gameLogic.sounds.powerUpAppear.play();
	}

	private void generateRandomJumping() {
		if (jumpingDelay <= 0 && !jumping && isGround()) {
			jumping = true;
			setGround(false);
			gravity = 5.0;
			jumpingDelay = random.nextInt(240);
		}
		jumpingDelay--;
	}

	public void render(Graphics g) {
		if (enviromentObject.isJumpedBlock())
			g.drawImage(gameLogic.graphics.starman[frame].getBufferedImage(), x, y, width, height, null);
	}

	public void update() {
		if (enviromentObject.isJumpedBlock()) {
			if (gameLogic.tempOfPlayerState != PlayerStates.dead) {
				x += velX;
				y += velY;
			}

			generateRandomJumping();

			if (isUnderground == 1) {
				if (y > enviromentObject.getY() - height) {
					y--;
				} else {
					isUnderground = 0;
					velX = 2;
				}
			} else {
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
						if (getBoundsTop().intersects(env.getBounds())) {
							y = env.getY() + height;
							jumping = false;
							falling = true;
							gravity = 0.0;
						}
						if (getBoundsLeft().intersects(env.getBounds())) {
							setVelX(3);
						}
						if (getBoundsRight().intersects(env.getBounds())) {
							setVelX(-3);
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

			frameDelay++;
			if (frameDelay >= 10) {
				frame++;
				frame = frame % 3;
				frameDelay = 0;
			}
		}
	}

	public void entityDieAnimation() {
		;
	}
}
