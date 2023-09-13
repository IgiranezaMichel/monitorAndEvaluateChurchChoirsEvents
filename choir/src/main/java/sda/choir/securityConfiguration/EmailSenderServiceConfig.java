package sda.choir.securityConfiguration;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderServiceConfig {
    @Autowired private JavaMailSender mailsender;
    public void sendEmail(String toemail,String subject,String body) throws MessagingException
    {

        MimeMessage message = mailsender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setSubject(subject);
        helper.setTo(toemail);
        helper.setText(body, true);
        mailsender.send(message);       
    }
    public void sendSignUpEmail(String toemail,String subject,String username) throws MessagingException
    {
        String message="<body>\r\n" + //
                "  <h1>\r\n" + //
                "    Seventh-Day-Adventist\r\n" + //
                "  </h1>\r\n" + //
                "  <div>\r\n" + //
                "    Hi Dear,<b>"+username+"</b>\r\n" + //
                "  </div>\r\n" + //
                "  <div>\r\n" + //
                "    your account has been created sucessfully \r\n" + //
                "  </div>\r\n" + //
                "  <div>\r\n" + //
                "    email use : <b>"+toemail+"</b>\r\n" + //
                "  </div>\r\n" + //
                "</body>";
    this.sendEmail(toemail,subject,message);
    }
   public void eventForApprovalNotification
   (String toemail,String subject,String Tousername,
    String eventTitle,String invitedChoir,String choir,
    String invitedChoirLocation,String date
    ) throws MessagingException
    { SimpleDateFormat sFormat=new SimpleDateFormat("MMM-dd HH:mm)");
         String today=sFormat.format(new Date());
        String message="<body>\r\n" + //
                "  <h1>\r\n" + //
                "    Seventh-Day-Adventist\r\n" + //
                "  </h1>\r\n" + //
                "  <div>\r\n" + //
                "    Hi Dear,<b>"+Tousername+"</b>\r\n" + //
                " Choir "+invitedChoir+" from "+invitedChoirLocation+" needs for your approval </div>\r\n" + //
                "  <div>\r\n" + //
                " \r\n" + //
                "  </div>\r\n" + //
                "  <div>\r\n" + //
                "   to participate "+eventTitle+" that has been prepared by "+choir+" that will take place on "+date+"\r\n" + //
                "  </div>\r\n" + //
                "  <div>\r\n" + //
                "    email use : <b>"+toemail+"</b>\r\n" + //
                " <nav>   Done at : <b>"+today+"</b> </nav>\r\n" + //
                "  </div>\r\n" + //
                "</body>";
    this.sendEmail(toemail,subject,message);
    }
// @EventListener(ApplicationReadyEvent.class)
//     public void sendEmail() throws MessagingException
//     { 
//         this.sendEmail("rukundojustin372@gmail.com","Login Successfully","<!doctype html><html lang=\"en\"><head><title>Sign up</title><body><h1 style='background-color:grey'>Decease</h1><div style='background-color:white,border:1px solid black,border-radius:15'><h4>Welcome Page</h4><p>Dear , your account has been created successfully</p>"+
//         "<a href='http://localhost:8000'>Visit website</a></div></div></body></html>");
// System.out.println("Executed");   
    // }

}
