package com.project.mario;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.project.mario.entity.Entity;
import com.project.mario.enums.Id;
import com.project.mario.gui.Launcher;
import com.project.mario.gui.ScreensAndBackgrounds;
import com.project.mario.input.KeyInput;
import com.project.mario.input.MouseInput;

/**
 * Klasa g³ówna gry. Wywo³uje wsystkie podstawowe elementy, dodaje listenery,
 * uruchamia w¹tek g³ówny gry
 * 
 * @author Pawe³ Sajnóg
 *
 */
public class Main extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;

	private final int WIDTH = 256;
	private final int HEIGHT = 240;
	private final int SCALE = 4;
	private final String TITLE = "Mario";
	private Dimension size;

	private boolean isRunning;
	private final int fps;

	private GameLogic gameLogic;

	private ScreensAndBackgrounds screensAndBackgrounds;
	private Launcher launcher;

	private boolean isPlaying;

	private KeyInput keyInput;
	private MouseInput mouseInput;

	public Main() {
		this.size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);

		this.fps = 60;
		this.isRunning = true;
		this.gameLogic = new GameLogic(size);
		this.isPlaying = false;

		this.launcher = new Launcher(size, gameLogic);

		keyInput = new KeyInput(gameLogic, launcher.getButtons());
		mouseInput = new MouseInput(gameLogic, launcher.getButtons());
		addKeyListener(keyInput);
		addMouseListener(mouseInput);
		addMouseMotionListener(mouseInput);

	}

	public static void main(String[] args) {
		(new Thread(new Main())).start();
	}

	/**
	 * Metoda inicjalizuj¹ca okno, ustalaj¹c jego podstawowe wartoœci
	 */
	public void initialize() {
		setTitle(TITLE);
		setSize(size);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	/**
	 * Metoda odpowiedzialna za wybranie tego, co ma byæ aktualizowane w
	 * kolejnym kroku. Najpierw sprawdzane jest czy gracz gracz, gra, jeœli nie
	 * wyœwietlane s¹ odpowienie ekrany. Jesli tak wywo³ywana jest aktualizacja
	 * z klasy {@link Handler} dla wszystkich elementów. Dodatkowo ustalana jest
	 * muzyka, która ma byæ grana w nastêpuj¹cym poziomie, resetowana pozycja
	 * kamery, jeœli ma byæ wygenerowany nowy poziom.
	 */
	public void update() {
		keyInput.update();

		if (gameLogic.showDeathScreen && (gameLogic.playing || gameLogic.gameOverScreen || gameLogic.win)
				&& !gameLogic.sounds.marioDieSound.isRunning()) {
			if (gameLogic.coins != gameLogic.tempOfCoins)
				gameLogic.coins = gameLogic.tempOfCoins;
			gameLogic.deathScreenShowTime++;
		}

		if (gameLogic.playing) {
			gameLogic.handler.update();

			for (Entity e : gameLogic.handler.entity) {
				if (e.getId() == Id.player) {
					if (!(e.isGoingDownPipe() || e.isGoingUpPipe()))
						gameLogic.cam.update(e);
				}
			}
		} else {
			gameLogic.sounds.mainThemeSong.stop();
		}

		if (gameLogic.deathScreenShowTime >= 180 && gameLogic.lives >= 0) {
			gameLogic.showDeathScreen = false;
			if (gameLogic.level % 2 == 0)
				gameLogic.sounds.mainThemeSong.play();
			else
				gameLogic.sounds.underworldSong.play();
			if (gameLogic.lives == 0 && gameLogic.gameOverScreen)
				gameLogic.gameOverScreen = false;
			gameLogic.deathScreenShowTime = 0;
			gameLogic.handler.clearLevel();
			if (!gameLogic.win) {
				try {
					gameLogic.cam.resetCamera();
					gameLogic.handler.createLevel(gameLogic.graphics.levelImages[gameLogic.level]);
				} catch (ArrayIndexOutOfBoundsException e) {
					gameLogic.win = true;
					gameLogic.showDeathScreen = true;
					System.out.println("zakonczenie gry");
				}
			}
			gameLogic.win = false;
		}

	}

	/**
	 * Metoda wywo³j¹ca rendering w³¹sciwych elementów gry. Jest decydowane czy
	 * ma zostaæ wyœwietlony launcher, cze poziom czy mo¿e ekran pomiêdzy
	 * poziomamy (deathscreen, win, gameOver). Dla wybranej decyzji jest
	 * wywo³ane renderowanie w³aœciwych elementów gry
	 */
	public void render() {
		// TODO
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		screensAndBackgrounds = new ScreensAndBackgrounds(g, size, gameLogic);

		if (gameLogic.win) {
			screensAndBackgrounds.winScreen();
		} else {
			if (gameLogic.playing || (gameLogic.gameOverScreen)) {
				if (!gameLogic.showDeathScreen) {
					g.translate(gameLogic.cam.getX(), gameLogic.cam.getY());
					screensAndBackgrounds.drawLevelBackground();
					gameLogic.handler.render(g);
					g.translate(-gameLogic.cam.getX(), -gameLogic.cam.getY());
					screensAndBackgrounds.writeStats();
				} else if (!gameLogic.sounds.marioDieSound.isRunning()) {
					if (!gameLogic.gameOverScreen) {
						screensAndBackgrounds.deathScreen();
					} else {
						screensAndBackgrounds.gameOverScreen();
					}
				}
			} else {
				launcher.render(g);
				if (gameLogic.lives == 0)
					gameLogic.lives = 3;
				if (gameLogic.coins != 0)
					gameLogic.coins = 0;
				if (!gameLogic.showDeathScreen)
					gameLogic.showDeathScreen = true;
			}
		}

		g.dispose();
		bs.show();
	}

	public void stop() {
		setVisible(false);
		System.exit(0);
	}

	/*
	 * Metoda uruchamiana wraz z utworzeniem w¹tku gry. Inicjuje wszystkie
	 * elementy oraz ogranicza liczbê generowanych klatek do zdefiniowanej przez
	 * u¿ytkownika
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		initialize();

		while (isRunning) {
			long time = System.currentTimeMillis();

			update();
			render();

			// delay for each frame - time it took for one frame
			time = (1000 / fps) - (System.currentTimeMillis() - time);

			if (time > 0) {
				try {
					Thread.sleep(time);
				} catch (Exception e) {

				}
			}
		}

		stop();
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public int getSCALE() {
		return SCALE;
	}

}
