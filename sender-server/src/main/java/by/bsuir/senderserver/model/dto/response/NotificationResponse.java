package by.bsuir.senderserver.model.dto.response;

import by.bsuir.senderserver.model.enums.NotificationStatus;
import by.bsuir.senderserver.model.enums.NotificationType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NotificationResponse(
        Long id,
        NotificationType type,
        String credential,
        NotificationStatus status,
        Integer retryAttempts,
        LocalDateTime createdAt,
        Long userId
) {
}
