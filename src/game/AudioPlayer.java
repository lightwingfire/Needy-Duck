package game;

import java.awt.image.BandCombineOp;

public class AudioPlayer{
    private Audio quack;
    private Audio background;

    public AudioPlayer(){
        quack = new Audio("quack");
        quack.volume( 0.2);

        background = new Audio("test");
        background.volume(0.00);
    }

    public void quack(){
        quack.play();
    }

    public void background(){
        background.playContiniously();
    }
}
