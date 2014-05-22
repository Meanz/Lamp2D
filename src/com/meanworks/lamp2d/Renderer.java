package com.meanworks.lamp2d;

import java.awt.Color;
import java.awt.Font;

public interface Renderer {

	/**
	 * 
	 * @param renderable
	 */
	public void draw(AnimatedSprite sprite, int x, int y);

	/**
	 * 
	 * @param renderable
	 */
	public void draw(LampImage sprite, int x, int y);

	/**
	 * Draw the given string at the given position
	 * 
	 * @param s
	 * @param x
	 * @param y
	 */
	public void drawString(String s, int x, int y);

	/**
	 * Draws a line from point (x1, y1) to (x2 y2)
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void drawLine(int x1, int y1, int x2, int y2);

	/**
	 * Draws a rectangle from point (x1, y1) to (x2, y2)
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void drawRect(int x1, int y1, int x2, int y2);

	/**
	 * Fills a rectangle from point (x1, y1) to (x2, y2)
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void fillRect(int x1, int y1, int x2, int y2);

	/**
	 * Set the font to use for drawing text
	 * 
	 * @param font
	 */
	public void setFont(Font font);

	/**
	 * Set the color to render with
	 * 
	 * @param color
	 */
	public void setColor(Color color);

	/**
	 * Used internally to prepare contents to be displayed on the screen
	 */
	public void preRender();

	/**
	 * Used internaly to display contents on the screen
	 */
	public void postRender();
}
