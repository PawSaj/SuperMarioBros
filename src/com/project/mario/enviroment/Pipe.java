package com.project.mario.enviroment;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Id;
import com.project.mario.enums.TypeOfPipe;
/**
 * Klasa generuj¹ca ró¿ne typy ruroci¹gów
 *
 */
public class Pipe extends EnviromentObject {

	public Pipe(int x, int y, int width, int height, Id id, GameLogic gameLogic, TypeOfPipe typeOfPipe) {
		super(x, y, width, height, id, gameLogic);
		this.typeOfPipe = typeOfPipe;
		/**
		 * @param facing 
		 * 		   --------Vertical---------
		 *		   -2 - closed output 			 	-> pipeVerical[4]
		 * 	       -1 - open output					-> pipeVerical[4]
		 *			0 - open entrance				-> pipeVerical[0]
		 *			1 - close entrance				-> pipeVerical[0]
		 *			2 - piece of pipe				-> pipeVerical[1]
		 *			3 - top of connector			-> pipeVerical[2]
		 *			4 - bottom of connector			-> pipeVerical[3]
		 *		   -------Horizontal--------
		 *			5 - entrance					-> pipeHorizontal[0]
		 *			6 - piece of pipe				-> pipeHorizontal[1]
		 */
	}

	public void render(Graphics g) {
		if(typeOfPipe == TypeOfPipe.verticalClosedOutput || typeOfPipe == TypeOfPipe.verticalOpenOutput)
			g.drawImage(gameLogic.graphics.pipeVerical[0].getBufferedImage(), x, y, width, height, null);
		else if (typeOfPipe == TypeOfPipe.verticalClosedEntrance || typeOfPipe == TypeOfPipe.verticalOpenEntrance) 
			g.drawImage(gameLogic.graphics.pipeVerical[0].getBufferedImage(), x, y, width, height, null);
		else if (typeOfPipe == TypeOfPipe.verticalPieceOfPipe) 
			g.drawImage(gameLogic.graphics.pipeVerical[1].getBufferedImage(), x, y, width, height, null);
		else if (typeOfPipe == TypeOfPipe.verticalTopOfConnector) 
			g.drawImage(gameLogic.graphics.pipeVerical[2].getBufferedImage(), x, y, width, height, null);
		else if (typeOfPipe == TypeOfPipe.verticalBottomOfConnector) 
			g.drawImage(gameLogic.graphics.pipeVerical[3].getBufferedImage(), x, y, width, height, null);
		else if (typeOfPipe == TypeOfPipe.horizontalEntrance) 
			g.drawImage(gameLogic.graphics.pipeHorizontal[0].getBufferedImage(), x, y, width, height, null);
		else if (typeOfPipe == TypeOfPipe.horizontalPieceOfPipe) 
			g.drawImage(gameLogic.graphics.pipeHorizontal[1].getBufferedImage(), x, y, width, height, null);
	}

	public void update() {
		;
	}

}
