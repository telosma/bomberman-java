package bomberman.entity.mob;

import bomberman.entity.Entity;
import bomberman.graphics.Screen;
import bomberman.level.Level;

public class Mob extends Entity {
	
	protected int dir = 2;
	public boolean isAlive;
	public static final int UP = 0;
        public static final int LEFT = 3;
        public static final int DOWN = 2;
        public static final int RIGHT = 1;
        
	public void move(int xa, int ya)
        {
		if(xa != 0 && ya != 0)
                {
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		if(xa > 0) dir = 1;
		if(xa < 0) dir = 3;
		if(ya > 0) dir = 2;
		if(ya < 0) dir = 0;
		
		if(!tileCollision(xa, ya))
                {
			x += xa;
			y += ya;
		}	
	}
	
	public void shoot() {
		
	}
	
	public void update() {
		
	}
	
	public boolean tileCollision(int xa, int ya) {
		//boolean solid = false;
		int x1 = 0, y1 = 0, x2 = 0, y2 = 0, xx = x + 5, yy = y + 5;
                
		if(dir == UP)
                {       //Kiểm tra 2 góc trên.
			x1 = (xx + xa) >> 6;
			y1 = (yy + ya) >> 6;
			x2 = (x + xa + 58) >> 6;
			y2 = (y + ya) >> 6;
		}else if(dir == RIGHT){
                        //Kiểm tra 2 góc phải
			x1 = (xx + xa + 58) >> 6;
			y1 = (yy + ya) >> 6;
			x2 = (xx + xa + 58) >> 6;
			y2 = (yy + ya + 58) >> 6;
		}else if(dir == DOWN) {
                        //Kiểm tra 2 góc dưới
			x1 = (xx + xa) >> 6;
			y1 = (yy + ya + 58) >> 6;
			x2 = (xx + xa + 58) >> 6;
			y2 = (yy + ya + 58) >> 6;
		}else {
                        //Kiểm tra 2 góc trái
			x1 = (x + xa) >> 6;
			y1 = (yy + ya) >> 6;
			x2 = (x + xa) >> 6;
			y2 = (yy + ya + 58) >> 6;
		}
		
		if(level.getTile(x1, y1).solid() || level.getTile(x2, y2).solid())
                {
                    return true;
                }
                
                int bombSize = Level.bomb.size();
                for (int i = 0; i < bombSize; i++) {
                    int bombX = Level.bomb.get(i).getX();
                    int bombY = Level.bomb.get(i).getY();
                    if((x + 32) >> 6 == (bombX + 32) >> 6 && (y + 32) >> 6 == (bombY + 32) >> 6)
                    {
                        return false;
                    }
                    else
                        if ((bombX >> 6 == x1 && bombY >> 6 == y1) ||
                            (bombX >> 6 == x2 && bombY >> 6 == y2))
                            return true;
                }
		
		return false;
	}

}
