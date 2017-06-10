package com.project.mario.entity;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Id;
import com.project.mario.enums.PlayerStates;
import com.project.mario.enums.Facing;
/**
 * Klasa generuj¹ca rozpadaj¹cy siê blok œciany.
 *
 */
public class CrashedBlock extends Entity {

	public CrashedBlock(int x, int y, int width, int height, Id id, GameLogic gameLogic, Facing facing, double gravity) {
		super(x, y, width, height, id, gameLogic, facing);
		jumping = true;
		falling = false;
		if (facing == Facing.left) {
			velX = -2;
		} else {
			velX = 2;
		}
		this.gravity = gravity;
		if (y > 14 * 64 || gameLogic.level%2 == 1)
			isUnderground = 0;
		else
			isUnderground = 1;
		
		FRAMES = 2;
	}

	public void render(Graphics g) {
		g.drawImage(gameLogic.graphics.block[frame+10][isUnderground].getBufferedImage(), x, y, width, height, null);
	}

	public void update() {
		if (gameLogic.tempOfPlayerState != PlayerStates.dead) {
			x += velX;
			y += velY;
		}
		if (jumping && !isGoingDownPipe() && !isGoingUpPipe()) {
			gravity -= 0.45;
			setVelY((int) -gravity);
			if (gravity <= 0.0) {
				jumping = false;
				falling = true;
			}
		}

		if (falling && !isGoingDownPipe() && !isGoingUpPipe()) {
			gravity += 0.45;
			setVelY((int) gravity);
		}

		if (gravity > 40.0) {
			die();
		}
		
		setNextFrame(DELAY_VALUE, FRAMES);
	}

	public void entityDieAnimation() {
		;
	}

}
