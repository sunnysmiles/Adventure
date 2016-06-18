package game;

import engine.utils.AbstractArt;
import engine.utils.Screen;

public class Wall extends Entity {

	public Wall(GameVector position) {
		setSolid(true);
		getPosition().setX(position.getX());
		getPosition().setY(position.getY());
		setHeight(32);
		setWidth(32);
	}

	public void draw(Screen screen, GameVector offSet) {
		GameVector onScreen = getPosition().getOnScreen(offSet);
		AbstractArt.drawSquare(screen, onScreen.getIntX(), onScreen.getIntY(),
				32, 32, 0xff0000ff);
	}

}
