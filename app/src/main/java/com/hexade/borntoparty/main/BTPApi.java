package com.hexade.borntoparty.main;

/**
 * Created by himanshusoni on 3/13/16.
 */

import com.hexade.borntoparty.main.models.EventsWrapper;
import com.hexade.borntoparty.main.models.FriendsWrapper;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface BTPApi {
    @GET("/users/{username}/friends")
    void getFriends(
            @Path("username") String id,
            Callback<FriendsWrapper> callback);

    @GET("/events/{username}")
    void getGetEvents(
            @Path("username") String id,
            Callback<EventsWrapper> callback
    );

//    @FormUrlEncoded
//    @POST("events/create")
//    void createEvent(
//    Callback<EventResponse> callback,
//    @FieldMap Map<String, String> names
//    );

}
