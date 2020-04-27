package game;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class Duck implements Serializable {

    private int x;
    private int y;
    private int destinX;
    private int destinY;
    private int nextX;
    private int nextY;
    private int mouseX;
    private int mouseY;

    private transient Eyes eye;
    private transient Emotes emote;
    private transient Mouth mouth;
    private transient Body body;
    private transient Arms arms;
    private transient Feet feet;

    private transient Button buttonSleep;
    private transient Button buttonClean;
    private transient Button buttonFeed;
    private transient Button buttonPlay;
    private transient Button buttonPotty;
    private transient Button buttonWaddle;
    private transient Button[] poops;

    private int ticksTillNextDrowsey;
    private int ticksTillNextHunger;
    private int ticksTillNextCleanliness;
    private int ticksTillNextPotty;
    private int ticksTillUnembarrassed;
    private int ticksTillNotTiredAnimation;
    private int ticksTillNextPlay;
    private int ticksTillNextWaddle;
    private int ticksWaddle;

    private int drowseyDecay;
    private int hungerDecay;
    private int cleanlinessDecay;
    private int pottyDecay;
    private int playDecay;

    private boolean isSleeping;
    private boolean isEating;
    private boolean isCleaning;
    private boolean isPottying;
    private boolean isEmbarrassed;
    private boolean isNotTired;
    private boolean isPlaying;
    private boolean isWaddling;
    private boolean isDead;

    private int eatCycle;
    private int poo;

    private int health;
    private int drowsy;
    private int hunger;
    private int cleanliness;
    private int potty;
    private int play;

    private transient Main games;
    private transient DuckGames duckGame;
    private transient AudioPlayer sound;
    public Duck(int drowseyDecay, int hungerDecay, int cleanlinessDecay, int pottyDecay, int playDecay, Main game){
        this.x = 230;
        this.y = 350;
        destinX = x;
        destinY = y;
        mouseX = -10;
        mouseY = -10;
        games = game;
        health =10;
        isDead = false;
        System.out.println("I am a new duck");

        this.drowseyDecay = drowseyDecay;
        ticksTillNextDrowsey = drowseyDecay;
        drowsy = rand(7,10);
        isSleeping = false;

        this.hungerDecay = hungerDecay;
        ticksTillNextHunger = hungerDecay;
        ticksTillNotTiredAnimation = 120;
        hunger = 10;
        isEating = false;

        this.cleanlinessDecay = cleanlinessDecay;
        ticksTillNextCleanliness = cleanlinessDecay;
        cleanliness = rand(5,10);
        isCleaning = false;

        this.pottyDecay = pottyDecay;
        ticksTillNextPotty = pottyDecay;
        ticksTillUnembarrassed = 150;
        potty =rand(5,10);
        isPottying = false;
        isEmbarrassed = false;
        poo = 0;
        poops = new Button[9];

        this.playDecay = playDecay;
        ticksTillNextPlay = playDecay;
        play = rand(5,10);
        isPlaying = false;

        ticksTillNextWaddle=600;
        ticksWaddle=60;
        isWaddling = false;

        loadTransientObjects(games);
    }
    public Duck(Main game){
        games = game;
    }
    public void loadTransientObjects(Main games){

        eye = new Eyes(games);
        emote = new Emotes(games);
        mouth = new Mouth(games);
        body = new Body(games);
        arms = new Arms(games);
        feet = new Feet(games);
        duckGame = new DuckGames(this,games);
        sound = new AudioPlayer();
        buttonSleep = new Button(0,576,128,64,"sleepButton",games);
        buttonClean = new Button(128,576,128,64,"cleanButton",games);
        buttonFeed = new Button(256,576,128,64,"feedButton",games);
        buttonPlay= new Button(384,576,128,64,"playButton",games);
        buttonPotty= new Button(513,576,128,64,"pottyButton",games);
        buttonWaddle = new Button(0,320,640,640,null,games);

        buttonSleep.setFunction(this,"trySleep");
        buttonClean.setFunction(this,"tryClean");
        buttonFeed.setFunction(this,"tryEat");
        buttonPlay.setFunction(this,"tryPlay");
        buttonPotty.setFunction(this,"tryPotty");
        buttonWaddle.setFunction(this,"tryWaddle");

        poops = new Button[9];

    }

    private int rand(int min, int max){
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;

    }


    //methods for button
    public void trySleep(){
        if(! isSleeping){
            isSleeping = true;
        }else{
            isSleeping = false;
        }
    }
    public void tryEat(){
        if(!isEating) {
            isEating = true;
        }else{
            isEating = false;
        }
        ticksTillNextHunger =0;
    }
    public void tryClean(){
        if(!isCleaning) {
            isCleaning = true;
        }else{
            isCleaning= false;
        }
        poops[poo] = null;
    }
    public void tryPotty(){
        if(!isPottying) {
            isPottying = true;
            ticksTillNextPotty = 400;
        }else{
            isPottying = false;
        }

    }
    public void tryPlay(){
        if(!isPlaying){
            isPlaying = true;
            duckGame.displayButtons();
        }else{
            isPlaying = false;
            duckGame.gameButtonHide();
        }


    }
    public void tryWaddle(){
        if(buttonWaddle.isRightClick()){
            System.out.println("rightClick");
            mouseX = buttonWaddle.getMouseX();
            mouseY = buttonWaddle.getMouseY();
            ticksTillNextWaddle = 0;
        }

    }

    public void addPlay(int addition){
        System.out.println("adding "+addition+" to play");
        play += addition;
    }

    public void cleanPoo(Integer num){
        poops[num].disable();
        poops[num] = null;
        poo--;
    }
    public boolean isDead(){
        if(health<1){
            System.out.println("DEAD");
            isDead = true;
            return true;
        }
        return false;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }


    private void sleepTick(){
        if(isSleeping&&drowsy>6){
            isSleeping = false;
            isNotTired = true;
        }
        if(isSleeping){//can't eat in sleep
            isEating = false;
        }
        ticksTillNextDrowsey--;
        if(ticksTillNextDrowsey<=0&&!isSleeping){
            if(drowsy<1){
                health--;
            }
            ticksTillNextDrowsey=drowseyDecay;
            drowsy--;
        }
        else if(ticksTillNextDrowsey<=0&&isSleeping){
            ticksTillNextDrowsey=drowseyDecay/2;
            drowsy++;
            System.out.println("sleeping");
        }

        if (drowsy<-2){
            drowsy=-2;
        }
        if(drowsy>9&&isSleeping){//should be adjusted based on relationship
            health += 3;////should be adjusted based on relationship
            isSleeping = false;
        }
        if(hunger<3){
            isSleeping=false;//wakes up duck if too hungry
        }
        if(isNotTired){
            ticksTillNotTiredAnimation--;
            if(ticksTillNotTiredAnimation<0){
                isNotTired = false;
                ticksTillNotTiredAnimation= 120;
            }
        }
    }
    private void hungerTick(){
        ticksTillNextHunger--;
        if (isEating){
            isPottying = false;
        }
        if(ticksTillNextHunger<=0&&!isEating){
            if(hunger<3){
                health--;
            }
            ticksTillNextHunger = hungerDecay;//relationship might effect
            hunger--;

        }
        else if(ticksTillNextHunger<=0&&isEating){
            ticksTillNextHunger = 70;//relationship might effect
            hunger++;
            eatCycle++;
            sound.quack();//TEST
            System.out.println("eating "+eatCycle);
        }
        if(eatCycle>5){//relationship might effect
            isEating = false;
            eatCycle = 0;
            System.out.println("Done eating");
        }
        if(hunger>18){//kill duck if it eats too much
            health = 0;
        }
    }
    private void cleanTick(){
        ticksTillNextCleanliness--;
        if (poops!=null&&poo>0){
            if(ticksTillNextCleanliness<=0&&!isCleaning){
                ticksTillNextCleanliness = cleanlinessDecay;//poo
                cleanliness--;
            }
            if (isCleaning){
                for (int r = 8;r>-1;r--){
                    if(poops[r]!=null) {
                        System.out.println(r);
                        cleanPoo(r);
                        return;
                    }
                }
                System.out.println("clean poop");
                isCleaning = false;
            }
        }
        if(ticksTillNextCleanliness<=0&&isCleaning){
            ticksTillNextCleanliness = 120;
            cleanliness++;
            System.out.println("Cleaning");
        }
        if(cleanliness>9&&isCleaning){
            isCleaning = false;
            System.out.println("Done Cleaning");
        }
        if(cleanliness<3){
            health--;
        }
        if(cleanliness<1){
            health--;
        }
    }
    private void pottyTick(){
        ticksTillNextPotty--;
        if(ticksTillNextPotty<0&&!isPottying&&!isSleeping){
            ticksTillNextPotty = pottyDecay;
            potty--;
        }
        if(ticksTillNextPotty<0&&isPottying){
            potty = 10;
            cleanliness--;
            isPottying = false;
            ticksTillNextPotty = pottyDecay;
            //poo++;//trouble shooting variable
            //ticksTillNextCleanliness = 0;
            System.out.println("Pooping");
        }
        if(potty<1){
            poops[poo] = new Button(rand(x-70,x+70),rand(y+50,y+100),64,64,"poo",games);
            poops[poo].setFunction(this,"cleanPoo",poo);
            poo++;
            isEmbarrassed = true;
            ticksTillNextWaddle = 0;
            ticksTillNextCleanliness = 0;
            potty = 10;
        }
        if(isEmbarrassed) {
            ticksTillUnembarrassed--;
            if(ticksTillUnembarrassed<0){
                isEmbarrassed=false;
                ticksTillUnembarrassed = 150;
            }
        }
    }
    private void playTick(){
        if(isPlaying){

        }else {
            ticksTillNextPlay--;
            if(ticksTillNextPlay<0){
                ticksTillNextPlay=playDecay;
                play--;
            }
            if(play<1){
                health--;
            }
        }

    }
    private void waddleTick(){
        if(!(isSleeping||isEating||isPottying||isCleaning)) {
            ticksTillNextWaddle--;
            boolean m = false;
            if (ticksTillNextWaddle < 0) {
                if(mouseX!=-10){//this is the code for the custom selection of where the duck will go
                    destinX = mouseX-80;
                    destinY = mouseY-100;
                    int prevX = nextX;
                    int prevY = nextY;
                    nextX = (destinX - x) / 30;//this is my slope so the duck can move at angles, it will move to its location in 30 tick intervals
                    nextY = (destinY - y) / 30;
                    if(isWaddling&&abs(nextX)>3&&((((prevX^nextX)<0)||nextX<=2&&nextX>=-2)&&((prevY^nextY)<0||nextY<=2&&nextY>=-2))) {
                        sound.skrt();
                    }
                    mouseX = -10;
                    mouseY = -10;
                    m = true;
                }else {
                    do {//this is the code for it randomly waddling around
                        destinX = rand(130, 470);
                        destinY = rand(299, 400);
                        nextX = (destinX - x) / 30;//this is my slope so the duck can move at angles, it will move to its location in 30 tick intervals
                        nextY = (destinY - y) / 30;
                        //System.out.println(nextX+" "+nextY);
                    } while (abs(nextX) < 3 && abs(nextY) < 3);
                }
                ticksTillNextWaddle =600;// rand(700,2000);
                System.out.println("@("+x+","+y+") going to ("+destinX+","+destinY+") at a rate of "+nextX+","+nextY);
                isWaddling = true;
            }
                ticksWaddle--;
                if (ticksWaddle < 0) {
                    ticksWaddle = 5;
                    //I am vv tired writing this if statement, I'm sure there is a much cleaner way of writing this
                    //its like this because there is a chance of the duck over shooting the destination because of integer math rounding, maybe if I had done vector stuff I wouldn't need it like this
                    //I need to figure out how to make the speed constant for the waddle so that the waddle animation looks ever so slightly less jank
                    if (destinX != x && destinX != x + 1 && destinX != x - 1 && destinX != x + 2 && destinX != x - 2 && destinX != x + 3 && destinX != x - 3 && destinX != x + 4 && destinX != x - 4 && destinX != x + 5 && destinX != x - 5) {
                        boolean waddleX = true;
                        boolean waddleY = true;
                        if (m||(x+80 >= 0 && x+80 <= 720)) {
                            x = x + nextX;
                        } else {
                            waddleX = false;
                        }
                        if (m||(y+170 >= 298 && y+170 <= 720)) {
                            y = y + nextY;
                        } else {
                            waddleY = false;
                        }
                        if (waddleX || waddleY) {//this prevents it from getting to the destination and just waddling in place
                            isWaddling = true;
                            sound.footstep();
                        } else {

                            isWaddling = false;
                        }
                        //System.out.println(x + " " + y);
                    } else {
                        isWaddling = false;
                    }
                }
        }else{
            isWaddling = false;
            if(ticksTillNextWaddle<1000) {
                ticksTillNextWaddle = rand(700, 2000);
            }
        }

    }
    private void statusTick(){
        //EYES
        {
            if (isSleeping) {
                eye.setEyeClose();
            } else if (drowsy < 4) {
                eye.setBlinkTired();
            } else {
                eye.setBlink();
            }

            if (isPottying) {
            eye.setReading();
            }else{
                eye.setNotReading();
            }
        }
        //MOUTH
        {
            if (isEating) {
                mouth.setEating();
            } else {
                mouth.setDefault();
            }
        }
        //ARMS
        {
            if (isSleeping) {
                arms.setSleep();
            } else if (isEating) {
                arms.setEat();
            }else if (isPottying) {
                arms.setPotty();
            }else if(isPlaying){
                duckGame.setArms(arms);
            }
            else {
                arms.setDefault();
            }
        }
        //LOWER BODY
        {
            if(hunger < 2){
                body.setStarved();
            }
            else if (hunger < 5) {
                body.setRumble();
            }else if (hunger>15){
                body.setFat();
            }else {
                body.setDefaultB();
            }
        }
        //UPPER BODY
        {
            if(isEmbarrassed){
                body.setBlush();
            }else {
                body.setDefaultT();
            }

        }
        //CLEANLINESS
        {
            if (cleanliness < 3) {
                body.setDirtiness(3);
            } else if (cleanliness < 4) {
                body.setDirtiness(2);
            } else if (cleanliness < 6) {
                body.setDirtiness(1);
            } else {
                body.setDirtiness(0);
            }
        }
        //FEET
        {
            if (isSleeping) {
                feet.setResting();
            } else if (isWaddling) {
                feet.setWaddling();
            } else {
                feet.setStanding();
            }
        }
        //EMOTES
        {
            if (health<5) {
                emote.setDeath();
            } else if (isCleaning) {
                emote.setShower();
            }else if(isNotTired) {
                emote.setNoSleep();
            }
            else if (drowsy < 3) {
                emote.setZZZ();
            }
            else if(isSleeping){
                emote.setZZZ();
            }
            else{
                //emote.setQuestion();
                //emote.setHearts();
            }
        }
    }

    public void tick(){
        for (Button poop : poops) {
            if (poop != null) {
                poop.tick();
            }
        }
        if(!isDead) {
            sleepTick();
            hungerTick();
            cleanTick();
            pottyTick();
            playTick();
            waddleTick();
            statusTick();

            body.tick();
            feet.tick();
            eye.tick();
            emote.tick();
            mouth.tick();
            arms.tick();

            buttonSleep.tick();
            buttonClean.tick();
            buttonFeed.tick();
            buttonPlay.tick();
            buttonPotty.tick();
            duckGame.tick();
        }
    }

    public void render(Graphics g){//default x=230, y=299
        if(!isDead) {

            body.render(g, x, y);
            feet.render(g, x, y);
            eye.render(g, x, y);
            mouth.render(g, x, y);
            arms.render(g, x, y);
            emote.render(g, x, y);

            duckGame.render(g);
            buttonSleep.render(g);
            buttonClean.render(g);
            buttonFeed.render(g);
            buttonPlay.render(g);
            buttonPotty.render(g);
        }
        for (Button poop : poops) {
            if (poop != null) {
                poop.render(g);
            }
        }

    }

    public void status(){//troubleshooting
        System.out.println("Heath:"+health+" Hunger:"+hunger+" Drowsy:"+drowsy+" Cleanliness:"+cleanliness+" Potty:"+potty+ " Play:"+play+" Poop:"+poo);
        System.out.println("isEating:"+isEating+" isSleeping:"+isSleeping+" isCleaning:"+isCleaning+" isPottying:"+isPottying+" isPlaying:"+isPlaying+" isEmbarrassed:"+isEmbarrassed);
    }

}
