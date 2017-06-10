package com.project.mario.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.project.mario.Handler;
import com.project.mario.entity.Entity;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;


/**
 * Klasa s³u¿¹ca do obs³ugi klawiatury
 *
 */
public class KeyInput implements KeyListener {
	private boolean[] keys;
	private boolean released;
	private Handler handler;

	public KeyInput(Handler handler) {
		this.handler = handler;
		keys = new boolean[65535];
		released = true;
	}

	private void launcherKeyListener() {
		/*if (!Main.playing) {
			int numberOfButtons = Main.launcher.buttons.length;
			if (keys[KeyEvent.VK_ENTER] || keys[KeyEvent.VK_SPACE]) {
				for (int i = 0; i < numberOfButtons; i++) {
					Button button = Main.launcher.buttons[i];
					if (button.selected) {
						button.triggerEvent();
					}
				}
			}

			if (keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) {
				int i = 0;
				while (i < numberOfButtons && Main.launcher.buttons[i].selected != true) {
					i++;
				}

				if (Main.launcher.buttons[i].selected && i > 0) {
					Main.launcher.buttons[i].selected = false;
					Main.launcher.buttons[i - 1].selected = true;
				}
			}

			if (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]) {
				int i = 0;
				while (i < numberOfButtons && Main.launcher.buttons[i].selected != true) {
					i++;
				}

				if (Main.launcher.buttons[i].selected && i < numberOfButtons - 1) {
					Main.launcher.buttons[i].selected = false;
					Main.launcher.buttons[i + 1].selected = true;
				}
			}
		}*/
	}

	private void playingKeyListener() {
		for (Entity e : handler.entity) {
			if (e.getId() == Id.player /*&& !(e.goingDownPipe || e.goingUpPipe) && e.state != PlayerStates.slidingOnPole
					&& e.state != PlayerStates.goingToCastel && e.state != PlayerStates.dead && !Main.showDeathScreen
					&& e.state != PlayerStates.goingToPrinces*/) {
				if (keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) {
					/*if (!e.jumping && e.ground) {
						e.falling = false;
						e.jumping = true;
						e.gravity = 18.0;
						e.ground = false;
						if (e.height == 64) {
							if (!Main.jumpSmallSound.isRunning())
								Main.jumpSmallSound.play();
						} else {
							if (!Main.jumpBigSound.isRunning())
								Main.jumpBigSound.play();
						}

					}*/
				} else if (!e.isFalling()) {
					double tempGravity = e.getGravity();
					if (tempGravity >= 2.0) {
						tempGravity -= 8.0;
						e.setGravity(tempGravity);
					}
					
				}

				if (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]) {
					/*for (Tile t : Main.handler.tile) {
						if (t.getId() == Id.pipe && t.facing == 0) {
							if (e.getBoundsBottomPipe().intersects(t.getBoundsPipe())) {
								if (!e.goingDownPipe)
									e.goingDownPipe = true;
								e.setVelY(0);
								if (!Main.pipe.isRunning())
									Main.pipe.play();
								Main.mainThemeSong.stop();
							}
						}
					}*/
				}

				if (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) {
					e.setVelX(-5);
					e.setFacing(Facing.left);
				} else if (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) {
					/*for (Tile t : Main.handler.tile) {
						if (t.getId() == Id.pipe && t.facing == 5) {
							if (e.getBounds().intersects(t.getBoundsLeft())) {
								if (!e.goingUpPipe) {
									e.goingUpPipe = true;
									e.setVelY(0);
								}
								if (!Main.pipe.isRunning())
									Main.pipe.play();
								Main.underworldSong.stop();
							}
						}
					}
					if (!e.goingUpPipe) {
						e.setVelX(5);
						e.facing = 1;
					}*/
					e.setVelX(5);
					e.setFacing(Facing.right);
				} else {
					e.setVelX(0);
				}

				/*if (keys[KeyEvent.VK_SPACE] && released) {
					if (Main.tempOfPlayerState == EntityStates.fire) {
						e.createMarioFireball();
						if (released) {
							released = false;
						}
					}
				}*/

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

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		launcherKeyListener();
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			released = true;
		}

	}

	public void keyTyped(KeyEvent e) {
		// ONLY CHARACTERS!!!!!
	}

	/**
	 * Metoda obs³uguj¹ca wciœniêcia klawiszy
	 */
	public void update() {
		playingKeyListener();
	}
}
