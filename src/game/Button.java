package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//I made my own button class because I didn't quite understand the documentation
//plus gives me more control over how I want the buttons to act.
// if I were to redo these buttons from the ground up I would probably make them
//each components in the jFrame. I feel that would optimize the program better
// I would also introduce more methods for customization because how I have it now
//it is extremely hard coded and not very flexible
public class Button implements MouseListener {

    private Main game;

    private int x;
    private int y;
    private String name;
    private Sprite button;
    private Animation click;
    private Object obj;
    private String method;
    private int num;
    private boolean show;
    private int sizeX;
    private int sizeY;
    private MouseEvent e;

    public Button(int x, int y,int sizeX,int sizeY, String file,Object obj, String method,Main game){
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.name = file;
        this.game = game;
        this.obj = obj;
        this.method = method;
        num = 25;
        show=true;
        game.addMouseListener(this);
        String path = "button//";
        if(file !=null) {
            button = new Sprite(path + name);
            BufferedImage[] buttons = {button.getSprite(0, 0, 64, "Button"), button.getSprite(1, 0, 64, "Button")};
            click = new Animation(buttons, 3);
        }

    }
    public Button(int x, int y, int sizeX,int sizeY, String file,Object obj, String method,int num,Main game){
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.name = file;
        this.game = game;
        this.obj = obj;
        this.method = method;
        this.num = num;
        show=true;
        game.addMouseListener(this);
        String path = "button//";
        button = new Sprite(path+name);
        BufferedImage[] buttons = {button.getSprite(0,0,64),button.getSprite(0,0,64)};
        click = new Animation(buttons,10);

    }

    public void tick(){
        click.update();
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
        System.out.println(this.x+" "+this.y);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public int getMouseX(){
        Point p = MouseInfo.getPointerInfo().getLocation();
        return p.x;
    }
    public int getMouseY(){
        Point p = MouseInfo.getPointerInfo().getLocation();
        return p.y;
    }
    public boolean isRightClick(){
        if(SwingUtilities.isRightMouseButton(e)){
            return true;
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if((e.getX()>x&&e.getX()<(x+sizeX)&&e.getY()>y&&e.getY()<(y+sizeY))){
            this.e = e;
            if(click!=null) {
                click.loop(2);
                click.start();
            }
            System.out.println(method);
            Class c = obj.getClass();
            Method methods = null;
            try {
                if(num ==25) {
                    methods = c.getDeclaredMethod(method);
                }else {
                    methods = c.getDeclaredMethod(method, Integer.class);
                }
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            }
            try {
                if(num ==25) {
                    methods.invoke(obj);
                }else{
                    methods.invoke(obj,num);
                }

            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }


        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        //if(e.getX()>x&&e.getX()<(x+128)&&e.getY()>y&&e.getY()<(y+64)){
            //x=e.getX()+(x-e.getX());
            //System.out.println("Test");
            //click.setCurrentFrame(1);
        //}
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //if(e.getX()>x&&e.getX()<(x+128)&&e.getY()>y&&e.getY()<(y+64)){
            //System.out.println("Test");
            //click.setCurrentFrame(0);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {}


}
