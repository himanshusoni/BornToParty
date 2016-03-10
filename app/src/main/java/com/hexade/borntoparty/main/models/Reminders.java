package com.hexade.borntoparty.main.models;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.hexade.borntoparty.main.Utils.DataManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * Created by himanshusoni on 2/28/16.
 */
public class Reminders {

    private ArrayList<Users.User> reminderList = new ArrayList<>();

    public Reminders(){

    }

    public ArrayList<Users.User> fetchReminders(Context context, Users user){

        Set<String> reminderNames = new DataManager(context).getReminders();
        Log.i("REMINDERS","is Reminder empty" + (reminderNames != null ? reminderNames.size() : "Empty"));
        for(String username : reminderNames){
            reminderList.add(user.getUserMap().get(username));
        }

        Collections.sort(reminderList, user.getComparator());

        return reminderList;
    }
}
