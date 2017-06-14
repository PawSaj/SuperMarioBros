package com.project.mario.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.project.mario.GameLogic;
import com.project.mario.entity.Entity;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;
import com.project.mario.enums.PlayerStates;
import com.project.mario.enums.TypeOfPipe;
import com.project.mario.enviroment.EnviromentObject;
import com.project.mario.gui.Button;

/**
 * Klasa s³u¿¹ca do obs³ugi klawiatury
 *
 */
public class KeyInput implements KeyListener {
	private boolean[] keys;
	private boolean released;
	private GameLogic gameLogic;
	private Button[] launcherButtons;

	public KeyInput(GameLogic gameLogic, Button[] launcherButtons) {
		this.launcherButtons = launcherButtons;
		this.gameLogic = gameLogic;
		keys = new boolean[65535];
		released = true;
	}

	/**
	 * Obs³uga klawiszy w menu g³ównym
	 */
	private void launcherKeyListener() {
		if (!gameLogic.playing) {
			int numberOfButtons = launcherButtons.length;
			if (keys[KeyEvent.VK_ENTER] || keys[KeyEvent.VK_SPACE]) {
				for (int i = 0; i < numberOfButtons; i++) {
					Button button = launcherButtons[i];
					if (button.isSelected()) {
						button.triggerEvent();
					}
				}
			}

			if (keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) {
				int i = 0;
				while (i < numberOfButtons && launcherButtons[i].isSelected() != true) {
					i++;
				}

				if (launcherButtons[i].isSelected() && i > 0) {
					launcherButtons[i].setSelected(false);
					launcherButtons[i - 1].setSelected(true);
				}
			}

			if (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]) {
				int i = 0;
				while (i < numberOfButtons && launcherButtons[i].isSelected() != true) {
					i++;
				}

				if (launcherButtons[i].isSelected() && i < numberOfButtons - 1) {
					launcherButtons[i].setSelected(false);
					launcherButtons[i + 1].setSelected(true);
				}
			}
		}
	}

	/**
	 * Obs³uga klawiszy podczas gry.
	 */
	private void playingKeyListener() {
		for (Entity e : gameLogic.handler.entity) {
			if (e.getId() == Id.player && !(e.isGoingDownPipe() || e.isGoingUpPipe())
					&& e.getState() != PlayerStates.slidingOnPole && e.getState() != PlayerStates.goingToCastel
					&& e.getState() != PlayerStates.dead && !gameLogic.showDeathScreen
					&& e.getState() != PlayerStates.goingToPrinces) {
				if (keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) {
					if (!e.isJumping() && e.isGround()) {
						e.setFalling(false);
						e.setJumping(true);
						e.setGravity(18.0);
						e.setGround(false);
						if (e.getHeight() == 64) {
							if (!gameLogic.sounds.jumpSmallSound.isRunning())
								gameLogic.sounds.jumpSmallSound.play();
						} else {
							if (!gameLogic.sounds.jumpBigSound.isRunning())
								gameLogic.sounds.jumpBigSound.play();
						}

					}
				} else if (!e.isFalling()) {
					double tempGravity = e.getGravity();
					if (tempGravity >= 2.0) {
						tempGravity -= 8.0;
						e.setGravity(tempGravity);
					}

				}

				if (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]) {
					for (EnviromentObject env : gameLogic.handler.enviromentObject) {
						if (env.getId() == Id.pipe && env.getTypeOfPipe() == TypeOfPipe.verticalOpenEntrance) {
							if (e.getBoundsBottomPipe().intersects(env.getBoundsPipe())) {
								if (!e.isGoingDownPipe())
									e.setGoingDownPipe(true);
								e.setVelY(0);
								if (!gameLogic.sounds.pipe.isRunning())
									gameLogic.sounds.pipe.play();
								gameLogic.sounds.mainThemeSong.stop();
							}
						}
					}
				}

				if (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) {
					e.setVelX(-5);
					e.setFacing(Facing.left);
				} else if (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) {
					for (EnviromentObject env : gameLogic.handler.enviromentObject) {
						if (env.getId() == Id.pipe && env.getTypeOfPipe() == TypeOfPipe.horizontalEntrance) {
							if (e.getBounds().intersects(env.getBoundsLeft())) {
								if (!e.isGoingUpPipe()) {
									e.setGoingUpPipe(true);
									e.setVelY(0);
								}
								if (!gameLogic.sounds.pipe.isRunning())
									gameLogic.sounds.pipe.play();
								gameLogic.sounds.underworldSong.stop();
							}
						}
					}
					if (!e.isGoingUpPipe()) {
						e.setVelX(5);
						e.setFacing(Facing.right);
					}
					e.setVelX(5);
					e.setFacing(Facing.right);
				} else {
					e.setVelX(0);
				}

				if (keys[KeyEvent.VK_SPACE] && released) {
					if (gameLogic.tempOfPlayerState == PlayerStates.fire) {
						e.createMarioFireball();
						if (released) {
							released = false;
						}
					}
				}

				if (keys[KeyEvent.VK_SHIFT]) {
					if (e.getVelX() == 5) {
						e.setVelX(e.getVelX() * 8 / 5);
					}
				} else {
					if (e.getVelX() == 5 * 8 / 5)
						e.setVelX(e.getVelX() * 5 / 8);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		launcherKeyListener();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			released = true;
		}

	}

	public void keyTyped(KeyEvent e) {
		// ONLY CHARACTERS!!!!! dlatego nie u¿ywaæ
	}

	/**
	 * Metoda obs³uguj¹ca wciœniêcia klawiszy
	 */
	public void update() {
		playingKeyListener();
	}
}
