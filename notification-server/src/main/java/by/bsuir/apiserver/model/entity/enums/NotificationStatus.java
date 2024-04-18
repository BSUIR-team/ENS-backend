package by.bsuir.apiserver.model.entity.enums;

public enum NotificationStatus {
    NEW,
    PENDING,
    SENT,
    RESENDING,
    ERROR,
    CORRUPT;

    public String getCode() {
        return name();
    }
}
