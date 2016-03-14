package com.hexade.borntoparty.main;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hexade.borntoparty.main.kinvey.ClientService;
import com.hexade.borntoparty.main.models.BornToPartyUser;
import com.kinvey.android.Client;
import com.kinvey.java.User;
import com.kinvey.java.core.KinveyCancellableCallback;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AccountAuthenticatorActivity {

    // UI references.
    private EditText mUsernameET;
    private EditText mPasswordET;
    private Button mSignInBtn;
    private Button mRegisterBtn;

    private Client kinveyClient;

    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // init UI references
        mUsernameET = (EditText) findViewById(R.id.et_username);
        mPasswordET= (EditText) findViewById(R.id.et_password);
        mSignInBtn = (Button) findViewById(R.id.btn_sign_in);
        mRegisterBtn = (Button) findViewById(R.id.btn_register);

        kinveyClient = ((ClientService) getApplication()).getKinveyService();

        accountManager = AccountManager.get(getApplicationContext());
    }

    public void registerNewAccount(View view) {
        Intent intent = new Intent(this, RegisterAccountActivity.class);
        startActivity(intent);
    }

    public void signIn(View view) {
        if (!validateFields())
            return;

        login();
    }

    private void login() {
        // logout current active user
        if (kinveyClient.user().isUserLoggedIn())
            kinveyClient.user().logout().execute();

        kinveyClient.user().login(getUsername(), mPasswordET.getText().toString(),
                new KinveyCancellableCallback<User>() {
                    @Override
                    public boolean isCancelled() {
                        return false;
                    }

                    @Override
                    public void onCancelled() {

                    }

                    @Override
                    public void onSuccess(User user) {
                        Toast.makeText(getApplicationContext(), "Logged in as " + user.getUsername(),
                                Toast.LENGTH_LONG).show();
                        authenticate(user.getId());

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(intent);
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Toast.makeText(getApplicationContext(), "Login failed: " + throwable.getMessage(),
                                Toast.LENGTH_LONG).show();
                        authenticate(null);
                    }
                });
    }

    private void authenticate(String id) {
        if (id == null || id.isEmpty()) {
            return;
        }

        // add or update account in Android Account Manager
        final Account account = new Account(getUsername(), ClientService.ACCOUNT_TYPE);

        String currUsername = getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        if (currUsername == null) {
            accountManager.addAccountExplicitly(account, mPasswordET.getText().toString(), null);
        } else {
            accountManager.setPassword(account, mPasswordET.getText().toString());
        }

        final Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, getUsername());
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, ClientService.ACCOUNT_TYPE);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
    }

    private boolean validateFields() {
        if (getUsername().isEmpty()) {
            showErrorCannotBeEmpty(mUsernameET.getHint().toString());
            return false;
        }
        if (mPasswordET.getText().toString().isEmpty()) {
            showErrorCannotBeEmpty(mPasswordET.getHint().toString());
            return false;
        }

        return true;
    }

    @NonNull
    private String getUsername() {
        return mUsernameET.getText().toString().trim();
    }

    private void showErrorCannotBeEmpty(String s) {
        showValidationError(s + " cannot be empty!");
    }

    private void showValidationError(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}

