package by.bsuir.senderserver.listener;

import by.bsuir.senderserver.client.NotificationClient;
import by.bsuir.senderserver.model.dto.kafka.NotificationKafka;
import by.bsuir.senderserver.service.MailService;
import by.bsuir.senderserver.service.TwilioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class SenderKafkaListener {

    private final MailService mailService;
    private final TwilioService twilioService;
    private final NotificationClient notificationClient;
    private final Random random = new Random();

    @Value("${notification.max-retry-attempts}")
    private int maxRetryAttempts;

    @KafkaListener(
            topics = "#{ '${spring.kafka.topics.email}' }",
            groupId = "emergency",
            containerFactory = "listenerContainerFactory"
    )
    private void emailNotificationListener(NotificationKafka notification) {
        log(notification);
        Long userId = notification.userId();
        Long notificationId = notification.id();
        if (notification.retryAttempts() >= maxRetryAttempts) {
            notificationClient.setNotificationAsError(userId, notificationId);
        } else {
//            notificationClient.setNotificationAsResending(userId, notificationId);
            notificationClient.setNotificationAsSent(userId, notificationId);
            mailService.send(notification.credential(), notification.content());
        } /*else {
            notificationClient.setNotificationAsCorrupt(userId, notificationId);

        }*/
    }

    @KafkaListener(
            topics = "#{ '${spring.kafka.topics.phone}' }",
            groupId = "emergency",
            containerFactory = "listenerContainerFactory"
    )
    private void phoneNotificationListener(NotificationKafka notification) {
        log(notification);
        Long userId = notification.userId();
        Long notificationId = notification.id();
        notificationClient.setNotificationAsSent(userId, notificationId);
        if (notification.retryAttempts() >= maxRetryAttempts) {
            notificationClient.setNotificationAsError(userId, notificationId);
        } else {
//            CompletableFuture<ResourceSet<Message>> completableFuture = twilioService.send(notification.credential(), notification.content());
//            completableFuture.whenComplete((messages, throwable) -> {
//                if (throwable != null) {
//                    System.out.println("Чет херня какая-то");
//                } else {
//                    messages.forEach(message -> {
//                        if (message.getStatus().equals(Message.Status.FAILED) ||
//                                message.getStatus().equals(Message.Status.UNDELIVERED)
//                        ) {
//                            System.out.println("Чет не то: " + message.getStatus());
//                        }
//                    });
//                }
//            });
//            try {
//                completableFuture.get().forEach(
//                        message -> System.out.println("Вот что есть: " + message.getStatus())
//                );
//            } catch (Exception e) {
//                System.out.println("Тут исключение: " + e.getMessage());
//            }
            twilioService.send(notification.credential(), notification.content()));

//            if (isSent) {
//                notificationClient.setNotificationAsSent(userId, notificationId);
//            } else {
//                notificationClient.setNotificationAsResending(userId, notificationId);
//            }
        } /*else {
            notificationClient.setNotificationAsCorrupt(userId, notificationId);

        }*/
    }

    private void log(NotificationKafka notificationKafka) {
        log.info(
                "Sending {} notification to `{}`, status={}, retryAttempts={}",
                notificationKafka.type(),
                notificationKafka.credential(),
                notificationKafka.status(),
                notificationKafka.retryAttempts()
        );
    }

    private boolean random() {
        return random.nextInt(100) <= 33;
    }
}
