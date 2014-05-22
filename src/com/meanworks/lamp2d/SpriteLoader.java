package com.meanworks.lamp2d;

import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.meanworks.lamp2d.Texture.TextureFilter;

public class SpriteLoader {

	/**
	 * 
	 * @param location
	 * @return
	 */
	public static LampImage loadSprite(String location) {
		// Try using AWT to load the sprite
		try {
			BufferedImage image = loadImageAWT(location);
			// Load the LWJGL version
			Texture texture = LWJGLImage.loadTexture(image);
			LampImage lampImage = new LampImage(texture);
			return lampImage;
		} catch (IOException iex) {
			iex.printStackTrace();
		}
		return null;
	}

	/**
	 * Load the given buffered image into an OpenGL texture
	 * 
	 * @param in
	 * @return
	 */
	public static Texture loadImage(BufferedImage in) {
		return LWJGLImage.loadTexture(in);
	}

	/**
	 * Load the image from the given and filter it with the given Color
	 * 
	 * @param location
	 * @param bg
	 * @return
	 */
	public static LampImage loadTransparentSprite(String location, Color bg) {
		// Try using AWT to load the sprite
		try {
			BufferedImage image = loadImageAWT(location);
			// Filter the image
			image = AWTImage.makeTransparent(image, bg);
			return new LampImage(LWJGLImage.loadTexture(image));
		} catch (IOException iex) {
			iex.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param location
	 * @return
	 * @throws IOException
	 */
	private static BufferedImage loadImageAWT(String location)
			throws IOException {
		return ImageIO.read(new File(location));
	}

	/**
	 * LWJGL functions
	 * 
	 * @author Meanz
	 * 
	 */
	private static class LWJGLImage {

		/**
		 * Our alpha color model
		 */
		private final static ColorModel glAlphaColorModel = new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8,
						8, 8 }, true, false, ComponentColorModel.TRANSLUCENT,
				DataBuffer.TYPE_BYTE);

		/**
		 * Our RGB color model
		 */
		private final static ColorModel glColorModel = new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8,
						8, 0 }, false, false, ComponentColorModel.OPAQUE,
				DataBuffer.TYPE_BYTE);

		/**
		 * Load the given buffered image into an OpenGL texture
		 * 
		 * @param image
		 * @return
		 */
		public static Texture loadTexture(BufferedImage image) {
			if (image == null) {
				return null;
			}

			// Create an image compatible with our system
			WritableRaster raster;
			BufferedImage temp;
			if (image.getColorModel().hasAlpha()) {
				raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
						image.getWidth(), image.getHeight(), 4, null);
				temp = new BufferedImage(glAlphaColorModel, raster, false,
						new Hashtable());
			} else {
				raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
						image.getWidth(), image.getHeight(), 3, null);
				temp = new BufferedImage(glColorModel, raster, false,
						new Hashtable());
			}

			// Write our input image to the temp image
			Graphics g = temp.getGraphics();
			g.setColor(new Color(0f, 0f, 0f, 0f));
			g.fillRect(0, 0, image.getWidth(), image.getHeight());
			g.drawImage(image, 0, 0, null);

			// Get the image data
			byte[] data = ((DataBufferByte) temp.getRaster().getDataBuffer())
					.getData();

			// Wrap a bytebuffer around our image data
			ByteBuffer dataBuffer = ByteBuffer.allocateDirect(data.length);
			dataBuffer.order(ByteOrder.nativeOrder());
			dataBuffer.put(data, 0, data.length);
			dataBuffer.flip();

			// Enable GL_TEXTURE_2D for texture loading
			Texture.enable();
			
			// Create a new texture
			Texture texture = new Texture(image.getWidth(), image.getHeight(), image);

			// Linear filtering for our image
			texture.setFilter(TextureFilter.LINEAR);

			// Bind our texture
			texture.bind();

			// Apply filters
			texture.applyFilter();

			// Upload image data
			GL11.glTexImage2D(
					GL11.GL_TEXTURE_2D, 
					0, 
					GL11.GL_RGBA, 
					image.getWidth(), 
					image.getHeight(), 
					0, 
					image.getColorModel().hasAlpha() ? GL11.GL_RGBA : GL11.GL_RGB, 
					GL_UNSIGNED_BYTE,
					dataBuffer);

			// Unbind our texture
			Texture.bindNone();
			Texture.disable();

			return texture;
		}
	}

	/**
	 * Awt functions
	 * 
	 * @author Meanz
	 * 
	 */
	private static class AWTImage {

		/**
		 * 
		 * @param image
		 * @param color
		 * @return
		 */
		private static BufferedImage makeTransparent(BufferedImage image,
				final Color color) {
			ImageFilter filter = new RGBImageFilter() {
				public int markerRGB = color.getRGB() | 0xFF000000;

				public final int filterRGB(int x, int y, int rgb) {
					if ((rgb | 0xFF000000) == markerRGB) {
						return 0x00FFFFFF & rgb;
					} else {
						return rgb;
					}
				}
			};
			ImageProducer ip = new FilteredImageSource(image.getSource(),
					filter);
			return (BufferedImage) Toolkit.getDefaultToolkit().createImage(ip);
		}
	}
}
