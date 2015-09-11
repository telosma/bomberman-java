package bomberman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean[120];
	public boolean up, down, left, right, space, enter,esc;
        public boolean p = false;
        
        public void update() 
        {
            up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
            down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
            left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
            right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
            space = keys[KeyEvent.VK_SPACE];
            enter = keys[KeyEvent.VK_ENTER];
            esc = keys[KeyEvent.VK_ESCAPE];
           
	}

	public void keyPressed(KeyEvent e) 
        {
            if(e.getKeyCode() == KeyEvent.VK_P)
            {
                p = !p;
                System.out.println(p);
            }
            else
                keys[e.getKeyCode()] = true;

	}

	public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() != KeyEvent.VK_P)
                keys[e.getKeyCode()] = false;

	}

	public void keyTyped(KeyEvent e) {
		
	}
        
        public boolean [] getKeys()
        {
            return keys;
        }

}
