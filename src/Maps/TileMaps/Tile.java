

package Maps.TileMaps;

import java.awt.image.BufferedImage;

public class Tile 
{
    private BufferedImage image;
    private int type;
    private int gid;
    
    // tile types
    
    public static final int NORMAL = 0;
    public static final int BLOCKED = 1;
    
    public Tile(BufferedImage image, int type)
    {
        this.image = image;
        this.type = type;
    }
    
    public Tile(BufferedImage image, int type, int gid)
    {
        this.image = image;
        this.type = type;
        this.gid = gid;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
    
    
}