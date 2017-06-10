package com.project.mario.enviroment;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Id;
/**
 * Klasa generuj¹ca mury. Kiedy jest niszczona tworzy elementy CrashedBlock
 *
 */
public class Wall extends EnviromentObject {

	private int timeToDestroy;

	public Wall(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super(x, y, width, height, id, gameLogic);
		destroyed = false;
		timeToDestroy = 30;
	}

	public void render(Graphics g) {
		if (id == Id.wall) {
			if (!destroyed) {
				g.drawImage(gameLogic.graphics.block[9][isUnderground].getBufferedImage(), x, y, width, height, null);
			}
		} else if (id == Id.hardWall) {
			g.drawImage(gameLogic.graphics.block[isUnderground][0].getBufferedImage(), x, y, width, height, null);
		} else if (id == Id.trunk) {
			g.drawImage(gameLogic.graphics.block[5][0].getBufferedImage(), x, y, width, height, null);
		}
	}

	public void update() {
		if ((activated || !jumpedBlock) && !destroyed)
			jumpedBlock = blockHitAnimation();

		if (jumpedBlock)
			activated = false;

		if (destroyed) {
			y -= 8;
			timeToDestroy--;
		}

		if (timeToDestroy == 0)
			die();
	}

}
