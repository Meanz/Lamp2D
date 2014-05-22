package com.meanworks.lamp2d;

public interface MouseListener {

	public void mousePressed(int button, int x, int y);
	
	public void mouseReleased(int button, int x, int y);
	
	public void mouseMoved(int newX, int newY);
	
}
