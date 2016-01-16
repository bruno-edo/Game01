/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import GameState.GameStateManager;
import javax.swing.JFrame;
import Main.GUI.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Avell-MS
 */
public class MainClass implements Runnable
{

    //game thread
    private Thread thread;
    private boolean running;
    private final int FPS = 60;
    private final long TARGET_FPS_TIME = 1000000000 / FPS;
    
    //Game screen
    private JFrame window;
    private GamePanel gamePanel;
    
    //game state manager
    private GameStateManager gsm;
    
    public static void main(String[] args) 
    {
        new MainClass();      
    }

    private MainClass() 
    {
        running = false;
        this.init();
    }
    
    public void init()
    {
        gamePanel = new GamePanel(this);
        gamePanel.init();
        
        window = new JFrame("Zelda jam game");
        window.setContentPane(gamePanel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
        
        gsm = new GameStateManager();
                
        if(thread == null)
        {
            thread = new Thread(this);
            running = true;
            thread.start();
        }
    }
    
    @Override
    public void run() 
    {        
        long lastLoop = System.nanoTime();
        int ticks = 0;
        int fps = 0;
        float delta = 0;
        
        //game loop
        while(running)
        {
            update();
            draw();
            gamePanel.drawToScreen();
            
            long current = System.nanoTime();
            long updateTime = current - lastLoop;
            lastLoop = current;
            delta += updateTime / 1000000000f;
            ticks++;
            fps++;
            
            if(delta >= 1)
            {
                System.out.println("ticks: "+ticks+"  fps: "+fps+"  delta: "+delta);
                ticks = 0;
                fps = 0;
                delta = 0;
            }
            
            try
            {
                Thread.sleep((lastLoop - System.nanoTime() + TARGET_FPS_TIME) /1000000);
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
        gsm.draw(gamePanel.getG2d());
    }
    
    private void shutdown()
    {
        running = false;
    }

    public void keyTyped(KeyEvent e) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void keyPressed(KeyEvent e) 
    {
        gsm.keyPressed(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) 
    {
        gsm.keyReleased(e.getKeyCode());
    }
    
}
