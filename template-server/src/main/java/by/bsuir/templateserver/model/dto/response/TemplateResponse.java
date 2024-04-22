package by.bsuir.templateserver.model.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record TemplateResponse(
        Long id,
        String title,
        String content,
        List<RecipientResponse> recipientIds
) {
}
