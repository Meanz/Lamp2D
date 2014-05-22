package com.meanworks.lamp2d;

public interface Application {

	/**
	 * Called before the game loop starts to load application data
	 */
	public void preload();

	/**
	 * Called every update tick ( @See Config )
	 */
	public void update();

	/**
	 * Called every frame to render the frame
	 * 
	 * @param renderer
	 */
	public void render(Renderer renderer);

}
