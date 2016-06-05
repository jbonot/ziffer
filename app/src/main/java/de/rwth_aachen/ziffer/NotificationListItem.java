package de.rwth_aachen.ziffer;

public class NotificationListItem {

    private String message;
    private String timestamp;

    public NotificationListItem(String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return this.message;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

}
