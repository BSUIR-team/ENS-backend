package by.bsuir.apiserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("Here we go again");
    }
}
