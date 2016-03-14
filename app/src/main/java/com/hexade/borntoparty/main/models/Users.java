package com.hexade.borntoparty.main.models;

import android.util.Log;

//import com.squareup.okhttp.Call;
//import com.squareup.okhttp.Callback;
//import com.squareup.okhttp.MediaType;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by himanshusoni on 2/27/16.
 */
public class Users {
/*
    private static final String TAG_RESULTS = "results";
    private static final String TAG_USER = "user";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_NAME = "name";
    private static final String TAG_NAME_FIRST= "first";
    private static final String TAG_NAME_LAST= "last";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_DOB = "dob";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_CELL = "cell";
    private static final String TAG_PICTURE = "picture";
    private static final String TAG_PICTURE_LARGE = "large";
    private static final String TAG_PICTURE_MEDIUM = "medium";
    private static final String TAG_PICTURE_THUMBNAIL = "thumbnail";

    *//**
     * using seed returns the same set of results for the seed string.
     *//*
    public final static String URL = "http://api.randomuser.me/?seed=sonihimanshu&results=10";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private static ArrayList<User> userList;
    private static HashMap<String, User> userMap;

    public Users(){

    }

    public static ArrayList<User> getUserList(){
        //fetch(URL,new MyCallBack());
        return userList;
    }

    public static HashMap<String, User> getUserMap() {
        return userMap;
    }

    public ArrayList<Users.User> fetch(String url, Callback callback){
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

        return null;
    }

    public ArrayList<User> createUsers(String responseString){
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(responseString);

            userList = new ArrayList<>();
            userMap = new HashMap<>();

            // Getting JSON Array node
            JSONArray users = jsonObj.getJSONArray(TAG_RESULTS);
            for (int i=0; i < users.length(); i++){
                JSONObject item = users.getJSONObject(i);
                JSONObject user = item.getJSONObject(TAG_USER);

                User userObj = new User();

                JSONObject name = user.getJSONObject(TAG_NAME);
                String firstName = name.getString(TAG_NAME_FIRST);
                char f = firstName.charAt(0);
                firstName = Character.toUpperCase(f) + firstName.substring(1);
                userObj.setFirstName(firstName);
                String lastName = name.getString(TAG_NAME_LAST);
                char l = lastName.charAt(0);
                lastName = Character.toUpperCase(l) + lastName.substring(1);

                userObj.setFirstName(firstName);
                userObj.setLastName(lastName);

                userObj.setUsername(user.getString(TAG_USERNAME));
                userObj.setEmail(user.getString(TAG_EMAIL));

                Date dob = new Date(user.getLong(TAG_DOB) * 1000);
//                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//                Date dob = sdf.parse(user.getString(TAG_DOB));
//                Date dob = sdf.format(d);

                userObj.setDob(dob);
                userObj.setPhone(user.getString(TAG_PHONE));
                userObj.setCell(user.getString(TAG_CELL));

                JSONObject picture = user.getJSONObject(TAG_PICTURE);
                userObj.setLargePicture(picture.getString(TAG_PICTURE_LARGE));
                userObj.setMediumPicture(picture.getString(TAG_PICTURE_MEDIUM));
                userObj.setThumbnail(picture.getString(TAG_PICTURE_THUMBNAIL));

                userList.add(userObj);
                userMap.put(userObj.getUsername(), userObj);
            }
        } catch (JSONException e) {
            Log.e("API", "JSON Exception");
            e.printStackTrace();
            return null;
//        } catch (ParseException e){
//            Log.e("API", "Date Parse Exception");
//            e.printStackTrace();
//            return null;
        }

        if(userList.size()!=0){
            Collections.sort(userList, getComparator());
        }

        return userList;
    }

    public Comparator<User> getComparator(){
        return new Comparator<User>() {
            @Override
            public int compare(User lhs, User rhs) {
                return lhs.getDaysLeft() - rhs.getDaysLeft();
            }
        };
    }

    public class User{
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private Date dob;
        private String phone;
        private String cell;
        private String largePicture;
        private String mediumPicture;
        private String thumbnail;

        private int daysLeft;
        private int age;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFullName(){
            return firstName + " " + lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Date getDob() {
            return dob;
        }

        public void setDob(Date dob) {
            this.dob = dob;

            Calendar d = Calendar.getInstance();
            d.setTime(this.dob);
            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - d.get(Calendar.YEAR);
            if (today.get(Calendar.DAY_OF_YEAR) <= d.get(Calendar.DAY_OF_YEAR))
                age--;

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            d.set(Calendar.YEAR, year);

            if(d.before(cal))
                d.set(Calendar.YEAR, year + 1);

            // weird calculation. (Calendar.getTime()) gives Date. Date.getTime(); give long.
            long days = d.getTime().getTime() - cal.getTime().getTime();

            this.age = age;
            this.daysLeft = (int)TimeUnit.DAYS.convert(days, TimeUnit.MILLISECONDS);
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCell() {
            return cell;
        }

        public void setCell(String cell) {
            this.cell = cell;
        }

        public String getLargePicture() {
            return largePicture;
        }

        public void setLargePicture(String largePicture) {
            this.largePicture = largePicture;
        }

        public String getMediumPicture() {
            return mediumPicture;
        }

        public void setMediumPicture(String mediumPicture) {
            this.mediumPicture = mediumPicture;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getDaysLeft() {
            return daysLeft;
        }

        public int getAge() {
            return age;
        }

        public String getFormattedDob(){
            Calendar cal = Calendar.getInstance();
            cal.setTime(dob);
            return new SimpleDateFormat("MMMM d").format(cal.getTime());
        }
    }

    class MyCallBack implements Callback {

        @Override
        public void onFailure(Request request, IOException e) {
            // Something went wrong
        }

        @Override public void onResponse(Response response) throws IOException {
            String responseString = response.body().string();
            if (response.isSuccessful()) {
                // Do what you want to do with the response.
                Log.i("API - SUCCESS", responseString);
                createUsers(responseString);
            } else {
                // Request not successful
                Log.i("API - ERROR", responseString);
            }
        }
    }*/
}
