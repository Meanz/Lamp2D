package com.meanworks.lamp2d;

import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * The Lamp2D class is responsible for initializing the engine and preparing it
 * for use
 * 
 * @author Steffen Evensen
 * 
 */
public class Game {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 632514293825353963L;
	/**
	 * The singleton object
	 */
	private static Game singleton;
	/**
	 * The number of frames per second this engine is displaying
	 */
	private int fps;
	/**
	 * The number of updates per second this engine is performing
	 */
	private int ups;
	/**
	 * The time the last frame took to render
	 */
	private double frameTime;
	/**
	 * Whether or not the engine is running
	 */
	private boolean running;
	/**
	 * The application callback
	 */
	private Application application;
	/**
	 * The configuration for this engine
	 */
	private Config config;
	/**
	 * The input for this engine
	 */
	private Input input;
	/**
	 * The renderer this engine is using
	 */
	private Renderer renderer;

	/**
	 * Construct a new Lamp2D object
	 */
	public Game(Application application, Config config) {
		if (singleton != null) {
			throw new RuntimeException(
					"Can not create more than one instance of Game");
		}

		// Init lwjgl
		loadLWJGL();

		// Create our window
		try {
			Display.setDisplayMode(new DisplayMode(config.width, config.height));
			Display.setTitle(config.title);
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fps = 0;
		ups = 0;
		frameTime = 0.0d;
		running = false;
		renderer = new LWJGLRenderer(this);
		input = new Input();
		this.application = application;
		this.config = config;
		singleton = this;
	}

	/**
	 * Load LWJGL
	 */
	public static void loadLWJGL() {
		try {
			String osName = System.getProperty("os.name").toLowerCase();
			boolean isMacOs = osName.startsWith("mac os x");
			boolean isLinuxOs = osName.startsWith("linux");
			if (isMacOs) {
				System.setProperty("java.library.path",
						System.getProperty("java.library.path") + ";"
								+ new File("native/macosx").getAbsolutePath());
				System.setProperty("org.lwjgl.librarypath", new File(
						"native/macosx").getAbsolutePath());
			} else if (isLinuxOs) {
				System.setProperty("java.library.path",
						System.getProperty("java.library.path") + ";"
								+ new File("native/linux").getAbsolutePath());
				System.setProperty("org.lwjgl.librarypath", new File(
						"native/linux").getAbsolutePath());
			} else {

				System.setProperty("java.library.path",
						System.getProperty("java.library.path")
								+ ";"
								+ new File("native/windows/").getAbsolutePath()
										.replaceAll("\\/", "\\"));
				System.setProperty("org.lwjgl.librarypath",
						new File("native/windows/").getAbsolutePath()
								.replaceAll("\\/", "\\"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Get the renderer of this game
	 * 
	 * @return
	 */
	public static Renderer getRenderer() {
		return singleton.renderer;
	}

	/**
	 * Get the configuration of this game
	 * 
	 * @return
	 */
	public static Config getConfig() {
		return singleton.config;
	}

	/**
	 * Get the last recorded frame time in milliseconds
	 * 
	 * @return
	 */
	public double getFrameTime() {
		return frameTime;
	}

	/**
	 * Get the last recorded fps value
	 * 
	 * @return The last recorded fps value
	 */
	public static int getFPS() {
		return singleton.fps;
	}

	/**
	 * Get the last recorded ups value
	 * 
	 * @return The last recorded ups value
	 */
	public static int getUPS() {
		return singleton.ups;
	}

	/**
	 * Sends a stop request to the Lamp 2D Engine
	 */
	public static void stop() {
		singleton.running = false;
	}

	/**
	 * Called when the engine has exited the run loop
	 */
	private void onExit() {
		// input.destroy();
		// ResourceManager.destroy();
		Display.destroy();
	}

	/**
	 * Start's the Lamp 2D Engine
	 */
	public void start() {
		running = true;
		run();
	}

	/**
	 * The core game loop
	 */
	public void run() {

		/*
		 * Game Loop
		 */
		int MAX_FRAMESKIP = 10;
		long last_game_tick = System.currentTimeMillis();
		long next_game_tick = last_game_tick;
		int loops;
		int fpsc = 0;
		int upsc = 0;
		long last_fps = System.currentTimeMillis();

		int SKIP_TICKS = 1000 / config.targetUps;

		int savedUps = 0;

		/*
		 * Preload
		 */
		preload();

		/*
		 * Start the render/update loop
		 */
		while (running) {
			/*
			 * Logging
			 */
			if (System.currentTimeMillis() - last_fps > 1000) {
				fps = fpsc;
				ups = upsc;
				upsc = 0;
				fpsc = 0;
				last_fps = System.currentTimeMillis();
			}
			/*
			 * Update
			 */
			long renderStart = System.nanoTime();
			loops = 0;
			while (System.currentTimeMillis() > next_game_tick
					&& loops < MAX_FRAMESKIP) {

				if (Display.isCloseRequested()) {
					// Stop running
					running = false;
				}

				/*
				 * Update the updates per second
				 */
				if (savedUps != config.targetUps) {
					SKIP_TICKS = 1000 / config.targetUps;
					savedUps = config.targetUps;
				}
				// Parse updates in the application
				update(); // Call the update function

				next_game_tick += SKIP_TICKS;
				loops++;
				upsc++;
			}
			/*
			 * Render and also store the time used to render the frame (Useful
			 * for analyzing performance)
			 */
			// Grab input keys
			Display.processMessages();
			input.update();
			render();
			Display.update(false);
			fpsc++;
			long renderEnd = System.nanoTime();
			long deltaRender = renderEnd - renderStart;
			frameTime = (double) (deltaRender * Math.pow(10, -9));
		}

		onExit();
	}

	/**
	 * Calls the callback and sends the preload operation. Mainly used to load
	 * assets to the game.
	 */
	public void preload() {
		application.preload();
	}

	/**
	 * Calls the callback and sends the update operation. Called to update the
	 * game.
	 */
	public void update() {
		application.update();
	}

	/**
	 * Calls the callback and sends the render operation. Then draws the
	 * rendered image to the screen
	 */
	public void render() {
		renderer.preRender();
		application.render(renderer);
		renderer.postRender();
	}

}
