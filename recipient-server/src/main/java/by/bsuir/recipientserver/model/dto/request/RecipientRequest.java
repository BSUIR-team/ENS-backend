package by.bsuir.recipientserver.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RecipientRequest(
        @Size(max = 50)
        String name,
        @NotNull @Email @Size(max = 255)
        String email,
        @Size(max = 20)
        String phoneNumber
) {
}
