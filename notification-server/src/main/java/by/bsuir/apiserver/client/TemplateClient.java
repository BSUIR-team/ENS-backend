package by.bsuir.apiserver.client;

import by.bsuir.apiserver.model.dto.response.TemplateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("${servers.template}")
public interface TemplateClient {

    @GetMapping("/api/v1/templates/{id}")
    ResponseEntity<TemplateResponse> getTemplateByUserIdAndTemplateId(
            @RequestHeader Long clientId,
            @PathVariable("id") Long templateId
    );

    @PostMapping("/api/v1/templates/history/{id}")
    ResponseEntity<TemplateResponse> createTemplate(
            @RequestHeader Long clientId,
            @PathVariable("id") Long templateId
    );

    @GetMapping("/api/v1/templates/history/{id}")
    ResponseEntity<TemplateResponse> getTemplate(
            @RequestHeader Long clientId,
            @PathVariable("id") Long templateId
    );

}
