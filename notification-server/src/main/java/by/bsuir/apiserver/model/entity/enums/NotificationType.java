package by.bsuir.apiserver.model.entity.enums;

public enum NotificationType {
    PHONE,
    EMAIL;

    public String getCode() {
        return name();
    }
}
