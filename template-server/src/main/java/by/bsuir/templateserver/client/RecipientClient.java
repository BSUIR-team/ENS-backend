package by.bsuir.templateserver.client;

import by.bsuir.templateserver.model.dto.response.RecipientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "${servers.recipient}")
public interface RecipientClient {

    @GetMapping(value = "/recipients/{id}")
    ResponseEntity<RecipientResponse> receiveRecipientById(
            @RequestHeader Long userId,
            @PathVariable("id") Long recipientId
    );

    @GetMapping(value = "/recipients/template/{id}")
    ResponseEntity<List<RecipientResponse>> receiveRecipientResponseListByTemplateId(
            @RequestHeader Long userId,
            @PathVariable("id") Long templateId
    );
}
