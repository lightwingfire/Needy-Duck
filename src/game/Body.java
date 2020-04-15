package game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Body {

    /*
    private Sprite ;
    private BufferedImage[] ;
    private Animation ;
     */

    private Animation defaultT;
    private Animation defaultB;
    private Animation starved;
    private Animation fat;
    private Animation hunger;
    private Animation dirtiness;
    private Animation blush;

    private Animation top;
    private Animation bottom;
    private Main game;


    public Body(Main game){
        this.game = game;
        String file = "body//";

        Sprite toper = new Sprite(file + "bodyupperhalf");
        BufferedImage[] tops = {toper.getSprite(0,0,154)};
        defaultT = new Animation(tops,1000);

        Sprite bottomer = new Sprite(file + "bodylowerhalf");
        BufferedImage[] bottoms = {bottomer.getSprite(0,0,154)};
        defaultB = new Animation(bottoms,1000);

        Sprite ful = new Sprite(file + "bodylowerhalfFat");
        BufferedImage[] full = {ful.getSprite(0,0,154)};
        fat = new Animation(full,10000);

        Sprite star = new Sprite(file + "bodylowerhalfStarved");
        BufferedImage[] starv = {star.getSprite(0,0,154)};
        starved = new Animation(starv,1000);

        Sprite hun = new Sprite(file+"bodylowerhalfRumble");
        BufferedImage[] hung = {hun.getSprite(0,0,154),hun.getSprite(1,0,154),hun.getSprite(2,0,154),hun.getSprite(1,0,154),hun.getSprite(2,0,154),hun.getSprite(0,0,154),hun.getSprite(0,0,154),hun.getSprite(0,0,154),hun.getSprite(0,0,154),hun.getSprite(0,0,154),hun.getSprite(0,0,154),hun.getSprite(0,0,154),hun.getSprite(0,0,154),hun.getSprite(0,0,154),hun.getSprite(0,0,154),hun.getSprite(0,0,154)};
        hunger = new Animation(hung,15);

        Sprite dirt = new Sprite(file+"dirt");
        BufferedImage[] dirty = {dirt.getSprite(0,0,300),dirt.getSprite(1,0,300),dirt.getSprite(2,0,300),dirt.getSprite(3,0,300)};
        dirtiness = new Animation(dirty,1000);

        Sprite blu = new Sprite(file+ "bodyupperhalfBlush");
        BufferedImage[] blus = {blu.getSprite(0,0,154)};
        blush = new Animation(blus,1000);

        setDefaultT();
        setDefaultB();
        setDirtiness(0);
        dirtiness.start();

    }
    public void setDefaultT(){
        top = defaultT;
        top.start();
        //top.stop();
    }
    public void setDefaultB(){
        bottom = defaultB;
        bottom.start();
        bottom.stop();
    }
    public void setStarved(){
        bottom = starved;
    }
    public void setFat(){
        bottom = fat;
    }
    public void setRumble(){
        bottom = hunger;
        bottom.start();
    }
    public void setBlush(){
        top = blush;
        top.start();
        top.stop();
    }
    public void setDirtiness(int d){
        dirtiness.setCurrentFrame(d);
        dirtiness.stop();
    }
    public void tick(){
        top.update();
        bottom.update();
        //dirtiness.update();
    }
    public void render(Graphics g,int x,int y){
        //g.setColor(new Color(255,0,0));
        g.drawImage(top.getSprite(),x,y-154,game);
        g.drawImage(bottom.getSprite(),x,y,game);
        g.drawImage(dirtiness.getSprite(),x-50,y-130,game);
    }
}
