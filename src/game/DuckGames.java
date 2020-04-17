package game;

import java.awt.*;

//three games
//game where you bounce a ball off its head
//guess between three cards
//simon says
//A tag like game
public class DuckGames {

    private Button ball;
    private Button buttonBounce;
    private Main game;
    private Duck duck;
    private int buttonScroll;
    private int gameSelect;

    private int bounceBallTick;
    private boolean direction;
    private int vertexX;
    private int vertexY;

    public DuckGames(Duck duck,Main game){
        this.game = game;
        this.duck = duck;
        buttonBounce= new Button(384,576,128,64,"relaxButton",this,"bounceBall",this.game);
        buttonBounce.disable();
        ball = new Button (24,260,64,64,"relaxButton",this,"bounceBallHit",this.game);
        ball.setAnimation("bounceBall",7,0,30);

        ball.disable();
    }
    public void displayButtons(){
            buttonBounce.setLocation(384, 576);
            buttonBounce.enable();
            buttonScroll = 5;
            gameSelect = 0;
    }
    public void buttonScroll(){//maybe want to add a bounce to the button, idk
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
    public void gameButtonHide(){
        buttonBounce.disable();
    }
    public void stop(){

    }
    public void bounceBall(){//idealizes game
        this.gameButtonHide();
        gameSelect = 1;
        bounceBallTick=60;
        ball.enable();
        if (duck.getX()<300){
            ball.setLocation(duck.getX()+100,duck.getY()-50);
            direction = false;
        }else{
            ball.setLocation(duck.getX()-100,duck.getY()-50);
            direction = true;
        }
        vertexY = -640;

    }
    public void bounceBallGameTick(){
        if(vertexY !=-640) {
            ball.animateContiniously(true);
            int bx = 0;
            if (direction) {
                bx = ball.getX() + 1;
            } else {
                bx = ball.getX() - 1;
            }
            //y = a(x-h)^2+k
            //vertex of a parabola is (h,k)
            ball.setLocation(bx,((ball.getX() - vertexX) * (ball.getX() - vertexX)) / 100 + vertexY);//y
            if(ball.getX()>duck.getX()&&ball.getX()<duck.getX()+154&&ball.getY()<duck.getY()&&ball.getY()>duck.getY()-120){
                bounceBallHit();
                System.out.println("duck Volley");
            }
            if(ball.getY()>640){//this ends the game
                duck.tryPlay();
            }
        }
    }
    public void bounceBallHit(){
        if(direction){//if it was going right
            direction = false;
            vertexX = ball.getX()-150;
            ball.setLocation(ball.getX()+20,ball.getY());
        }else{//if it was going left
            direction = true;
            vertexX = ball.getX()+150;
            ball.setLocation(ball.getX()-20,ball.getY());
        }
        vertexY = ball.getY()-300;

    }

    public void tick(){
        if(gameSelect==1){
            bounceBallGameTick();
        }else if(gameSelect==2){

        }else if(gameSelect==3){

        }
        buttonBounce.tick();
        ball.tick();
        buttonScroll();
    }
    public void render(Graphics g){
        buttonBounce.render(g);
        ball.render(g);
    }
    public void setArms(Arms arms){
        arms.setSleep();
    }
}


