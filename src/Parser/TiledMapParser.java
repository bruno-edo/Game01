package Parser;

import GameState.GameStateManager;
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
    private GameStateManager gsm;
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document doc;
    private File inputFile;
    
    public TiledMapParser(GameStateManager gsm)
    {
        this.gsm = gsm;
        
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
    
    
    //TODO: change this to return a tileMap object (gotta change most of the tileMap class)
    public void parseNewMapFile(String address)
    {
        URL u = getClass().getResource(address);
        inputFile = new File(u.getPath());
        
        try 
        {
            doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();
            
            //Examine the attributes from the root element to know the tileMap dimensions
            int height = Integer.parseInt(doc.getDocumentElement().getAttribute("height"));
            int width = Integer.parseInt(doc.getDocumentElement().getAttribute("width"));
            
            ArrayList<Tileset> tilesets = getTilesetsTileDimensions(doc);
            
            //grabs the map layers, so the tile matrix can be generated
            NodeList nList = doc.getElementsByTagName("layer");
            int numLayers = nList.getLength(); //number of layers
            TileMapLayer[] mapLayers = new TileMapLayer[numLayers];
            
           
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
                    if(temp != "")
                    {
                        double opacity = Double.parseDouble(temp);
                        mapLayers[layer].setOpacity(opacity);
                    }
                    
                    temp = element.getAttribute("offsety");
                    if(temp != "")
                    {
                        double yOffset = Double.parseDouble(temp);
                        mapLayers[layer].setyOffset(yOffset);
                    }
                    
                    temp = element.getAttribute("offsetx");
                    if(temp != "")
                    {
                        double xOffset = Double.parseDouble(temp);
                        mapLayers[layer].setxOffset(xOffset);
                    }
                }
            }
        }
        
        
        catch (SAXException ex) 
        {
            Logger.getLogger(TiledMapParser.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(TiledMapParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    //Grabs the tileset relevant information and orders it by name.
    private ArrayList<Tileset> getTilesetsTileDimensions(Document doc) 
    {
        String tilesetName = "";
        ArrayList tilesetsList = new ArrayList<Tileset>();
        
        NodeList nodeList = doc.getElementsByTagName("tileset");
        NodeList imageList =  doc.getElementsByTagName("image");
        
        for(int i = 0; i < nodeList.getLength(); i++)
        {
            Node n = nodeList.item(i);
            
            //TODO: change name to filename
            int height = Integer.parseInt(n.getAttributes().getNamedItem("tileheight").getTextContent());
            int width = Integer.parseInt(n.getAttributes().getNamedItem("tilewidth").getTextContent());
            int gid = Integer.parseInt(n.getAttributes().getNamedItem("tilewidth").getTextContent());
           // System.out.println("tilesetName: "+tilesetName+"    h: "+dimensions[0]+"    w: "+dimensions[1]);
            
        }
                
        return tilesetsList;
    }
}