package com.hexade.borntoparty.main.kinvey;

import android.support.multidex.MultiDexApplication;

import com.kinvey.android.Client;

/**
 * Created by upendra on 3/13/16.
 */
public class ClientService extends MultiDexApplication {

    public static final String AUTHTOKEN_TYPE = "com.hexade.borntoparty.auth_token";
    public final static String ACCOUNT_TYPE = "com.hexade.borntoparty.account";

    private Client kinveyService;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        String app_id = "kid_W1f4eXSIkW";
        String app_secret = "57faef3b99d04d8c9e12e6cee1b7ea8c";

        this.kinveyService = new Client.Builder(app_id, app_secret,
                getApplicationContext()).build();
    }

    public Client getKinveyService() {
        return this.kinveyService;
    }
}
