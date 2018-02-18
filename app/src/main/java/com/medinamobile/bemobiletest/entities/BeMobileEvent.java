package com.medinamobile.bemobiletest.entities;

/**
 * Created by Erick on 2/17/2018.
 */

public class BeMobileEvent {

    private int eventType;
    private String error;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
