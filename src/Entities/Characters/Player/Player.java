

package Entities.Characters.Player;

import Entities.Animation;
import Entities.Entity;
import Maps.TileMaps.TileMap;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Player extends Entity
{
    //Player information
    
    private String name;
    private int health;
    private int maxHealth;
    private boolean dead;
    
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {10}; //TODO when I have the character spritesheet
    
    //Animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 3;

    public Player(TileMap tm) 
    {
        super(tm);
        
        //PlaceHolders
        width = 32;
        height = 32;
        cwidth = 18;
        cheight = 32;
        
        moveSpeed = 0.3;
        maxSpeed = 1.35;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        
        health = maxHealth = 5;
        
        dead = false;
        sprites = new ArrayList<BufferedImage[]>();
        
        try
        {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
            "/Spritesheets/player warrior.png"));
            
            for(int i = 0;  i < 10; i++)
            {
                BufferedImage[] bi = new BufferedImage[10];
                
                for(int k = 0; k < 10; k++)
                {
                    bi[k] = spritesheet.getSubimage(k * 32, i * 32, 32, 32);
                }
                
                sprites.add(bi);
            }
            
            
        }
        catch(Exception ex)
        {
            
        }
        
        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(2));
        animation.setDelay(100);
    }
    
    public void update()
    {
        //update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        
        //set animation
        switch(currentAction)
        {
            case WALKING:
            {
                animation.setFrames(sprites.get(2)); //3 = placeholder
                animation.setDelay(40);
                break;
            }
            
            default:
            {
                break;
            }
        }
        
        animation.update();
        
        //Set player direction
        //TODO
        
        
    }
    
    public void getNextPosition()
    {
        
        //Tratar caso a seta oposta seja pressionada junto da atual (parada)
        //Descobrir pq o movimento diagonal se mantem de qualquer maneira
        //movement        
        if(right)
        {
            dx += moveSpeed;
            if(dx > maxSpeed)
            {
                dx = maxSpeed;
            }
        }
            
        if(left)
        {
            dx -= moveSpeed;
            if(dx < -maxSpeed)
            {
                dx = -maxSpeed;
            }
        }
            
        if(up)
        {
            dy -= moveSpeed;
            if(dy < -maxSpeed)
            {
                dy = -maxSpeed;
            }
        }
            
        if(down)
        {
            dy += moveSpeed;
            if(dy > maxSpeed)
            {
                dy = maxSpeed;
            }
        }
            
        if(((!right) && (!left)) || ((right) && (left)))
        {
            dx = 0;
            
        }
        
        if(!up && (!down) || ((up) && (down)))
        {
            dy = 0;
        }
    }
    
    public void draw(Graphics2D g)
    {
        setMapPosition();
        
        // draw player
        
        //TODO: different orientations sprites and logic
        
        
        g.drawImage(animation.getImage(),
                (int)(x + xmap - width / 2),
                (int)(y + ymap - height / 2),
                32,32,null);
    }
    
    public void keyTyped(KeyEvent e) 
    {
        //System.out.println("x: "+this.getX());
    }

    public void keyPressed(int keyCode) 
    {
        //System.out.println("key pressed");
        //Orientation: 0 - Looking right, 1 - Left, 2 - Up and 3 - Down.
        if(keyCode == KeyEvent.VK_LEFT)
        {
            left = true;
            setOrientation(1);
        }
        else if(keyCode == KeyEvent.VK_RIGHT)
        {
            right = true;
            setOrientation(0);
        }
        else if(keyCode == KeyEvent.VK_UP)
        {
            up = true;
            setOrientation(2);
        }
        else if(keyCode == KeyEvent.VK_DOWN)
        {
            down = true;
            setOrientation(3);
        }
    }

    public void keyReleased(int keyCode) 
    {
        //System.out.println("key released");
        if(keyCode == KeyEvent.VK_LEFT)
        {
            left = false;
            setOrientation(1);
        }
        else if(keyCode == KeyEvent.VK_RIGHT)
        {
            right = false;
            setOrientation(0);
        }
        else if(keyCode == KeyEvent.VK_UP)
        {
            up = false;
            setOrientation(2);
        }
        else if(keyCode == KeyEvent.VK_DOWN)
        {
            down = false;
            setOrientation(3);
        }
    }
    

}