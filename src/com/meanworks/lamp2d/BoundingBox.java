package com.meanworks.lamp2d;

import java.awt.Point;

public class BoundingBox {

	private Point p1;
	private Point p2;
	
	public BoundingBox(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public BoundingBox(int x, int y, int width, int height) {
		this(new Point(x, y), new Point(x + width, y + height));
	}
	
	public boolean intersects(BoundingBox other) {
		if(other.p2.x < p1.x)
			return false;
		if(other.p2.y < p1.y)
			return false;
		if(other.p1.x > p2.x)
			return false;
		if(other.p1.y > p2.y)
			return false;
		return true;
	}
	
	public boolean intersects(Point other) {
		if(other.x >= p1.x && other.x <= p2.x) {
			if(other.y >= p1.y && other.y <= p2.y) {
				return true;
			}
		}
		return false;
	}
	
}
