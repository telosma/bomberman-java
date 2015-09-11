package bomberman.graphics;

import bomberman.Game;
import bomberman.font.Font;
import bomberman.level.tile.Tile;
import score.Score;


public class Screen {
	
	public int width;
	public int height;
	public int[] pixels;
	
	public Screen(int width, int height) 
        {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void renderTile(int xp, int yp, Tile tile) {
		for(int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp;
			for(int x = 0; x < tile.sprite.SIZE; x++)
                        {
				int xa = x + xp;
				if(xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break; 
				if(xa < 0) xa = 0;
				int col = tile.sprite.pixels[x + y * tile.sprite.SIZE];
				pixels[xa + ya * width] = col;
			}
		}
	}
	
	public void renderText(int xp, int yp, Font font)
        {
		for(int y = 0; y < 32; y++) {
			int ya = y + yp;
			for(int x = 0; x < font.SIZE; x++) {
				int xa = x + xp;
				int col = font.pixels[x + y * font.SIZE];
                                if(col != 0xFFFFFFFF)
				pixels[xa + ya * width] = col;
			}
		}
	}
        
        public void renderScore(Score score)
        {
            int k = 0;
            for(int y = 0; y < height; y++)
                for(int x = 960; x < width; x ++)
                {
                    pixels[x + y * width] = score.pixels[k++];
                }
            
            viewScore(16, 2, Game.score.getScore());
            viewScore(16, 4, Game.score.getHighScore());
            viewScore(16, 6, Game.score.getLevel());
        }
        
        private void viewScore(int x, int y, int score)
        {
            int num2 = score % 10;
            int num1 = score / 10;
            
            viewNumber(x, y, num1);
            viewNumber(x + 1, y, num2);
        }
        
        private void viewNumber(int x, int y, int number){
            switch(number)
            {
                case 0:
                    renderNumber(x, y, Sprite.num_0);
                    break;
                case 1:
                    renderNumber(x, y, Sprite.num_1);
                    break;
                case 2:
                    renderNumber(x, y, Sprite.num_2);
                    break;
                case 3:
                    renderNumber(x, y, Sprite.num_3);
                    break;
                case 4:
                    renderNumber(x, y, Sprite.num_4);
                    break;
                case 5:
                    renderNumber(x, y, Sprite.num_5);
                    break;
                case 6:
                    renderNumber(x, y, Sprite.num_6);
                    break;
                case 7:
                    renderNumber(x, y, Sprite.num_7);
                    break;                        
                case 8:
                    renderNumber(x, y, Sprite.num_8);
                    break;
                case 9:
                    renderNumber(x, y, Sprite.num_9);
                    break;
            }
        }
        
        public void renderNumber(int xp, int yp, Sprite sprite)
        {
            xp = xp << 6;
            yp = yp << 6;
		
            for(int y = 0; y < sprite.SIZE; y++) {
                    int ya = yp + y;
                    for(int x = 0; x < sprite.SIZE; x++) {
                            int xa = xp + x;
                            int col = sprite.pixels[x + y * sprite.SIZE];
                            pixels[xa + ya * width] = col;
                    }
            }
        }
	
	public void renderPlayer(int xp, int yp, Sprite sprite, int flip)
        {
		for(int y = 0; y < sprite.SIZE; y++)
                {
			int ya = yp + y;
			int ys = y;
			if(flip == 0 || flip == 2) ys = (sprite.SIZE - 1) - ys;
			for(int x = 0; x < sprite.SIZE; x++){
				int xa = xp + x;
				int xs = x;
				if(flip == 1 || flip == 3) xs = (sprite.SIZE - 1) - xs;
				if(x < -sprite.SIZE || x >= width || y < 0 || y >= height) break; 
				if(x < 0) x = 0;
				int col = sprite.pixels[xs + ys * sprite.SIZE];
				if(col == 0xFF870087) continue;
				pixels[xa + ya * width] = col;
			}
		}
	}
	
	public void renderBomb(int xp, int yp, Sprite sprite)
        {
		xp = xp << 6;
		yp = yp << 6;
		
		for(int y = 0; y < sprite.SIZE; y++) {
			int ya = yp + y;
			for(int x = 0; x < sprite.SIZE; x++) {
				int xa = xp + x;
				int col = sprite.pixels[x + y * sprite.SIZE];
				if(col == 0xFF870087) continue;
				pixels[xa + ya * width] = col;
			}
		}
	}
        
        public void renderCoin(int xp, int yp, Sprite sprite) {
		xp = xp << 6;
		yp = yp << 6;
		
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = yp + y;
			for (int x = 0; x < sprite.SIZE; x++)
                        {
				int xa = xp + x;
				int col = sprite.pixels[x + y * sprite.SIZE];
				if (col == 0xFF870087)
					continue;
				pixels[xa + ya * width] = col;
			}
		}
	}
	
	public void renderFlame(int xp, int yp, Sprite sprite) {
		xp = xp << 6;
		yp = yp << 6;
		
		for(int y = 0; y < sprite.SIZE; y++) {
			int ya = yp + y;
			for(int x = 0; x < sprite.SIZE; x++) {
				int xa = xp + x;
				int col = sprite.pixels[x + y * sprite.SIZE];
				if(col == 0xFF870087) continue;
				pixels[xa + ya * width] = col;
			}
		}
	}
	
	public void renderCreeps(int xp, int yp, Sprite sprite, int flip) {
		for(int y = 0; y < sprite.SIZE; y++) {
			int ya = yp + y;
			int ys = y;
			if(flip == 0 || flip == 2) ys = (sprite.SIZE - 1) - ys;
			for(int x = 0; x < sprite.SIZE; x++) {
				int xa = xp + x;
				int xs = x;
				if(flip == 1 || flip == 3) xs = (sprite.SIZE - 1) - xs;
				if(x < -sprite.SIZE || x >= width || y < 0 || y >= height) break; 
				if(x < 0) x = 0;
				int col = sprite.pixels[xs + ys * sprite.SIZE];
				if(col == 0xFF870087) continue;
				pixels[xa + ya * width] = col;
			}
		}
	}

}
