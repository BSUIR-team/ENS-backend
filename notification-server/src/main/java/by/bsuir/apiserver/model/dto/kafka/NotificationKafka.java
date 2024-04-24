package by.bsuir.apiserver.model.dto.kafka;

import by.bsuir.apiserver.model.entity.enums.NotificationStatus;
import by.bsuir.apiserver.model.entity.enums.NotificationType;

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
