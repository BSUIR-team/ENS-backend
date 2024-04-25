package by.bsuir.senderserver.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    static {
        Twilio.init(
                System.getenv("TWILIO_USERNAME"),
                System.getenv("TWILIO_PASSWORD")
        );
    }

    public Integer send(String phoneNumber, String message) {
        System.out.println("Phone message: " + message);
        Message sms = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber("+12513179103"),
                message
        ).create();
        return sms.getErrorCode();
    }
}
