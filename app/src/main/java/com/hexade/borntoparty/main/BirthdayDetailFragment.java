package com.hexade.borntoparty.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hexade.borntoparty.main.Utils.DataManager;
import com.hexade.borntoparty.main.dummy.DummyBirthday;
import com.hexade.borntoparty.main.dummy.DummyContent;
import com.hexade.borntoparty.main.models.Users;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class BirthdayDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Users.User mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BirthdayDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = Users.getUserMap().get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
//                appBarLayout.setTitle(mItem.content);
                ImageView backdrop = (ImageView)appBarLayout.findViewById(R.id.main_backdrop);
                Picasso.with(getActivity()).load(mItem.getLargePicture()).skipMemoryCache().into(backdrop);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.birthday_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.getFullName());
            ((TextView) rootView.findViewById(R.id.item_date)).setText(mItem.getFormattedDob());
            ((TextView) rootView.findViewById(R.id.daysandage)).setText("Turns " + (mItem.getAge()+1) + " in "  + mItem.getDaysLeft() + " days!");
            ((TextView) rootView.findViewById(R.id.phone)).setText(mItem.getPhone()+"\n"+mItem.getCell()+"\n"+mItem.getEmail());

            Button btnSetReminder = (Button) rootView.findViewById(R.id.set_reminder);
            Button btnSendWishes = (Button) rootView.findViewById(R.id.set_reminder);

            btnSetReminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new DataManager(MainActivity.myAppContext).setReminder(mItem.getUsername());
                    Toast.makeText(getActivity(),"Reminder set for "+mItem.getFullName(),Toast.LENGTH_SHORT).show();
                }
            });
        }

        return rootView;
    }
}
