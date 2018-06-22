package com.brsatalay.projectbase.library.core.data.model.event;

import android.support.annotation.IdRes;

import com.brsatalay.projectbase.library.core.data.model.EventType;

/**
 * Created by barisatalay on 20.03.2018.
 */

public class BaseEvent extends Throwable {
    private int id = -1;
    private String code;
    private EventType type;
    private String description;
    private @IdRes
    int messageResourceId = -1;
    private @IdRes
    int titleResourceId = -1;

    /**
     * @param message hata içeriği
     * */
    public BaseEvent(String message){
        super(message);
    }

    public BaseEvent(String message, Throwable throwable){
        super(message, throwable);
    }

    public BaseEvent(int messageResourceId){
        this.messageResourceId = messageResourceId;
    }
    public BaseEvent(int messageResourceId, int titleResourceId){
        this.messageResourceId = messageResourceId;
        this.titleResourceId = titleResourceId;
    }

    public BaseEvent(int messageResourceId, EventType type){
        this.messageResourceId = messageResourceId;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public BaseEvent setId(int id) {
        this.id = id;

        return this;
    }

    public String getCode() {
        if (code == null)
            code = "";
        return code;
    }

    public BaseEvent setCode(String code) {
        this.code = code;

        return this;
    }

    public EventType getType() {
        return type;
    }

    public BaseEvent setType(EventType type) {
        this.type = type;

        return this;
    }

    public String getDescription() {
        if (description == null)
            description = "";
        return description;
    }

    public BaseEvent setDescription(String description) {
        this.description = description;

        return this;
    }

//    public String getMessage() {
//        if (message == null)
//            message = "";
//        return message;
//    }
//
//    public BaseEvent setMessage(String message) {
//        this.message = message;
//
//        return this;
//    }

    public int getMessageResourceId() {
        return messageResourceId;
    }

    public BaseEvent setMessageResourceId(int messageResourceId) {
        this.messageResourceId = messageResourceId;

        return this;
    }

    public int getTitleResourceId() {
        return titleResourceId;
    }

    public BaseEvent setTitleResourceId(int titleResourceId) {
        this.titleResourceId = titleResourceId;

        return this;
    }
}
