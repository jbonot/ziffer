package de.rwth_aachen.ziffer;

import android.graphics.Color;

public class EventListItem {
    private String headline;
    private String description;
    private String level;

    public EventListItem(String level, String headline, String description) {
        this.headline = headline;
        this.description = description;
        this.level = level;
    }

    public int getColor() {
        if (this.level.contains("A1")){
            return Color.RED;
        } else if (this.level.contains("A2")) {
            return Color.GREEN;
        } else if (this.level.contains("B1")) {
            return Color.MAGENTA;
        } else if (this.level.contains("B2")) {
            return Color.YELLOW;
        }

        return Color.CYAN;
    }

    public String getLevel() { return this.level; }

    public String getHeadline() {
        return this.headline;
    }

    public String getDescription() {
        return this.description;
    }

}
