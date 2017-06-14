package com.project.mario.enviroment;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Id;

/**
 * Klasa generuj¹ca Flagê, która s³u¿y do zakoñczenia poziomu.
 *
 */
public class Flag extends EnviromentObject {

	public Flag(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super(x, y, width, height, id, gameLogic);

		activated = false;
		velY = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.project.mario.enviroment.EnviromentObject#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		g.drawImage(gameLogic.graphics.flag[0].getBufferedImage(), x, y, width, height / 10, null);
		for (int i = 1; i < 9; i++) {
			g.drawImage(gameLogic.graphics.flag[1].getBufferedImage(), x, y + i * 64, width, height / 10, null);
		}
		g.drawImage(gameLogic.graphics.flag[2].getBufferedImage(), x, y + 9 * 64, width, height / 10, null);

		if (!activated) {
			g.drawImage(gameLogic.graphics.flag[3].getBufferedImage(), x - 12, y + 8, width, height / 10, null);
		} else if (velY != 0) {
			if (velY < y + height - 128)
				g.drawImage(gameLogic.graphics.flag[3].getBufferedImage(), x - 12, velY, width, height / 10, null);
			else {
				g.drawImage(gameLogic.graphics.flag[3].getBufferedImage(), x - 12, y + height - 128, width, height / 10,
						null);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.enviroment.EnviromentObject#update()
	 */
	public void update() {
		;
	}

}
