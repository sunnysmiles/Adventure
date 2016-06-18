package game;

import engine.utils.Vector;

public class GameVector extends Vector {

	public GameVector(float x, float y) {
		super(x, y);
	}
	
	public void setOnScreen(GameVector offSet){
		this.setX(this.getX() + offSet.getX());
		this.setY(this.getY() + offSet.getY());
	}
	
	public GameVector getOnScreen(GameVector offSet){
		GameVector ret = new GameVector(getX(), getY());
		ret.setOnScreen(offSet);
		return ret;
	}

}
