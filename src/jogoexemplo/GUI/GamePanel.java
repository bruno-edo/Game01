

package jogoexemplo.GUI;

import GameState.GameStateManager;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable, KeyListener
{
    //Dimensions
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;
    
    //game thread
    private Thread thread;
    private boolean running;
    private final int TPS = 60; 
    private final long OPTIMAL_TIME = 1000000000 / TPS;  
    
    //image
    private BufferedImage image;
    private Graphics2D g2d;
    
    //game state manager
    private GameStateManager gsm;
    
    
    public GamePanel()
    {
        super();
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
    }
    
    private void init()
    {
        image = new BufferedImage(WIDTH, HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        
        g2d = (Graphics2D) image.getGraphics();
        
        running = true;
        
        gsm = new GameStateManager();
    }
    
    public void addNotify()
    {
        super.addNotify();
        if(thread == null)
        {
            thread = new Thread(this);
            this.addKeyListener(this);
            thread.start();
        }
    }

    @Override
    public void run() 
    {
        init();
        
        long lastLoop = System.nanoTime();
        int ticks =0;
        float delta = 0;
        
        //game loop
        while(running)
        {
            update();
            draw();
            drawToScreen();
            
            long current = System.nanoTime();
            long updateTime = current - lastLoop;
            lastLoop = current;
            delta += updateTime / 1000000000f;
            ticks++;
            
            if(delta >= 1)
            {
                System.out.println("ticks: "+ticks+" delta: "+delta);
                ticks=0;
                delta=0;
            }
            
            try
            {
                Thread.sleep((lastLoop - System.nanoTime() + OPTIMAL_TIME) /1000000);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    private void update()
    {
        gsm.update();
    }
    
    private void draw()
    {
        gsm.draw(g2d);
    }
    
    private void drawToScreen()
    {
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        gsm.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        gsm.keyReleased(e.getKeyCode());
    }
}
