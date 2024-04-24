package by.bsuir.senderserver.service;

import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    public boolean send(String message) {
        System.out.println("Phone message: " + message);
        return true;
    }
}
