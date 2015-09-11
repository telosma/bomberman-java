
package score;

import bomberman.graphics.Screen;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Score 
{
    private int score = 0;
    private int highScore = 0;
    private int level = 0;
    public int []pixels;
    String path;
    String pathFile = "HighScore.txt";
    
    public Score(String path)
    {
        this.path = path;
        load();
        readHighScore();
    }
    
    public void render(int score, Screen screen)
    {
        screen.renderScore(this);
    }
    
    private void readHighScore()
    {
        File file = new File(pathFile);
        if(file.exists() == true)
        {
            try {
                FileReader fr;
                fr = new FileReader(pathFile);
                BufferedReader br = new BufferedReader(fr);
                highScore = Integer.parseInt(br.readLine());
                if(highScore < 0)
                    highScore = 0;
                br.close();
            } catch (FileNotFoundException ex) {
                System.out.println("File không tồn tại");
            }
            catch(IOException ex)
            {
                System.out.println("Không đọc được file");
            }
            catch(NumberFormatException ex)
            {
                highScore = 0;
            }
        }
    }
    
    public void saveHighScore()
    {
        try {
            FileWriter fr = new FileWriter(pathFile);
            BufferedWriter bw = new BufferedWriter(fr);
            if(highScore == 0)
                bw.write("0");
            else
                bw.write(String.valueOf(highScore));
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void incScore()
    {
        score++;
        if(score > highScore){
            highScore = score;
        }
    }
    
    public void incLevel()
    {
        level++;
    }
    
    public int getScore()
    {
        return score;
    }
    
    public int getHighScore()
    {
        return highScore;
    }
    
    public int getLevel()
    {
        return level;
    }
    
    public void reset()
    {
        score = 0;
    }
    
    private void load() {
		try {
			BufferedImage image = ImageIO.read(Score.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
                        pixels = new int[w * h];
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
