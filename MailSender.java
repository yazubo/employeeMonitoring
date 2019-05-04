/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
import GUI.Utama;
import java.awt.Color;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart; 
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import monitoringemployees.MonitoringEmployees;
/**
 *
 * @author bherz
 */
public class MailSender extends Thread{
    public MailSender() {
    }
    @Override
    public void run(){
        try{
             //https://www.tutorialspoint.com/javamail_api/javamail_api_gmail_smtp_server.htm
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(MonitoringEmployees.MAIL_USR_FROM, MonitoringEmployees.MAIL_PWD_FROM);
                    }
                }
            );
            GregorianCalendar cal = new GregorianCalendar();
//            MimeBodyPart mbp1 = new MimeBodyPart();
            String h = (Utama.ticks/60*60) >= 10 ? ""+(Utama.ticks/60*60) : "0"+(Utama.ticks/60*60);
            String m = (Utama.ticks/60)%60 >= 10 ? ""+(Utama.ticks/60)%60 : "0"+(Utama.ticks/60)%60;
            String s = Utama.ticks%60 >= 10 ? ""+Utama.ticks%60 : "0"+Utama.ticks%60;           
            String mailReport = "Activity record of user "+MonitoringEmployees.MAIL_USR_FROM+" ("+MonitoringEmployees.MAIL_USR_FROM+"@gmail.com)\n";
            mailReport +="<html><br /><table border='0' width='70%'><tr><td align='center' width='45%'>Screenshot date</td><td width='10%'>&nbsp;</td><td align='center' width='45%'>Screenshot file name</td></tr>";
            Iterator iter = Utama.history.keySet().iterator();
            Object[] elem;
            Date dateSS;
            String nameSS;
            while (iter.hasNext()){
                elem = (Object[]) Utama.history.get((Integer)iter.next());
                dateSS = (Date) elem[0];
                nameSS = (String) elem[1];
                mailReport += "<tr><td align='center'>"+dateSS+"</td><td>&nbsp;</td><td align='center'>"+nameSS+"</td></tr>";
            }
            mailReport += "</table></html>";
            String mailFooter = "\nAttached file contains all screenshots taken today.\n\nTotal time watched: "+h+":"+m +":"+s+"\n\nMonitoringEmployees.-";
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(MonitoringEmployees.MAIL_ADDR_FROM));
            msg.addRecipient(Message.RecipientType.TO, 
            new InternetAddress(MonitoringEmployees.MAIL_ADDR_TO));
            msg.setSubject("Screenshots of "+(cal.get(GregorianCalendar.MONTH)+1)+"-"+cal.get(GregorianCalendar.DAY_OF_MONTH)+"-"+cal.get(GregorianCalendar.YEAR));
            Multipart multipart = new MimeMultipart();
            
            BodyPart part1 = new MimeBodyPart();
            part1.setContent(mailReport,"text/html");
            
            BodyPart part2 = new MimeBodyPart();
            part2.setText(mailFooter);
            multipart.addBodyPart(part1);
            multipart.addBodyPart(part2);
            
            BodyPart part3 = new MimeBodyPart();
            String zipFileName = MonitoringEmployees.ZIP_DIR.substring(0, MonitoringEmployees.ZIP_DIR.length()-4)+MonitoringEmployees.Date+".zip";
            FileDataSource fds = new FileDataSource(zipFileName);
            part3.setDataHandler(new DataHandler(fds));
            part3.setFileName(fds.getName());
            multipart.addBodyPart(part3);
            msg.setSentDate(new Date());
            msg.setContent(multipart);
            Transport.send(msg);
            System.exit(0);
        
        } catch (MessagingException ex){
            Utama.message.setForeground(new Color(255, 0, 29));
            Utama.message.setText("An error occured while sending mail, check mail data");
            //Utama.message.setText(MonitoringEmployees.);
            Utama.sending = false;
        }
    }
}
