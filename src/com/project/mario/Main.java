package com.project.mario;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Main extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int WIDTH = 256;
	private final int HEIGHT = 240;
	private final int SCALE = 4;
	private final String TITLE = "Mario";
	private Dimension size;
	
	private boolean isRunning;
	private final int fps;

	public Main() {
		// TODO Auto-generated constructor stub
		this.size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		
		this.fps = 60;
		this.isRunning = true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main game = new Main();
		game.run();
		System.exit(0);

	}

	public void initialize() {
		// TODO
		setTitle(TITLE);
		setSize(size);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void update() {
		// TODO
	}

	public void render() {
		// TODO
	}
	
	public void stop() {
		//TODO
		setVisible(false);
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

}
