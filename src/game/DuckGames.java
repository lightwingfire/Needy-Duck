package game;

import java.awt.*;

//three games
//game where you bounce a ball off its head
//guess between three cards
//simon says
//A tag like game
public class DuckGames {

    private Button buttonBounce;
    private Main game;
    private Duck duck;
    private int buttonScroll;


    public DuckGames(Duck duck,Main game){
        this.game = game;
        this.duck = duck;
        buttonBounce= new Button(384,576,128,64,"playButton",this,"bounceBall",this.game);
        buttonBounce.disable();
    }
    public void displayButtons(){
        if(!buttonBounce.isEnable()) {
            buttonBounce.setLocation(384, 576);
            buttonBounce.enable();
            buttonScroll = 5;
        }
    }
    public void buttonScroll(){
        if (buttonScroll !=0) {
            //ticksTillNextButtonScroll--;
            //if(ticksTillNextButtonScroll<0) {
                buttonBounce.setLocation(buttonBounce.getX(), buttonBounce.getY() - buttonScroll);
                //ticksTillNextButtonScroll=1;
           // }
            if(buttonBounce.getY()<=512){
                buttonScroll = 0;
            }
        }
    }
    public void stop(){

    }
    public void bounceBall(){

    }

    public void tick(){
        buttonBounce.tick();
        buttonScroll();
    }
    public void render(Graphics g){
        buttonBounce.render(g);
    }
    public void setArms(Arms arms){
        arms.setSleep();
    }
}


