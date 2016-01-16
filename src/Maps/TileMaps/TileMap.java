package Maps.TileMaps;


import Maps.TileMaps.Objects.*;
import Parser.TiledMapParser;
import java.awt.*;
import java.util.ArrayList;
import Main.GUI.GamePanel;

public class TileMap 
{
    //position
    private double x;
    private double y;
    
    //bounds
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;
    
    private double tween;
    
    //map
    private TileMapLayer[] layerMap;
    private ArrayList<Collision> collisions;
    private int tileSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;
    
    // tileset
    ArrayList<Tileset> tilesetsArray =  new ArrayList<Tileset>();
    
    //drawing only the tilesetsArray on the screen
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;
      
    //Loading map
    TiledMapParser xmlParser;
    
    private final int maxTileSize = 64;
    
    public TileMap()
    {
        tween = 1;
        collisions = new ArrayList<Collision>();
        
        xmlParser = new TiledMapParser(this);
    }
    
    public void loadFile(String path)
    {
        boolean response = xmlParser.parseNewMapFile(path);
    }
    
    //loads tileset images from the tileset sheet
    public void loadTileset()
    {       
        tilesetsArray = xmlParser.getTilesets();
        
        //gets tileSize. Tiles must always be square.
        this.tileSize = xmlParser.getTilesets().get(0).getTileHeight(); 
        numRowsToDraw = GamePanel.HEIGHT / this.tileSize + 4;
        numColsToDraw = GamePanel.WIDTH / this.tileSize + 4;
        
    }
    
    public void loadMap()
    {    
        //grabs the map layers, so the tile matrix can be generated 
        layerMap = xmlParser.getMapLayers(xmlParser.getMapHeight(), xmlParser.getMapWidth());
        
        numCols = layerMap[0].getColumns();
        numRows = layerMap[0].getRows();
        
        
    }
    
    public void loadMapObjects() 
    {
        ArrayList<TileMapObject> objects  =  xmlParser.getObjectLayer();
        
        for(int i = 0; i < objects.size(); i++)
        {
            if(objects.get(i) instanceof Collision)
            {
                //System.out.println("eh colisao em x,y: "+ objects.get(i).getX()+ " , "+objects.get(i).getY());
                collisions.add((Collision) objects.get(i));
            }
        }
    }
    
    //Sets the camera position
    public void setPosition(double x, double y)
    {
        //implement tween?
        this.x = x;
        this.y = y;
        
        fixBounds();
        
        width = numCols * tileSize;
        height = numRows * tileSize;
        
        colOffset = (int)-this.x / tileSize;
        rowOffset = (int)-this.y / tileSize;
        
        
    }
    
    public void fixBounds()
    {
        if(x < xmin) x = xmin;
        if(y < ymin) y = ymin;
        if(x > xmax) x = xmax;
        if(y > ymax) y = ymax;
            
    }
    
    public void draw(Graphics2D g)
    {
       /* g.drawImage(tilesetsArray.get(0).getTiles().get(10).getImage(), 
                        (int) x + 0 * tilesetsArray.get(0).getTileWidth(),
                        (int) y + 0 * tilesetsArray.get(0).getTileHeight(),
                        null);*/
        for(int layer = 0; layer < layerMap.length; layer++)
        {
            int[][] map = layerMap[layer].getTilematrix();
            for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++)
            {
            
                if(row >= numRows)
                {
                    break;
                }

                for(int col = colOffset; col < colOffset + numColsToDraw; col++)
                {
                    if(col >= numCols)
                    {
                        break;
                    }

                    /*
                            0 (first tile in the tile set) was
                            has no image, so no reason to try to draw it.
                            Hence this line of code below.
                    */

                    if(map[row][col] == 0)
                    {
                        continue;
                    }

                    int gid = map[row][col];

                    g.drawImage(
                            getTileByGid(gid).getImage(), 
                            (int) x + col * tileSize + (int) layerMap[layer].getxOffset(),
                            (int) y + row * tileSize + (int) layerMap[layer].getyOffset(),
                            null);
                }
            }
        }   
    }
    
    private Tile getTileByGid(int gid)
    {
        for(int i = 0; i < tilesetsArray.size(); i++)
        {
            int finalGid = tilesetsArray.get(i).getFirstGid() + tilesetsArray.get(i).getTileCount();
            
            if(finalGid >= gid)
            {
                return tilesetsArray.get(i).getTileByGid(gid);
            }
        }
        return null;
    }
    
    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public int getType(int row, int col)
    {
        
        return -1;
    }

    public ArrayList<Collision> getCollisions() {
        return collisions;
    }
    
    public ArrayList<Rectangle> getCollisionRectangles()
    {
        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        
        for(int i  = 0; i < collisions.size(); i++)
        {
            rectangles.add(collisions.get(i).getBox());
        }
        return rectangles;
    }    
}