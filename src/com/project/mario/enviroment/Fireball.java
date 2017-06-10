package com.project.mario.enviroment;

import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;

/**
 * Klasa generuj¹ca pojedyncze elementy wiruj¹ce, nale¿¹ce do FireBlock'u
 *
 */
public class Fireball extends EnviromentObject {
	/**
	 * @param angle
	 *            k¹t obrotu pod jakim znajduje siê fragment p³omienia
	 * @param radius
	 *            promieñ o jaki jest oddalony od centrum bloku
	 * @param centerX
	 * @paramcenterY Wspó³rzêdne œrodka okrêgu, po którym porusza siê element
	 */
	private float angle;
	private int radius;
	private int centerX, centerY;
	private Facing facing;

	public Fireball(int x, int y, int width, int height, Id id, GameLogic gameLogic, Facing facing, int radius, int centerX,
			int centerY) {
		super(x, y, width, height, id, gameLogic);
		this.facing = facing;
		this.radius = radius;
		this.centerX = centerX;
		this.centerY = centerY;
		angle = 0;
	}

	public void render(Graphics g) {
		g.drawImage(gameLogic.graphics.marioFireball[frame].getBufferedImage(), x, y, width, height, null);
	}

	public void update() {
		if (facing == Facing.left)
			angle++;
		else
			angle--;
		x = (int) (radius * (float) Math.cos(Math.toRadians(angle)) + centerX);
		y = (int) (radius * (float) Math.sin(Math.toRadians(angle)) + centerY);
		frameDelay++;
		if (frameDelay >= 5) {
			frame++;
			frame = frame % 4;
			frameDelay = 0;
		}
	}

}
