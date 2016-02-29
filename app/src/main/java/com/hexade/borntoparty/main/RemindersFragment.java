package com.hexade.borntoparty.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hexade.borntoparty.main.dummy.DummyReminder;
import com.hexade.borntoparty.main.dummy.DummyReminder.DummyItem;
import com.hexade.borntoparty.main.models.Reminders;
import com.hexade.borntoparty.main.models.Users;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnRemindersListFragmentInteractionListener}
 * interface.
 */
public class RemindersFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnRemindersListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RemindersFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RemindersFragment newInstance(int columnCount) {
        RemindersFragment fragment = new RemindersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminders_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
//            recyclerView.setAdapter(new MyRemindersRecyclerViewAdapter(DummyReminder.ITEMS, mListener));

            final Users users = new Users();
            users.fetch(Users.URL, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    final String responseString = response.body().string();
                    if (response.isSuccessful()) {
                        // Do what you want to do with the response.
                        Log.i("API - SUCCESS", responseString);

                        ArrayList<Users.User> list = users.createUsers(responseString);
                        Log.i("REMINDERS", " list size " + list.size());
                        final Reminders reminders = new Reminders();

                        // Always run the View update on the main/UI Thread
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            recyclerView.setAdapter(new MyRemindersRecyclerViewAdapter(getActivity(), reminders.fetchReminders(MainActivity.myAppContext, users), mListener));
                            }
                        });

                    } else {
                        // Request not successful
                        Log.i("API - ERROR", responseString);
                    }
                }
            });

        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRemindersListFragmentInteractionListener) {
            mListener = (OnRemindersListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRemindersListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRemindersListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRemindersListFragmentInteraction(Users.User item);
    }
}
