package com.project.mario.gui;

import java.awt.Color;
import java.awt.Graphics;

import com.project.mario.GameLogic;
/**
 * Klasa generuj¹ca dodatkowe ekrany w rozgrywce oraz t³a poszczególnych poziomów
 *
 */
public class ScreensAndBackgrounds {
	private Graphics g;
	private int tempWidth, tempHeight;
	private int widthOfWindow, heightOfWindow;
	private GameLogic gameLogic;
	


	public ScreensAndBackgrounds(Graphics g, int widthOfWindow, int heightOfWindow, GameLogic gameLogic) {
		this.setG(g);
		tempWidth = 0;
		tempHeight = 0;
		this.widthOfWindow = widthOfWindow;
		this.heightOfWindow = heightOfWindow;
		this.gameLogic = gameLogic;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, widthOfWindow + 10, heightOfWindow + 10);
		
	}
	/**
	 * Metoda renderuj¹ca ekran œmierci gracza
	 */
	public void deathScreen() {
		g.setColor(Color.WHITE);
		g.drawImage(gameLogic.graphics.player[4][0].getBufferedImage(), (widthOfWindow / 2) - 150,
				(heightOfWindow / 2) - 50, 100, 100, null);
		g.setFont(gameLogic.graphics.font);
		g.setFont(g.getFont().deriveFont(100.0f));
		g.drawString("x" + gameLogic.lives, (widthOfWindow / 2) - 50, (heightOfWindow / 2) + 50);
		writeStats();
	}
	/**
	 * Metoda renderuj¹ca ekran przegranej
	 */
	public void gameOverScreen() {
		endingScreen("GAME OVER");
	}
	/**
	 * Metoda renderuj¹ca ekran wygranej
	 */
	public void winScreen() {
		if (!gameLogic.sounds.worldClear.isRunning())
			gameLogic.sounds.worldClear.play();
		endingScreen("YOU WIN!");
	}
	/**
	 * Metoda renderuj¹ca informacje o osi¹gach gracza
	 */
	public void writeStats() {
		g.setColor(Color.WHITE);
		g.drawImage(gameLogic.graphics.coin[4].getBufferedImage(), 50, 20, 64, 64, null);

		g.setFont(gameLogic.graphics.font);
		g.setFont(g.getFont().deriveFont(64.0f));
		g.drawString("x" + gameLogic.coins, 120, 84);
	}

	/**
	 * Metoda renderuj¹ca t³o poziomu
	 */
	public void drawLevelBackground() {
		try {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, widthOfWindow + 10, heightOfWindow + 10);
			int backgroundWidthForAllLevel = 0;
			tempWidth = gameLogic.graphics.levelBackgrounds[gameLogic.level].getWidth(null);
			tempHeight = gameLogic.graphics.levelBackgrounds[gameLogic.level].getHeight(null);
			while (backgroundWidthForAllLevel < gameLogic.graphics.levelImages[gameLogic.level].getWidth() * 64
					&& backgroundWidthForAllLevel < tempWidth * 5) {
				g.drawImage(gameLogic.graphics.levelBackgrounds[gameLogic.level], backgroundWidthForAllLevel, -10, tempWidth, tempHeight,
						null);
				backgroundWidthForAllLevel += tempWidth;
			}
		} catch (NullPointerException e) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, widthOfWindow + 10, heightOfWindow + 10);
		}
	}

	private void endingScreen(String str) {
		g.setColor(Color.WHITE);
		g.setFont(gameLogic.graphics.font);
		g.setFont(g.getFont().deriveFont(64.0f));
		int xForString = (widthOfWindow / 2) - 200;
		g.drawString(str, xForString, (heightOfWindow / 2) + 50);
		gameLogic.playing = false;
	}

	public Graphics getG() {
		return g;
	}

	public void setG(Graphics g) {
		this.g = g;
	}
}
