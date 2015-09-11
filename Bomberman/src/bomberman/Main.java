package bomberman;

import bomberman.sound.Sound;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Main extends JFrame implements ActionListener ,MouseListener
{
    private JPanel instructionPanel;
    private JPanel startPanel;
    private JPanel creditPanel;
    private JButton startButton;
    private JButton instructionButton;
    private JButton exitButton;
    private JButton backButton;
    private JButton backButton1;
    private JButton creditButton;
    private ImageIcon playIcon = new ImageIcon("res/play.png");
    private ImageIcon playActiveIcon = new ImageIcon("res/playactive.png");
    private ImageIcon instructionIcon = new ImageIcon("res/instruction.png");
    private ImageIcon instructionActiveIcon = new ImageIcon("res/instructionactive.png");
    private ImageIcon exitIcon = new ImageIcon("res/exit.png");
    private ImageIcon exitActiveIcon = new ImageIcon("res/exitactive.png");
    private ImageIcon backIcon = new ImageIcon("res/back.png");
    private ImageIcon backActiveIcon = new ImageIcon("res/backactive.png");
    private ImageIcon creditIcon = new ImageIcon("res/credit.png");
    private ImageIcon creditActiveIcon = new ImageIcon("res/creditactive.png");
    
    private Game game;
    private Sound startSound ;
    
    public Main()
    {
        startSound = new Sound("music/start.wav", true);
        startSound.play();
        initStartPanel();
        initInstructionPanel();
        initCreditPanel();
        this.add(startPanel);
        this.setSize(1216,600);
        this.setLayout(null);
        this.setTitle("BOMBERMAN WITH A*");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("res/boom.jpg").getImage());
	this.setLocationRelativeTo(null);
	this.setVisible(true);
    }
    
    public void initInstructionPanel()
    {
        instructionPanel = new JPanelImpl("res/InstructionPanel.png");
        backButton = new JButton(backIcon);
        backButton.setBounds(950, 20, 160, 50);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(this);
        backButton.addMouseListener(this);
        instructionPanel.add(backButton);
    }
    
    public void initStartPanel()
    {
        startPanel = new JPanelImpl("res/GameBackGround.jpg");
        startButton = new JButton(playIcon);
        startButton.setBounds(524, 300, 160, 50);
        startButton.addActionListener(this);
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.addMouseListener(this);
        
        startPanel.add(startButton);
        
        instructionButton = new JButton(instructionIcon);
        instructionButton.setBounds(524, 360, 160, 50);
        instructionButton.addActionListener(this);
        instructionButton.setBorderPainted(false);
        instructionButton.setContentAreaFilled(false);
        instructionButton.addMouseListener(this);
        startPanel.add(instructionButton);
        
        creditButton = new JButton(creditIcon);
        creditButton.setBounds(524, 420, 160, 50);
        creditButton.addActionListener(this);
        creditButton.setBorderPainted(false);
        creditButton.setContentAreaFilled(false);
        creditButton.addMouseListener(this);
        startPanel.add(creditButton);
        
        
        exitButton = new JButton(exitIcon);
        exitButton.setBounds(524, 480, 160, 50);
        exitButton.addActionListener(this);
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.addMouseListener(this);
        startPanel.add(exitButton);
    }
    
    public void initCreditPanel()
    {
        creditPanel = new JPanelImpl("res/creditpanel.jpg");
        backButton1 = new JButton(backIcon);
        backButton1.setBounds(950, 20, 160, 50);
        backButton1.setBorderPainted(false);
        backButton1.setContentAreaFilled(false);
        backButton1.addActionListener(this);
        backButton1.addMouseListener(this);
        creditPanel.add(backButton1);
        
    }
    
    public static void main(String[] args) 
    {
        new Main();
    }
    
    public void back()
    {
        startSound.play();
        this.remove(game);
        this.add(startPanel);
        this.validate();
        this.repaint();
        game.stop();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == exitButton)
        {
            JOptionPane.showMessageDialog(this, "Thanks for playing !", "Thanks for playing !", JOptionPane.PLAIN_MESSAGE);
            System.exit(1);
        }
        else if(e.getSource() == startButton)
        {
            startSound.stop();
            game = new Game();
            this.remove(startPanel);
            this.add(game);
            this.validate();
            this.repaint();
            game.start();
        }
        else if(e.getSource() == instructionButton )
        {
            this.remove(startPanel);
            this.add(instructionPanel);
            this.validate();
            this.repaint();
            
        }
        else if(e.getSource() == creditButton )
        {
            this.remove(startPanel);
            this.add(creditPanel);
            this.validate();
            this.repaint();
            
        }
        else if(e.getSource() == backButton)
        {
            this.remove(instructionPanel);
            this.add(startPanel);
            this.validate();
            this.repaint();
        }   
        else if(e.getSource() == backButton1)
        {
            this.remove(creditPanel);
            this.add(startPanel);
            this.validate();
            this.repaint();
        }   
        
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
         
    }

    @Override
    public void mouseEntered(MouseEvent e) 
    {
        if(e.getSource() == startButton )
            startButton.setIcon(playActiveIcon);
        else if(e.getSource() == instructionButton)
            instructionButton.setIcon(instructionActiveIcon);
        else if(e.getSource() == exitButton)
            exitButton.setIcon(exitActiveIcon);
        else if(e.getSource() == backButton)
            backButton.setIcon(backActiveIcon);
         else if(e.getSource() == backButton1)
            backButton1.setIcon(backActiveIcon);
        else if(e.getSource() == creditButton)
            creditButton.setIcon(creditActiveIcon);
        
    }

    @Override
    public void mouseExited(MouseEvent e) 
    {
        if(e.getSource() == startButton )
            startButton.setIcon(playIcon);
        else if(e.getSource() == instructionButton)
            instructionButton.setIcon(instructionIcon);
        else if(e.getSource() == exitButton)
            exitButton.setIcon(exitIcon);
        else if(e.getSource() == backButton)
            backButton.setIcon(backIcon);
        else if(e.getSource() == backButton1)
            backButton1.setIcon(backIcon);
        else if(e.getSource() == creditButton)
            creditButton.setIcon(creditIcon);
    }

    private static class JPanelImpl extends JPanel 
    {
        Image img;
        public JPanelImpl(String path) 
        {
            this.setSize(1216,576);
            this.setLayout(null);
            try {
            img = ImageIO.read(new File(path));
            } catch (IOException e) 
            {
            e.printStackTrace();
            }
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
}
