package by.bsuir.apiserver.model.entity;

import by.bsuir.apiserver.model.entity.enums.NotificationStatus;
import by.bsuir.apiserver.model.entity.enums.NotificationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long recipientId;

    private NotificationType type;
    private String content;
    private String credential;

    @Builder.Default
    private Integer retryAttempts = 0;

    @Builder.Default
    private NotificationStatus status = NotificationStatus.NEW;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public Notification setNotificationStatus(NotificationStatus status) {
        this.setStatus(status);
        return this;
    }

    public Notification incrementRetryAttempts() {
        this.retryAttempts++;
        return this;
    }

    public Notification updateCreatedAt() {
        setCreatedAt(LocalDateTime.now());
        return this;
    }

}
