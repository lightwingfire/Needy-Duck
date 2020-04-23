package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import javax.imageio.ImageIO;

public class Sprite {

    private static BufferedImage spriteSheet;
    private static String filePath;
    private static final int TILE_SIZE = 32;
    public Sprite(String file){
        try {
            filePath = new File("").getAbsolutePath();
            //System.out.println(filePath);
            FileSystem fs = FileSystems.getDefault();
            String sep = fs.getSeparator();
            spriteSheet = ImageIO.read(new File(filePath+sep+"res"+sep+file+".png"));//"res/" + file + ".png"
        } catch (IOException e) {
            //System.out.println("IT FUCKED");
            e.printStackTrace();
        }
    }


    public static BufferedImage loadSprite(String file) {

        BufferedImage sprite = null;

        try {
            FileSystem fs = FileSystems.getDefault();
            String sep = fs.getSeparator();
            sprite = ImageIO.read(new File(filePath+sep+"res"+sep+file+".png"));//"res/" + file + ".png"
        } catch (IOException e) {
            //System.out.println("IT FUCKED");
            e.printStackTrace();
        }

        return sprite;
    }
    public static BufferedImage getSprite(){
        return spriteSheet;
    }
    public static BufferedImage getSprite(int xGrid, int yGrid) {
        if (spriteSheet == null) {
            spriteSheet = loadSprite("AnimationSpriteSheet");
        }

        return spriteSheet.getSubimage(xGrid * TILE_SIZE, yGrid * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
    public static BufferedImage getSprite(int xGrid, int yGrid,int tileSize) {

        if (spriteSheet == null) {
            spriteSheet = loadSprite("AnimationSpriteSheet");
        }

        return spriteSheet.getSubimage(xGrid * tileSize, yGrid * tileSize, tileSize, tileSize);
    }
    public static BufferedImage getSprite(int xGrid, int yGrid,int tileSize,String Button) {

        if (spriteSheet == null) {
            spriteSheet = loadSprite("AnimationSpriteSheet");
        }

        return spriteSheet.getSubimage(xGrid * tileSize*2, yGrid * tileSize, tileSize*2, tileSize);
    }
    public static BufferedImage getSprite(int xGrid, int yGrid, int xGrid2, int yGrid2) {

        if (spriteSheet == null) {
            spriteSheet = loadSprite("AnimationSpriteSheet");
        }

        return spriteSheet.getSubimage(xGrid*xGrid2 , yGrid *yGrid2 , xGrid2, yGrid2);
    }

}
