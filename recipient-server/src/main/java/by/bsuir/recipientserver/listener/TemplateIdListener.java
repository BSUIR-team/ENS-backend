package by.bsuir.recipientserver.listener;

import by.bsuir.recipientserver.model.dto.kafka.TemplateRecipientKafka;
import by.bsuir.recipientserver.model.entity.TemplateId;
import by.bsuir.recipientserver.model.dto.kafka.Operation;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class TemplateIdListener {

    private final KafkaTemplate<String, TemplateRecipientKafka> kafkaTemplate;

    @Value("${spring.kafka.topics.template-update}")
    private String recipientUpdateTopic;

    @PostRemove
    public void postRemove(TemplateId templateId) {
        sendKafkaEvent(templateId, Operation.REMOVE);
    }

    @PostPersist
    public void postPersist(TemplateId templateId) {
        sendKafkaEvent(templateId, Operation.PERSISTS);
    }

    private void sendKafkaEvent(TemplateId templateId, Operation operation) {
        kafkaTemplate.send(
                recipientUpdateTopic,
                TemplateRecipientKafka.builder()
                        .recipientId(templateId.getRecipient().getId())
                        .templateId(templateId.getTemplateId())
                        .operation(operation)
                        .build()
        );
    }
}
