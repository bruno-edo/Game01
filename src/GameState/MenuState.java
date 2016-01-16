

package GameState;

import java.awt.event.*;
import Maps.Background;
import java.awt.*;

public class MenuState extends GameState
{
    private Background bg;
    private GameStateManager gsm;
    private int currentChoice = 0;
    private String[] options = 
    {
        "Start",
        "Help",
        "Quit"
    };
    
    private Color titleColor;
    private Font titleFont;
    private Font font;
    
    public MenuState(GameStateManager gsm)
    {
        this.gsm = gsm;
        
        try
        {
            bg = new Background("/Backgrounds/menubg.gif", 0);
            bg.setVector(-0.1, 0);
            
            titleColor =  new Color(128, 0, 0);
            titleFont = new Font("Century Gothic", Font.PLAIN, 28);
            font = new Font("Arial", Font.PLAIN, 12);
        }
        
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
        
    @Override
    public void init() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update() 
    {
        bg.update();
    }

    @Override
    public void draw(Graphics2D g) 
    {
        //draw bg
        bg.draw(g);
        
        //draw title
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("Game LOKO!", 80, 70);
        
        //draw menu options
        g.setFont(font);
        for(int i = 0; i < options.length; i++)
        {
            if(i == currentChoice)
            {
                g.setColor(Color.BLACK);
            }
            else
            {
                g.setColor(Color.BLUE);
            }
            
            g.drawString(options[i], 145, 140+i*15);
        }
    }
    
    private void select()
    {
        switch(currentChoice)
        {
            case 0:
            {
                gsm.setState(1);
                break;
            }
            
            case 1:
            {
                //help
                break;
            }
            
            case 2:
            {
                //quit
                System.exit(0);
                break;
            }
            
            default:
            {
                //quit as well
                System.exit(0);
                break;
            }
        }
    }

    @Override
    public void keyPressed(int k) 
    {
        if(k == KeyEvent.VK_ENTER)
        {
            select();
        }
        
        else if(k == KeyEvent.VK_UP)
        {
            currentChoice--;
            
            if(currentChoice < 0)
            {
                currentChoice = options.length - 1;
            }
        }
        
        else if(k == KeyEvent.VK_DOWN)
        {
            currentChoice++;
            
            if(currentChoice >= options.length)
            {
                currentChoice = 0;
            }
        }
        
    }

    @Override
    public void keyReleased(int k) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}