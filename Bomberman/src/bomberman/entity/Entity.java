package bomberman.entity;

import bomberman.graphics.Screen;
import bomberman.graphics.Sprite;
import bomberman.level.Level;

public class Entity {
	
	protected int x, y;
	public boolean removed;
	protected Sprite sprite;
	protected Level level;
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void update() {
		
	}
	
	public void remove() {
		
	}
	
	public void render(Screen screen) {
		
	}

}
