package by.bsuir.templateserver.model.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface EntityMapper<Entity, RequestDTO, ResponseDTO> {

    @Mapping(target = "id", ignore = true)
    Entity toEntity(RequestDTO request);

    ResponseDTO toDto(Entity entity);

    @Mapping(target = "id", ignore = true)
    Entity updateEntity(RequestDTO request, @MappingTarget Entity entity);
}
