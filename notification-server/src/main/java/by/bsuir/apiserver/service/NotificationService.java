package by.bsuir.apiserver.service;

import by.bsuir.apiserver.client.TemplateClient;
import by.bsuir.apiserver.exception.TemplateRecipientsNotFoundException;
import by.bsuir.apiserver.model.dto.kafka.RecipientListKafka;
import by.bsuir.apiserver.model.dto.response.RecipientResponse;
import by.bsuir.apiserver.model.dto.response.TemplateResponse;
import by.bsuir.apiserver.repository.NotificationRepository;
import by.bsuir.apiserver.utils.CollectionUtils;
import by.bsuir.apiserver.utils.NodeChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final KafkaTemplate<String, RecipientListKafka> kafkaTemplate;
    private final TemplateClient templateClient;
    private final NodeChecker nodeChecker;
    private final NotificationRepository notificationRepository;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.kafka.topics.splitter}")
    private String recipientListDistributionTopic;

    public String distributeNotifications(Long userId, Long templateId) {
        TemplateResponse templateResponse = templateClient.getTemplateByUserIdAndTemplateId(userId, templateId)
                .getBody();
        // TODO: List of IDs

        if (templateResponse == null) {
            return "TODO"; // TODO
        }

        List<Long> recipientIds = templateResponse.recipientIds()
                .stream()
                .map(RecipientResponse::id)
                .toList();
        if (recipientIds.isEmpty()) {
            throw new TemplateRecipientsNotFoundException(
                    String.format("Template %s does not contain any recipients for userId %s", templateId, userId)
            );
        }

        TemplateResponse createdTemplate = templateClient.createTemplate(userId, templateId)
                .getBody();

        for (List<Long> recipients : CollectionUtils.splitList(recipientIds, nodeChecker.getAmountOfRunningNodes(applicationName))) {
            RecipientListKafka listKafka = new RecipientListKafka(recipients, templateResponse, userId);
            kafkaTemplate.send(recipientListDistributionTopic, listKafka);
        }

        return "Notification's been successfully sent!";
    }
}
