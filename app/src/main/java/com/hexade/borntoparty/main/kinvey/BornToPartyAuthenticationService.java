package com.hexade.borntoparty.main.kinvey;

import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BornToPartyAuthenticationService extends Service {
    private static final String TAG = "AccountAuthenticatorService";
    private static BornToPartyAccountAuthenticator sAccountAuthenticator = null;

    private BornToPartyAccountAuthenticator getAuthenticator() {
        if (sAccountAuthenticator == null)
            sAccountAuthenticator = new BornToPartyAccountAuthenticator(this);

        return sAccountAuthenticator;
    }

    @Override
    public IBinder onBind(Intent intent) {
        IBinder binder = null;
        if (intent.getAction().equals(AccountManager.ACTION_AUTHENTICATOR_INTENT)){
            binder = new BornToPartyAccountAuthenticator(this).getIBinder();
        }
        return binder;
    }
}
