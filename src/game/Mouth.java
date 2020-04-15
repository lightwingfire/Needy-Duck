package game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Mouth {

    /*
    private Sprite ;
    private BufferedImage[] ;
    private Animation ;
     */

    private Animation chewing;
    private Animation de;

    private Animation animation;

    private Main game;
    public Mouth(Main game){
        this.game = game;

        Sprite eat = new Sprite("mouth//eating");
        BufferedImage[] eating = {eat.getSprite(0,0,64), eat.getSprite(1,0,64)};
        chewing = new Animation(eating,35);

        Sprite defaul = new Sprite("mouth//mouth");
        BufferedImage[] defa = {defaul.getSprite(0,0,64), defaul.getSprite(0,0,64)};
        de = new Animation(defa,10);

        setDefault();
    }

    public void setDefault(){
        animation = de;
        animation.stop();
    }

    public void setEating(){
        animation = chewing;
        animation.start();//need this here no del
    }

    public void tick(){
        animation.update();
    }
    public void render(Graphics g,int x,int y){
        g.drawImage(animation.getSprite(), x+42, y-49, game);
    }
}
