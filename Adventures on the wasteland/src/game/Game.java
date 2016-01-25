package game;

import engine.client.AbstractGame;
import engine.utils.Screen;
import engine.utils.SunnyFrame;

public class Game extends AbstractGame {

	int testOutputCounter = 0;
	private Screen screen;

	// Instantiate and send to sunnyframe
	public static void main(String[] args) {
		// new SunnyFrame(new Game());
		new Thread(new Game()).start();
	}

	public void init() {
		screen = this.getScreen();
		this.setSize(2);
		this.setDisplayFPS(true);
	}

	public void update() {
		// Static visual test output
		int[] pixels = screen.getPixels();
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xffff00 + i * 200;
		}

		// Static non-visual test output
		testOutputCounter++;
		System.out.println("Test output, number of updates = " + testOutputCounter);
	}

}
