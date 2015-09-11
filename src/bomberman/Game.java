package bomberman;

import bomberman.entity.mob.Player;
import bomberman.font.Font;
import bomberman.graphics.Screen;
import bomberman.input.Keyboard;
import bomberman.level.Level;
import bomberman.sound.Sound;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import score.Score;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
        JPanel panel = new JPanel(null);
        
	private int width = 1216, height = 576;
	private int scale = 1;
	private Dimension size;
	private String title = "Bomberman";
	private Thread thread;
	private boolean running = false;
	private int levelCounter = 0;
	
	private Screen screen;
	private Keyboard input;
	private Player player;
	private Level level;
	private Font font;
	private Font temp;
        public static Score score;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        private Sound playSound = new Sound("music/play.wav",true);
        
	
	public Game() {
                panel.setBounds(0, 0, 960, 576);
                panel.setBackground(Color.red);
                panel.setLayout(null);
		size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		this.setSize(size);
                screen = new Screen(width, height);
		input = new Keyboard();
                score = new Score("/score.png");
		setLevel();
		font = Font.completed;
		temp = new Font(960 + (1 * 32 / 2), 0, "A");
		addKeyListener(input);
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
                playSound.play();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		
		while(running) 
                {
                    if(input.p == false)
                    {
                        long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}
			
			render();
			frames++;
			
			if((System.currentTimeMillis() - timer) > 1000) 
                        {
				
                            frames = 0;
                            updates = 0;
                            timer += 1000;
			}
                    }
                    else
                    {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                        BufferStrategy bs = getBufferStrategy();
                        Graphics g = bs.getDrawGraphics();
                        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                        g.dispose();
                        bs.show();
                    }
			
                }
	}
	
	public void update() 
        {	
            input.update();
            if(player.isAlive)
            {
                level.update();
                player.update();
                if( level.creep.size() < level.creeps )
                {
                    level.rebornCreeps();
                }
            }
            else
                player.clear();
            if(input.esc == true)
            {
                playSound.stop();
                input.getKeys()[KeyEvent.VK_ESCAPE] = false;
                JFrame frame =    (JFrame)(SwingUtilities.getWindowAncestor(this));
                Main main1 = (Main)frame;
                main1.back();
            }
            
	}
        
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) 
                {
                    createBufferStrategy(3);
                    return;
		}
		
		if(level.isComplete())
                {
                    level.setComplete(false);
                    screen.clear();
                    level.render(screen, player);
                    player.render(screen);
                    font = Font.completed;
                    font.render(screen);
                    setLevel();
		}
		else if(player.isAlive) 
                {
                    screen.clear();
                    level.render(screen, player);
                    player.render(screen);
		}
		else 
                {
                    screen.clear();
                    level.render(screen, player);
                    player.render(screen);
                    font = Font.gameover;
                    font.render(screen);
                    score.saveHighScore();
		}
                
                score.render(1, screen);
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	private void setLevel() {
                Game.score.incLevel();
		level = new Level("/textures/levels/Level" + Game.score.getLevel() + ".png", Game.score.getLevel());
		level.addCreeps();
                level.addCoin();
		player = new Player(1, 1, input, level);
	}
}
