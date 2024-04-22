package by.bsuir.templateserver.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record RecipientListRequest(
        @Valid @NotEmpty
        List<Long> recipientIds
) {
}
