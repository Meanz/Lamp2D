package com.meanworks.lamp2d;

import java.awt.Component;

public class Config {

	/**
	 * The version of the lamp 2d engine
	 */
	public final static double version = 0.2d;

	/**
	 * The parent component of the client
	 */
	public Component parentComponent;

	/**
	 * The title of the game window
	 */
	public String title = "Lamp2D Alpha " + version;

	/**
	 * The target update rate per second
	 */
	public int targetUps = 60;

	/**
	 * The width of the client
	 */
	public int width = 800;

	/**
	 * The height of the client
	 */
	public int height = 640;

}
