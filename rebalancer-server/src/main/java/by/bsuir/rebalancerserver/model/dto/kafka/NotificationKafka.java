package by.bsuir.rebalancerserver.model.dto.kafka;

import by.bsuir.rebalancerserver.model.enums.NotificationStatus;
import by.bsuir.rebalancerserver.model.enums.NotificationType;

public record NotificationKafka(
        Long id,
        NotificationType type,
        String credential,
        NotificationStatus status,
        Integer retryAttempts,
        Long userId
) {
}
