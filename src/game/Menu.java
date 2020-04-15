package game;

import java.awt.*;

import static java.lang.Math.round;

public class Menu {

    private Button buttonRelaxed;
    private Button buttonNormal;
    private Button buttonNeedy;
    private Button buttonContinue;

    private Button buttonReset;
    private Background fade;
    Main game;
    private int duckChoice;
    public Menu(Main game){
        this.game = game;
        buttonContinue = new Button (24,260,128,64,"contiButton",this,"contiButton",this.game);
        buttonRelaxed = new Button(24,327,128,64, "relaxButton", this, "relaxedButton",this.game);
        buttonNormal = new Button(24,394,128,64, "normalButton", this, "normalButton",this.game);
        buttonNeedy = new Button(24,461,128,64, "needyButton", this, "needyButton",this.game);

        buttonReset = new Button(384,461,128,64,"playButton",this,"resetButton",this.game);
        buttonReset.disable();



    }
    public void tick(){
        buttonContinue.tick();
        buttonNeedy.tick();
        buttonNormal.tick();
        buttonRelaxed.tick();

        buttonReset.tick();
        if(fade!=null)
            fade.tick();
        fadeTick();
    }

    public void render(Graphics g){
        buttonContinue.render(g);
        buttonRelaxed.render(g);
        buttonNormal.render(g);
        buttonNeedy.render(g);

        buttonReset.render(g);
        if(fade != null) {
         fade.render(g);
        }
    }

    public void mainMenu(){
        if(game.save.isDead()){
            buttonContinue.disable();
        }
        else{
            buttonContinue.enable();
        }
        buttonRelaxed.enable();
        buttonNormal.enable();
        buttonNeedy.enable();
        game.background.menu(true);
        game.background.background();
        fade = new Background(this.game);

    }
    public void reset(){
        buttonReset.enable();
    }


    public void fadeTick(){
        //System.out.println(fade.fadeX);
        if(fade!=null&&fade.fadeX > -640&&fade.fadeX<-615){
            if(duckChoice == 0){
                game.duck = new Duck(3600,
                        1800,
                        1800,
                        1800,
                        1800,game);
                duckChoice = -1;
            }
            else if(duckChoice == 1){
                game.duck = new Duck(1800,
                        900,
                        900,
                        900,
                        900,game);
                duckChoice = -1;
            }
            else if(duckChoice == 2){
                game.duck = new Duck(600,
                        300,
                        300,
                        300,
                        300,game);
                duckChoice = -1;
            }
            else if (duckChoice == 3){
                //game.save.load();
                game.duck = new Duck(game);
                game.duck = game.save.load();
                game.duck.loadTransientObjects(game);
                //game.duck.loadDuckParts();
                duckChoice = -1;
            }
            buttonContinue.disable();
            buttonRelaxed.disable();
            buttonNormal.disable();
            buttonNeedy.disable();
            game.background.menu(false);
            game.save.saveDuck();
        }
        if(fade!=null&&fade.fadeX>1920) {
            fade = null;
        }

    }

    public void contiButton(){
        duckChoice = 3;
        fade.fade();
    }
    public void relaxedButton(){
        duckChoice = 0;
        fade.fade();

    }
    public void normalButton(){
        duckChoice = 1;
        fade.fade();
    }
    public void needyButton(){
        duckChoice = 2;
        fade.fade();
    }
    public void resetButton(){
        buttonReset.disable();
        mainMenu();
    }
}
