package by.bsuir.senderserver.model.enums;

import lombok.Getter;

@Getter
public enum NotificationStatus {
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
