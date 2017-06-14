package com.project.mario;

import com.project.mario.entity.Entity;
import com.project.mario.enums.PlayerStates;

/**
 * Klasa odpowiadaj�ca za pod��aniem obrazu za graczem, wyznacza po�o�enie
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
	 * Metoda wyznaczaj�ca warto�ci zmiennych kamery dla kolejnej klatki gry
	 * 
	 * @param player
	 *            obiekt b�d�cy graczem
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
	 * Metoda resetuj�ca pozycj� kamery
	 */
	public void resetCamera() {
		x = 0;
		y = 10;
	}

	/**
	 * Metoda pozwalaj�ca pobra� warto�� zmiennej X
	 * 
	 * @return Wsp�rz�dna po�o�enia x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Metoda pozwalaj�ca pobra� warto�� zmiennej Y
	 * 
	 * @return Wsp�rz�dna po�o�enia y
	 */
	public int getY() {
		return y;
	}

}
