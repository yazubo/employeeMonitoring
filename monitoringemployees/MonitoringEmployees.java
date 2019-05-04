/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoringemployees;

import GUI.Utama;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 *
 * @author bherz
 */
public class MonitoringEmployees {
    
    public static Utama p;
    public static String ZIP_DIR = getProperty("SCREENSHOTS_DIR");
    public static String MAIL_USR_TO = getProperty("MAIL_USR");
    public static String MAIL_ADDR_TO = getProperty("MAIL_USR") + "@gmail.com";
    public static String ZIP_PASSWORD = decrypt(getProperty("ZIP_PASSWORD"));
    public static String MAIL_USR_FROM = "";
    public static String MAIL_PWD_FROM = "";
    public static String MAIL_ADDR_FROM = "";
    static GregorianCalendar cal = new GregorianCalendar();
    public static String Date = " "+(cal.get(GregorianCalendar.MONTH)+1)+"-"+cal.get(GregorianCalendar.DAY_OF_MONTH)+"-"+cal.get(GregorianCalendar.YEAR);
    public static void main(String[] args) {
        p = new Utama();
        p.setLocationRelativeTo(null);
        p.setVisible(true);
    }

    private static String getProperty(String prop) {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("userInfo.txt"));
            return props.getProperty(prop);
        } catch (IOException ex) {
            return "";
        }
    }

    private static String decrypt(String pwd) {
        BasicTextEncryptor bte = new BasicTextEncryptor();
        bte.setPassword("EM123456");
        String decrypted = "";
        try{
            decrypted = bte.decrypt(pwd);
        }catch(Exception x){
            Logger.getLogger(MonitoringEmployees.class.getName()).log(Level.SEVERE, "INVALID ZIP FILE PASSWORD, CONTACT YOUR EMPLOYER");
        }
        return decrypted;
    }
    
}
