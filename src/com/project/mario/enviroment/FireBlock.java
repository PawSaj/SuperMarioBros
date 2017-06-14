package com.project.mario.enviroment;

import java.awt.Graphics;
import java.util.Random;

import com.project.mario.GameLogic;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;

/**
 * Tworzy blok, który generuje dodatkowo elementy krêc¹ce siê wokó³ œrodka bloku
 *
 */
public class FireBlock extends EnviromentObject {

	private int radius;
	private Random random;
	private Facing facing;

	public FireBlock(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super(x, y, width, height, id, gameLogic);
		random = new Random();
		boolean temp = random.nextBoolean();
		if (temp) {
			facing = Facing.left;
		} else {
			facing = Facing.right;
		}
		activated = true;

		radius = 192;
		for (int i = 0; i < 6; i++) {
			int positionX = (int) ((radius * i / 6) + x + 32);
			int positionY = (int) ((radius * i / 6) + y + 32);
			gameLogic.handler.addEnviromentObject(new Fireball(positionX, positionY, 32, 32, Id.fireball, gameLogic,
					facing, (radius * i / 6), x + 16, y + 16));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.project.mario.enviroment.EnviromentObject#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		g.drawImage(gameLogic.graphics.block[6][1].getBufferedImage(), x, y, width, height, null);
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
