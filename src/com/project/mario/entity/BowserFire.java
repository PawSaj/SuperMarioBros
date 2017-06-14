package com.project.mario.entity;

import java.awt.Graphics;
import java.util.Random;

import com.project.mario.GameLogic;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;
import com.project.mario.enums.PlayerStates;

/**
 * Klasa generuj¹ca p³omienie tworzone przez Bowser'a.
 * 
 * 
 */
public class BowserFire extends Entity {
	/**
	 * @param firePositionShift
	 *            Parametr okreœlaj¹cy przesuniêcie p³omienia po jego stworzeniu
	 *            przez Bowsera, tak by mo¿liwe by³o wystrzelenie p³omienia na
	 *            wysokoœci gracza, gdy jest ma³y
	 * @param MAX_X
	 *            Parametr okreœlaj¹cy maksymaln¹ odleg³oœæ jak¹ mo¿e pokonaæ
	 *            p³omie
	 */
	private int firePositionShift;
	private final int MAX_X;

	public BowserFire(int x, int y, int width, int height, Id id, GameLogic gameLogic, Facing facing) {
		super(x, y, width, height, id, gameLogic, facing);

		MAX_X = x + 600;
		random = new Random();
		firePositionShift = random.nextInt(64);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		if (facing == Facing.left)
			g.drawImage(gameLogic.graphics.bowserFire[frame].getBufferedImage(), x, y, width, height, null);
		else
			g.drawImage(gameLogic.graphics.bowserFire[frame + 2].getBufferedImage(), x, y, width, height, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#update()
	 */
	public void update() {
		if (gameLogic.tempOfPlayerState != PlayerStates.dead) {
			if (facing == Facing.left)
				x -= 2;
			else
				x += 2;
		}

		if (firePositionShift > 0) {
			y++;
			firePositionShift--;
		}

		frameDelay++;
		if (frameDelay >= 10) {
			frame++;
			frame = frame % 2;
			frameDelay = 0;
		}

		if (x < 100 || x > MAX_X) {
			gameLogic.handler.removeEntity(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.project.mario.entity.Entity#entityDieAnimation()
	 */
	public void entityDieAnimation() {
		;
	}
}
