package com.hexade.borntoparty.main.UI.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hexade.borntoparty.main.R;
import com.hexade.borntoparty.main.models.BornToPartyUser;
import com.hexade.borntoparty.main.models.Friends;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.squareup.picasso.Picasso;

public class AddFriendActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private EditText username;
    private Button addButton;
    private Button confirmButton;

    private LinearLayout usercontainer;
    private TextView searchUsername;
    private ImageView mThumbnail;

    BornToPartyUser searchUser;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        context = getApplicationContext();

        progressBar = (ProgressBar) findViewById(R.id.progress);
        username = (EditText) findViewById(R.id.usernameField);
        searchUsername = (TextView) findViewById(R.id.username);
        usercontainer = (LinearLayout) findViewById(R.id.usercontainer);
        mThumbnail = (ImageView) findViewById(R.id.thumbnail);

        confirmButton = (Button) findViewById(R.id.btn_confirm);
        addButton = (Button) findViewById(R.id.btn_add);
        addButton.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
//                    Toast.makeText(getApplicationContext(), "got the focus", Toast.LENGTH_LONG).show();
                    usercontainer.setVisibility(View.GONE);
                    addButton.setClickable(true);

                }else {
//                    Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        showProgress();

        int id = v.getId();
        switch (id){
            case R.id.btn_add:
                searchUser();
                break;
            case R.id.btn_confirm:
                confirmAddUser();
                break;
        }
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    public void searchUser(){
        String uname = username.getText().toString().trim();
        if(!uname.equals("")){

            // TODO clear focus not working.
            username.clearFocus();

            final Client mKinveyClient = MainActivity.kinveyClient;

            Query myQuery = mKinveyClient.query();

            myQuery.equals("username", uname);//MainActivity.loggedInUser.getUsername());
            final AsyncAppData<BornToPartyUser> checkUser = mKinveyClient.appData("usersmaster", BornToPartyUser.class);
            checkUser.get(myQuery, new KinveyListCallback<BornToPartyUser>() {
                @Override
                public void onSuccess(BornToPartyUser[] results) {
                    Log.i("RESULT",results.length+"");
                    hideProgress();

                    usercontainer.setVisibility(View.VISIBLE);

                    if(results.length > 0){
                        Log.i("RESULT",results[0].getUsername());
//                        Log.i("RESULT",results[0].Nam().toString());
//                        Log.i("RESULT",results[0].getFriends().size() + "");

                        searchUser = results[0];

                        searchUsername.setText(searchUser.getName().getFullName());
                        Picasso.with(context).load(searchUser.getPicture().getThumbnail()).skipMemoryCache().into(mThumbnail);

                        // Show Confirm button
                    }else{
                        searchUsername.setText(" Not Found !!");

                        addButton.setClickable(false);
                    }
                }

                @Override
                public void onFailure(Throwable error) {
                    Log.e("TAG", "failed to fetchByFilterCriteria 2", error);

                    Toast.makeText(context," Unable to Fetch, Please try again.",Toast.LENGTH_SHORT).show();
                    hideProgress();
                }
            });
        }
    }

    public void confirmAddUser(){
        // TODO add searchUser as a friend
    }
}
