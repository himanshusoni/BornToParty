package com.hexade.borntoparty.main.UI.Fragments;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.hexade.borntoparty.main.BTPApi;
import com.hexade.borntoparty.main.MyBirthdayRecyclerViewAdapter;
import com.hexade.borntoparty.main.MyEventsRecyclerViewAdapter;
import com.hexade.borntoparty.main.R;
import com.hexade.borntoparty.main.dummy.DummyEvent;
import com.hexade.borntoparty.main.dummy.DummyEvent.DummyItem;
import com.hexade.borntoparty.main.models.Event;
import com.hexade.borntoparty.main.models.EventsWrapper;
import com.hexade.borntoparty.main.models.Friend;
import com.hexade.borntoparty.main.models.FriendsWrapper;
import com.hexade.borntoparty.main.models.User;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnEventsListFragmentInteractionListener}
 * interface.
 */
public class EventsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnEventsListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EventsFragment newInstance(int columnCount) {
        EventsFragment fragment = new EventsFragment();
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
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

            JsonDeserializer js = new JsonDeserializer() {

                @Override
                public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    JsonObject content = json.getAsJsonObject();
                    EventsWrapper message = new Gson().fromJson(json, typeOfT);

                    int i = 0;
                    for(Event e : message.getResult()){
                        e.seteDate(new Date()); // this will ignore this parameter and the date using local eventDate variable
                    }

                    if(message.getResult().size()!=0){
                        Collections.sort(message.getResult(), message.getComparator());
                    }

                    return message;
                }
            };

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(EventsWrapper.class, js);
            Gson gson = gsonBuilder.create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(getString(R.string.API_URL))
                    .setConverter(new GsonConverter(gson))
                    .build();
            BTPApi methods = restAdapter.create(BTPApi.class);
            Callback<EventsWrapper> callback = new Callback<EventsWrapper>() {
                @Override
                public void success(EventsWrapper e, Response response) {
                    Log.i("HIM", e.getLength() + "");
//                    recyclerView.setAdapter(new MyBirthdayRecyclerViewAdapter(getActivity(), f.getResult() , mListener));
                    recyclerView.setAdapter(new MyEventsRecyclerViewAdapter(e.getResult(), mListener));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.e("HIM",retrofitError.getMessage());
                    retrofitError.printStackTrace();

                }
            };
            methods.getGetEvents("crazysnake682", callback);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventsListFragmentInteractionListener) {
            mListener = (OnEventsListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEventsListFragmentInteractionListener");
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
    public interface OnEventsListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onEventsListFragmentInteraction(Event item);
    }
}
