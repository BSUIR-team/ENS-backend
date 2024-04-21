package by.bsuir.apiserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @GetMapping("/hi")
    public String notification() {
        return "Hello World";
    }

}
