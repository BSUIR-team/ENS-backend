package by.bsuir.apiserver.listener;

import by.bsuir.apiserver.client.RecipientClient;
import by.bsuir.apiserver.model.dto.kafka.NotificationKafka;
import by.bsuir.apiserver.model.dto.kafka.RecipientListKafka;
import by.bsuir.apiserver.model.dto.request.NotificationRequest;
import by.bsuir.apiserver.model.dto.response.NotificationResponse;
import by.bsuir.apiserver.model.dto.response.RecipientResponse;
import by.bsuir.apiserver.model.dto.response.TemplateResponse;
import by.bsuir.apiserver.model.entity.enums.NotificationType;
import by.bsuir.apiserver.model.mapper.NotificationMapper;
import by.bsuir.apiserver.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class NotificationKafkaListener {

    private final KafkaTemplate<String, NotificationKafka> kafkaTemplate;
    private final NotificationService notificationService;
    private final RecipientClient recipientClient;
    private final NotificationMapper mapper;

    @Value("${spring.kafka.topics.notifications.email}")
    private String emailTopic;

    @Value("${spring.kafka.topics.notifications.phone}")
    private String phoneTopic;

    @KafkaListener(
            topics = "#{ '${spring.kafka.topics.splitter}' }",
            groupId = "emergency",
            containerFactory = "listenerContainerFactory"
    )
    private void listener(RecipientListKafka recipientListKafka) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Runnable runnable = () -> {
            Long userId = recipientListKafka.userId();
            TemplateResponse template = recipientListKafka.templateResponse();

            for (Long recipientId : recipientListKafka.recipientIds()) {
                RecipientResponse response;
                try {
                    response = recipientClient.receiveByUserIdAndRecipientId(userId, recipientId)
                            .getBody();
                } catch (RuntimeException e) {
                    System.out.println("Error receiving recipient " + recipientId);
                    continue;
                }

                if (response == null) {
                    continue;
                }

                sendNotificationByCredential(response::email, NotificationType.EMAIL, response, userId, template, emailTopic);
                sendNotificationByCredential(response::phoneNumber, NotificationType.PHONE, response, userId, template, phoneTopic);
            }
        };

        executorService.execute(runnable);
        executorService.shutdown();
    }

    private void sendNotificationByCredential(
            Supplier<String> supplier,
            NotificationType type,
            RecipientResponse recipientResponse,
            Long userId,
            TemplateResponse templateResponse,
            String topic
    ) {
        String credential = supplier.get();
        if (credential != null) {
            Long notificationId;
            try {
                notificationId = notificationService.createNotification(
                        NotificationRequest.builder()
                                .type(type)
                                .credential(credential)
                                .recipientId(recipientResponse.id())
                                .title(templateResponse.title())
                                .content(templateResponse.content())
                                .userId(userId)
                                .build()
                ).id();
            } catch (EntityNotFoundException e) {
                System.out.println("Error creating notification " + recipientResponse.id());
                return;
            }
            NotificationResponse response = notificationService.setNotificationAsPending(userId, notificationId);
            NotificationKafka notificationKafka = mapper.mapToKafka(response);
            kafkaTemplate.send(topic, notificationKafka);
        }
    }
}
