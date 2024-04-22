package by.bsuir.apiserver.model.dto.kafka;

import by.bsuir.apiserver.model.dto.response.TemplateResponse;

import java.util.List;

public record RecipientListKafka(
        List<Long> recipientIds,
        TemplateResponse templateHistoryResponse,
        Long clientId
) {
}
