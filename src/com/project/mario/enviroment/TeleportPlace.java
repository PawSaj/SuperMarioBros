package com.project.mario.enviroment;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Id;

/**
 * Klasa generuj�ca obiekt b�d�cy docelowym miejscem teleportacj po wej�ciu
 * Mario do ruroci�gu
 *
 */
public class TeleportPlace extends EnviromentObject {

	public TeleportPlace(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super(x, y, width, height, id, gameLogic);
	}

	public void render(Graphics g) {
		;
	}

	public void update() {
		;
	}

}
