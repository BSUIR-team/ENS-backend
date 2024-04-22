package by.bsuir.templateserver.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record TemplateRequest(

        @NotNull @Size(min = 5, max = 25)
        String title,

        @NotNull @Size(min = 5, max = 225)
        String content
) {
}
