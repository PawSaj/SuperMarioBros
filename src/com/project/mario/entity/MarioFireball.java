package com.project.mario.entity;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;
import com.project.mario.enums.PlayerStates;
import com.project.mario.enviroment.EnviromentObject;
/**
 * Klasa generuj¹ca pociski tworzone przez Fiery Mario.
 *
 */
public class MarioFireball extends Entity {

	public MarioFireball(int x, int y, int width, int height, Id id, GameLogic gameLogic, Facing facing) {
		super(x, y, width, height, id, gameLogic);
		this.facing = facing;
		if (facing == Facing.left)
			velX = -8;
		else
			velX = 8;
		
		DELAY_VALUE = 2;
		FRAMES = 4;
	}

	/* (non-Javadoc)
	 * @see com.project.mario.entity.Entity#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		g.drawImage(gameLogic.graphics.marioFireball[frame].getBufferedImage(), x, y, width, height, null);
	}

	/* (non-Javadoc)
	 * @see com.project.mario.entity.Entity#update()
	 */
	public void update() {
		if (gameLogic.tempOfPlayerState != PlayerStates.dead) {
			x += velX;
			y += velY;
		}

		for (EnviromentObject env: gameLogic.handler.enviromentObject) {
			if (env.getId() != Id.coin) {
				if (getBoundsTopFireball().intersects(env.getBounds())) {
					y = env.getY() + env.getHeight() + height;
					falling = true;
					jumping = false;
				}
				if (getBoundsBottomFireball().intersects(env.getBounds())) {
					y = env.getY() - height - height;
					falling = false;
					jumping = true;
					gravity = 3.0;
				}
				if (getBoundsLeftFireball().intersects(env.getBounds())) {
					entityDieAnimation();
				}
				if (getBoundsRightFireball().intersects(env.getBounds())) {
					entityDieAnimation();
				}
			}
		}

		for (Entity e : gameLogic.handler.entity) {
			if (e.getId() == Id.goomba || e.getId() == Id.koopaTroopa || e.getId() == Id.piranhaPlant) {
				if (getBounds().intersects(e.getBounds())) {
					e.entityDieAnimation();
					entityDieAnimation();
				}
			}
		}

		if (jumping) {
			gravity -= 0.5;
			setVelY(-8);
			if (gravity <= 0.0) {
				jumping = false;
				falling = true;
			}
		}

		if (falling) {
			setVelY(8);
		}
		
		setNextFrame(DELAY_VALUE, FRAMES);
		
	}

	/* (non-Javadoc)
	 * @see com.project.mario.entity.Entity#entityDieAnimation()
	 */
	public void entityDieAnimation() {
		die();
	}
}
