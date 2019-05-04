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
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import monitoringemployees.MonitoringEmployees;
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
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_MAXIMUM);
            parameters.setEncryptFiles(true); 
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            parameters.setPassword(MonitoringEmployees.ZIP_PASSWORD);
            String zipFileName = MonitoringEmployees.ZIP_DIR.substring(0, MonitoringEmployees.ZIP_DIR.length()-4)+MonitoringEmployees.Date+".zip";
            ZipFile zf = new ZipFile(new File (zipFileName));   
            zf.addFile(file, parameters);   
        } catch (Exception ex) {
            ex = ex;
        }
    }
    
}
