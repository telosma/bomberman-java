package bomberman.entity.creep;


/*
 * dang code do line 72
 */
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;

import bomberman.entity.mob.Mob;
import bomberman.entity.mob.Player;
import bomberman.graphics.Screen;
import bomberman.graphics.Sprite;
import bomberman.level.Level;
import bomberman.pathfinding.Node;
import bomberman.pathfinding.PathFinding;
import bomberman.sound.Sound;

public class Creep extends Mob {
	public static final int SPEED_CREEP = 1;
	private Random random;
	private int anim, xa, ya, creepDir, counter, flip;
	
	public Creep(int x, int y, Sprite sprite, Level level) {
		this.x = x << 6;
		this.y = y << 6;
		this.sprite = sprite;
		this.level = level;
		creepDir = 0;
		counter = 0;
		anim = 0;
		removed = false;
		flip = -1;
		random = new Random();
	}
	
	public void update(Player player) {	
		xa = 0;
		ya = 0;
		
		if(anim < 7500) anim++;
		else anim = 0;
		
		updateCreepDir();
        generateDir(player);
		
		for(int i = 0; i < Level.flame.size(); i++) {
			if(Level.flame.get(i).flameCollision(x + 20 >> 6, y + 10 >> 6)) {
                                new Sound("music/enemydown.wav", false).play();
				removed = true;
				break;
			}
		}
		
	
		
	}
	
	// Tim bang thuat toan A*
	private void generateDir(Player player)	{
		
		PathFinding pathFinding = new PathFinding(level);
		 
		int goalX = player.getX();
		int goalY = player.getY();
 
		if (x % sprite.SIZE != 0 || y % sprite.SIZE != 0)
		{
			//System.out.println(x + "," + y);
			move(xa, ya);
			return;
		}
 
		ArrayList<Node> arrList = 
				pathFinding.doAlgorithmAStar(level, x, y, goalX, goalY);
 
 
		// Khong tim thay duong
		if (arrList.isEmpty()) {
			System.out.println("Khong tim thay duong di, cho creep di random");
			generateRandomDir();
			return;
		}
 
		// Co tim thay duong
		else
		{
			System.out.println("Da tim thay duong");
			Point nextStep = pathFinding.nextStep(arrList.get(arrList.size() - 1));
			if (nextStep.x > (x >> 6))
			{
				creepDir = 1;
			}
			else if (nextStep.x < (x >> 6))
			{
				creepDir = 3;
			}
			else // nextStep.x == x
			{
				if (nextStep.y > (y >> 6))
				{
					creepDir = 2;
				}
				else if (nextStep.y < (y >> 6))
				{
					creepDir = 0;
				}
			}
			xa = 0;
			ya = 0;
			updateCreepDir();
			move(xa, ya);
		}
	}
	
	private void generateRandomDir() {
		if(x % sprite.SIZE == 0 && y % sprite.SIZE == 0) {
			if(counter < 7500) counter++;
			else counter = 0;
		}
		
		if(tileCollision(xa, ya) || counter % 4 == 0) creepDir = random.nextInt(4);
		else move(xa, ya);
	}
	
	public void updateCreepDir() {
		if(creepDir == UP) ya -= SPEED_CREEP;
		else if(creepDir == RIGHT) xa += SPEED_CREEP;
		else if(creepDir == DOWN) ya += SPEED_CREEP;
		else xa -= SPEED_CREEP;
	}
	
	public void render(Screen screen) {
		if(dir == 0) {
			flip = -1;
			if(anim % 60 > 50) {
				sprite = Sprite.creep_back_6;
			}else if(anim % 60 > 40) {
				sprite = Sprite.creep_back_5;
			}else if(anim % 60 > 30) {
				sprite = Sprite.creep_back_4;
			}else if(anim % 60 > 20) {
				sprite = Sprite.creep_back_3;
			}else if(anim % 60 > 10) {
				sprite = Sprite.creep_back_2;
			}else {
				sprite = Sprite.creep_back_1;
			}
		}else if(dir == 1) {
			flip = -1;
			if(anim % 60 > 50) {
				sprite = Sprite.creep_side_6;
			}else if(anim % 60 > 40) {
				sprite = Sprite.creep_side_5;
			}else if(anim % 60 > 30) {
				sprite = Sprite.creep_side_4;
			}else if(anim % 60 > 20) {
				sprite = Sprite.creep_side_3;
			}else if(anim % 60 > 10) {
				sprite = Sprite.creep_side_2;
			}else {
				sprite = Sprite.creep_side_1;
			}
		}else if(dir == 2) {
			flip = -1;
			if(anim % 60 > 50) {
				sprite = Sprite.creep_forward_6;
			}else if(anim % 60 > 40) {
				sprite = Sprite.creep_forward_5;
			}else if(anim % 60 > 30) {
				sprite = Sprite.creep_forward_4;
			}else if(anim % 60 > 20) {
				sprite = Sprite.creep_forward_3;
			}else if(anim % 60 > 10) {
				sprite = Sprite.creep_forward_2;
			}else {
				sprite = Sprite.creep_forward_1;
			}
		}else {
			flip = 3;
			if(anim % 60 > 50) {
				sprite = Sprite.creep_side_6;
			}else if(anim % 60 > 40) {
				sprite = Sprite.creep_side_5;
			}else if(anim % 60 > 30) {
				sprite = Sprite.creep_side_4;
			}else if(anim % 60 > 20) {
				sprite = Sprite.creep_side_3;
			}else if(anim % 60 > 10) {
				sprite = Sprite.creep_side_2;
			}else {
				sprite = Sprite.creep_side_1;
			}
		}
		
		screen.renderCreeps(x >> 64, y >> 64, sprite, flip);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
