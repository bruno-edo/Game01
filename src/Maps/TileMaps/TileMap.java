package Maps.TileMaps;


import Parser.TiledMapParser;
import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
import jogoexemplo.GUI.GamePanel;

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
    private int tileSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;
    
    // tileset
    ArrayList<Tileset> tileSetsCollection =  new ArrayList<Tileset>();
    private BufferedImage tileset;
    private int numTilesAcross;
    private Tile[][] tiles;
    
    //drawing only the tiles on the screen
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;
    
    TiledMapParser xmlParser;
    
    private final int maxTileSize = 64;
    
    public TileMap(int tileSize)
    {
        this.tileSize = 16;
        numRowsToDraw = GamePanel.HEIGHT / this.tileSize + 4;
        numColsToDraw = GamePanel.WIDTH / this.tileSize + 4;
        
        tween = 1;
        
        xmlParser = new TiledMapParser(this);
        xmlParser.parseNewMapFile("/TileMaps/test.tmx");
    }
    
    public void loadFile(String path)
    {
        boolean response = xmlParser.parseNewMapFile(path);
    }
    
    //loads tileset images from the tileset sheet
    public void loadTileset()
    {/*
        try
        {
            tileset = ImageIO.read(getClass().getResourceAsStream(s));
            numTilesAcross = tileset.getWidth() / tileSize;
            
            tiles =  new Tile[2][numTilesAcross];
            
            BufferedImage subimage;*/
            
            
            /*
            abaixo o código considera os tiles na primeira linha como
            "passáveis", enquanto os da segunda linha não o são.
            Isso limita a organização da imagem dos tileMaps e não
            é a solução mais inteligente. 
            
            Repensar essa parte feita no tutorial, para que seja o mais
            genérico o possível.
            
            */
           /* for(int col = 0; col < numTilesAcross; col++)
            {
                subimage = tileset.getSubimage(col * tileSize,
                        0, tileSize, tileSize);
                
                tiles[0][col] =  new Tile(subimage, Tile.NORMAL);
                
                subimage = tileset.getSubimage(col * tileSize,
                        tileSize, tileSize, tileSize);
                
                 tiles[1][col] =  new Tile(subimage, Tile.BLOCKED);
            }
        }
        
        catch(Exception e)
        {
            e.printStackTrace();
        }*/
        
        tileSetsCollection =  xmlParser.getTilesets();
        
    }
    
    public void loadMap()
    {
        /*try
        {
            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in));
            
            numCols = Integer.parseInt(br.readLine());
            numRows = Integer.parseInt(br.readLine());
            
            map = new int[numRows][numCols];
            width = numCols * tileSize;
            height = numRows * tileSize;
            
            String delims = "\\s+";
            
            for(int row = 0; row < numRows; row++)
            {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                
                for(int col = 0; col < numCols; col++)
                {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }*/
        
                    
        //grabs the map layers, so the tile matrix can be generated 
        layerMap = xmlParser.getMapLayers(xmlParser.getMapHeight(), xmlParser.getMapWidth());
        
        numCols = layerMap[0].getColumns();
        numRows = layerMap[0].getRows();
        
        
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
    
    public void setPosition(double x, double y)
    {
        //implement tween?
        this.x = x;
        this.y = y;
        
        fixBounds();
        
        width = numCols * tileSetsCollection.get(0).getTileWidth();
        height = numRows * tileSetsCollection.get(0).getTileHeight();
        
        colOffset = (int)-this.x / tileSetsCollection.get(0).getTileWidth();
        rowOffset = (int)-this.y / tileSetsCollection.get(0).getTileHeight();
        
        
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
       /* g.drawImage(tileSetsCollection.get(0).getTiles().get(10).getImage(), 
                        (int) x + 0 * tileSetsCollection.get(0).getTileWidth(),
                        (int) y + 0 * tileSetsCollection.get(0).getTileHeight(),
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
                            In his tutorial 0 (first tile in the tile set) was
                            had no image, so no reason to try to draw it.
                            Hence this line of code below.
                    */

                    if(map[row][col] == 0)
                    {
                        continue;
                    }

                    int rc = map[row][col];
                    //System.out.println("rc: "+rc);
                    
                    //Adequar pra trabalhar com mais de uma coleção de tiles e pegar o tile usando o gid
                    g.drawImage(tileSetsCollection.get(0).getTiles().get(rc-1).getImage(), 
                            (int) x + col * tileSize,
                            (int) y + row * tileSize,
                            null);
                }
            }
        }   
    }
}