package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//I made my own button class because I wanted better control over the buttons than jframe offers
//(and I didn't understand the documentation at first so this was easier and turns out
// it was the easier option after all)
// if I were to redo these buttons from the ground up I would probably make them
//each components in the jFrame. I feel that would optimize the program better
// I would also introduce more methods for customization because how I have it now
//it is extremely hard coded and not very flexible
public class Button implements MouseListener, MouseMotionListener {

    private Main game;

    private int x;
    private int y;
    private int sizeX;
    private int sizeY;

    private String name;
    private Sprite button;

    private Animation click;
    private boolean show;

    private Object obj;
    private String method;
    private int num;

    private boolean functionHasIntegar;
    private boolean doToolTipTick;

    private MouseEvent e;

    public Button(int x, int y, int sizeX,int sizeY, String file,Main game){
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.name = file;
        this.game = game;
        functionHasIntegar = false;
        show=true;
        game.addMouseListener(this);
        if(file !=null) {
            setAnimation(file,0,0,3);
        }


    }

    public void setFunction(Object obj,String method){
        functionHasIntegar = false;
        this.obj = obj;
        this.method = method;
    }
    public void setFunction(Object obj,String method,int inputNum){
        functionHasIntegar = true;
        this.obj = obj;
        this.method = method;
        this.num = inputNum;
    }
    private void doFunction(){
        if(click!=null) {
            //click.setFrameDelay(-5);
            click.loop(2);
            click.start();
        }
        System.out.println(method);
        Class c = obj.getClass();
        Method methods = null;
        try {
            if(functionHasIntegar) {
                methods = c.getDeclaredMethod(method, Integer.class);
            }else {
                methods = c.getDeclaredMethod(method);
            }
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        }
        try {
            if(functionHasIntegar) {
                methods.invoke(obj,num);

            }else{
                methods.invoke(obj);
            }

        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }

    public void setAnimation(String name,int width, int height,int frameDelay){
        BufferedImage[] buttons;
        if(width*height==0){
            buttons = new BufferedImage[width+1];
        }else {
            buttons = new BufferedImage[width * height+1];
        }
        int frame = 0;
        String path = "button//";
        button = new Sprite(path+name);
        for(int y = 0;y<=height;++y){
            for(int x =0;x<=width;++x){
                //System.out.println(x+" "+y);
                buttons[frame]=button.getSprite(x, y, sizeX, sizeY);
                frame++;
            }
        }
        click = new Animation(buttons,frameDelay);
        //click.start();
    }
    public void animateContiniously(boolean animationContiniously){
        if(animationContiniously){
            click.start();
        }else{
            click.stop();
        }
    }


    public void tick(){
        click.update();
        //if(e!=null) {
           // setLocation((int) game.getMousePosition().getX(), (int) game.getMousePosition().getY());
        //}

    }
    public void render(Graphics g){
        if(show) {
            g.drawImage(click.getSprite(), x, y, game);
        }
    }

    public void disable(){
        game.removeMouseListener(this);
        show = false;
    }
    public void enable(){
        game.addMouseListener(this);
        show = true;
    }
    public boolean isEnable(){
        if(show){
            return true;
        }
        return false;
    }

    public void setLocation(int x1, int y1){
        this.x = x1;
        this.y = y1;
        //System.out.println(this.x+" "+this.y);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public int getMouseX(){
        return e.getX();
    }
    public int getMouseY(){
        return e.getY();
    }
    public boolean isRightClick(){
        if(SwingUtilities.isRightMouseButton(e)){
            return true;
        }
        return false;
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        /*
        if(e.getX()>x&&e.getX()<(x+128)&&e.getY()>y&&e.getY()<(y+64)) {
            System.out.println("Test");
            //click.setCurrentFrame(0);
        }

         */
        if((e.getX()>x&&e.getX()<(x+sizeX)&&e.getY()>y&&e.getY()<(y+sizeY))){
            this.e = e;
            doFunction();


        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {//if I had more time I would implement tooltips
        if((e.getX()>x&&e.getX()<(x+sizeX)&&e.getY()>y&&e.getY()<(y+sizeY))){
            doToolTipTick = true;

        }else{
            doToolTipTick = false;
        }
    }
    //rest are empty
    @Override
    public void mouseClicked(MouseEvent e) {
        this.e = e;
    }

    @Override
    public void mousePressed(MouseEvent e) {}


    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) {

    }


}
