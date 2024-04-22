package by.bsuir.apiserver.client;

import by.bsuir.apiserver.model.dto.response.TemplateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("${servers.template}")
public interface TemplateClient {

    @GetMapping("/templates/{id}")
    ResponseEntity<TemplateResponse> getTemplateByUserIdAndTemplateId(
            @RequestHeader Long userId,
            @PathVariable("id") Long templateId
    );

}
