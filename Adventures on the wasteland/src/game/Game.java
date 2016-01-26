package game;

import engine.client.AbstractGame;
import engine.utils.AbstractArt;
import engine.utils.Rectangle;
import engine.utils.Screen;
import engine.utils.SunnyFrame;

public class Game extends AbstractGame {

	int testOutputCounter = 0;
	private Screen screen;

	// Instantiate and send to sunnyframe
	public static void main(String[] args) {
		new SunnyFrame(new Game());
		// new Thread(new Game()).start();
	}

	public void init() {
		screen = this.getScreen();
		this.setSize(2);
		this.setDisplayFPS(true);

	}

	public void update() {
		//Draw the background
		AbstractArt.drawSquare(screen, 0, 0, this.getPixelsWidth(), this.getPixelsHeight(), 0xff000000);
		
		// Static visual test output
		this.setPixelsHeight(320);
		this.setPixelsWidth(320);
		screen = this.getScreen();
		int numberOfColumns = 10;
		int numberOfRows = 10;
		int rectangleWidth = 32;
		int rectangleHeight = 32;

		for (int ci = 0; ci < numberOfColumns; ci++) {
			for (int ri = 0; ri < numberOfRows; ri++) {
				AbstractArt.drawRect(screen, new Rectangle(rectangleWidth * ci,
						rectangleHeight * ri, rectangleWidth, rectangleHeight),
						0xff550000);
			}
		}

		// Static non-visual test output
//		testOutputCounter++;
//		System.out.println("Test output, number of updates = "
//				+ testOutputCounter);
	}

}
