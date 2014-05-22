package com.meanworks.lamp2d;

/**
 * An enum describing a direction
 * 
 * @author Meanz
 * 
 */
public enum Direction {

	LEFT(0), UP(1), RIGHT(2), DOWN(3)

	;

	private int id;

	private Direction(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
