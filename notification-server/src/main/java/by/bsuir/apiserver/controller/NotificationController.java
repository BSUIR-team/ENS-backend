package by.bsuir.apiserver.controller;

import by.bsuir.apiserver.model.dto.kafka.NotificationKafka;
import by.bsuir.apiserver.model.dto.response.NotificationResponse;
import by.bsuir.apiserver.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/{id}")
    public ResponseEntity<String> notify(
            @RequestHeader Long userId,
            @PathVariable("id") Long templateId
    ) {
        return ResponseEntity.status(OK).body(notificationService.distributeNotifications(userId, templateId));
    }

    @PostMapping("/{id}/sent")
    public ResponseEntity<NotificationResponse> setNotificationAsSent(
            @RequestHeader Long userId,
            @PathVariable("id") Long notificationId
    ) {
        return ResponseEntity.status(OK).body(notificationService.setNotificationAsSent(userId, notificationId));
    }

    @PostMapping("/{id}/error")
    public ResponseEntity<NotificationResponse> setNotificationAsError(
            @RequestHeader Long userId,
            @PathVariable("id") Long notificationId
    ) {
        return ResponseEntity.status(OK).body(notificationService.setNotificationAsError(userId, notificationId));
    }

    @PostMapping("/{id}/corrupt")
    public ResponseEntity<NotificationResponse> setNotificationAsCorrupt(
            @RequestHeader Long userId,
            @PathVariable("id") Long notificationId
    ) {
        return ResponseEntity.status(OK).body(notificationService.setNotificationAsCorrupt(userId, notificationId));
    }

    @PostMapping("/{id}/resending")
    public ResponseEntity<NotificationResponse> setNotificationAsResending(
            @RequestHeader Long userId,
            @PathVariable("id") Long notificationId
    ) {
        return ResponseEntity.status(OK).body(notificationService.setNotificationAsResending(userId, notificationId));
    }

    @GetMapping("/")
    public ResponseEntity<List<NotificationKafka>> getNotificationsForRebalancing(
            @RequestParam(name = "pending", required = false, defaultValue = "10") Long pendingSec,
            @RequestParam(name = "new", required = false, defaultValue = "10") Long newSec,
            @RequestParam(name = "size", required = false, defaultValue = "20") Integer size
    ) {
        return ResponseEntity.status(OK).body(
                notificationService.getNotificationsForRebalancing(pendingSec, newSec, size)
        );
    }

}
