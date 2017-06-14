package com.project.mario.enviroment;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Id;

/**
 * Klasa generuj¹ca zamek, po osi¹gniêciu którego gracz ukañcza poziom.
 *
 */
public class Castel extends EnviromentObject {

	public Castel(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super(x, y, width, height, id, gameLogic);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.project.mario.enviroment.EnviromentObject#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		g.drawImage(gameLogic.graphics.castel.getBufferedImage(), x, y, width, height, null);
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
