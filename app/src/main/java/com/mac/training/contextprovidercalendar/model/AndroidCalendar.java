package com.mac.training.contextprovidercalendar.model;

import android.provider.CalendarContract;

import java.io.Serializable;

/**
 * Created by User on 8/30/2016.
 */
public class AndroidCalendar {

    private long id;
    private String name;
    private String displayName;
    private String color;
    private boolean selected;

    public AndroidCalendar() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "AndroidCalendar{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", color='" + color + '\'' +
                ", selected=" + selected +
                '}';
    }
}
