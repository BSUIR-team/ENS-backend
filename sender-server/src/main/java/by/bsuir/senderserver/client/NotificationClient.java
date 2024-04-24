package by.bsuir.senderserver.client;

import by.bsuir.senderserver.model.dto.response.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${servers.notification}")
public interface NotificationClient {

    @PostMapping(value = "/notifications/{id}/sent")
    ResponseEntity<NotificationResponse> setNotificationAsSent(
            @RequestHeader Long userId,
            @PathVariable("id") Long notificationId
    );

    @PostMapping(value = "/notifications/{id}/resending")
    ResponseEntity<NotificationResponse> setNotificationAsResending(
            @RequestHeader Long userId,
            @PathVariable("id") Long notificationId
    );

    @PostMapping(value = "/notifications/{id}/corrupt")
    ResponseEntity<NotificationResponse> setNotificationAsCorrupt(
            @RequestHeader Long userId,
            @PathVariable("id") Long notificationId
    );

    @PostMapping(value = "/notifications/{id}/error")
    ResponseEntity<NotificationResponse> setNotificationAsError(
            @RequestHeader Long userId,
            @PathVariable("id") Long notificationId
    );
}

