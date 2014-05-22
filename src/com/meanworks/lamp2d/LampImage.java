package com.meanworks.lamp2d;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class LampImage {

	/**
	 * The image of this Sprite
	 */
	private Texture image;

	/**
	 * Construct a new Sprite
	 * 
	 * @param image
	 */
	public LampImage(Texture image) {
		this.image = image;
	}

	/**
	 * 
	 * @param other
	 */
	public LampImage(LampImage other) {
		this(other.image);
	}

	/**
	 * Copies this sprite
	 * 
	 * @return
	 */
	public LampImage copy() {
		return new LampImage(this);
	}

	/**
	 * Creates another instance of this Sprite, so whatever changes is done to
	 * this sprite will also be done to the other sprite
	 * 
	 * @return
	 */
	public LampImage instance() {
		return new LampImage(this.image);
	}

	/**
	 * Flips this sprite vertically
	 */
	public void flipVertically() {

	}

	/**
	 * Flips this sprite horizontally
	 */
	public void flipHorizontally() {

	}

	/**
	 * Get the underlying texture for this image
	 * 
	 * @return
	 */
	public Texture getTexture() {
		return image;
	}
}
