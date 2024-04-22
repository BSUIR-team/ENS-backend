package by.bsuir.recipientserver.controller;

import by.bsuir.recipientserver.model.dto.request.RecipientRequest;
import by.bsuir.recipientserver.model.dto.response.RecipientResponse;
import by.bsuir.recipientserver.service.RecipientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class RecipientController {

    private final RecipientService recipientService;

    @PostMapping("/")
    public ResponseEntity<RecipientResponse> register(
            @RequestHeader Long userId,
            @RequestBody @Valid RecipientRequest request
    ) {
        return ResponseEntity.status(CREATED).body(recipientService.register(userId, request));
    }

    @GetMapping("/")
    public ResponseEntity<List<RecipientResponse>> receiveByUserId(
            @RequestHeader Long userId
    ) {
        return ResponseEntity.status(OK).body(recipientService.receiveByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipientResponse> receive(
            @RequestHeader Long userId,
            @PathVariable("id") Long recipientId
    ) {
        return ResponseEntity.status(OK).body(recipientService.receive(userId, recipientId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @RequestHeader Long userId,
            @PathVariable("id") Long recipientId
    ) {
        return ResponseEntity.status(OK).body(recipientService.delete(userId, recipientId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RecipientResponse> update(
            @RequestHeader Long userId,
            @PathVariable("id") Long recipientId,
            @RequestBody @Valid RecipientRequest request
    ) {
        return ResponseEntity.status(OK).body(recipientService.update(userId, recipientId, request));
    }

    @GetMapping("/template/{id}")
    public ResponseEntity<List<RecipientResponse>> receiveByTemplateId(
            @RequestHeader Long userId,
            @PathVariable("id") Long templateId
    ) {
        return ResponseEntity.status(OK).body(recipientService.receiveByTemplate(userId, templateId));
    }

}
