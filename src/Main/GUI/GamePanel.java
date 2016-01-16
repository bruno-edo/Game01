

package Main.GUI;

import Main.MainClass;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener
{
    private MainClass main;
    
    //Dimensions
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;  
    
    //image
    private BufferedImage image;
    private Graphics2D g2d;

    public GamePanel(MainClass m)
    {
        super();
        
        main = m;
        
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
    }
    
    public void init()
    {
        image = new BufferedImage(WIDTH, HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        
        g2d = (Graphics2D) image.getGraphics();
    }
    
    public void addNotify()
    {
        super.addNotify();
        this.addKeyListener(this);
    }
    
    public void drawToScreen()
    {
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g.dispose();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Graphics2D getG2d() {
        return g2d;
    }

    public void setG2d(Graphics2D g2d) {
        this.g2d = g2d;
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
        main.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        main.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        main.keyReleased(e);
    }
    
    
}
