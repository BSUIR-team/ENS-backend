package by.bsuir.apiserver.model.mapper;

import by.bsuir.apiserver.model.dto.kafka.NotificationKafka;
import by.bsuir.apiserver.model.dto.request.NotificationRequest;
import by.bsuir.apiserver.model.dto.response.NotificationResponse;
import by.bsuir.apiserver.model.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper extends EntityMapper<Notification, NotificationRequest, NotificationResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "retryAttempts", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Notification toEntity(NotificationRequest request);

//    @Mapping(target = "template", expression = "java(templateClient.getTemplateByUserIdAndTemplateId(notification.getUserId(), notification.getTemplateId()).getBody())")
    NotificationResponse toDto(Notification notification);

    NotificationKafka mapToKafka(Notification notification);

    NotificationKafka mapToKafka(NotificationResponse notificationResponse);

}
