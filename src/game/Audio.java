package game;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class Audio {
    AudioInputStream audioInputStream;
    FloatControl gainControl;
    Clip clip;
    String filePath;
    String file;
    public Audio(String file){
        this.file = file;
         filePath = new File("").getAbsolutePath();
        getAudio();
    }

    public void play(){
        clip.stop();
        clip.close();
        getAudio();
        clip.start();
    }
    public void playContiniously(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }

    public void volume(double gain){//must be from 0.0-1.0
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
    private void getAudio(){
        try {
            FileSystem fs = FileSystems.getDefault();
            String sep = fs.getSeparator();
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath+sep+"res"+sep+"sound"+sep+file+".wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
