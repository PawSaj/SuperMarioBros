package com.project.mario.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.project.mario.GameLogic;

/**
 * Klasa generuj¹ca okno startowe gry
 * 
 */
public class Launcher {

	private Button[] buttons;
	private Image background;
	private final int fontHeight;
	private Dimension sizeOfWindow;

	public Launcher(Dimension sizeOfWindow, GameLogic gameLogic) {
		this.sizeOfWindow = sizeOfWindow;
		fontHeight = 50;
		setButtons(new Button[2]);
		getButtons()[0] = new Button(sizeOfWindow.width * 2 / 6, sizeOfWindow.height * 3 / 5, 300, fontHeight,
				"START GAME", true, gameLogic);
		getButtons()[1] = new Button(sizeOfWindow.width * 2 / 6, sizeOfWindow.height * 3 / 5 + 50, 250, fontHeight,
				"EXIT GAME", false, gameLogic);

		try {
			background = ImageIO.read(new File("./res/launcher.png"));
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
		g.drawImage(background, 0, 0, sizeOfWindow.width + 10, sizeOfWindow.height + 10, null);

		for (int i = 0; i < getButtons().length; i++) {
			if (getButtons()[i] != null)
				getButtons()[i].render(g);
		}
	}

	public Button[] getButtons() {
		return buttons;
	}

	public void setButtons(Button[] buttons) {
		this.buttons = buttons;
	}
}
