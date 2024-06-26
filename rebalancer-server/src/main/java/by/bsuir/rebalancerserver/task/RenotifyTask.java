package by.bsuir.rebalancerserver.task;

import by.bsuir.rebalancerserver.client.NotificationClient;
import by.bsuir.rebalancerserver.model.dto.kafka.NotificationKafka;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class RenotifyTask {

    @Value("${spring.kafka.topics.notifications.phone}")
    private String phoneTopic;

    @Value("${spring.kafka.topics.notifications.email}")
    private String emailTopic;

    @Value("${rebalancer.seconds-before-resend-pending}")
    private Long secondsBeforeResendPending;

    @Value("${rebalancer.seconds-before-resend-new}")
    private Long secondsBeforeResendNew;

    @Value("${rebalancer.max-amount-to-fetch}")
    private Integer amountToFetch;

    private final NotificationClient notificationClient;
    private final KafkaTemplate<String, NotificationKafka> kafkaTemplate;

    @Scheduled(fixedDelay = 5000)
    private void renotify() {
        List<NotificationKafka> notificationKafkaList = notificationClient.getNotificationsForRebalancing(
                secondsBeforeResendPending,
                secondsBeforeResendNew,
                amountToFetch
        ).getBody();

        if (notificationKafkaList == null || notificationKafkaList.isEmpty()) {
            return;
        }

        for (NotificationKafka notification : notificationKafkaList) {
            switch (notification.type()) {
                case PHONE -> kafkaTemplate.send(phoneTopic, notification);
                case EMAIL -> kafkaTemplate.send(emailTopic, notification);
            }
        }
    }
}
