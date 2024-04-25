package by.bsuir.senderserver.model.dto.kafka;

import by.bsuir.senderserver.model.enums.NotificationStatus;
import by.bsuir.senderserver.model.enums.NotificationType;

public record NotificationKafka(
        Long id,
        NotificationType type,
        String credential,
        String content,
        NotificationStatus status,
        Integer retryAttempts,
        Long userId
) {
}
