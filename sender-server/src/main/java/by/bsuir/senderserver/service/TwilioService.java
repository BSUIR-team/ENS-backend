package by.bsuir.senderserver.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    static {
        Twilio.init(
                System.getenv("TWILIO_USERNAME"),
                System.getenv("TWILIO_PASSWORD")
        );
    }

    public Message.Status send(String phoneNumber, String message) {
        System.out.println("Phone message: " + message);
        //        Futures.addCallback(
//                future,
//                new FutureCallback<ResourceSet<Message>>() {
//                    public void onSuccess(ResourceSet<Message> messages) {
//                        for (Message message : messages) {
//                            System.out.println(message.getSid() + " : " + message.getStatus());
//                        }
//                    }
//                    public void onFailure(Throwable t) {
//                        System.out.println("Failed to get message status: " + t.getMessage());
//                    }
//                });
//        Message sms = Message.creator(
//                new PhoneNumber(phoneNumber),
//                new PhoneNumber("+12513179103"),
//                "Добрый день ебланы"
//        ).create();
//        return sms.getStatus();
        return Message.Status.ACCEPTED;
    }
}
