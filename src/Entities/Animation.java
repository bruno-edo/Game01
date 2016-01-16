

package Entities;

import java.awt.image.BufferedImage;

public class Animation 
{
    private BufferedImage[] frames;
    int currentFrame;
    
    private long startTime;
    private long delay;
    
    private boolean playedOnce;
    
    public Animation()
    {
        playedOnce = false;
    }
    
    public void setFrames(BufferedImage[] b)
    {
        frames = b;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }
    
    public void update()
    {
        if(delay == -1)
        {
            return;
        }
        
        long elapsed = (System.nanoTime() - startTime) /1000000;
        
        if(elapsed > delay)
        {
            currentFrame++;
            startTime =  System.nanoTime();
        }
        
        if(currentFrame == frames.length)
        {
            currentFrame = 0;
            playedOnce = true;
        }
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public boolean isPlayedOnce() {
        return playedOnce;
    }

    public void setPlayedOnce(boolean playedOnce) {
        this.playedOnce = playedOnce;
    }
    
    public BufferedImage getImage()
    {
        return frames[currentFrame];
    }
}