package com.project.mario.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.project.mario.GameLogic;

/**
 * Klasa generuj¹ca okno startowe gry
 * 
 */
public class Launcher {

	public Button[] buttons;
	private Image background;
	private final int fontHeight;
	private int widthOfWindow, heightOfWindow;

	public Launcher(int widthOfWindow, int heightOfWindow, GameLogic gameLogic) {
		this.widthOfWindow = widthOfWindow;
		this.heightOfWindow = heightOfWindow;
		fontHeight = 50;
		buttons = new Button[2];
		buttons[0] = new Button(widthOfWindow * 2 / 6, heightOfWindow * 3 / 5, 300, fontHeight,
				"START GAME", true, gameLogic);
		buttons[1] = new Button(widthOfWindow * 2 / 6, heightOfWindow * 3 / 5 + 50, 250, fontHeight,
				"EXIT GAME", false, gameLogic);

		try {
			background = ImageIO.read(getClass().getResource("/launcher.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("background");
		}
	}

	/**
	 * Metoda renderuj¹ca ekran startowy
	 * 
	 * @param g
	 *            zmienna graficzna
	 */
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawImage(background, 0, 0, widthOfWindow + 10, heightOfWindow + 10, null);

		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i] != null)
				buttons[i].render(g);
		}
	}
}
