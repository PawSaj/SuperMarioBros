package com.project.mario.enviroment;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Id;
/**
 * klasa generuj�ca ksi�niczk� - cel gry
 *
 */
public class Princes extends EnviromentObject{

	public Princes(int x, int y, int width, int height, Id id, GameLogic gameLogic) {
		super(x, y, width, height, id, gameLogic);
		activated = false;
	}

	public void render(Graphics g) {
		g.drawImage(gameLogic.graphics.princes.getBufferedImage(), x, y, width, height, null);
	}

	public void update() {
		if(activated) {
			gameLogic.switchLevel();
		}
	}
}