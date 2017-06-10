package com.project.mario.enviroment;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Id;

/**
 * Klasa generuj¹ca obiekt bêd¹cy docelowym miejscem teleportacj po wejœciu
 * Mario do ruroci¹gu
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
