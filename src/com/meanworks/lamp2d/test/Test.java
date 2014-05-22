package com.meanworks.lamp2d.test;

import java.awt.Color;

import com.meanworks.lamp2d.Application;
import com.meanworks.lamp2d.Config;
import com.meanworks.lamp2d.Game;
import com.meanworks.lamp2d.Input;
import com.meanworks.lamp2d.Renderer;
import com.meanworks.lamp2d.LampImage;
import com.meanworks.lamp2d.SpriteLoader;
import com.sun.glass.events.KeyEvent;

public class Test implements Application {

	/**
	 * A test sprite
	 */
	private LampImage testImage;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meanworks.lamp2d.Application#preload()
	 */
	@Override
	public void preload() {
		testImage = SpriteLoader.loadSprite("./data/MarioSMBW.png");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.meanworks.lamp2d.Application#update()
	 */
	@Override
	public void update() {
		if(Input.isKeyDown(KeyEvent.VK_ESCAPE)) {
			Game.stop();
		}
		
		if(Input.isKeyPressed(KeyEvent.VK_A)) {
			System.err.println("KEY A Pressed");
		}
		if(Input.isKeyDown(KeyEvent.VK_A)) {
			System.err.println("KEY A Held");
		}
		if(Input.isKeyReleased(KeyEvent.VK_A)) {
			System.err.println("KEY A Released.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.meanworks.lamp2d.Application#render(com.meanworks.lamp2d.Renderer)
	 */
	@Override
	public void render(Renderer renderer) {
		renderer.setColor(Color.blue);
		renderer.fillRect(1, 1, 5, 5);
		renderer.drawLine(100, 100, 200, 100);
		renderer.draw(testImage, 100, 300);
		renderer.drawString("Hello there, Woop!", 20, 50);
	}

	/**
	 * Entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Config config = new Config();
		config.width = 1024;
		config.height = 768;

		Game lamp2d = new Game(new Test(), config);
		lamp2d.start();
	}
}
