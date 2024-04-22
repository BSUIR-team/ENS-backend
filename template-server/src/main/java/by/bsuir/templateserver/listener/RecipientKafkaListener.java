package by.bsuir.templateserver.listener;

import by.bsuir.templateserver.model.dto.kafka.TemplateRecipientKafka;
import by.bsuir.templateserver.model.mapper.RecipientIdMapper;
import by.bsuir.templateserver.repository.RecipientIdRepository;
import by.bsuir.templateserver.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class RecipientKafkaListener {

    private final RecipientIdRepository recipientIdRepository;
    private final TemplateRepository templateRepository;
    private final RecipientIdMapper mapper;

    @Transactional
    @KafkaListener(
            topics = "#{ '${spring.kafka.topics.template-update}' }",
            groupId = "emergency",
            containerFactory = "listenerContainerFactory"
    )
    public CompletableFuture<Void> listener(TemplateRecipientKafka kafka) {
        switch (kafka.operation()) {
            case REMOVE -> {
                templateRepository.findById(kafka.templateId())
                        .map(template -> template.removeRecipient(kafka.recipientId()))
                        .ifPresent(templateRepository::saveAndFlush);
            }
            case PERSISTS -> {
                if (!recipientIdRepository.existsByTemplateIdAndRecipientId(
                        kafka.templateId(),
                        kafka.recipientId()
                )) {
                    recipientIdRepository.save(mapper.toEntity(kafka));
                }
            }
        }
        return CompletableFuture.completedFuture(null);
    }
}
