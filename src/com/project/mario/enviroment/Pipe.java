package com.project.mario.enviroment;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Id;
import com.project.mario.enums.TypeOfPipe;

/**
 * Klasa generuj¹ca ró¿ne typy ruroci¹gów
 * 
 * 
 */
public class Pipe extends EnviromentObject {

	/**
	 * @param typeOfPipe
	 *            Okreœla typ ruroci¹gu, dla danego typu s¹ przypisane
	 *            odpowiednie mijsca w tablicy grafiki ruroci¹gów.
	 *            --------Vertical--------- closed output - pipeVerical[0]; open
	 *            output - pipeVerical[0]; open entrance - pipeVerical[0]; close
	 *            entrance - pipeVerical[0]; piece of pipe - pipeVerical[1]; top
	 *            of connector - pipeVerical[2]; bottom of connector -
	 *            pipeVerical[3]; -------Horizontal-------- entrance -
	 *            pipeHorizontal[0]; piece of pipe - pipeHorizontal[1];
	 */
	public Pipe(int x, int y, int width, int height, Id id, GameLogic gameLogic, TypeOfPipe typeOfPipe) {
		super(x, y, width, height, id, gameLogic);
		this.typeOfPipe = typeOfPipe;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.project.mario.enviroment.EnviromentObject#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		if (typeOfPipe == TypeOfPipe.verticalClosedOutput || typeOfPipe == TypeOfPipe.verticalOpenOutput)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.enviroment.EnviromentObject#update()
	 */
	public void update() {
		;
	}

}
