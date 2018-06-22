package com.brsatalay.projectbase.library.core.data.model;

/**
 * Created by barisatalay on 10.04.2018.
 */

public enum EventType {
    Error (1), Success(2), Information(3), Warning(4);
    private int value;

    EventType(int value) {
        this.value = value;
    }
    public int id(){
        return value;
    }

    public static EventType fromId(int value) {
        for(EventType item : values()) {
            if (item.value == value) {
                return item;
            }
        }
        return null;
    }
}
