package com.project.mario.gui;

import java.awt.Color;
import java.awt.Graphics;
import com.project.mario.GameLogic;

/**
 * Moja w³asna klasa opisuj¹ca Button, zamiast dostarczonej w pakiecie, by
 * wiedzieæ dok³adnie co mogê zrobiæ z moim guzikeim
 * 
 *
 */
public class Button {
	/**
	 * @param selected
	 *            Okreœla czy dany przycisk zosta³ wybrany
	 */
	private int x, y;
	private int width, height;
	private String label;
	private boolean selected;
	private GameLogic gameLogic;

	public Button(int x, int y, int widht, int height, String label, boolean selected, GameLogic gameLogic) {
		this.x = x;
		this.y = y;
		this.width = widht;
		this.height = height;
		this.label = label;
		this.setSelected(selected);
		this.gameLogic = gameLogic;
	}

	/**
	 * Metoda renderuj¹ca dany przycisk
	 * 
	 * @param g
	 *            zmienna graficzna
	 */
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(gameLogic.graphics.font);
		g.setFont(g.getFont().deriveFont((float) height - 20));
		g.drawString(getLabel(), getX(), getY() + height);
		if (isSelected()) {
			g.drawImage(gameLogic.graphics.selectingMushroom.getBufferedImage(), x - 64, y + 16, height - 16, height - 16, null);
		}
	}

	/**
	 * Metoda obs³uguj¹ca zdarzenie aktywacji przycisku
	 */
	public void triggerEvent() {
		if (getLabel().toLowerCase().contains("start")) {
			gameLogic.playing = true;
		} else if (getLabel().toLowerCase().contains("exit")) {
			System.exit(0);

			gameLogic.sounds.mainThemeSong.close();
			gameLogic.sounds.jumpSmallSound.close();
			gameLogic.sounds.coinSound.close();
			gameLogic.sounds.marioDieSound.close();
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidht() {
		return width;
	}

	public void setWidht(int widht) {
		this.width = widht;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
