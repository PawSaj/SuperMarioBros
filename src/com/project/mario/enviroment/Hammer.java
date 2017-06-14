package com.project.mario.enviroment;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Id;

/**
 * Klasa generuj�ca element umo�liwiaj�cy zabicie boss'a - bowser'a
 *
 */
public class Hammer extends EnviromentObject {
	/**
	 * @param bridgeDestryDelay
	 *            zmienna okre�laj�ca op�ienie pomi�dzy zniszczeniem kolejnych
	 *            element�w mostu Bowsera
	 */

	private int bridgeDestroyDelay;

	public Hammer(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super(x, y, width, height, id, gameLogic);
		bridgeDestroyDelay = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.project.mario.enviroment.EnviromentObject#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		g.drawImage(gameLogic.graphics.hammer.getBufferedImage(), x, y, width, height, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.enviroment.EnviromentObject#update()
	 */
	public void update() {
		bridgeDestroyDelay++;
		bridgeDestroyDelay %= 10;
		if (activated && bridgeDestroyDelay == 0) {
			for (EnviromentObject env : gameLogic.handler.enviromentObject) {
				if (env.getId() == Id.bowserCastelBridge) {
					env.destroy();
				}
			}
			gameLogic.sounds.bowserFallSound.play();
			destroy();
		}
	}

}
