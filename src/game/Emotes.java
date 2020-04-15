package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Emotes {
    private Main game;

    private Animation animation;

    private Animation heartAnimation;
    private Animation questionAnimation;
    private Animation zzAnimation;
    private Animation showerAnimation;
    private Animation deathAnimation;
    private Animation noSleepAnimation;

    public Emotes(Main game){
        this.game = game;
        String file = "emote//";
        Sprite heart = new Sprite(file+"hearts");
        BufferedImage[] hearts = {heart.getSprite(0,0 ), heart.getSprite(1, 0), heart.getSprite(2, 0), heart.getSprite(3, 0)};
        heartAnimation = new Animation(hearts, 20);

        Sprite question = new Sprite(file+"question");
        BufferedImage[] questions = {question.getSprite(0,0), question.getSprite(1,0), question.getSprite(2,0)};
        questionAnimation = new Animation(questions,20);

        Sprite zzz = new Sprite(file+"zzz");
        BufferedImage[] zz = {zzz.getSprite(0,0), zzz.getSprite(1,0), zzz.getSprite(2,0)};
        zzAnimation = new Animation(zz,20);

        Sprite sh = new Sprite(file+"shower");
        BufferedImage[] sho = {sh.getSprite(0,0),sh.getSprite(1,0)};
        showerAnimation = new Animation(sho,20);

        Sprite skull = new Sprite(file+"skull");
        BufferedImage[] dy = {skull.getSprite(0,0),skull.getSprite(1,0),skull.getSprite(2,0),skull.getSprite(3,0)};
        deathAnimation = new Animation(dy,20);

        Sprite ns = new Sprite(file+"noSleep");
        BufferedImage[] nsle = {ns.getSprite(0,0),ns.getSprite(1,0)};
        noSleepAnimation = new Animation(nsle,30);


        setQuestion();
        animation.start();
    }
    public void setNoSleep(){
        animation = noSleepAnimation;
        animation.start();
    }
    public void setHearts(){
        animation = heartAnimation;
        animation.start();
    }
    public void setDeath(){
        animation = deathAnimation;
        animation.start();
    }

    public void setQuestion(){
        animation = questionAnimation;
        animation.start();
    }

    public void setZZZ(){
        animation = zzAnimation;
        animation.start();
    }
    public void setShower(){
        animation = showerAnimation;
        animation.start();
    }
    /*public boolean isEnd(){
        if(animation.isLastFrame()){
            return true;
        }else{
            return false;
        }
    }*/

    public void tick(){
        animation.update();
    }
    public void render(Graphics g, int x, int y){
        g.drawImage(animation.getSprite(), x+90, y-109, game);
    }

}
