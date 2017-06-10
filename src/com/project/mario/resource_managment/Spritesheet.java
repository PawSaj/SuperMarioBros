package com.project.mario.resource_managment;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Klasa wycinaj¹ca odpowiedni fragment siatki elementów.
 *
 */
public class Spritesheet {

	private BufferedImage sheet;

	public Spritesheet(String path) {
		try {
			sheet = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Problem z siatk¹ grafiki");
			e.printStackTrace();
			
		}
	}

	/**
	 * 
	 * @param x
	 *            Wspó³rzêdna po³o¿enia elementu na siatce
	 * @param y
	 *            Wspó³rzêdna po³o¿enia elementu na siatce
	 * @return Fragment siatki znajduj¹cy siê we wskazanym miejscu o wskazanych
	 *         parametrach wysokoœci i szerokoœci
	 */
	public BufferedImage getSprite1(int x, int y) {
		return sheet.getSubimage(x * 64 - 64, y * 64 - 64, 64, 64);
	}
	/**
	 * 
	 * @param x
	 *            Wspó³rzêdna po³o¿enia elementu na siatce
	 * @param y
	 *            Wspó³rzêdna po³o¿enia elementu na siatce
	 * @return Fragment siatki znajduj¹cy siê we wskazanym miejscu o wskazanych
	 *         parametrach wysokoœci i szerokoœci
	 */
	public BufferedImage getSprite2(int x, int y) {
		return sheet.getSubimage(x * 64 - 64, y * 64 - 64, 64, 128);
	}
	/**
	 * 
	 * @param x
	 *            Wspó³rzêdna po³o¿enia elementu na siatce
	 * @param y
	 *            Wspó³rzêdna po³o¿enia elementu na siatce
	 * @return Fragment siatki znajduj¹cy siê we wskazanym miejscu o wskazanych
	 *         parametrach wysokoœci i szerokoœci
	 */
	public BufferedImage getSprite3(int x, int y) {
		return sheet.getSubimage(x * 64 - 64, y * 64 - 64, 128, 64);
	}
	/**
	 * 
	 * @param x
	 *            Wspó³rzêdna po³o¿enia elementu na siatce
	 * @param y
	 *            Wspó³rzêdna po³o¿enia elementu na siatce
	 * @return Fragment siatki znajduj¹cy siê we wskazanym miejscu o wskazanych
	 *         parametrach wysokoœci i szerokoœci
	 */
	public BufferedImage getSprite4(int x, int y) {
		return sheet.getSubimage(x * 64 - 64, y * 64 - 64, 128, 128);
	}
	/**
	 * 
	 * @param x
	 *            Wspó³rzêdna po³o¿enia elementu na siatce
	 * @param y
	 *            Wspó³rzêdna po³o¿enia elementu na siatce
	 * @return Fragment siatki znajduj¹cy siê we wskazanym miejscu o wskazanych
	 *         parametrach wysokoœci i szerokoœci
	 */
	public BufferedImage getSprite5(int x, int y) {
		return sheet.getSubimage(x * 64 - 64, y * 64 - 16, 48, 16);
	}
	/**
	 * 
	 * @param x
	 *            Wspó³rzêdna po³o¿enia elementu na siatce
	 * @param y
	 *            Wspó³rzêdna po³o¿enia elementu na siatce
	 * @return Fragment siatki znajduj¹cy siê we wskazanym miejscu o wskazanych
	 *         parametrach wysokoœci i szerokoœci
	 */
	public BufferedImage getSprite6(int x, int y) {
		return sheet.getSubimage(x * 64 - 64, y * 64 - 32, 64, 96);
	}
}
