package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class Background {

    /*
    private Sprite ;
    private BufferedImage[] ;
    private Animation ;
     */

    private int menuY;
    private int ticksTillNextMove;
    private int ticksTillNextFade;
    private int ticksTillNight;
    private double darkness;
    public int fadeX;
    private Animation bgAnimation;
    private Animation deadAnimation;
    private Animation menuBackgroundAnimation;
    private Animation menuBackgroundAnimation2;
    private Animation fadeAnimation;

    private FileSystem fs = FileSystems.getDefault();
    private String sep = fs.getSeparator();
    String file = "background"+sep;

    private Sprite mn = new Sprite(file+"menuBackground1");
    private BufferedImage[] menu = {mn.getSprite(0,0,640),mn.getSprite(0,0,640)};
    private Sprite mn2 = new Sprite(file+"menuBackground2");
    private BufferedImage[] menu2 = {mn2.getSprite(0,0,640),mn2.getSprite(0,0,640)};
    private Sprite fa = new Sprite(file+"fade");
    private BufferedImage[] fad = {fa.getSprite(0,0,1920,640),fa.getSprite(0,0,1920,640)};
    private Sprite bg = new Sprite(file+"background");
    private BufferedImage[] back = {bg.getSprite(0,0,640),bg.getSprite(0,0,640)};


    private Main game;


    public Background(Main game){
        this.game = game;

        fadeX = -1320;
        ticksTillNight = 100;
        darkness = 0;

        Sprite ded = new Sprite(file+"dead");
        BufferedImage[] dead = {ded.getSprite(0,0,640), ded.getSprite(0,0,630)};
        deadAnimation = new Animation(dead,6000000);

        //bgAnimation.start();

    }
    public void background(){
        bgAnimation = new Animation(back,(6000000));
        bgAnimation.start();
    }
    public void fade(){
        //System.out.println("Sd");
        fadeAnimation = new Animation(fad,60);
        bgAnimation = null;
        fadeX = -1320;
        ticksTillNextFade=10;
        //fadeAnimation.start();

    }

    public void setDead(){

        bgAnimation = deadAnimation;
        bgAnimation.start();
        bgAnimation.stop();
    }
    public void menu(boolean t){
        if (t==true){
            menuBackgroundAnimation = new Animation(menu,60);
            menuBackgroundAnimation.start();
            menuY=0;
            menuBackgroundAnimation2 = new Animation(menu2,60);
            menuBackgroundAnimation2.start();

            ticksTillNextMove = 30;
        }else{
            menuBackgroundAnimation=null;
            menuBackgroundAnimation2=null;
        }


    }
    public void tick(){
        if(menuBackgroundAnimation!=null) {
            menuBackgroundAnimation.update();
            menuBackgroundAnimation2.update();
            menuY = (int) ( 10* Math.sin(0.0625*ticksTillNextMove)-1);
            ticksTillNextMove++;
            if(ticksTillNextMove>=360*1000){
                ticksTillNextMove=0;
            }
        }
        if(fadeAnimation!=null) {
            fadeAnimation.update();
            if (ticksTillNextFade<0){
                ticksTillNextMove = 10;
                fadeX=fadeX+25;
            }
            if(fadeX<1920) {
                ticksTillNextFade--;
            }
        }

        if(bgAnimation!=null) {
            bgAnimation.update();
        }
        /*ticksTillNight--;
        if(ticksTillNight<0){
            darkness+=0.1;
            if(!(bgAnimation==null)) {
                bgAnimation.setDarkness(.001);
            }
            ticksTillNight = 60;
        }*/
    }
    public void render(Graphics g){
        if(!(bgAnimation==null)) {
            g.drawImage(bgAnimation.getSprite(), 0, 0, game);
        }
        if(!(fadeAnimation==null)){
            g.drawImage(fadeAnimation.getSprite(), fadeX, 0, game);
        }
        if(!(menuBackgroundAnimation==null)) {
            g.drawImage(menuBackgroundAnimation.getSprite(), 0, 0, game);
            g.drawImage(menuBackgroundAnimation2.getSprite(), 0, menuY, game);
        }
    }

}
