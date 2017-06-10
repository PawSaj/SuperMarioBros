package com.project.mario;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.project.mario.entity.Player;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;
import com.project.mario.input.KeyInput;

public class Main extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private final int WIDTH = 256;
	private final int HEIGHT = 240;
	private final int SCALE = 4;
	private final String TITLE = "Mario";
	private Dimension size;
	
	private boolean isRunning;
	private final int fps;
	
	private GameLogic gameLogic;
	
	private boolean isPlaying;
	
	private KeyInput keyInput;

	public Main() {
		// TODO Auto-generated constructor stub
		this.size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		
		this.fps = 60;
		this.isRunning = true;
		this.gameLogic = new GameLogic();
		this.isPlaying = false;
		
		keyInput = new KeyInput(gameLogic.handler);
		addKeyListener(keyInput);
		
		
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		(new Thread(new Main())).start();
	}

	public void initialize() {
		// TODO
		setTitle(TITLE);
		setSize(size);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
		
		gameLogic.handler.addEntity(new Player(10, 30, 100, 20, Id.player, gameLogic, Facing.right));
	}

	public void update() {
		// TODO
		keyInput.update();
		gameLogic.handler.update();
		
	}

	public void render() {
		// TODO
		BufferStrategy bs = getBufferStrategy();
		if(bs==null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.blue);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		gameLogic.handler.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public void stop() {
		//TODO
		setVisible(false);
		System.exit(0);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		initialize();
		
		while (isRunning) {
			long time = System.currentTimeMillis();

			update();
			render();

			// delay for each frame - time it took for one frame
			time = (1000 / fps) - (System.currentTimeMillis() - time);

			if (time > 0) {
				try {
					Thread.sleep(time);
				} catch (Exception e) {

				}
			}
		}
		
		stop();
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public int getSCALE() {
		return SCALE;
	}

}
