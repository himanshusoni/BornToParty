package com.hexade.borntoparty.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hexade.borntoparty.main.RemindersFragment.OnRemindersListFragmentInteractionListener;
import com.hexade.borntoparty.main.models.Users;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Users.User} and makes a call to the
 * specified {@link OnRemindersListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRemindersRecyclerViewAdapter extends RecyclerView.Adapter<MyRemindersRecyclerViewAdapter.ViewHolder> {

    private final List<Users.User> mValues;
    private final OnRemindersListFragmentInteractionListener mListener;

    public MyRemindersRecyclerViewAdapter(Context context, List<Users.User> items, OnRemindersListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_reminders, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mUsername.setText(mValues.get(position).getFullName());
        holder.mBirthdayView.setText(mValues.get(position).getFormattedDob());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onRemindersListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mUsername;
        public final TextView mBirthdayView;
        public Users.User mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUsername = (TextView) view.findViewById(R.id.id);
            mBirthdayView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mBirthdayView.getText() + "'";
        }
    }
}
