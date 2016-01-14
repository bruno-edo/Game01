

package Maps.TileMaps;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Tileset 
{
    private String path = "/Tilesets/";      
    private int tileHeight;
    private int tileWidth; 
    private int firstGid;
    private int tileCount;
    private int sheetColumns;
    private int sheetRows;
    private BufferedImage spriteSheet;
    private ArrayList<Tile> tiles;
    
    
    public Tileset(String pathP, int tileHeight, int tileWidth, int gid, int tileCount, int sheetColumns)
    {
        this.path += pathP;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.firstGid = gid;
        this.tileCount = tileCount;
        this.sheetColumns = sheetColumns;
        tiles = new ArrayList<Tile>();
        sheetRows = tileCount / sheetColumns;
        
        try 
        {
            InputStream in = this.getClass().getResourceAsStream(path);
            spriteSheet = ImageIO.read(in);
        } 
        
        catch (IOException ex) 
        {
            Logger.getLogger(Tileset.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.separateTiles();
    }

    private void separateTiles() 
    {
        int gid = firstGid;
        for(int row = 0; row < sheetRows; row++)
        {
            for(int column = 0; column < sheetColumns; column++)
            {
                BufferedImage image = spriteSheet.getSubimage(column * tileWidth, row * tileHeight, tileWidth, tileHeight);
                Tile t =  new Tile(image, Tile.NORMAL, gid);
                tiles.add(t);
                gid++;
            }
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getFirstGid() {
        return firstGid;
    }

    public void setFirstGid(int firstGid) {
        this.firstGid = firstGid;
    }

    public int getTileCount() {
        return tileCount;
    }

    public void setTileCount(int tileCount) {
        this.tileCount = tileCount;
    }

    public int getSheetColumns() {
        return sheetColumns;
    }

    public void setSheetColumns(int sheetColumns) {
        this.sheetColumns = sheetColumns;
    }

    public int getSheetRows() {
        return sheetRows;
    }

    public void setSheetRows(int sheetRows) {
        this.sheetRows = sheetRows;
    }

    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }

    public void setSpriteSheet(BufferedImage spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }
    
    
}