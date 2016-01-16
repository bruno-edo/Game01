

package GameState.Levels;

import Entities.Characters.Player.Player;
import Maps.TileMaps.TileMap;
import GameState.GameState;
import GameState.GameStateManager;
import java.awt.Color;
import java.awt.Graphics2D;
import Main.GUI.GamePanel;
import java.awt.Rectangle;
import java.util.ArrayList;


public class LevelDungeon1Floor1 extends GameState
{
    private GameStateManager gsm;
    private TileMap tileMap;
    private Player player;

    public LevelDungeon1Floor1(GameStateManager gsm)
    {
        this.gsm = gsm;
        init();
    }
    
    @Override
    public void init() 
    {
        tileMap = new TileMap();
        tileMap.loadFile("/TileMaps/test.tmx");
        tileMap.loadTileset();
        tileMap.loadMap();
        tileMap.loadMapObjects();
        tileMap.setPosition(0, 0);
        
        player = new Player(tileMap);
        player.setOrientation(1);
        player.setPosition(100, 100);
    }

    @Override
    public void update() 
    {
        player.update();
    }

    @Override
    public void draw(Graphics2D g) 
    {
        //clear screen
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        //draw tileMap
        tileMap.draw(g);
        
        Rectangle rec = player.getPlayerMovementRectangle();
        g.setColor(Color.blue);
        g.fillRect((int) rec.getX(), (int)rec.getY(), (int)rec.getWidth(), (int)rec.getHeight());
        
        player.draw(g);
        
        
        g.setColor(Color.red);
        ArrayList<Rectangle> r = tileMap.getCollisionRectangles();
        for(int i  = 0; i < r.size(); i++)
        {
            g.fillRect(r.get(i).x, r.get(i).y, r.get(i).width, r.get(i).height);
        }
        
        ArrayList<Rectangle> overlap = player.getOverlap();
        
        g.setColor(Color.green);
        for(int i = 0; i < overlap.size(); i++)
        {
            g.fillRect(overlap.get(i).x, overlap.get(i).y, overlap.get(i).width, overlap.get(i).height);
        }
        
    }

    @Override
    public void keyPressed(int k) 
    {
        player.keyPressed(k);
    }

    @Override
    public void keyReleased(int k) 
    {
        player.keyReleased(k);
    }
    
}