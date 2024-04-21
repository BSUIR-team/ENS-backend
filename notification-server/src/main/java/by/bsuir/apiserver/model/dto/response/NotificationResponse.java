package by.bsuir.apiserver.model.dto.response;

import by.bsuir.apiserver.model.entity.enums.NotificationStatus;
import by.bsuir.apiserver.model.entity.enums.NotificationType;
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
