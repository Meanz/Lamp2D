package com.meanworks.lamp2d;

public class AnimatedSprite {

	/**
	 * The frames of this animated sprite
	 */
	private LampImage[] frames;

	/**
	 * The current frame of this animated sprite
	 */
	private int currentFrame;

	/**
	 * The animation speed of this animated sprite
	 */
	private double animationSpeed;

	/**
	 * The position of this animation pos > Config.targetUps -> next frame
	 */
	private double animationPos;

	/**
	 * 
	 * @param frames
	 * @param animationSpeed
	 */
	public AnimatedSprite(LampImage[] frames, double animationSpeed) {
		this.frames = frames;
		this.animationSpeed = animationSpeed;
	}

	/**
	 * 
	 * @param other
	 */
	public AnimatedSprite(AnimatedSprite other) {
		frames = new LampImage[other.frames.length];
		int idx = 0;
		for (LampImage spr : other.frames) {
			frames[idx++] = new LampImage(spr);
		}
		this.animationSpeed = other.animationSpeed;
	}

	/**
	 * Copies this AnimatedSprite making another unique AnimatedSprite
	 * 
	 * @return
	 */
	public AnimatedSprite copy() {
		return new AnimatedSprite(this);
	}

	/**
	 * Creates another instance of this animated sprite, any changes done to
	 * this animated sprites will reflect on the other sprite excluding instance
	 * specific values, such as animation speed and frame index
	 * 
	 * @return
	 */
	public AnimatedSprite instance() {
		return new AnimatedSprite(this.frames, this.animationSpeed);
	}

	/**
	 * 
	 * @param frames
	 */
	public AnimatedSprite(LampImage[] frames) {
		this(frames, Game.getConfig().targetUps / 10);
	}

	/**
	 * Cool hack
	 */
	public void flipFrames() {
		for (LampImage spr : frames) {
			spr.flipHorizontally();
		}
	}

	/**
	 * Get the current sprite that this animated sprite is on
	 * 
	 * @return
	 */
	public LampImage getCurrentSprite() {
		return frames[currentFrame];
	}

	/**
	 * Set the current frame of this animated sprite
	 * 
	 * @param frameIdx
	 */
	public void setCurrentFrame(int frameIdx) {
		if (frameIdx < 0 || frameIdx >= frames.length) {
			return;
		}
		currentFrame = frameIdx;
	}

	/**
	 * The current frame of this animated sprite
	 * 
	 * @return
	 */
	public int getCurrentFrame() {
		return currentFrame;
	}

	/**
	 * Get the animation speed for this animated sprite
	 * 
	 * @return
	 */
	public double getAnimationSpeed() {
		return animationSpeed;
	}

	/**
	 * Set the animation speed for this animated sprite
	 * 
	 * @param animationSpeed
	 */
	public void setAnimationSpeed(double animationSpeed) {
		if (animationSpeed < 0)
			return;
		this.animationSpeed = animationSpeed;
	}

	/**
	 * Updates this animated sprite
	 */
	public void update() {
		animationPos += animationSpeed;
		if (animationPos >= Game.getConfig().targetUps) {
			currentFrame++;
			if (currentFrame >= frames.length) {
				currentFrame = 0;
			}
			animationPos -= Game.getConfig().targetUps;
		}
	}
}
