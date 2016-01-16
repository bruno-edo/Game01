package Parser;

import Maps.TileMaps.Objects.Collision;
import Maps.TileMaps.TileMap;
import Maps.TileMaps.TileMapLayer;
import Maps.TileMaps.Objects.TileMapObject;
import Maps.TileMaps.Tileset;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    //Grabs the map layers from the loaded document
    
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

    
    //Grabs the tileset relevant information
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
    
    public ArrayList<TileMapObject> getObjectLayer()
    {
        //TODO: finish it
        ArrayList<TileMapObject> objects = new ArrayList<TileMapObject>();
        NodeList nList = doc.getElementsByTagName("object");
        HashMap<String, String> propertiesHash;
        
        for(int i = 0; i < nList.getLength(); i++)
        {
            TileMapObject obj;
            //String name = nList.item(i).getAttributes().getNamedItem("name").getTextContent();
            
            /*
                    List of types and their effects:
                1 - Spawn, spawns certain entities,
                2 - StartingPoint, places the player character after a transition,
                3 - Damage, does damage to the player,
                4 - Trigger, triggers events,
                5 - Teleport, moves the player to another map,
                6 - Collision, hinders or not the player (collision etc)
            */
            String type = nList.item(i).getAttributes().getNamedItem("type").getTextContent();
            
            double x = Double.parseDouble(nList.item(i).getAttributes().getNamedItem("x").getTextContent());
            double y = Double.parseDouble(nList.item(i).getAttributes().getNamedItem("y").getTextContent());
            double width = Double.parseDouble(nList.item(i).getAttributes().getNamedItem("width").getTextContent());
            double height = Double.parseDouble(nList.item(i).getAttributes().getNamedItem("height").getTextContent());
            
            
            propertiesHash = new HashMap<String, String>();
            NodeList childs = nList.item(i).getChildNodes();
            
            if(childs.getLength() > 0)
            {
                /*
                    List of properties and their effects:
                    1 - FirstGid, tells the gid number from the left top corner 
                of the image, so the entity can have its sprite rendered correctly - int;
                    2 Passable, hinders or not the player (collision etc) -  int (0 - impassable, 1 - passable and 2 - slowed);
                    3 - GoTo, tells which map to transport the player to - String (map path?);
                    4 - SpawnID, tells what to spawn - int (ID???)
                    5 - TriggerType, tells what kind of trigger it is (Cutscene, event, animation etc) - int (???)
                    6 - Path, path to whatever resource is needed - String
                
                Note: So there's always at least 3 childs when the node has descendants,
                that's because when there's ONE child another two nodes, containing line break
                characters, will be generated. Leaving us with the following structure:
                
                Node: "\n"
                Node: First real child
                ...
                Node: "\n"
                 -end-
                */
                
                Node properties = childs.item(1);
                
                /*
                    Here I have the node properties, its structure is as shown bellow:
                
                    <properties>
                        <property name="GoesTo" value="NextRoom"/>
                    </properties>
                
                    So it's necessary to grab properties childs and go through them one by one.
                */
                
                NodeList propertyNodes = properties.getChildNodes();
                
                for(int k = 1; k < propertyNodes.getLength()-1; k++) //Ignores the line break characters.
                {
                    //TODO: sort these properties
                    String propertyName = propertyNodes.item(k).getAttributes().getNamedItem("name").getTextContent();
                    String propertyValue = propertyNodes.item(k).getAttributes().getNamedItem("value").getTextContent();
                    propertiesHash.put(propertyName, propertyValue);
                }
            }
            if(type.toLowerCase().equals("collision"))
            {
                obj = new Collision(x, y, width, height, type);
            }
            else
            {
                obj = null;
            }
            
            if(obj != null)
            {
                objects.add(obj);
            }           
        }
        //System.out.println(nList.item(0).getAttributes().getNamedItem("name"));
        return objects;
        
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