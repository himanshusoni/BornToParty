package com.hexade.borntoparty.main.kinvey;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hexade.borntoparty.main.UI.Activities.LoginActivity;
import com.kinvey.android.Client;
import com.kinvey.java.User;

import java.io.IOException;

/**
 * Created by upendra on 3/14/16.
 */

public class BornToPartyAccountAuthenticator extends AbstractAccountAuthenticator {

    private Client kinveyClient;
    private static final String TAG = BornToPartyAccountAuthenticator.class.getSimpleName();
    private final Context context;


    public BornToPartyAccountAuthenticator(Context context) {
        super(context);
        this.context = context;
        kinveyClient = new Client.Builder(context).build();
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response,
                                 String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response,
                             String accountType, String authTokenType,
                             String[] requiredFeatures, Bundle options)
            throws NetworkErrorException {

        final Intent intent = new Intent(this.context, LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response,
                                     Account account,
                                     Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response,
                               Account account, String authTokenType,
                               Bundle options) throws NetworkErrorException {
        if (!authTokenType.equals(ClientService.AUTHTOKEN_TYPE)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
            return result;
        }
        final AccountManager am = AccountManager.get(this.context);
        final String password = am.getPassword(account);

        if (password !=null) {
            User ku;
            try {
                ku = kinveyClient.user().loginBlocking(account.name, password).execute();
                // TODO make async
            } catch (IOException ex) {ku=null;}

            if (ku != null) {
                final Bundle result = new Bundle();
                result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
                result.putString(AccountManager.KEY_ACCOUNT_TYPE, ClientService.ACCOUNT_TYPE);
                result.putString(AccountManager.KEY_AUTHTOKEN, ku.getId());
                return result;
            }

        }

        // No valid user
        final Intent intent = new Intent(this.context, LoginActivity.class);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;

    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response,
                                    Account account, String authTokenType,
                                    Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response,
                              Account account, String[] features)
            throws NetworkErrorException {
        return null;
    }
}
