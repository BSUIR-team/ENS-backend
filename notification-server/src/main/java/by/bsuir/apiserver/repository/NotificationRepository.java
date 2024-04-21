package by.bsuir.apiserver.repository;

import by.bsuir.apiserver.model.entity.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByIdAndUserId(Long notificationId, Long userId);

    @Query("""
            SELECT n
            FROM Notification n
            WHERE
            n.status = 'R'
                OR
            n.status = 'N' AND n.createdAt < :newDateTime
                OR
            n.status = 'P' AND n.createdAt  < :pendingDateTime
            """)
    List<Notification> findNotificationsByStatusAndCreatedAt(
            @Param("pendingDateTime") LocalDateTime pendingDateTime,
            @Param("newDateTime") LocalDateTime newDateTime,
            Pageable pageable
    );

}
