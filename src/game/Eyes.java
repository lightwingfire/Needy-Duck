package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class Eyes {

    private int ticksTillReading;
    private int readingShift;

    private Animation blinkAnimation;
    private Animation blinkTiredd;
    private Animation closer;

    private Animation animation;
    private Main game;
    public Eyes(Main game){
        this.game = game;
        FileSystem fs = FileSystems.getDefault();
        String sep = fs.getSeparator();
        String file = "eye"+sep;

        readingShift = 0;

        Sprite blink = new Sprite(file + "blink");
        BufferedImage[] blinks = {blink.getSprite(0,0), blink.getSprite(0,0), blink.getSprite(0,0), blink.getSprite(0,0), blink.getSprite(0,0), blink.getSprite(0,0), blink.getSprite(0,0), blink.getSprite(1,0)};
        blinkAnimation = new Animation(blinks, 10);

        Sprite blinkTired = new Sprite(file + "blinkTired");
        BufferedImage[] blinkTire = {blinkTired.getSprite(0,0), blinkTired.getSprite(0,0), blinkTired.getSprite(0,0), blinkTired.getSprite(0,0), blinkTired.getSprite(0,0), blinkTired.getSprite(0,0), blinkTired.getSprite(0,0), blinkTired.getSprite(1,0)};
        blinkTiredd = new Animation(blinkTire,12);

        Sprite c = new Sprite(file + "blink");
        BufferedImage[] closed = {c.getSprite(1,0), c.getSprite(0,0)};
        closer = new Animation(closed,10);


        setBlink();
        animation.start();
    }
    public void setBlink(){
        //animation.stop();
        animation = blinkAnimation;
        animation.start();

    }
    public void setEyeClose(){
        animation =closer;
    }
    public void setBlinkTired(){//this works don't mess with
        animation.stop();
        animation =  blinkTiredd;
        animation.start();
    }
    public void setReading(){


        ticksTillReading--;
        if (ticksTillReading<0){
            ticksTillReading = 60;
            if(readingShift==0){
                readingShift = 5;
            }else if(readingShift == 5){
                readingShift = -5;
            }else{
                readingShift = 0;
            }
        }
    }
    public void setNotReading(){
        readingShift = 0;
    }

    public void tick(){
        animation.update();
    }
    public void render(Graphics f, int x, int y){
        f.drawImage(animation.getSprite(),(x+40)+readingShift,y-59,game);//left eye
        f.drawImage(animation.getSprite(),(x+75)+readingShift,y-59,game);//right eye
    }
}
