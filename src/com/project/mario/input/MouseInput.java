package com.project.mario.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.event.MouseInputListener;

import com.project.mario.GameLogic;
import com.project.mario.gui.Button;

/**
 * Klasa s³u¿¹ca do obs³ugi myszy
 *
 */
public class MouseInput implements MouseInputListener, MouseMotionListener {

	private int x, y;
	private Button[] buttons;
	private GameLogic gameLogic;

	public MouseInput(GameLogic gameLogic, Button[] buttons) {
		super();
		this.gameLogic = gameLogic;
		this.buttons = buttons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		if (!gameLogic.playing) {
			for (int i = 0; i < buttons.length; i++) {
				Button button = buttons[i];

				if (x >= button.getX() && x <= button.getX() + button.getWidht() && y >= button.getY()
						&& y <= button.getY() + button.getHeight()) {
					button.triggerEvent();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.
	 * MouseEvent)
	 */
	public void mouseDragged(MouseEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

}
