package com.hexade.borntoparty.main.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.hexade.borntoparty.main.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by himanshusoni on 2/28/16.
 */
public class DataManager {

    private static final String TAG_REMINDERS = "reminders";

    Set<String> reminderList;

    SharedPreferences sharedPref;

    public DataManager(Context appContext){
        sharedPref = PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    public void setReminder(String username){
        SharedPreferences.Editor editor = sharedPref.edit();

        Set<String> reminders = getReminders();
        if(reminders == null){
            reminders = new HashSet<>();
        }
        reminders.add(username);

        Log.i("REMINDER", "new size " + reminders.size());
        reminderList = reminders;

        editor.putStringSet(TAG_REMINDERS, reminders);
        editor.commit();
    }

    public Set<String> getReminders(){
        return sharedPref.getStringSet(TAG_REMINDERS, null);
    }
}
