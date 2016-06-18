package game;

import engine.utils.Screen;

public abstract class Entity {
	private boolean solid;
	private GameVector position;
	private float width;
	private float height;
	
	public Entity(){
		position = new GameVector(0,0);
		width = 0;
		height = 0;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	
	public void tick(){
	}
	
	public abstract void draw(Screen screen, GameVector offSet);

	public GameVector getPosition() {
		return position;
	}

	public void setPosition(GameVector position) {
		this.position = position;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
}
