package by.bsuir.apiserver.model.entity.enums;

import lombok.Getter;

@Getter
public enum NotificationType implements EnumEntity {
    PHONE("PHONE"),
    EMAIL("EMAIL");

    private final String code;

    NotificationType(String code) {
        this.code = code;
    }

}
