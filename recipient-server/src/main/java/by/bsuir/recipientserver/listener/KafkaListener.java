package by.bsuir.recipientserver.listener;

import by.bsuir.recipientserver.model.dto.kafka.TemplateRecipientKafka;
import by.bsuir.recipientserver.model.entity.Recipient;
import by.bsuir.recipientserver.model.entity.TemplateId;
import by.bsuir.recipientserver.repository.RecipientRepository;
import by.bsuir.recipientserver.repository.TemplateIdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class KafkaListener {

    private final TemplateIdRepository templateIdRepository;
    private final RecipientRepository recipientRepository;

    @Transactional
    @org.springframework.kafka.annotation.KafkaListener(
            topics = "#{ '${spring.kafka.topics.recipient-update}' }",
            groupId = "emergency",
            containerFactory = "listenerContainerFactory"
    )
    public void listener(TemplateRecipientKafka kafka) {
        switch (kafka.operation()) {
            case REMOVE -> {
                recipientRepository.findById(kafka.recipientId())
                        .map(recipient -> recipient.removeTemplate(kafka.templateId()))
                        .ifPresent(recipientRepository::saveAndFlush);
            }
            case PERSISTS -> {
                if (!templateIdRepository.existsByTemplateIdAndRecipientId(
                        kafka.templateId(),
                        kafka.recipientId()
                )) {
                    templateIdRepository.save(
                            TemplateId.builder() // TODO: mapper
                                    .recipient(
                                            Recipient.builder()
                                                    .id(kafka.recipientId())
                                                    .build()
                                    )
                                    .templateId(kafka.templateId())
                                    .build()
                    );
                }
            }
        }
    }
}
