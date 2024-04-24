package by.bsuir.rebalancerserver.model.enums;

import lombok.Getter;

@Getter
public enum NotificationStatus {
    NEW("N"),
    PENDING("P"),
    SENT("S"),
    RESENDING("R"),
    ERROR("E"),
    CORRUPT("C");

    private final String code;

    NotificationStatus(String code) {
        this.code = code;
    }

}
