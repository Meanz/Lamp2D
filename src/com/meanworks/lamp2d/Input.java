package com.meanworks.lamp2d;

import java.util.LinkedList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Input {

	/**
	 * The singleton object for this Input class
	 */
	private static Input singleton;

	/**
	 * The synchronization object, or lock if you will
	 */
	private Object syncObject;

	/**
	 * The keys variables
	 */
	private LinkedList<Integer> keysPressed;
	private LinkedList<Integer> keysHeld;
	private LinkedList<Integer> keysReleased;

	/**
	 * The mouse variables
	 */
	private int mouseX;
	private int mouseY;
	private int mouseDX;
	private int mouseDY;
	private LinkedList<Integer> mouseKeysPressed;
	private LinkedList<Integer> mouseKeysHeld;
	private LinkedList<Integer> mouseKeysReleased;

	/**
	 * The mouse listeners
	 */
	private LinkedList<com.meanworks.lamp2d.MouseListener> mouseListeners;

	/**
	 * 
	 */
	public Input() {
		if (singleton != null) {
			throw new RuntimeException(
					"Attempted to create two instances of the singleton class Input.");
		}

		// Initialize the lists
		keysPressed = new LinkedList<Integer>();
		keysHeld = new LinkedList<Integer>();
		keysReleased = new LinkedList<Integer>();
		mouseKeysPressed = new LinkedList<Integer>();
		mouseKeysHeld = new LinkedList<Integer>();
		mouseKeysReleased = new LinkedList<Integer>();
		mouseListeners = new LinkedList<com.meanworks.lamp2d.MouseListener>();

		// Create our synchronization object
		syncObject = new Object();

		// Assign the singleton
		singleton = this;

		// Create mouse and keyboard
		try {
			Mouse.create();
			Keyboard.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Add a mouse listener to this input
	 * 
	 * @param ml
	 */
	public static void addMouseListener(com.meanworks.lamp2d.MouseListener ml) {
		singleton.mouseListeners.add(ml);
	}

	/**
	 * Remove a mouse listener from this input
	 * 
	 * @param ml
	 */
	public static void removeMouseListener(com.meanworks.lamp2d.MouseListener ml) {
		singleton.mouseListeners.remove(ml);
	}

	/**
	 * Called the process the input data
	 */
	public void update() {
		synchronized (syncObject) {
			/*
			 * Update mouse related stuff
			 */
			mouseDX = Mouse.getDX();
			mouseDY = Mouse.getDY(); // Upwards would be neg, so is fine
			mouseX = Mouse.getX();
			mouseY = Display.getHeight() - Mouse.getY();

			if (mouseDX != 0 || mouseDY != 0) {
				for (com.meanworks.lamp2d.MouseListener ml : mouseListeners) {
					ml.mouseMoved(mouseX, mouseY);
				}
			}

			// Clear all old information, this information only last for 1 tick
			for (Integer key : mouseKeysPressed) {
				mouseKeysHeld.push(key);
			}
			mouseKeysPressed.clear();
			mouseKeysReleased.clear();

			// Update Mouse information
			while (Mouse.next()) {

				int button = Mouse.getEventButton();
				boolean state = Mouse.getEventButtonState();
				int evX = Mouse.getEventX();
				int evY = Display.getHeight() - Mouse.getEventY();

				if (button == -1)
					continue;

				if (state) {
					for (com.meanworks.lamp2d.MouseListener ml : mouseListeners) {
						ml.mousePressed(button, evX, evY);
					}
					mouseKeysPressed.push(button);
				} else {
					for (com.meanworks.lamp2d.MouseListener ml : mouseListeners) {
						ml.mouseReleased(button, evX, evY);
					}
					mouseKeysHeld.remove((Object) button);
					mouseKeysReleased.push(button);
				}

			}

			// Clear all old information, this information only last for 1 tick
			for (Integer key : keysPressed) {
				keysHeld.push(key);
			}
			keysPressed.clear();
			keysReleased.clear();

			// Update Keyboard information
			while (Keyboard.next()) {

				int key = Keyboard.getEventKey();
				boolean state = Keyboard.getEventKeyState();
				
				if (state) {

					// Keylistener -> Push

					keysPressed.push(key);

				} else {

					// Keylistener -> Push

					keysHeld.remove((Object) key);
					keysReleased.push(key);
				}

			}
		}
	}

	/**
	 * Check if the given key code is pressed
	 * 
	 * @param keyCode
	 * @return
	 */
	public static boolean isKeyPressed(int keyCode) {
		synchronized (singleton.syncObject) {
			for (Integer i : singleton.keysPressed) {
				if (i == keyCode) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check if the given key code is being held down
	 * 
	 * @param keyCode
	 * @return
	 */
	public static boolean isKeyDown(int keyCode) {
		synchronized (singleton.syncObject) {
			for (Integer i : singleton.keysHeld) {
				if (i == keyCode) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check if the given key code is being released
	 * 
	 * @param keyCode
	 * @return
	 */
	public static boolean isKeyReleased(int keyCode) {
		synchronized (singleton.syncObject) {
			for (Integer i : singleton.keysReleased) {
				if (i == keyCode) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check if the given mouse button is being pressed
	 * 
	 * @param keyCode
	 * @return
	 */
	public static boolean isMousePressed(int button) {
		synchronized (singleton.syncObject) {
			for (Integer i : singleton.mouseKeysPressed) {
				if (i == button) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check if the given mouse button is being held
	 * 
	 * @param keyCode
	 * @return
	 */
	public static boolean isMouseDown(int button) {
		synchronized (singleton.syncObject) {
			for (Integer i : singleton.mouseKeysHeld) {
				if (i == button) {
					return true;
				}
			}
		}
		return false;
	}

}
