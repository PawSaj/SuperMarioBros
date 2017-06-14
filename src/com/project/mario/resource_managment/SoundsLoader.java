package com.project.mario.resource_managment;

/**
 * Klasa ³aduj¹ca i przechowywuj¹ca pliki diêków gry.
 * 
 * @author Pawe³ Sajnóg
 *
 */
public class SoundsLoader {
	public SoundFileGetter jumpSmallSound;
	public SoundFileGetter jumpBigSound;
	public SoundFileGetter coinSound;
	public SoundFileGetter marioDieSound;
	public SoundFileGetter mainThemeSong;
	public SoundFileGetter fireball;
	public SoundFileGetter powerUpAppear;
	public SoundFileGetter bumpBlock;
	public SoundFileGetter breakBlock;
	public SoundFileGetter kickDeadKoopa;
	public SoundFileGetter hitEnemy;
	public SoundFileGetter flagPole;
	public SoundFileGetter pipe;
	public SoundFileGetter worldClear;
	public SoundFileGetter stageClear;
	public SoundFileGetter underworldSong;
	public SoundFileGetter powerUp;
	public SoundFileGetter starmanSound;
	public SoundFileGetter gameOverSound;
	public SoundFileGetter bowserFireSound;
	public SoundFileGetter bowserFallSound;

	public SoundsLoader() {
		initSounds();
	}

	/**
	 * Metoda inicjuj¹ca zmienne dzwiêków
	 */
	private void initSounds() {
		jumpSmallSound = new SoundFileGetter("./res/smb_jumpsmall.wav");
		jumpBigSound = new SoundFileGetter("./res/smb_jumpbig.wav");
		coinSound = new SoundFileGetter("./res/smb_coin.wav");
		marioDieSound = new SoundFileGetter("./res/smb_mariodie.wav");
		mainThemeSong = new SoundFileGetter("./res/smb_mainTheme.wav");
		fireball = new SoundFileGetter("./res/smb_fireball.wav");
		powerUpAppear = new SoundFileGetter("./res/smb_powerup_appears.wav");
		bumpBlock = new SoundFileGetter("./res/smb_bumpblock.wav");
		breakBlock = new SoundFileGetter("./res/smb_breakblock.wav");
		kickDeadKoopa = new SoundFileGetter("./res/smb_kick.wav");
		hitEnemy = new SoundFileGetter("./res/smb_stomp.wav");
		flagPole = new SoundFileGetter("./res/smb_flagpole.wav");
		pipe = new SoundFileGetter("./res/smb_pipe.wav");
		worldClear = new SoundFileGetter("./res/smb_stage_clear.wav");
		stageClear = new SoundFileGetter("./res/smb_world_clear.wav");
		underworldSong = new SoundFileGetter("./res/smb_underworld.wav");
		powerUp = new SoundFileGetter("./res/smb_powerup.wav");
		starmanSound = new SoundFileGetter("./res/smb_starman.wav");
		gameOverSound = new SoundFileGetter("./res/smb_gameover.wav");
		bowserFireSound = new SoundFileGetter("./res/smb_bowserfire.wav");
		bowserFallSound = new SoundFileGetter("./res/smb_bowserfall.wav");
	}
}
