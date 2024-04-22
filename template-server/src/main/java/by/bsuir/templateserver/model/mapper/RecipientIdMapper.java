package by.bsuir.templateserver.model.mapper;

import by.bsuir.templateserver.model.dto.kafka.TemplateRecipientKafka;
import by.bsuir.templateserver.model.entity.RecipientId;
import by.bsuir.templateserver.model.entity.Template;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        imports = {
                Template.class
        }
)
public interface RecipientIdMapper {

    @Mapping(target = "template", expression = "java(Template.builder().id(kafka.templateId()).build())")
    RecipientId toEntity(TemplateRecipientKafka kafka);
}