package com.hexade.borntoparty.main.UI.Fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment
                                implements DatePickerDialog.OnDateSetListener {

    public static final String TAG_DATE_PICKER = "tag_date_picker";

    private OnDatePickerSetListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.mListener = (OnDatePickerSetListener)activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.mListener.OnDatePickerSet(year, month, day);
    }

    public static interface OnDatePickerSetListener {
        public abstract void OnDatePickerSet(int year, int month, int day);
    }
}
