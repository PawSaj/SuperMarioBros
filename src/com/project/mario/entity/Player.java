package com.project.mario.entity;

import java.awt.Color;
import java.awt.Graphics;

import com.project.mario.GameLogic;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;

public class Player extends Entity {

	public Player(int x, int y, int width, int height, Id id, GameLogic gameLogic, Facing facing) {
		super(x, y, width, height, id, gameLogic, facing);
		// TODO Auto-generated constructor stub

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		x += velX;
		y += velY;
		if (x <= 0) {
			x = 0;
		}
		if (y <= 0) {
			y = 0;
		}
	}

}
