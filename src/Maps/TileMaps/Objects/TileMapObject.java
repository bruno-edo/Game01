

package Maps.TileMaps.Objects;

import java.awt.Rectangle;
import java.util.HashMap;

public abstract class TileMapObject 
{
    // X and Y refere to the top left corner of the rectangle
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected String type;
    protected HashMap<String, String> properties;
    protected Rectangle box;
    
    public TileMapObject(double x, double y, double width, double height, String type)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        box = new Rectangle((int) x,(int) y, (int) width, (int) height);
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }

    public Rectangle getBox() {
        return box;
    }

    public void setBox(Rectangle box) {
        this.box = box;
    }
    
    
}