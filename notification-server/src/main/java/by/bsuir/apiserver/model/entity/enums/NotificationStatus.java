package by.bsuir.apiserver.model.entity.enums;

import lombok.Getter;

@Getter
public enum NotificationStatus implements EnumEntity {
    NEW("N"),
    PENDING("P"),
    SENT("S"),
    RESENDING("R"),
    ERROR("E"),
    CORRUPT("C"),
    IN_PROCESS("I");

    private final String code;

    NotificationStatus(String code) {
        this.code = code;
    }

}
