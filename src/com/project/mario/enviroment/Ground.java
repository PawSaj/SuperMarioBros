package com.project.mario.enviroment;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Id;

/**
 * Klasa generuj¹ca ró¿ne typy pod³o¿a w grze.
 *
 */
public class Ground extends EnviromentObject {

	public Ground(int x, int y, int width, int height, boolean solid, Id id, GameLogic gameLogic) {
		super(x, y, width, height, id, gameLogic);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.project.mario.enviroment.EnviromentObject#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		if (id == Id.ground)
			g.drawImage(gameLogic.graphics.block[7][isUnderground].getBufferedImage(), x, y, width, height, null);
		else if (id == Id.groundTreeLeft)
			g.drawImage(gameLogic.graphics.block[2][0].getBufferedImage(), x, y, width, height, null);
		else if (id == Id.groundTreeMiddle)
			g.drawImage(gameLogic.graphics.block[3][0].getBufferedImage(), x, y, width, height, null);
		else if (id == Id.groundTreeRight)
			g.drawImage(gameLogic.graphics.block[4][0].getBufferedImage(), x, y, width, height, null);
		else if (id == Id.bowserCastelGround)
			g.drawImage(gameLogic.graphics.block[12][1].getBufferedImage(), x, y, width, height, null);
		else if (id == Id.bowserCastelBridge)
			g.drawImage(gameLogic.graphics.block[12][0].getBufferedImage(), x, y, width, height, null);
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
