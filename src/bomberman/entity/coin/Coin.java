package bomberman.entity.coin;

import bomberman.entity.Entity;
import bomberman.graphics.Screen;
import bomberman.graphics.Sprite;
import bomberman.level.Level;

public class Coin extends Entity {
	
	private int anim;
	private boolean eaten;
	
	public Coin(int x, int y, Sprite sprite, Level level) {
		this.x = x << 6;
		this.y = y << 6;
		this.sprite = sprite;
		this.level = level;
		anim = 0;
		eaten = false;
	}
	
	public void update() {
		if(anim < 7500) anim++;
		else anim = 0;
	}
	
	public void render(Screen screen) {

	    if(anim % 90 > 80) {
	    	sprite = Sprite.coin_9;
	    }
	    else if(anim % 90 > 70) {
			sprite = Sprite.coin_8;
		}
	    else if(anim % 90 > 60) {
			sprite = Sprite.coin_7;
		}
		else if(anim % 90 > 50) {
			sprite = Sprite.coin_6;
		}
		else if(anim % 90 > 40) {
			sprite = Sprite.coin_5;
		}
		else if(anim % 90 > 30) {
			sprite = Sprite.coin_4;
		}
		else if(anim % 90 > 20) {
			sprite = Sprite.coin_3;
		}
		else if(anim % 90 > 10) {
			sprite = Sprite.coin_2;
		}
		else {
			sprite = Sprite.coin_1;
		}
	    
	    screen.renderCoin(x >> 6, y >> 6, sprite);
	}
	
}
