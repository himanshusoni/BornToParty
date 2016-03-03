package com.hexade.borntoparty.main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hexade.borntoparty.main.dummy.DummyBirthday;
import com.hexade.borntoparty.main.dummy.DummyContent;
import com.hexade.borntoparty.main.dummy.DummyEvent;
import com.hexade.borntoparty.main.dummy.DummyReminder;
import com.hexade.borntoparty.main.models.Users;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BirthdayFragment.OnBirthdayListFragmentInteractionListener,
        RemindersFragment.OnRemindersListFragmentInteractionListener,
        EventsFragment.OnEventsListFragmentInteractionListener,
        InviteFragment.OnInviteListFragmentInteractionListener{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    public static Context myAppContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myAppContext = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Birthday");
        // load the first pages - default - birthdayFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, new BirthdayFragment()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            // open a modal to enter the username to add new Friend;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Log.i("NAV-CLICK", "item " + item.getTitle());
        selectDrawerItem(item);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void selectDrawerItem(MenuItem item){
        // Create a new fragment and specify the planet to show based on
        // position
        Fragment fragment = null;

        Class fragmentClass;
        switch(item.getItemId()) {
            case R.id.nav_birthdays:
                fragmentClass = BirthdayFragment.class;
                break;
            case R.id.nav_events:
                fragmentClass = EventsFragment.class;
                break;
            case R.id.nav_reminders:
                fragmentClass = RemindersFragment.class;
                break;
            case R.id.nav_invites:
                fragmentClass = InviteFragment.class;
                break;
            /*case R.id.nav_friends:
                // TODO open a new Activity
                break;
            case R.id.nav_settings:
                // TODO open a new Activity
                break;*/
            default:
                fragmentClass = BirthdayFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        item.setChecked(true);
        setTitle(item.getTitle());

    }

    @Override
    public void onBirthdayListFragmentInteraction(Users.User item) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(BirthdayDetailFragment.ARG_ITEM_ID, item.getUsername());
            BirthdayDetailFragment fragment = new BirthdayDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Context context = getApplicationContext();
            Intent intent = new Intent(context, BirthdayDetailActivity.class);
            intent.putExtra(BirthdayDetailFragment.ARG_ITEM_ID, item.getUsername());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }
    }

    @Override
    public void onEventsListFragmentInteraction(DummyEvent.DummyItem item) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Context context = getApplicationContext();
            Intent intent = new Intent(context, ItemDetailActivity.class);
            intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }

    }

    @Override
    public void onRemindersListFragmentInteraction(Users.User item) {
        /**
         * TODO show group with the options, - Bash, Wish, Ignore in detail.
         * The view will update based on the selected Option.
        */
        /*if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.getUsername());
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Context context = getApplicationContext();
            Intent intent = new Intent(context, ItemDetailActivity.class);
            intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.getUsername());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }*/

        final Users.User userItem = item;

        DialogFragment reminderDialogFragment = new DialogFragment() {
            public String[] reminderActions =  new String[]{"Wish","Plan a Bash","Ignore"};

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose an Action")
                        .setItems(reminderActions, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                Log.i("REMINDER", reminderActions[which]);

                                Context context = getApplicationContext();
                                switch (which){
                                    case 0:
                                        break;
                                    case 1:{
                                        Intent intent = new Intent(context, CreateEventActivity.class);
                                        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, userItem.getUsername());
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                        break;
                                    case 2:
                                        break;
                                }
                            }
                        });
                return builder.create();
            }
        };

        reminderDialogFragment.show(getSupportFragmentManager(), "RemindersFragment");
    }

    @Override
    public void onInviteListFragmentInteraction(DummyContent.DummyItem item) {
        DialogFragment reminderDialogFragment = new DialogFragment() {
            public String[] inviteActions =  new String[]{"Yes","No","May be","Later"};

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Send a Response")
                        .setItems(inviteActions, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                Log.i("INVITE", inviteActions[which]);

                                switch (which){
                                    case 0:
                                        break;
                                    case 1:
                                        break;
                                    case 2:
                                        break;
                                    default:
                                }
                            }
                        });
                return builder.create();
            }
        };

        reminderDialogFragment.show(getSupportFragmentManager(), "RemindersFragment");
    }
}
