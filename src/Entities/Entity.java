

package Entities;

import Maps.TileMaps.TileMap;
import java.awt.Rectangle;
import Main.GUI.GamePanel;
import java.awt.Point;
import java.util.ArrayList;

public abstract class Entity 
{
    //Tile information
    protected TileMap tileMap;
    protected double xmap;
    protected double ymap;
    
    //Position and vector
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;
    
    //dimensions
    protected int width;
    protected int height;
    
    //Colision box
    protected int cwidth;
    protected int cheight;
    
    //Collision
    protected int currentRow;
    protected int currentCol;
    protected double xdest;
    protected double ydest;
    protected double xtemp;
    protected double ytemp;
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;
    protected ArrayList<Rectangle> overlap;
    
    //Animation
    protected Animation animation;
    protected int currentAction;
    protected int previousAction;
    
    //0 - Looking right, 1 - Left, 2 - Up and 3 - Down.
    protected int orientation;
    
    //movement;
    protected boolean right;
    protected boolean left;
    protected boolean up;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;
    
    //movement attributes
    protected double moveSpeed;
    protected double maxSpeed;
    protected double stopSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpStart;
    protected double flutterSpeed;
    
    public Entity(TileMap tm)
    {
        tileMap = tm;
        
        //Will not implement like this as to not too much code redundancy
        //tileSize = tm.getTileSize();
    }
    
    //Checking collision between entities.
    public void checkTileMapCollision()
    {
        currentCol = (int)x / tileMap.getTileSize();
        currentRow = (int)y / tileMap.getTileSize();
        
        xdest = x + dx;
        ydest = y + dy;
        
        xtemp = x;
        ytemp = y;
        
        boolean collided = checkCollision();
        
        if(collided)
        {           
            for(int i = 0; i < overlap.size(); i++)
            {
                double overlapX = overlap.get(i).getWidth();
                double overlapY = overlap.get(i).getHeight();

                //One of the sides collided
                if(overlapX < overlapY)
                {
                    //System.out.print("colidiu X");
                    if(dx > 0)
                    {
                        //right side collided
                        xdest -= overlapX;
                        dx = 0;
                    }
                    else if(dx < 0)
                    {
                        //left side collided
                        xdest += overlapX;
                        dx = 0;
                    }
                    
                    
                    else if(dx == 0  && (dy != 0))
                    {
                        if(dy > 0)
                        {
                            //right side collided
                            ydest -= overlapY;
                        }
                        else if(dy < 0)
                        {
                            //left side collided
                            ydest += overlapY;
                        }
                        
                        if(overlap.get(i).getX() < x)
                        {
                            xtemp += overlapX;
                        }
                        
                        else
                        {
                            xtemp -= overlapX;
                        }
                        
                        dy = 0;
                        
                    }
                }

                //one of the tops collided
                else if(overlapX > overlapY)
                {
                    //System.out.print("colidiu Y - overlapX: "+overlapX+"    overlapY: "+overlapY);
                    if(dy > 0)
                    {
                        //right side collided
                        ydest -= overlapY;
                        dy = 0;
                    }
                    else if(dy < 0)
                    {
                        //left side collided
                        ydest += overlapY;
                        dy = 0;
                    }
                    
                    else if(dy == 0  && (dx != 0))
                    {
                        if(dx > 0)
                        {
                            xdest -= overlapX;
                        }
                        else if(dx < 0)
                        {
                            xdest += overlapX;
                        }
                        
                        if(overlap.get(i).getY() < y)
                        {
                            ytemp += overlapY;
                        }
                        
                        else
                        {
                            ytemp -= overlapY;
                        }
                        
                        dx = 0;
                        
                    }
                }
                
                else if(overlapX == overlapY)
                {
                    //System.out.print("colidiu igual - overlapX: "+overlapX+"    overlapY: "+overlapY+"   dx: "+dx+" dy: "+dy);
                    if(dy > 0)
                    {
                        ydest -= overlapY;
                    }
                    else if(dy < 0)
                    {
                        ydest += overlapY;
                    }
                    if(dx > 0)
                    {
                        xdest -= overlapX;
                    }
                    else if(dx < 0)
                    {
                        xdest += overlapX;
                    }
                                      
                }
            }
            
            
            
        }
        
        xtemp+=dx;
        ytemp+=dy;
		       
        /*
        
        will implement later
        if(!falling) 
        {
            //calculateCorners(x, ydest + 1);
            if(!bottomLeft && !bottomRight) 
            {
                //falling = true;
            }
	}*/
    }
    
    public boolean checkCollision()
    {
        Rectangle rPlayer = this.getPlayerMovementRectangle();
        ArrayList<Rectangle> collisionBoxes = tileMap.getCollisionRectangles();
        overlap = new ArrayList<Rectangle>();
        boolean resp = false;
        for (Rectangle collisionBoxe : collisionBoxes) 
        {
            if (rPlayer.intersects(collisionBoxe)) 
            {
                //Adds overlapping collision rectangles to an array, so they can all be treated correctly
                overlap.add((Rectangle) rPlayer.createIntersection(collisionBoxe));
                resp = true;
            }
        }
        
        return resp;
    }
    
    public boolean intersects(Entity o)
    {
        Rectangle r1 = getPlayerMovementRectangle();       
        Rectangle r2 = o.getPlayerMovementRectangle();
        
        return r1.intersects(r2);
    }
    
    public Rectangle getPlayerMovementRectangle()
    {
        return new Rectangle(
                (int)xdest - cwidth / 2, 
                (int)ydest - cheight / 2,
                cwidth,
                cheight);
    }
    
    //Checks to see if the point is contained within the rectangle, used for collision detection.
    public boolean isContained(Point point, Rectangle rectangle)
    {
        return rectangle.contains(point);
    }
    
    public void setMapPosition()
    {
        xmap = tileMap.getX();
        ymap = tileMap.getY();
    }
    
    public boolean notOnScreen()
    {
        return x +xmap + width < 0 ||
                x + xmap - width > GamePanel.WIDTH ||
                y + ymap + height < 0 ||
                y + ymap - height > GamePanel.HEIGHT;
    }
    
    //0 - Looking right, 1 - Left, 2 - Up and 3 - Down.
    public int getOrientation() 
    {
        return orientation;
    }
    //0 - Looking right, 1 - Left, 2 - Up and 3 - Down.
    public void setOrientation(int orientation) 
    {
        this.orientation = orientation;
    }    
    

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCwidth() {
        return cwidth;
    }

    public void setCwidth(int cwidth) {
        this.cwidth = cwidth;
    }

    public int getCheight() {
        return cheight;
    }

    public void setCheight(int cheight) {
        this.cheight = cheight;
    }
    
    public void setPosition(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void setVector(double dx, double dy)
    {
        this.dx = dx;
        this.dy = dy;
    }

    public ArrayList<Rectangle> getOverlap() {
        return overlap;
    }

    public void setOverlap(ArrayList<Rectangle> overlap) {
        this.overlap = overlap;
    }
    
    
    
}