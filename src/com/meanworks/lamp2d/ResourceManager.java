package com.meanworks.lamp2d;

import java.util.HashMap;

public class ResourceManager {

	/**
	 * A class that handles OpenGL textures
	 * 
	 * @author Meanz
	 * 
	 */
	private static class TextureManager {

		/**
		 * The list of loaded textures
		 */
		private HashMap<Integer, Texture> textures = new HashMap<Integer, Texture>();

	}

	/**
	 * The singleton of this resource manager
	 */
	private static ResourceManager singleton = null;

	/**
	 * Get the resource manager
	 * 
	 * @return
	 */
	public static ResourceManager get() {
		return singleton;
	}

	/**
	 * Our texture manager
	 */
	private TextureManager textureManager;

	/**
	 * Construct the ResourceManager
	 */
	public ResourceManager() {
		if (singleton != null) {
			throw new RuntimeException(
					"Can not initialize 2 resource managers.");
		}
		singleton = this;

		textureManager = new TextureManager();
	}

	/**
	 * Load an image
	 * 
	 * @param resourcePath
	 * @return
	 */
	public static LampImage loadImage(String resourcePath) {
		return null;
	}

}
