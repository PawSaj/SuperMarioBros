package com.project.mario;

import com.project.mario.enums.PlayerStates;
import com.project.mario.gui.Launcher;
import com.project.mario.resource_loaders.GraphicsLoader;
import com.project.mario.resource_loaders.SoundsLoader;

public class GameLogic {
	public PlayerStates tempOfPlayerState;
	
	public int level;
	public int lives;
	public int coins;
	public int tempOfCoins;
	public boolean win;

	public boolean showDeathScreen;
	public int deathScreenTime;
	public boolean gameOverScreen;
	public boolean playing;

	public Handler handler;
	public Camera cam;
	public Launcher launcher;
	//public MouseInput mouseInput;

	public SoundsLoader sounds;
	public GraphicsLoader graphics;
	
	
	public GameLogic() {
		handler = new Handler();
		sounds = new SoundsLoader();
		graphics = new GraphicsLoader();
		setInitialValuesOfVariables();
	}
	
	private void setInitialValuesOfVariables() {
		handler = new Handler();

		level = 0;
		lives = 3;
		coins = 0;
		tempOfCoins = 0;

		showDeathScreen = true;
		deathScreenTime = 0;
		gameOverScreen = false;
		playing = false;

		tempOfPlayerState = PlayerStates.small;
	}
	
	
}
