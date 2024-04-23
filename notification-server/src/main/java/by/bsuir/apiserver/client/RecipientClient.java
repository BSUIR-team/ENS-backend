package by.bsuir.apiserver.client;

import by.bsuir.apiserver.model.dto.response.RecipientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${servers.recipient}")
public interface RecipientClient {

    @GetMapping("/recipients/{id}")
    ResponseEntity<RecipientResponse> receiveByUserIdAndRecipientId(
            @RequestHeader Long userId,
            @PathVariable("id") Long recipientId
    );
}
