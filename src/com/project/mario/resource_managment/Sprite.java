package com.project.mario.resource_managment;

import java.awt.image.BufferedImage;

/**
 * Klasa wywo³uj¹ca pobieranie odpowiednich fragmentów siatki elementów
 *
 */

public class Sprite {
	public Spritesheet sheet;

	public BufferedImage image;

	public Sprite(Spritesheet sheet, int x, int y, int img) {
		if (img == 1)
			image = sheet.getSprite1(x, y); // 64x64
		else if (img == 2)
			image = sheet.getSprite2(x, y); // 64x128
		else if (img == 3)
			image = sheet.getSprite3(x, y); // 128x64
		else if (img == 4)
			image = sheet.getSprite4(x, y); // 128x128
		else if (img == 5)
			image = sheet.getSprite5(x, y); // 16x48
		else if (img == 6)
			image = sheet.getSprite6(x, y); // 64x96

	}

	/**
	 * Metoda zwracaj¹ca grafikê typu BufferedImage
	 * 
	 * @return Pobrany fragment grafiki typu BufferedImage
	 */

	public BufferedImage getBufferedImage() {
		return image;
	}
}
