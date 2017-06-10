package com.project.mario.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.event.MouseInputListener;

/**
 * Klasa s³u¿¹ca do obs³ugi myszy
 *
 */
public class MouseInput implements MouseInputListener, MouseMotionListener {

	public int x, y;

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		/*if (!Main.isPlaying) {
			for (int i = 0; i < Main.launcher.buttons.length; i++) {
				Button button = Main.launcher.buttons[i];

				if (x >= button.getX() && x <= button.getX() + button.getWidht() && y >= button.getY()
						&& y <= button.getY() + button.getHeight()) {
					button.triggerEvent();
				}
			}
		}*/
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

}
