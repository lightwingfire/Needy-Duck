package game;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Save {

    private Main game;

    private FileWriter myWriter;
    String filePath;
    String mySave;
    ArrayList<String> raw = new ArrayList<String>();
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> data = new ArrayList<String>();

    public Save(Main game) {
        this.game = game;
        //if the window is closed it saves the state of the duck
        WindowListener windowListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //System.out.println("CLOSING AND SAVING");
                saveDuck();
                //game.stop();
            }
        };
        FileSystem fs = FileSystems.getDefault();
        String sep = fs.getSeparator();
        game.window.addWindowListener(windowListener);
        mySave = new File("").getAbsolutePath()+sep+"res"+sep+"save.txt";
        filePath = new File("").getAbsolutePath()+sep+"res"+sep+"deaths.txt";
        //read();


    }
    public void read(){
        int line = 0;
        Scanner myReader = new Scanner(mySave);
        while(myReader.hasNextLine()){
            raw.add(myReader.nextLine());
            //title.add(myReader.nextLine());
            data.add("");
            title.add("");
            data.set(line,raw.get(line).replaceAll("\\D+",""));
            title.set(line,raw.get(line).replaceAll("\\d+",""));
            //System.out.println(title.get(line));
            line++;
        }
        System.out.println("READ FROM FILE");
    }
    public void write(){
        String dataCombined="";
        try {
            myWriter = new FileWriter(filePath);

            for(int x = 0; x<data.size();x++){
                dataCombined += title.get(x) +data.get(x)+"\n";
            }
            myWriter.write(dataCombined);
            myWriter.close();
            System.out.println("WROTE TO FILE");
            //System.out.println(dataCombined);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveDuck(){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(mySave);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(game.getDuck());
            out.close();
            fileOut.close();
            System.out.println("saved");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public Duck load(){
        Duck d = null;
        try {
            FileInputStream fileIn = new FileInputStream(mySave);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            d = (Duck) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Loaded");
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        return d;
    }

    public boolean isDead(){
        //System.out.println("duck is:"+load().isDead());
        if(load() == null){
            return true;
        }
        if(load().isDead()){
            return true;
        }
        return false;
    }


}
