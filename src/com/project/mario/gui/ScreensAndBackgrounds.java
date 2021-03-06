package com.project.mario.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import com.project.mario.GameLogic;

/**
 * Klasa generująca dodatkowe ekrany w rozgrywce oraz tła poszczególnych
 * poziomów
 *
 */
public class ScreensAndBackgrounds {
	private Graphics g;
	private int tempWidth, tempHeight;
	private Dimension sizeOfWindow;
	private GameLogic gameLogic;

	public ScreensAndBackgrounds(Graphics g, Dimension sizeOfWindow, GameLogic gameLogic) {
		this.setG(g);
		tempWidth = 0;
		tempHeight = 0;
		this.sizeOfWindow = sizeOfWindow;
		this.gameLogic = gameLogic;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, sizeOfWindow.width + 10, sizeOfWindow.height + 10);

	}

	/**
	 * Metoda renderująca ekran śmierci gracza
	 */
	public void deathScreen() {
		g.setColor(Color.WHITE);
		g.drawImage(gameLogic.graphics.player[4][0].getBufferedImage(), (sizeOfWindow.width / 2) - 150,
				(sizeOfWindow.height / 2) - 50, 100, 100, null);
		g.setFont(gameLogic.graphics.font);
		g.setFont(g.getFont().deriveFont(100.0f));
		g.drawString("x" + gameLogic.lives, (sizeOfWindow.width / 2) - 50, (sizeOfWindow.height / 2) + 50);
		writeStats();
	}

	/**
	 * Metoda renderująca ekran przegranej
	 */
	public void gameOverScreen() {
		endingScreen("GAME OVER");
	}

	/**
	 * Metoda renderująca ekran wygranej
	 */
	public void winScreen() {
		if (!gameLogic.sounds.worldClear.isRunning())
			gameLogic.sounds.worldClear.play();
		endingScreen("YOU WIN!");
	}

	/**
	 * Metoda renderująca informacje o osiągach gracza
	 */
	public void writeStats() {
		g.setColor(Color.WHITE);
		g.drawImage(gameLogic.graphics.coin[4].getBufferedImage(), 50, 20, 64, 64, null);

		g.setFont(gameLogic.graphics.font);
		g.setFont(g.getFont().deriveFont(64.0f));
		g.drawString("x" + gameLogic.coins, 120, 84);
	}

	/**
	 * Metoda renderująca tło poziomu
	 */
	public void drawLevelBackground() {
		try {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, sizeOfWindow.width + 10, sizeOfWindow.height + 10);
			int backgroundWidthForAllLevel = 0;
			tempWidth = gameLogic.graphics.levelBackgrounds[gameLogic.level].getWidth(null);
			tempHeight = gameLogic.graphics.levelBackgrounds[gameLogic.level].getHeight(null);
			while (backgroundWidthForAllLevel < gameLogic.graphics.levelImages[gameLogic.level].getWidth() * 64
					&& backgroundWidthForAllLevel < tempWidth * 5) {
				g.drawImage(gameLogic.graphics.levelBackgrounds[gameLogic.level], backgroundWidthForAllLevel, -10,
						tempWidth, tempHeight, null);
				backgroundWidthForAllLevel += tempWidth;
			}
		} catch (NullPointerException e) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, sizeOfWindow.width + 10, sizeOfWindow.height + 10);
		}
	}

	/**
	 * Metoda wyświetlająca zadany String na ekranie.
	 * 
	 * @param str
	 *            String do wyświetlenia.
	 */
	private void endingScreen(String str) {
		g.setColor(Color.WHITE);
		g.setFont(gameLogic.graphics.font);
		g.setFont(g.getFont().deriveFont(64.0f));
		int xForString = (sizeOfWindow.width / 2) - 200;
		g.drawString(str, xForString, (sizeOfWindow.height / 2) + 50);
		gameLogic.playing = false;
	}

	public Graphics getG() {
		return g;
	}

	public void setG(Graphics g) {
		this.g = g;
	}
}
