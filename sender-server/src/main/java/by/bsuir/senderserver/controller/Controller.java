package by.bsuir.senderserver.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/sendSMS")
    public ResponseEntity<String> sendMessage(@RequestParam String message) {
        Twilio.init(
                System.getenv("TWILIO_USERNAME"),
                System.getenv("TWILIO_PASSWORD")
        );
        Message.creator(
                new PhoneNumber("+375447429490"),
                new PhoneNumber("+12513179103"),
                message
        ).create();
        return new ResponseEntity<>("Message sent!", HttpStatus.OK);
    }
}
