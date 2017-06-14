package com.project.mario;

import java.awt.Dimension;
import java.awt.Rectangle;

import com.project.mario.entity.Entity;
import com.project.mario.enums.Id;
import com.project.mario.enums.PlayerStates;
import com.project.mario.resource_managment.GraphicsLoader;
import com.project.mario.resource_managment.SoundsLoader;

/**
 * Klasa odpowiadaj¹ca za warstwê logiczn¹ gry. Poza parametrami logicznymi jak
 * nr poziomu, iloœæ ¿yæ czy zebranych monet klasa posiada te¿ parametry bêd¹ce
 * zbiorczymi obiektami wszytskich elementów graficznych i diêkowych
 * 
 * @author Pawe³ Sajnóg
 *
 */
public class GameLogic {
	private Dimension sizeOfWindow;

	public PlayerStates tempOfPlayerState;

	public int level;
	public int lives;
	public int coins;
	public int tempOfCoins;
	public boolean win;

	public boolean showDeathScreen;
	public int deathScreenShowTime;
	public boolean gameOverScreen;
	public boolean playing;

	public Handler handler;
	public Camera cam;

	public SoundsLoader sounds;
	public GraphicsLoader graphics;

	public GameLogic(Dimension sizeOfWindow) {
		this.setSizeOfWindow(sizeOfWindow);
		handler = new Handler(this);
		cam = new Camera(this);
		sounds = new SoundsLoader();
		graphics = new GraphicsLoader();
		setInitialValuesOfVariables();
	}

	/**
	 * Metoda inicjuj¹ca wartoœci parametrów logicznych gry.
	 */
	private void setInitialValuesOfVariables() {

		level = 0;
		lives = 3;
		coins = 0;
		tempOfCoins = 0;

		showDeathScreen = true;
		deathScreenShowTime = 0;
		gameOverScreen = false;
		playing = false;

		tempOfPlayerState = PlayerStates.small;
	}

	/**
	 * Metoda s³u¿¹ca do zaminy poziomu rozgrywki
	 * 
	 */
	public void switchLevel() {
		level++;
		showDeathScreen = true;
		tempOfCoins = coins;
		handler.clearLevel();
		try {
			handler.createLevel(graphics.levelImages[level]);
		} catch (ArrayIndexOutOfBoundsException e) {
			win = true;
			showDeathScreen = true;
			System.out.println("zakonczenie gry");
			level = 0;
		}
		cam.resetCamera();

	}

	/**
	 * Mateoda pozwalaj¹ca ograniczyæ renderowanie elementów do wy³¹cznie
	 * widocznych w oknie
	 * 
	 * @return Obszar widoczny z poziomu okna, gdy gracz nie ¿yje musi zwracaæ
	 *         dowolny, niezerowy obszar nie szerszy ni¿ 100, zaczynaj¹cy siê w
	 *         pocz¹tku uk³adu wspó³rzêdnych
	 */

	public Rectangle getVisibleField() {
		for (Entity e : handler.entity) {
			if (e.getId() == Id.player)
				return new Rectangle(-cam.getX() - 64, -cam.getY() - 64, getSizeOfWindow().width + 128,
						getSizeOfWindow().height + 128);
		}

		return new Rectangle(0, 0, 10, 10);
	}

	public Dimension getSizeOfWindow() {
		return sizeOfWindow;
	}

	public void setSizeOfWindow(Dimension sizeOfWindow) {
		this.sizeOfWindow = sizeOfWindow;
	}

}
