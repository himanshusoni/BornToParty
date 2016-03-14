package com.hexade.borntoparty.main.models;

import java.util.Comparator;
import java.util.List;

/**
 * Created by himanshusoni on 3/13/16.
 */
public class EventsWrapper {

    public int code;
    public String status;
    public int length;

    public List<Event> result;

    public EventsWrapper(List<Event> result) {

        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Event> getResult() {
        return result;
    }

    public void setResult(List<Event> result) {
        this.result = result;
    }

    public Comparator<Event> getComparator(){
        return new Comparator<Event>() {
            @Override
            public int compare(Event lhs, Event rhs) {
                return lhs.geteDate().compareTo(rhs.geteDate());
            }
        };
    }
}
