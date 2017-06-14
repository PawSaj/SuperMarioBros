package com.project.mario;

import com.project.mario.entity.Entity;
import com.project.mario.enums.PlayerStates;

/**
 * Klasa odpowiadaj¹ca za pod¹¿aniem obrazu za graczem, wyznacza po³o¿enie
 * kamery
 * 
 * 
 * 
 *
 */
public class Camera {

	private int x, y;
	private GameLogic gameLogic;

	public Camera(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
		this.x = 0;
		this.y = 10;
	}

	/**
	 * Metoda wyznaczaj¹ca wartoœci zmiennych kamery dla kolejnej klatki gry
	 * 
	 * @param player
	 *            obiekt bêd¹cy graczem
	 */
	public void update(Entity player) {
		if (gameLogic.tempOfPlayerState != PlayerStates.dead) {
			int tempOfPlayerX = -player.getX() + gameLogic.getSizeOfWindow().width / 2;
			int tempOfPlayerY = -player.getY() + gameLogic.getSizeOfWindow().height * 2 / 3;
			if (x > tempOfPlayerX) {
				x = tempOfPlayerX;
			}
			if (tempOfPlayerY < -4 * 64 && player.getIsUnderground() == 1)
				y = -17 * 64 + 10;
			else
				y = 10;
		}

	}

	/**
	 * Metoda resetuj¹ca pozycjê kamery
	 */
	public void resetCamera() {
		x = 0;
		y = 10;
	}

	/**
	 * Metoda pozwalaj¹ca pobraæ wartoœæ zmiennej X
	 * 
	 * @return Wspó³rzêdna po³o¿enia x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Metoda pozwalaj¹ca pobraæ wartoœæ zmiennej Y
	 * 
	 * @return Wspó³rzêdna po³o¿enia y
	 */
	public int getY() {
		return y;
	}

}
