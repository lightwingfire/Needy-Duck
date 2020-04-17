package game;

import org.w3c.dom.css.RGBColor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class Animation {

    private int frameCount;                 // Counts ticks for change
    private int frameDelay;                 // frame delay 1-12 (You will have to play around with this)
    private int currentFrame;               // animations current frame
    private int animationDirection;         // animation direction (i.e counting forward or backward)
    private int totalFrames;                // total amount of frames for your animation

    private boolean stopped;                // has animations stopped

    private int loopTotal;
    private int currentLoop;
    private int duration;

    private List<Frame> frames = new ArrayList<Frame>();    // Arraylist of frames

    public Animation(BufferedImage[] frames, int frameDelay) {
        this.frameDelay = frameDelay;
        this.stopped = true;
        duration = frameDelay;

        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i], frameDelay);
        }

        this.frameCount = 0;
        this.frameDelay = frameDelay;
        this.currentFrame = 0;
        this.animationDirection = 1;
        this.totalFrames = this.frames.size();

    }

    public void loop(int num){
        loopTotal = num;
        currentLoop = 0;
        start();
    }

    public void start() {
        if (!stopped) {
            return;
        }

        if (frames.size() == 0) {
            return;
        }
        stopped = false;
    }

    public void stop() {
        if (frames.size() == 0) {
            return;
        }

        stopped = true;
    }

    public void restart() {
        if (frames.size() == 0) {
            return;
        }

        stopped = false;
        currentFrame = 0;
    }

    public void reset() {
        this.stopped = true;
        this.frameCount = 0;
        this.currentFrame = 0;
    }

    public boolean isLastFrame(){
        if(currentFrame==totalFrames){
            return true;
        }
        else{
            return false;
        }
    }
    public int getcurrentFrame(){
        return currentFrame;
    }
    public void setCurrentFrame(int c){
        currentFrame = c;
    }

    public void setFrameDelay(int t){
        if(frameDelay+t>0){
            frameDelay = frameDelay+t;
            System.out.println(frameDelay);
        }

    }

    private void addFrame(BufferedImage frame, int duration) {
        if (duration <= 0) {
            System.err.println("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }

        frames.add(new Frame(frame, duration));
        currentFrame = 0;
    }

    public BufferedImage getSprite() {
        return frames.get(currentFrame).getFrame();
    }

    public void update() {
        if (!stopped) {
            frameCount++;
            if(currentLoop==loopTotal&&loopTotal!=0){
                stop();
            }
            if (frameCount > frameDelay) {
                frameCount = 0;
                currentFrame += animationDirection;
                currentLoop++;

                if (currentFrame > totalFrames - 1) {
                    currentFrame = 0;
                }
                else if (currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                }
            }
        }

    }

    public void setDarkness(double darkness){
        for(int f =0;f<frames.size();f++){
            BufferedImage newFrame = frames.get(f).getFrame();
            for (int x = 0;x<frames.get(f).getFrame().getWidth();x++) {
                for(int y =0;y<frames.get(f).getFrame().getHeight();y++) {

                    int pixel = newFrame.getRGB(x,y);
                    //the image library uses a 32 bit integer
                    //so I had to get the values of the through the bits of the integer
                    //alpha controls the transparency of the pixel
                    int alpha = (pixel>>24) & 0xff;

                    int r = (pixel>>16) & 0xff;
                    int g = (pixel>>8) & 0xff;
                    int b = pixel & 0xff;

                    r = (int) (r *(1-darkness));
                    g = (int) (g *(1-darkness));
                    b = (int) (b *(1-darkness));

                    pixel = (alpha<<24) | (r<<16) | (g<<8) | b;

                    if(alpha>128) {//I choose to make it 128 in case of weird semi transparency
                        newFrame.setRGB(x, y, pixel);
                    }
                }
            }
            frames.set(f, new Frame(newFrame, duration));
        }

    }
}
