package by.bsuir.apiserver.model.entity;

import by.bsuir.apiserver.model.entity.enums.NotificationStatus;
import by.bsuir.apiserver.model.entity.enums.NotificationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long recipientId;

    private NotificationType type;
    private NotificationStatus status;

    private String message;
    private Integer retryAttempts;

}
