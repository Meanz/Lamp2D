package com.meanworks.lamp2d;

import java.awt.Color;
import java.awt.Font;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class LWJGLRenderer implements Renderer {
	/** The java.awt font we're going to test */
	private java.awt.Font awtFont;
	/** The True Type font we're going to use to render */
	private TrueTypeFont font;

	public LWJGLRenderer(Game game) {
		awtFont = new java.awt.Font("Verdana", Font.PLAIN, 12);
		font = new TrueTypeFont(awtFont, false);
	}

	@Override
	public void draw(AnimatedSprite sprite, int x, int y) {
		// TODO Auto-generated method stub
		draw(sprite.getCurrentSprite(), x, y);
	}

	@Override
	public void draw(LampImage sprite, int x, int y) {
		// TODO Auto-generated method stub
		sprite.getTexture().draw(x, y);
	}

	@Override
	public void drawString(String s, int x, int y) {
		// TODO Auto-generated method stub
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// font.drawString(x, y, s);

		font.drawString(x, y, s, Color.white);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		GL11.glBegin(GL11.GL_LINES);
		{
			GL11.glVertex2f(x1, y1);
			GL11.glVertex2f(x2, y2);
		}
		GL11.glEnd();
	}

	@Override
	public void drawRect(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		// Draw 4 lines
		drawLine(x, y, x + width, y);
		drawLine(x, y, x, y + height);
		drawLine(x + width, y + height, x + width, y);
		drawLine(x + width, y + height, x, y + height);
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glVertex2f(x, y + height);
			GL11.glVertex2f(x + width, y + height);
			GL11.glVertex2f(x + width, y);
			GL11.glVertex2f(x, y);
		}
		GL11.glEnd();
	}

	@Override
	public void setFont(Font _font) {
		// TODO Auto-generated method stub
		font = new TrueTypeFont(_font, false);
	}

	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub
		GL11.glColor3f(color.getRed() / 255f, color.getGreen() / 255f,
				color.getBlue() / 255f);
	}

	@Override
	public void preRender() {
		// TODO Auto-generated method stub
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		// Setup ortho
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluOrtho2D(0, Display.getWidth(), Display.getHeight(), 0);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	@Override
	public void postRender() {
		// TODO Auto-generated method stub

	}

}
