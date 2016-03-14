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
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hexade.borntoparty.main.BTPApi;
import com.hexade.borntoparty.main.DividerItemDecoration;
import com.hexade.borntoparty.main.MyBirthdayRecyclerViewAdapter;
import com.hexade.borntoparty.main.R;
import com.hexade.borntoparty.main.models.Friend;
import com.hexade.borntoparty.main.models.FriendsWrapper;
import com.hexade.borntoparty.main.models.User;
import com.hexade.borntoparty.main.models.Users;
//import com.squareup.okhttp.Callback;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnBirthdayListFragmentInteractionListener}
 * interface.
 */
public class BirthdayFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnBirthdayListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BirthdayFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BirthdayFragment newInstance(int columnCount) {
        BirthdayFragment fragment = new BirthdayFragment();
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
        View view = inflater.inflate(R.layout.fragment_birthday_list, container, false);

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
            Log.i("API", "In BirthdayFragment, onCreateView");

            JsonDeserializer js = new JsonDeserializer() {

                @Override
                public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    JsonObject content = json.getAsJsonObject();
                    FriendsWrapper message = new Gson().fromJson(json, typeOfT);
                    JsonElement data = content.get("user");

                    for(User u : message.getResult()){
                        Friend f = u.getFriend();
                        f.setDob(f.getDob());
                    }

                    if(message.getResult().size()!=0){
                        Collections.sort(message.getResult(), message.getComparator());
                    }

                    return message;
                }
            };

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(FriendsWrapper.class, js);
            Gson gson = gsonBuilder.create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(getString(R.string.API_URL))
                    .setConverter(new GsonConverter(gson))
                    .build();
            BTPApi methods = restAdapter.create(BTPApi.class);
            Callback<FriendsWrapper> callback = new Callback<FriendsWrapper>() {
                @Override
                public void success(FriendsWrapper f, Response response) {
                    Log.i("HIM",f.getLength() + "");
                    recyclerView.setAdapter(new MyBirthdayRecyclerViewAdapter(getActivity(), f.getResult() , mListener));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.e("HIM",retrofitError.getMessage());
                    retrofitError.printStackTrace();

                }
            };
            methods.getFriends("crazysnake682", callback);

//            new Users().fetch();
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBirthdayListFragmentInteractionListener) {
            mListener = (OnBirthdayListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBirthdayListFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnBirthdayListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onBirthdayListFragmentInteraction(Friend item);
    }
}
