package by.bsuir.templateserver.model.mapper;

import by.bsuir.templateserver.model.dto.request.TemplateRequest;
import by.bsuir.templateserver.model.dto.response.TemplateResponse;
import by.bsuir.templateserver.model.entity.Template;
import by.bsuir.templateserver.client.RecipientClient;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TemplateMapper extends EntityMapper<Template, TemplateRequest, TemplateResponse> {

    @Mapping(
            target = "recipientIds",
            expression = "java(recipientClient.receiveRecipientResponseListByTemplateId(template.getUserId(), template.getId()).getBody())"
    )
    TemplateResponse mapToResponse(Template template, @Context RecipientClient recipientClient);
}
