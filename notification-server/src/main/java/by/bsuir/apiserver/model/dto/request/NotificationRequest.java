package by.bsuir.apiserver.model.dto.request;

import by.bsuir.apiserver.model.entity.enums.NotificationType;
import lombok.Builder;

@Builder
public record NotificationRequest(
        NotificationType type,
        String credential,
        Long recipientId,
        Long userId
) {
}
