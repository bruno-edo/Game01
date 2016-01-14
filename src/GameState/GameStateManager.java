

package GameState;

import GameState.Levels.LevelDungeon1Floor1;
import Parser.TiledMapParser;
import java.util.ArrayList;

public class GameStateManager 
{
    private ArrayList<GameState> gameStates;
    private int currentState;
    
    public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;
    
    public GameStateManager()
    {
        gameStates = new ArrayList<GameState>();
        
        currentState = 0;
        gameStates.add(new MenuState(this));
        gameStates.add(new LevelDungeon1Floor1(this));
        
        TiledMapParser xmlParser = new TiledMapParser(this);
        xmlParser.parseNewMapFile("/TileMaps/test.tmx");
    }
    
    public void setState(int state)
    {
        try
        {
            gameStates.get(state).init();
            currentState = state;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void update()
    {
        gameStates.get(currentState).update();
    }
    
    public void draw(java.awt.Graphics2D g)
    {
        gameStates.get(currentState).draw(g);
    }
    
    public void keyPressed(int k)
    {
        gameStates.get(currentState).keyPressed(k);
    }
    
    public void keyReleased(int k)
    {
        gameStates.get(currentState).keyReleased(k);
    }
}