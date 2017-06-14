package com.project.mario.entity.mob;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.entity.Entity;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;
import com.project.mario.enums.PlayerStates;
import com.project.mario.enviroment.EnviromentObject;

/**
 * Klasa generuj¹ca i opisuj¹ca przeciwnika Koopa Troopa.
 *
 * 
 */
public class KoopaTroopa extends Entity {

	/**
	 * @param RECOVERING_TIME
	 *            czas po którym Koopa o¿yje ze skorupy
	 */
	private final int RECOVERING_TIME;
	private int toRecoverCounter;

	public KoopaTroopa(int x, int y, int width, int height, Id id, GameLogic gameLogic, Facing facing) {
		super(x, y, width, height, id, gameLogic);
		this.facing = facing;
		RECOVERING_TIME = 300;
		toRecoverCounter = RECOVERING_TIME;
		if (facing == Facing.left)
			velX = -2;
		else
			velX = 2;
		setState(null);

		FRAMES = 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		if (id == Id.koopaTroopa) {
			if (facing == Facing.right) {
				g.drawImage(gameLogic.graphics.koopaTroopa[frame].getBufferedImage(), x, y, width, height, null);
			} else {
				g.drawImage(gameLogic.graphics.koopaTroopa[frame + 2].getBufferedImage(), x, y, width, height, null);
			}
		} else {
			if (getState() != PlayerStates.dead) {
				g.drawImage(gameLogic.graphics.deadKoopa.getBufferedImage(), x, y, width, height, null);
			} else {
				drawRotatedImage(g, gameLogic.graphics.deadKoopa.getBufferedImage());
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

		if (gameLogic.tempOfPlayerState != PlayerStates.dead
				&& (int) gameLogic.getVisibleField().getMaxX() + 200 >= x) {
			x += velX;
			y += velY;

			if (toRecoverCounter <= 0) {
				y -= 36;
				height = 96;
				id = Id.koopaTroopa;
				toRecoverCounter = RECOVERING_TIME;
				if (facing == Facing.left)
					setVelX(2);
				else
					setVelX(-2);
			}

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
								if (!isGround())
									setGround(true);
							}
						} else if (getState() != PlayerStates.dead) {
							if (!falling) {
								gravity = 0.0;
								falling = true;
							}
						}
						if (getBoundsLeftKoopa().intersects(env.getBounds())) {
							x = env.getX() + env.getWidth();
							if (id == Id.koopaTroopa || getState() == PlayerStates.dead)
								setVelX(2);
							else
								setVelX(8);
							facing = Facing.right;
						}
						if (getBoundsRightKoopa().intersects(env.getBounds())) {
							x = env.getX() - width;
							if (id == Id.koopaTroopa || getState() == PlayerStates.dead)
								setVelX(-2);
							else
								setVelX(-8);
							facing = Facing.left;
						}
					}
				}
			}

			if (jumping) {
				gravity -= 0.4;
				setVelY((int) -gravity);
				if (gravity <= 0.0) {
					jumping = false;
					falling = true;
					setGround(false);
				}
			}

			if (falling) {
				gravity += 0.4;
				setVelY((int) gravity);
			}

			setNextFrame(DELAY_VALUE, FRAMES);

			if (id == Id.deadKoopa) {
				collisionWithEnemy();
				if (velX == 0)
					toRecoverCounter--;
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
		id = Id.deadKoopa;
		jumping = true;
		falling = false;
		gravity = 10.0;
	}

	/**
	 * Metoda odpowiadaj¹ca za detekcjê kolicji, gdy Koopa jest w skorupie. W
	 * tym stanie zabija wszystkie jednostki zderzaj¹ce siê z nim.
	 */
	private void collisionWithEnemy() {
		for (Entity e : gameLogic.handler.entity) {
			if (!e.equals(this) && (e.getId() == Id.goomba || e.getId() == Id.koopaTroopa))
				if (getBounds().intersects(e.getBounds()))
					e.entityDieAnimation();
		}
	}

}
