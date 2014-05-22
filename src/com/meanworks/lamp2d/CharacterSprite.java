package com.meanworks.lamp2d;

public class CharacterSprite {

	/**
	 * The left sprite
	 */
	private AnimatedSprite charLeft;
	/**
	 * The up sprite
	 */
	private AnimatedSprite charUp;
	/**
	 * The right sprite
	 */
	private AnimatedSprite charRight;
	/**
	 * The down sprite
	 */
	private AnimatedSprite charDown;
	/**
	 * The direction of this character sprite
	 */
	private Direction charDir;
	/**
	 * Whether this character sprite is moving or not
	 */
	private boolean isMoving;

	/**
	 * Construct a new CharacterSprite
	 * 
	 * @param left
	 * @param up
	 * @param right
	 * @param down
	 */

	public CharacterSprite(AnimatedSprite left, AnimatedSprite up,
			AnimatedSprite right, AnimatedSprite down) {
		this.charLeft = left;
		this.charUp = up;
		this.charRight = right;
		this.charDown = down;
		this.charDir = Direction.DOWN;
	}

	/**
	 * Check whether this character sprite is moving or not
	 * 
	 * @return
	 */
	public boolean isMoving() {
		return isMoving;
	}

	/**
	 * Set whether this character sprite is moving or not
	 * 
	 * @param moving
	 */
	public void setMoving(boolean moving) {
		this.isMoving = moving;
		if (!moving) {
			// Reset frame status
			if (charDir == Direction.LEFT) {
				charLeft.setCurrentFrame(0);
			} else if (charDir == Direction.UP) {
				charUp.setCurrentFrame(0);
			} else if (charDir == Direction.RIGHT) {
				charRight.setCurrentFrame(0);
			} else if (charDir == Direction.DOWN) {
				charDown.setCurrentFrame(0);
			}
		}
	}

	/**
	 * Get the direction of this character sprite
	 * 
	 * @return The direction of this sprite
	 */
	public Direction getDirection() {
		return charDir;
	}

	/**
	 * Set the direction of this CharacterSprite
	 * 
	 * @param dir
	 */
	public void setDirection(Direction dir) {
		if (dir == charDir) {
			return; // Nothing is changed
		}
		this.charDir = dir;
	}

	/**
	 * Update the sprite
	 */
	public void update() {
		if (!isMoving) {
			return;
		}
		if (charDir == Direction.LEFT) {
			charLeft.update();
		} else if (charDir == Direction.UP) {
			charUp.update();
		} else if (charDir == Direction.RIGHT) {
			charRight.update();
		} else if (charDir == Direction.DOWN) {
			charDown.update();
		}
	}

	/**
	 * Draw the sprite
	 * 
	 * @param renderer
	 */
	public void draw(Renderer renderer, int x, int y) {
		if (charDir == Direction.LEFT) {
			renderer.draw(charLeft, x, y);
		} else if (charDir == Direction.UP) {
			renderer.draw(charUp, x, y);
		} else if (charDir == Direction.RIGHT) {
			renderer.draw(charRight, x, y);
		} else if (charDir == Direction.DOWN) {
			renderer.draw(charDown, x, y);
		}
	}

}
