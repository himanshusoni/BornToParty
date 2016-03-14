package com.hexade.borntoparty.main.models;

import java.util.Comparator;
import java.util.List;

/**
 * Created by himanshusoni on 3/13/16.
 */
public class FriendsWrapper {
//    private Number page;

    public int code;
    public String status;
    public int length;

    public List<User> result;

//    private Number total_pages;
//    private Number total_results;

    public FriendsWrapper(List<User> result) {

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

    public List<User> getResult() {
        return result;
    }

    public void setResults(List<User> result) {
        this.result = result;
    }

    public Comparator<User> getComparator(){
        return new Comparator<User>() {
            @Override
            public int compare(User lhs, User rhs) {
                return lhs.getFriend().getDaysLeft() - rhs.getFriend().getDaysLeft();
            }
        };
    }
}
