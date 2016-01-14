package Maps.TileMaps;

public class TileMapLayer 
{
    private int[][] tilematrix;
    private double yOffset;
    private double xOffset;
    private int rows;
    private int columns;
    
    private double opacity; // 1 = 100% Opaque, 0 = 0% opaque
    
    public TileMapLayer(int[][] tileMatrix)
    {
        this.tilematrix = tileMatrix;
        
        rows = tileMatrix.length;
        columns = tileMatrix[0].length;
        
        yOffset = 0;
        xOffset = 0;
        opacity = 1;
    }

    public int[][] getTilematrix() 
    {
        return tilematrix;
    }

    public void setTilematrix(int[][] tilematrix) 
    {
        this.tilematrix = tilematrix;
    }

    public double getyOffset() 
    {
        return yOffset;
    }

    public void setyOffset(double yOffset) 
    {
        this.yOffset = yOffset;
    }

    public double getxOffset() 
    {
        return xOffset;
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public double getOpacity() 
    {
        return opacity;
    }

    public void setOpacity(double opacity) 
    {
        this.opacity = opacity;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
    
    
    
    
}