package by.bsuir.recipientserver.model.mapper;

import by.bsuir.recipientserver.model.dto.request.RecipientRequest;
import by.bsuir.recipientserver.model.dto.response.RecipientResponse;
import by.bsuir.recipientserver.model.entity.Recipient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipientMapper extends EntityMapper<Recipient, RecipientRequest, RecipientResponse> {
}
