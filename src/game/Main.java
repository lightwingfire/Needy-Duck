package game;
//Program by Jakob Coughlan
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class Main extends Canvas implements Runnable{

    public static final int WIDTH = 315;
    public static final int HEIGHT = 315;
    public static final int SCALE = 2;
    public static final String TITLE = "Needy Duckling";

    public boolean running = false;
    private Thread thread;
    public Duck duck;
    public Background background;


    public static JFrame window;

    public Save save;
    private AudioPlayer music;
    private Menu menu;
    public static void main(String[] args) {

        Main game = new Main();
        game.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        game.setMaximumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        game.setMinimumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));

        String filePath = new File("").getAbsolutePath();
        FileSystem fs = FileSystems.getDefault();
        String sep = fs.getSeparator();
        ImageIcon defaultIcon = new ImageIcon(filePath+sep+"res"+sep+"icon.png");

        window = new JFrame(game.TITLE);//creates window
        window.setIconImage(defaultIcon.getImage());
        window.add(game);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);

        game.start();
    }

    private synchronized void start(){//creates thread that program runs on
        if(running){
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    private synchronized void stop(){//stops thread tat program runs on
        if(!running){
            return;
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    private void init(){
        background = new Background(this);
        save = new Save(this);
        background.background();

        music = new AudioPlayer();
        music.background();
        menu = new Menu(this);
        menu.mainMenu();

    }

    @Override
    public void run() {
    init();
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000 /amountOfTicks;
        double delta = 0;
        int updates =0;
        int frames = 0;
        long timer=System.currentTimeMillis();
        while (running){//this is the loop for the whole program
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1){//This if stops the ticks from going higher than the number of ticks I want per seconds
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;
            if(System.currentTimeMillis()-timer>1000){
                timer+=1000;
                System.out.println(updates+" Ticks, Fps "+frames);
                if (duck!=null) {
                    duck.status();//troubleshooting
                }
                updates =0;
                frames =0;
            }
        }
        stop();
    }
    private void tick(){
        if (duck==null){//MIGHT NEED TO BE CHANGED
            background.tick();
            menu.tick();
        }else if(duck.isDead()){
            save.saveDuck();
            duck = null;
            window.setTitle("Dead Duckling");
            background.setDead();
            menu.reset();
        }else {
            window.setTitle("Needy Duckling");
            background.tick();
            duck.tick();
            menu.tick();
        }




    }
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();

        if (bs==null){
            createBufferStrategy(4);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        ///////////////////////////////
        background.render(g);
        if (duck!=null){
        duck.render(g);

        }

        menu.render(g);

        ///////////////////////////////
        g.dispose();
        bs.show();
    }

    public Duck getDuck(){
        return duck;
    }




}
