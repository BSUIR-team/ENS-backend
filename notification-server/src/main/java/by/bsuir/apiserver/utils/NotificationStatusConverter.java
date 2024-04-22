package by.bsuir.apiserver.util;

import by.bsuir.apiserver.model.entity.enums.NotificationStatus;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NotificationStatusConverter extends BaseEnumConverter<NotificationStatus> {

    public NotificationStatusConverter() {
        super(NotificationStatus.class);
    }
}

