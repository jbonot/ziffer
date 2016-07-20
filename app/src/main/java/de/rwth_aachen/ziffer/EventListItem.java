package de.rwth_aachen.ziffer;

import android.graphics.Color;

public class EventListItem {
    private String headline;
    private String description;
    private String level;
    private int id;
    private  int Event_id;

    public EventListItem(){

    }

    public EventListItem(String level, String headline, String description) {
        this.headline = headline;
        this.description = description;
        this.level = level.substring(0,2);

    }
    public EventListItem(String level, String headline, String description,int Event_id) {
        this.headline = headline;
        this.description = description;
        this.level = level.substring(0,2);
        this.Event_id=Event_id;

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


    public void setEvent_id(int Event_id) {
        this.Event_id = Event_id;
    }

    public int getEvent_id() {
        return this.Event_id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
