/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
/**
 *
 * @author bherz
 */
public class ScreenshotManager extends Thread{
    String FN;
    public ScreenshotManager(String fileName) {
        FN = fileName;
    }
    @Override
    public void run() {
        takeScreenshot(FN);
    }
    public void takeScreenshot(String fileName){
        try {
            //https://stackoverflow.com/questions/4490454/how-to-take-a-screenshot-in-java
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            File file = new File(fileName);
            ImageIO.write(image, "jpg", file);
        } catch (Exception ex) {
            ex = ex;
        }
    }
    
}
