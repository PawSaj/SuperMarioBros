package com.project.mario.resource_loaders;

public class SoundsLoader {
	public Sound jumpSmallSound;
	public Sound jumpBigSound;
	public Sound coinSound;
	public Sound marioDieSound;
	public Sound mainThemeSong;
	public Sound fireball;
	public Sound powerUpAppear;
	public Sound bumpBlock;
	public Sound breakBlock;
	public Sound kickDeadKoopa;
	public Sound hitEnemy;
	public Sound flagPole;
	public Sound pipe;
	public Sound worldClear;
	public Sound stageClear;
	public Sound underworldSong;
	public Sound powerUp;
	public Sound starmanSound;
	public Sound gameOverSound;
	public Sound bowserFireSound;
	public Sound bowserFallSound;
	
	public SoundsLoader() {
		initSounds();
	}
	
	private void initSounds() {
		jumpSmallSound = new Sound("./res/smb_jumpsmall.wav");
		jumpBigSound = new Sound("./res/smb_jumpbig.wav");
		coinSound = new Sound("./res/smb_coin.wav");
		marioDieSound = new Sound("./res/smb_mariodie.wav");
		mainThemeSong = new Sound("./res/smb_mainTheme.wav");
		fireball = new Sound("./res/smb_fireball.wav");
		powerUpAppear = new Sound("./res/smb_powerup_appears.wav");
		bumpBlock = new Sound("./res/smb_bumpblock.wav");
		breakBlock = new Sound("./res/smb_breakblock.wav");
		kickDeadKoopa = new Sound("./res/smb_kick.wav");
		hitEnemy = new Sound("./res/smb_stomp.wav");
		flagPole = new Sound("./res/smb_flagpole.wav");
		pipe = new Sound("./res/smb_pipe.wav");
		worldClear = new Sound("./res/smb_stage_clear.wav");
		stageClear = new Sound("./res/smb_world_clear.wav");
		underworldSong = new Sound("./res/smb_underworld.wav");
		powerUp = new Sound("./res/smb_powerup.wav");
		starmanSound = new Sound("./res/smb_starman.wav");
		gameOverSound = new Sound("./res/smb_gameover.wav");
		bowserFireSound = new Sound("./res/smb_bowserfire.wav");
		bowserFallSound = new Sound("./res/smb_bowserfall.wav");
	}
}
