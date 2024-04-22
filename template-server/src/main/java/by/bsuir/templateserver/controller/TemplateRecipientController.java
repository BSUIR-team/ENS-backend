package by.bsuir.templateserver.controller;

import by.bsuir.templateserver.model.dto.request.RecipientListRequest;
import by.bsuir.templateserver.model.dto.response.TemplateResponse;
import by.bsuir.templateserver.service.TemplateRecipientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class TemplateRecipientController {

    private final TemplateRecipientService templateRecipientsService;

    @PostMapping("/{id}/recipients")
    public ResponseEntity<TemplateResponse> addRecipients(
            @RequestHeader Long userId,
            @PathVariable("id") Long templateId,
            @RequestBody @Valid RecipientListRequest request
    ) {
        return ResponseEntity.status(CREATED).body(templateRecipientsService.addRecipients(userId, templateId, request));
    }

    @DeleteMapping("/{id}/recipients")
    public ResponseEntity<TemplateResponse> removeRecipients(
            @RequestHeader Long userId,
            @PathVariable("id") Long templateId,
            @RequestBody @Valid RecipientListRequest request
    ) {
        return ResponseEntity.status(OK).body(templateRecipientsService.removeRecipients(userId, templateId, request));
    }
}
