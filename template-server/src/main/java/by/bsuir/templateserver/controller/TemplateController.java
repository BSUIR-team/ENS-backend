package by.bsuir.templateserver.controller;

import by.bsuir.templateserver.model.dto.request.TemplateRequest;
import by.bsuir.templateserver.model.dto.response.TemplateResponse;
import by.bsuir.templateserver.service.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping("/")
    public ResponseEntity<TemplateResponse> create(
            @RequestHeader Long userId,
            @RequestBody @Valid TemplateRequest request
    ) {
        return ResponseEntity.status(CREATED).body(templateService.create(userId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemplateResponse> get(
            @RequestHeader Long userId,
            @PathVariable("id") Long templateId
    ) {
        return ResponseEntity.status(OK).body(templateService.get(userId, templateId));
    }

    @GetMapping("/")
    public ResponseEntity<List<TemplateResponse>> getAll(
            @RequestHeader Long userId
    ) {
        return ResponseEntity.status(OK).body(templateService.getAll(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @RequestHeader Long userId,
            @PathVariable("id") Long templateId
    ) {
        return ResponseEntity.status(OK).body(templateService.delete(userId, templateId));
    }
}
