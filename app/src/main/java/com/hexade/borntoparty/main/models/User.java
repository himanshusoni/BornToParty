package com.hexade.borntoparty.main.models;

/**
 * Created by himanshusoni on 3/13/16.
 */
public class User {
    String id;
    Friend user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Friend getFriend() {
        return user;
    }

    public void setFriend(Friend friends) {
        this.user = friends;
    }
}
