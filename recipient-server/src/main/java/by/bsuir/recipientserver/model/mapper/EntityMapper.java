package by.bsuir.recipientserver.model.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface EntityMapper<Entity, RequestDTO, ResponseDTO> {

    @Mapping(target = "id", ignore = true)
    Entity toEntity(RequestDTO requestDTO);

    ResponseDTO toDTO(Entity entity);

    @Mapping(target = "id", ignore = true)
    Entity updateEntity(RequestDTO requestDTO, @MappingTarget Entity entity);
}
