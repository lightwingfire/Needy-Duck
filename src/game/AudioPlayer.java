package game;

import java.awt.image.BandCombineOp;
import java.util.Random;

public class AudioPlayer{
    private Audio quack;
    private Audio step;
    private Audio background;
    private double gainControl;

    public AudioPlayer(){
        gainControl = 1;


    }

    public void quack(){
        quack = new Audio("quack");
        quack.volume( 0.2);
        quack.play();
    }
    public void footstep(){
        Random r = new Random();
        int rr = r.nextInt(3)+1;
        step = new Audio(String.format("footstep%s", rr));
        step.volume(0.001);
        step.play();
    }
    public void skrt(){
        Audio skrt = new Audio("skrt3");
        skrt.volume(0.01);
        skrt.play();
    }
    public void background(){
        background = new Audio("Duck_Song_Lively");
        background.volume(0.3);
        background.playContiniously();
    }
}
