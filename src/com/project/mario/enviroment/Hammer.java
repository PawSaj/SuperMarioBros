package com.project.mario.enviroment;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Id;
/**
 * Klasa generuj¹ca element umo¿liwiaj¹cy zabicie boss'a - bowser'a
 *
 */
public class Hammer extends EnviromentObject {
	/**
	 * @param bridgeDestryDelay
	 *            zmienna okreœlaj¹ca opóienie pomiêdzy zniszczeniem kolejnych
	 *            elementów mostu Bowsera
	 */

	private int bridgeDestroyDelay;

	public Hammer(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super(x, y, width, height, id, gameLogic);
		bridgeDestroyDelay = 0;
	}

	public void render(Graphics g) {
		g.drawImage(gameLogic.graphics.hammer.getBufferedImage(), x, y, width, height, null);
	}

	public void update() {
		bridgeDestroyDelay++;
		bridgeDestroyDelay %= 10;
		if (activated && bridgeDestroyDelay == 0) {
			for (EnviromentObject env: gameLogic.handler.enviromentObject) {
				if (env.getId() == Id.bowserCastelBridge) {
					env.die();
				}
			}
			gameLogic.sounds.bowserFallSound.play();
			die();
		}
	}

}
