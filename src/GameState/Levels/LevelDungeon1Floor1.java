

package GameState.Levels;

import Maps.TileMaps.TileMap;
import GameState.GameState;
import GameState.GameStateManager;
import java.awt.Color;
import java.awt.Graphics2D;
import jogoexemplo.GUI.GamePanel;


public class LevelDungeon1Floor1 extends GameState
{
    private GameStateManager gsm;
    private TileMap tileMap;

    public LevelDungeon1Floor1(GameStateManager gsm)
    {
        this.gsm = gsm;
        init();
    }
    
    @Override
    public void init() 
    {
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/grasstileset.gif");
        tileMap.loadMap("/TileMaps/level1-1.map");
        tileMap.setPosition(0, 0);
    }

    @Override
    public void update() 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draw(Graphics2D g) 
    {
        //clear screen
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        
        //draw tileMap
        tileMap.draw(g);
        
    }

    @Override
    public void keyPressed(int k) 
    {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(int k) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}