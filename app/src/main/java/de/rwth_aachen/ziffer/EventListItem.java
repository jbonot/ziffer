package de.rwth_aachen.ziffer;

public class EventListItem {
    private String headline;
    private String description;

    public EventListItem(String headline, String description) {
        this.headline = headline;
        this.description = description;
    }

    public String getHeadline() {
        return this.headline;
    }

    public String getDescription() {
        return this.description;
    }

}
