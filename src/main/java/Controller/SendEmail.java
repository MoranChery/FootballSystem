package Controller;

import Model.Alert;
import Model.Season;

import javax.mail.*;
import java.util.Properties;
import javax.mail.internet.*;
import javax.activation.*;


public class SendEmail {
    public static void main(String [] args) throws MessagingException {
        String email = "noasatla@gmail.com";
        Alert alert = new Alert("test mail", "Dear Nemo, \n this mail sent from our system");
        NotificationController nc = new NotificationController();
        boolean sent = nc.sendMessageInMail(email,alert);
        if(sent){
            System.out.println("sent");
        }
        else {
            System.out.println("no sent");
        }
    }

}
