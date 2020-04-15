package game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Arms {


    private Animation defaul;
    private Animation sleep;
    private Animation eat;

    private Animation animation;
    private Main game;
    private Animation potty;

    public Arms(Main game){
        this.game = game;
        String file = "arm//";

        /*
    private Sprite ;
    private BufferedImage[] ;
    private Animation ;
     */
        Sprite defa = new Sprite(file + "wings");
        BufferedImage[] defau = {defa.getSprite(0,0,160), defa.getSprite(0,0,160)};
        defaul = new Animation(defau,1000);

        Sprite sl = new Sprite(file + "wingsSleep");
        BufferedImage[] slee = {sl.getSprite(0,0,160)};
        sleep = new Animation(slee,20);

        Sprite ea = new Sprite(file + "wingsEating");
        BufferedImage[]eea = {ea.getSprite(0,1,160),
                                ea.getSprite(1,0,160),ea.getSprite(1,1,160),
                                ea.getSprite(2,0,160),ea.getSprite(2,1,160),
                                ea.getSprite(3,0,160),ea.getSprite(3,1,160),
                                ea.getSprite(4,0,160),ea.getSprite(4,1,160),
                                ea.getSprite(0,0,160)};
        eat = new Animation(eea,35);
        eat.loop(1);

        Sprite nes = new Sprite(file+"wingsPotty");
        BufferedImage[]nees = {nes.getSprite(0,0,160)};
        potty = new Animation(nees,20);

        setDefault();
        //animation.start();

    }
    public void setDefault(){
        animation = defaul;
        animation.stop();
    }
    public void setSleep(){
        animation = sleep;
        animation.stop();
    }
    public void setEat(){
        animation = eat;
        animation.start();
    }
    public void setPotty(){
        animation = potty;
        animation.stop();
    }
    public void tick(){
        animation.update();
    }
    public void render(Graphics g, int x, int y){
        g.drawImage(animation.getSprite(),x-4,y-69,game);
    }
}
