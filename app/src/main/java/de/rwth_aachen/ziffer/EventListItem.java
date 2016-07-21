package de.rwth_aachen.ziffer;

import android.graphics.Color;

public class EventListItem {
    private String headline;
    private String description;
    private String level;
    private int id;

    public EventListItem(){

    }

    public EventListItem(String level, String headline, String description) {
        this.headline = headline;
        this.description = description;
        this.level = level.substring(0,2);
    }

    public int getColor() {
        if (this.level.contains("A1")){
            return Color.parseColor("#cc0066");
        } else if (this.level.contains("A2")) {
            return Color.parseColor("#e6b800");
        } else if (this.level.contains("B1")) {
            return Color.parseColor("#00802b");
        } else if (this.level.contains("B2")) {
            return Color.parseColor("#336699");
        }

        return Color.parseColor("#990099");
    }

    public String getLevel() { return this.level; }

    public void setLevel(String level) {
        this.level = level.substring(0,2);
    }

    public String getHeadline() {
        return this.headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
