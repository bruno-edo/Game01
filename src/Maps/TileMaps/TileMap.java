package Maps.TileMaps;


import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
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
    private int[][] map;
    private int tileSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;
    
    // tileset
    private BufferedImage tileset;
    private int numTilesAcross;
    private Tile[][] tiles;
    
    //drawing only the tiles on the screen
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;
    
    public TileMap(int tileSize)
    {
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numColsToDraw = GamePanel.WIDTH / tileSize + 2;
        
        tween = 1;
    }
    
    //loads tileset images from the tileset sheet
    public void loadTiles(String s)
    {
        try
        {
            tileset = ImageIO.read(getClass().getResourceAsStream(s));
            numTilesAcross = tileset.getWidth() / tileSize;
            
            tiles =  new Tile[2][numTilesAcross];
            
            BufferedImage subimage;
            
            
            /*
            abaixo o código considera os tiles na primeira linha como
            "passáveis", enquanto os da segunda linha não o são.
            Isso limita a organização da imagem dos tileMaps e não
            é a solução mais inteligente. 
            
            Repensar essa parte feita no tutorial, para que seja o mais
            genérico o possível.
            
            */
            for(int col = 0; col < numTilesAcross; col++)
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
        }
    }
    
    public void loadMap(String s)
    {
        try
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
        }
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
        int rc = map[row][col];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;
        
        return tiles[r][c].getType();
    }
    
    public void setPosition(double x, double y)
    {
        //implement tween?
        this.x = x;
        this.y = y;
        
        fixBounds();
        
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
                int r = rc / numTilesAcross;
                int c = rc % numTilesAcross;
                
                g.drawImage(tiles[r][c].getImage(), 
                        (int) x + col * tileSize,
                        (int) y + row * tileSize,
                        null);
            }
        }
    }
}