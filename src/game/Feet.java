package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class Feet {

        /*
    private Sprite ;
    private BufferedImage[] ;
    private Animation ;
     */
    private Animation standing;
    private Animation waddling;
    private Animation resting;
    private Animation animation;

    private Main game;

    public Feet(Main game){
        this.game=game;
        FileSystem fs = FileSystems.getDefault();
        String sep = fs.getSeparator();
        String file = "feet"+sep;

        Sprite defa = new Sprite(file + "standing");
        BufferedImage[] defau = {defa.getSprite(0,0,160), defa.getSprite(0,0,160)};
        standing = new Animation(defau,1000);

        Sprite wa = new Sprite (file+"walking");
        BufferedImage[] wad = {wa.getSprite(0,0,160),wa.getSprite(1,0,160)};
        waddling = new Animation(wad,30);

        Sprite sle = new Sprite(file + "sleeping");
        BufferedImage[] slee = {sle.getSprite(0,0,160), sle.getSprite(0,0,160)};
        resting = new Animation(slee,1000);

        setStanding();
        animation.start();
    }

    public void setStanding(){
        animation = standing;
    }
    public void setWaddling(){
        animation = waddling;
        animation.start();
    }
    public void setResting(){
        animation = resting;
        animation.start();
    }

    public void tick(){
        animation.update();
    }

    public void render(Graphics g, int x, int y){
        g.drawImage(animation.getSprite(), x-6, y+22, game);
    }
}
