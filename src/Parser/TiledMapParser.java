package Parser;

import Maps.TileMaps.TileMap;
import Maps.TileMaps.TileMapLayer;
import Maps.TileMaps.Tileset;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;

public class TiledMapParser 
{
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document doc;
    private File inputFile;
    private TileMap tileMap;
    
    public TiledMapParser(TileMap tileMap)
    {
        this.tileMap = tileMap;
        
        factory = DocumentBuilderFactory.newInstance();
        
        try 
        {
            builder = factory.newDocumentBuilder();
        } 
        catch (ParserConfigurationException ex) 
        {
            Logger.getLogger(TiledMapParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
        This method builds the tile maps based on an XML file that is generated
    using the Tiled map editor.
        First it separates the tile layers, after that it grabs the object layers and then
    proceeds to generate the TileMap object, so it can be returned to the controller for use.
    */
      
    //Loads the specified xml file to memory.
    public boolean parseNewMapFile(String address)
    {
        boolean r =false;
        URL u = getClass().getResource(address);
        inputFile = new File(u.getPath());
        
        try 
        {
            doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();
            
            r = true;
            //TODO: grab the object layers
        }
        
        
        catch (SAXException ex) 
        {
            Logger.getLogger(TiledMapParser.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(TiledMapParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return r;
    }
    
    public TileMapLayer[] getMapLayers(int height, int width)
    {
         NodeList nList = doc.getElementsByTagName("layer");
         int numLayers = nList.getLength(); //number of layers
         TileMapLayer[] mapLayers =  new TileMapLayer[numLayers];
         
         for (int layer = 0; layer < numLayers; layer++) 
         {                
                int[][] tileMatrix = new int[height][width];
                Node nNode = nList.item(layer);
                
                //Examining to be sure only element nodes are selected.
                if(nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) nNode;
                    String s = element.getElementsByTagName("data").item(0).getTextContent();
                    
                    //Cleaning up the data, so it can be stored into an array correctly.
                    s = s.replace("\n","");
                    String[] split = s.split(",");                    
                    
                    //creating the tile matrix
                    for(int i = 0; i < split.length; i++)
                    {
                        int row = i / height;
                        int column = i % width;
                        tileMatrix[row][column] = Integer.parseInt(split[i]);
                    }
                    
                    mapLayers[layer] = new TileMapLayer(tileMatrix);
                    
                    /*
                        In this section the node attributes will be separated and
                    sent to the layer object.
                        Possible attributes:
                        - Name
                        - Vertical offset (offsety)
                        - Horizontal offset (offsetx)
                        - Opacity
                        - Height
                        - Width
                    
                        Height and width can be ignored since the root node carries
                    that information already.
                    */
                    
                    String temp = "";
                    
                    temp = element.getAttribute("opacity");
                    if(!temp.equals(""))
                    {
                        double opacity = Double.parseDouble(temp);
                        mapLayers[layer].setOpacity(opacity);
                    }
                    
                    temp = element.getAttribute("offsety");
                    if(!temp.equals(""))
                    {
                        double yOffset = Double.parseDouble(temp);
                        mapLayers[layer].setyOffset(yOffset);
                    }
                    
                    temp = element.getAttribute("offsetx");
                    if(!temp.equals(""))
                    {
                        double xOffset = Double.parseDouble(temp);
                        mapLayers[layer].setxOffset(xOffset);
                    }
                }
            }
         
         return mapLayers;
    }

    
    //Grabs the tileset relevant information and orders it by name.
    public ArrayList<Tileset> getTilesets() 
    {
        String tilesetName = "";
        ArrayList tilesetsList = new ArrayList<Tileset>();
        
        NodeList nodeList = doc.getElementsByTagName("tileset");
        NodeList imageList =  doc.getElementsByTagName("image");
        
        for(int i = 0; i < nodeList.getLength(); i++)
        {
            Node n = nodeList.item(i);
            Node imageN = imageList.item(i);
            
            //Grabs the name of the image to serve as tileset name.
            tilesetName = imageN.getAttributes().getNamedItem("source").getTextContent();
            
            System.out.println("tileset name: "+tilesetName);
            
            int tileHeight = Integer.parseInt(n.getAttributes().getNamedItem("tileheight").getTextContent());
            int tileWidth = Integer.parseInt(n.getAttributes().getNamedItem("tilewidth").getTextContent());
            int gid = Integer.parseInt(n.getAttributes().getNamedItem("firstgid").getTextContent());
            int tileCount = Integer.parseInt(n.getAttributes().getNamedItem("tilecount").getTextContent());
            int sheetColumns = Integer.parseInt(n.getAttributes().getNamedItem("columns").getTextContent());
            
            Tileset tilesetTemp = new Tileset(tilesetName, tileHeight, tileWidth, gid, tileCount, sheetColumns);
            tilesetsList.add(tilesetTemp);
        }
                
        return tilesetsList;
    }
    
    //Examine the attributes from the root element to know the tileMap dimensions
    public int getMapHeight()
    {
        return Integer.parseInt(doc.getDocumentElement().getAttribute("height"));
    }
    
    //Examine the attributes from the root element to know the tileMap dimensions
    public int getMapWidth()
    {
        return Integer.parseInt(doc.getDocumentElement().getAttribute("width"));
    }

    public DocumentBuilderFactory getFactory() {
        return factory;
    }

    public void setFactory(DocumentBuilderFactory factory) {
        this.factory = factory;
    }

    public DocumentBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(DocumentBuilder builder) {
        this.builder = builder;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }
    
    
}