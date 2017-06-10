package com.project.mario.enviroment;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Id;
/**
 * Klasa generuj�ca zamek, po osi�gni�ciu kt�rego gracz uka�cza poziom
 *
 */
public class Castel extends EnviromentObject {

	public Castel(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super(x, y, width, height, id, gameLogic);
	}

	public void render(Graphics g) {
		g.drawImage(gameLogic.graphics.castel.getBufferedImage(), x, y, width, height, null);
	}

	public void update() {
		
	}

}
