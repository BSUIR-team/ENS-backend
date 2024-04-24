package by.bsuir.senderserver.model.enums;

import lombok.Getter;

@Getter
public enum NotificationType {
    PHONE("PHONE"),
    EMAIL("EMAIL");

    private final String code;

    NotificationType(String code) {
        this.code = code;
    }

}
