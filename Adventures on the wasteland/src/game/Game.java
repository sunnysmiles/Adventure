package game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import engine.client.AbstractGame;
import engine.utils.AbstractArt;
import engine.utils.Input;
import engine.utils.Rectangle;
import engine.utils.Screen;
import engine.utils.SunnyFrame;

public class Game extends AbstractGame {

	public static final int TILE_SIZE = 32;
	int testOutputCounter = 0;
	private Screen screen;
	private GameVector playerPosition;
	private GameVector offSet;
	private Input input;
	private ArrayList<Entity> entities;

	// Instantiate and send to sunnyframe
	public static void main(String[] args) {
		new SunnyFrame(new Game());
		// new Thread(new Game()).start();
	}

	public void init() {
		screen = this.getScreen();
		this.setSize(2);
		this.setDisplayFPS(true);
		this.setPixelsHeight(352);
		this.setPixelsWidth(352);
		screen = this.getScreen();
		playerPosition = new GameVector(32, 32);
		offSet = new GameVector(0, 0);
		input = this.getInput();
		entities = new ArrayList<Entity>();
		
		// Import/Add game data
		entities.add(new Wall(new GameVector(64,64)));
	}

	public void update() {
		// Static non-visual test output
		// testOutputCounter++;
		// System.out.println("Test output, number of updates = "
		// + testOutputCounter);

		// Handle User input
		if (input.jPressed(KeyEvent.VK_A))
			playerPosition.addThis(new GameVector(-32, 0));
		if (input.jPressed(KeyEvent.VK_W))
			playerPosition.addThis(new GameVector(0, -32));
		if (input.jPressed(KeyEvent.VK_D))
			playerPosition.addThis(new GameVector(32, 0));
		if (input.jPressed(KeyEvent.VK_S))
			playerPosition.addThis(new GameVector(0, 32));

		// Calculate the offSet based on the players position
		if (playerPosition.getX() + offSet.getX() < (this.getPixelsWidth() - 32) / 2 - 32)
			offSet.setX((this.getPixelsWidth() - 32) / 2 - 32
					- playerPosition.getX());
		if (playerPosition.getX() + offSet.getX() > (this.getPixelsWidth() - 32) / 2 + 32)
			offSet.setX((this.getPixelsWidth() - 32) / 2 + 32
					- playerPosition.getX());
		if (playerPosition.getY() + offSet.getY() < (this.getPixelsHeight() - 32) / 2 - 32)
			offSet.setY((this.getPixelsHeight() - 32) / 2 - 32
					- playerPosition.getY());
		if (playerPosition.getY() + offSet.getY() > (this.getPixelsHeight() - 32) / 2 + 32)
			offSet.setY((this.getPixelsHeight() - 32) / 2 + 32
					- playerPosition.getY());
		draw();
	}

	private void draw() {
		// Draw the background
		AbstractArt.drawSquare(screen, 0, 0, this.getPixelsWidth(),
				this.getPixelsHeight(), 0xff000000);

		// Static visual test output
		int numberOfColumns = 11;
		int numberOfRows = 11;
		int rectangleWidth = 32;
		int rectangleHeight = 32;

		for (int ci = 0; ci < numberOfColumns; ci++) {
			for (int ri = 0; ri < numberOfRows; ri++) {
				AbstractArt.drawRect(screen, new Rectangle(rectangleWidth * ci,
						rectangleHeight * ri, rectangleWidth, rectangleHeight),
						0xff550000);
			}
		}

		// Draw the player;
		GameVector playerOnScreenPosition = playerPosition.getOnScreen(offSet);
		AbstractArt.drawSquare(screen, playerOnScreenPosition.getIntX(),
				playerOnScreenPosition.getIntY(), 32, 32, 0xff00ff00);
		
		// Draw All entities
		// TODO: Should draw only visible entities.
		for(Entity e : entities){
			e.draw(screen, offSet);
		}
	}

}
