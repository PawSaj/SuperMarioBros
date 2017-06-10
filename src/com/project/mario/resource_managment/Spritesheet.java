package com.project.mario.resource_managment;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Klasa wycinaj�ca odpowiedni fragment siatki element�w.
 *
 */
public class Spritesheet {

	private BufferedImage sheet;

	public Spritesheet(String path) {
		try {
			sheet = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Problem z siatk� grafiki");
			e.printStackTrace();
			
		}
	}

	/**
	 * 
	 * @param x
	 *            Wsp�rz�dna po�o�enia elementu na siatce
	 * @param y
	 *            Wsp�rz�dna po�o�enia elementu na siatce
	 * @return Fragment siatki znajduj�cy si� we wskazanym miejscu o wskazanych
	 *         parametrach wysoko�ci i szeroko�ci
	 */
	public BufferedImage getSprite1(int x, int y) {
		return sheet.getSubimage(x * 64 - 64, y * 64 - 64, 64, 64);
	}
	/**
	 * 
	 * @param x
	 *            Wsp�rz�dna po�o�enia elementu na siatce
	 * @param y
	 *            Wsp�rz�dna po�o�enia elementu na siatce
	 * @return Fragment siatki znajduj�cy si� we wskazanym miejscu o wskazanych
	 *         parametrach wysoko�ci i szeroko�ci
	 */
	public BufferedImage getSprite2(int x, int y) {
		return sheet.getSubimage(x * 64 - 64, y * 64 - 64, 64, 128);
	}
	/**
	 * 
	 * @param x
	 *            Wsp�rz�dna po�o�enia elementu na siatce
	 * @param y
	 *            Wsp�rz�dna po�o�enia elementu na siatce
	 * @return Fragment siatki znajduj�cy si� we wskazanym miejscu o wskazanych
	 *         parametrach wysoko�ci i szeroko�ci
	 */
	public BufferedImage getSprite3(int x, int y) {
		return sheet.getSubimage(x * 64 - 64, y * 64 - 64, 128, 64);
	}
	/**
	 * 
	 * @param x
	 *            Wsp�rz�dna po�o�enia elementu na siatce
	 * @param y
	 *            Wsp�rz�dna po�o�enia elementu na siatce
	 * @return Fragment siatki znajduj�cy si� we wskazanym miejscu o wskazanych
	 *         parametrach wysoko�ci i szeroko�ci
	 */
	public BufferedImage getSprite4(int x, int y) {
		return sheet.getSubimage(x * 64 - 64, y * 64 - 64, 128, 128);
	}
	/**
	 * 
	 * @param x
	 *            Wsp�rz�dna po�o�enia elementu na siatce
	 * @param y
	 *            Wsp�rz�dna po�o�enia elementu na siatce
	 * @return Fragment siatki znajduj�cy si� we wskazanym miejscu o wskazanych
	 *         parametrach wysoko�ci i szeroko�ci
	 */
	public BufferedImage getSprite5(int x, int y) {
		return sheet.getSubimage(x * 64 - 64, y * 64 - 16, 48, 16);
	}
	/**
	 * 
	 * @param x
	 *            Wsp�rz�dna po�o�enia elementu na siatce
	 * @param y
	 *            Wsp�rz�dna po�o�enia elementu na siatce
	 * @return Fragment siatki znajduj�cy si� we wskazanym miejscu o wskazanych
	 *         parametrach wysoko�ci i szeroko�ci
	 */
	public BufferedImage getSprite6(int x, int y) {
		return sheet.getSubimage(x * 64 - 64, y * 64 - 32, 64, 96);
	}
}
