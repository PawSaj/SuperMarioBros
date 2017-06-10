package com.project.mario.resource_loaders;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.project.mario.gfx.Sprite;
import com.project.mario.gfx.Spritesheet;

public class GraphicsLoader {
	public Spritesheet sheet;
	
	public BufferedImage[] levelImages;
	public Image[] levelBackgrounds;
	
	public Sprite[][] block;
	public Sprite[] pipeVerical;
	public Sprite[] pipeHorizontal;
	public Sprite[] flag;
	public Sprite castel;
	public Sprite hammer;
	public Sprite princes;

	public Sprite mushroom;
	public Sprite[] coin;
	public Sprite[] starman;
	public Sprite[] fireFlow;

	public Sprite[][] player;
	public Sprite[] goomba;
	public Sprite[] bowser;
	public Sprite[] bowserFire;
	public Sprite[] koopaTroopa;
	public Sprite deadKoopa;
	public Sprite[] piranhaPlant;
	public Sprite[] marioFireball;
	
	public Font font;
	
	public Sprite selectingMushroom;
	
	public GraphicsLoader() {
		sheet = new Spritesheet("./res/spritesheet.png");
		
		initLevelImages();
		initEntityElements();
		initEnviromentElements();
		initPowerUpElements();
		initFont();
		
		initLauncherElements();
		
		
		
	}
	
	private void initLauncherElements() {
		selectingMushroom = new Sprite(sheet, 10, 8, 1);
		
	}

	private void initFont() {
		try {
			File file = new File("./res/fonts/marioFont.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, file);
		} catch (Exception e) {
			System.out.println("Nie odnaleziono czcionki, ustawiono standardową");
			font = new Font("Arial", Font.BOLD, 20);
		}
	}

	private int getNumberFromFileName(String path) {
		path = path.replaceAll("\\D+", "");

		return Integer.parseInt(path);
	}
	
	private File[] graphicFilesArray(final String nameOfLookingFiles) {
		File file = new File("./res/");
		final String extension;
		if (nameOfLookingFiles.contains("background"))
			extension = "jpg";
		else
			extension = "png";
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.contains(nameOfLookingFiles) && name.contains(extension))
					return true;
				return false;
			}
		};

		File[] files = file.listFiles(filter);
		return files;
	}

	private void initLevelImages() {
		File[] levelFiles = graphicFilesArray("level");

		levelImages = new BufferedImage[levelFiles.length];
		int temp;
		for (int i = 0; i < levelFiles.length; i++) {
			temp = getNumberFromFileName(levelFiles[i].getPath());
			if (temp < levelFiles.length + 1) {
				try {
					levelImages[temp - 1] = ImageIO.read(new File("./res/" + levelFiles[i].getName()));
				} catch (IOException e) {
					System.out.println("odczytywanie level'u");
					e.printStackTrace();
				}
			}
		}

	}

	private void initEntityElements() {
		player = new Sprite[17][2];
		goomba = new Sprite[3];
		bowser = new Sprite[6];
		bowserFire = new Sprite[4];
		koopaTroopa = new Sprite[4];
		deadKoopa = new Sprite(sheet, 13, 3, 1);
		piranhaPlant = new Sprite[2];
		marioFireball = new Sprite[4];

		for (int i = 0; i < player.length; i++) {
			for (int j = 0; j < player[0].length; j++) {
				player[i][j] = new Sprite(sheet, i + 1, j + 15, 1);
			}
		}

		for (int i = 0; i < goomba.length; i++) {
			goomba[i] = new Sprite(sheet, i + 14, 1, 1);
		}

		for (int i = 0; i < bowser.length; i++) {
			bowser[i] = new Sprite(sheet, i * 2 + 1, 9, 4);
		}

		for (int i = 0; i < bowserFire.length; i++) {
			bowserFire[i] = new Sprite(sheet, i + 1, 11, 5);
		}

		for (int i = 0; i < koopaTroopa.length; i++) {
			koopaTroopa[i] = new Sprite(sheet, i + 5, 3, 6);
		}

		for (int i = 0; i < piranhaPlant.length; i++) {
			piranhaPlant[i] = new Sprite(sheet, i + 1, 12, 6);
		}

		for (int i = 0; i < marioFireball.length; i++) {
			marioFireball[i] = new Sprite(sheet, i + 11, 8, 1);
		}
	}

	private void initEnviromentElements() {
		block = new Sprite[13][2];
		pipeVerical = new Sprite[5];
		pipeHorizontal = new Sprite[2];
		flag = new Sprite[4];
		coin = new Sprite[9];
		castel = new Sprite(sheet, 13, 9, 4);
		hammer = new Sprite(sheet, 11, 5, 1);
		princes = new Sprite(sheet, 14, 3, 6);

		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < player[0].length; j++) {
				block[i][j] = new Sprite(sheet, i + 1, j + 1, 1);
			}
		}

		for (int i = 0; i < pipeVerical.length; i++) {
			pipeVerical[i] = new Sprite(sheet, 1, i + 3, 3);
		}

		for (int i = 0; i < pipeHorizontal.length; i++) {
			pipeHorizontal[i] = new Sprite(sheet, i + 3, 3, 2);
		}

		for (int i = 0; i < flag.length; i++) {
			flag[i] = new Sprite(sheet, 5, i + 11, 1);
		}

		for (int i = 0; i < coin.length; i++) {
			coin[i] = new Sprite(sheet, i + 1, 8, 1);
		}
	}

	private void initPowerUpElements() {
		mushroom = new Sprite(sheet, 3, 5, 1);
		starman = new Sprite[3];
		fireFlow = new Sprite[4];

		for (int i = 0; i < starman.length; i++) {
			starman[i] = new Sprite(sheet, i + 8, 5, 1);
		}

		for (int i = 0; i < fireFlow.length; i++) {
			fireFlow[i] = new Sprite(sheet, i + 4, 5, 1);
		}
	}
	
	
}
