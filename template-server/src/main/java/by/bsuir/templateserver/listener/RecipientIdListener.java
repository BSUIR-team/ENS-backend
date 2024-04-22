package by.bsuir.templateserver.listener;

import by.bsuir.templateserver.model.dto.kafka.Operation;
import by.bsuir.templateserver.model.dto.kafka.TemplateRecipientKafka;
import by.bsuir.templateserver.model.entity.RecipientId;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class RecipientIdListener {

    private final KafkaTemplate<String, TemplateRecipientKafka> kafkaTemplate;

    @Value("${spring.kafka.topics.recipient-update}")
    private String recipientUpdateTopic;

    @PostRemove
    public void postRemove(RecipientId recipientId) {
        sendKafkaEvent(recipientId, Operation.REMOVE);
    }

    @PostPersist
    public void postPersist(RecipientId recipientId) {
        sendKafkaEvent(recipientId, Operation.PERSISTS);
    }

    private void sendKafkaEvent(RecipientId recipientId, Operation operation) {
        kafkaTemplate.send(
                recipientUpdateTopic,
                TemplateRecipientKafka.builder()
                        .recipientId(recipientId.getRecipientId())
                        .templateId(recipientId.getTemplate().getId())
                        .operation(operation)
                        .build()
        );
    }
}
