package com.project.mario;

import com.project.mario.entity.Entity;
import com.project.mario.enums.PlayerStates;

/**
 * Klasa odpowiadaj¹ca za pod¹¿anie za graczem
 * 
 *
 */
public class Camera {

	private int x, y;
	private int widthOfWindow, heightOfWindow;
	private GameLogic gameLogic;

	public Camera(int widthOfWindow, int heightOfWindow, GameLogic gameLogic) {
		this.widthOfWindow = widthOfWindow;
		this.heightOfWindow = heightOfWindow;
		this.gameLogic = gameLogic;
		this.x = 0;
		this.y = 10;
	}

	/**
	 * Metoda wyznaczaj¹ca ustalaj¹ca wartoœci zmiennych dla kolejnej klatki gry
	 * 
	 * @param player
	 *            obiekt bêd¹cy graczem
	 */
	public void update(Entity player) {
		if (gameLogic.tempOfPlayerState != PlayerStates.dead) {
			int tempOfPlayerX = -player.getX() + widthOfWindow / 2;
			int tempOfPlayerY = -player.getY() + heightOfWindow * 2 / 3;
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
