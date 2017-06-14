package com.project.mario.enviroment;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Id;
/**
 * klasa generuj¹ca ksiê¿niczkê - cel gry
 *
 */
public class Princes extends EnviromentObject{

	public Princes(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super(x, y, width, height, id, gameLogic);
		activated = false;
	}

	/* (non-Javadoc)
	 * @see com.project.mario.enviroment.EnviromentObject#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		g.drawImage(gameLogic.graphics.princes.getBufferedImage(), x, y, width, height, null);
	}

	/* (non-Javadoc)
	 * @see com.project.mario.enviroment.EnviromentObject#update()
	 */
	public void update() {
		if(activated) {
			gameLogic.switchLevel();
		}
	}
}
