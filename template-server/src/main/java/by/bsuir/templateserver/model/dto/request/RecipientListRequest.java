package by.bsuir.templateserver.model.dto.request;

import jakarta.validation.Valid;
import lombok.Builder;

import java.util.List;

@Builder
public record RecipientListRequest(
        @Valid
        List<Long> recipientIds
) {
}
