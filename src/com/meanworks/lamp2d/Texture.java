package com.meanworks.lamp2d;

import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

public class Texture {

	/**
	 * 
	 * @author Meanz
	 * 
	 */
	public enum TextureFilter {
		LINEAR, NEAREST
	}

	/**
	 * The id of this texture
	 */
	private int id;

	/**
	 * The width of this texture
	 */
	private int width;

	/**
	 * The height of this texture
	 */
	private int height;

	/**
	 * The filter of this texture
	 */
	private TextureFilter filter;

	/**
	 * The image
	 */
	private BufferedImage image;

	/**
	 * Whether mipmapping is enabled or not
	 */
	private boolean mipmapping;

	/**
	 * Construct a new Texture class with the given parameters
	 * 
	 * @param width
	 * @param height
	 * @param id
	 * @param image
	 */
	public Texture(int width, int height, int id, BufferedImage image) {
		this.width = width;
		this.height = height;
		this.id = id;
		this.filter = TextureFilter.NEAREST;
		this.image = image;
	}

	/**
	 * 
	 * @param width
	 * @param height
	 * @param image
	 */
	public Texture(int width, int height, BufferedImage image) {
		this(width, height, 0, image);
		generate();
	}

	/**
	 * Construct a new empty Texture class
	 */
	public Texture() {
		this.width = 0;
		this.height = 0;
		this.id = 0;
		this.filter = TextureFilter.NEAREST;
		this.image = null;
	}

	/**
	 * Generates a new OpenGL texture
	 * 
	 * @return
	 */
	public boolean generate() {
		id = GL11.glGenTextures();
		if (id > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isMipMapping() {
		return mipmapping;
	}

	/**
	 * 
	 * @param mipmapping
	 */
	public void setMipMapping(boolean mipmapping) {
		this.mipmapping = mipmapping;
	}

	/**
	 * 
	 * @param filter
	 */
	public void setFilter(TextureFilter filter) {
		this.filter = filter;
	}

	/**
	 * 
	 */
	public void applyFilter() {
		if (filter == TextureFilter.LINEAR) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		} else if (filter == TextureFilter.NEAREST) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
					GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		}
	}

	/**
	 * Enable GL_TEXTURE_2D
	 */
	public static void enable() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	/**
	 * Disable GL_TEXTURE_2D
	 */
	public static void disable() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	/**
	 * Bind no texture
	 */
	public static void bindNone() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	/**
	 * Bind this texture
	 */
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}

	/**
	 * Draws this texture
	 * 
	 * @param x
	 * @param y
	 */
	public void draw(int x, int y) {
		Texture.enable();
		bind();
		applyFilter();
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex2f(x, y + height);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex2f(x + width, y + height);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex2f(x + width, y);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex2f(x, y);
		}
		GL11.glEnd();
		Texture.disable();
	}

}
