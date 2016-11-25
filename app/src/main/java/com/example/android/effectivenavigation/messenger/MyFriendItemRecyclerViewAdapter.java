//package com.example.android.effectivenavigation.messenger;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.android.effectivenavigation.MainActivity;
//import com.example.android.effectivenavigation.R;
//import com.example.android.effectivenavigation.messenger.FriendItemFragment.OnListFragmentInteractionListener;
//
//import java.util.List;
//
///**
// * specified {@link OnListFragmentInteractionListener}.
// * TODO: Replace the implementation with code for your data type.
// */
//public class MyFriendItemRecyclerViewAdapter extends RecyclerView.Adapter<MyFriendItemRecyclerViewAdapter.ViewHolder> {
//
//    private final List<FriendItem> mValues;
//    private final OnListFragmentInteractionListener mListener;
//
//    public MyFriendItemRecyclerViewAdapter(List<FriendItem> items, OnListFragmentInteractionListener listener) {
//        mValues = items;
//        mListener = listener;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.fragment_frienditem, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        holder.mItem = mValues.get(position);
//        holder.mFirstView.setText(mValues.get(position).fName);
//        holder.mLastView.setText(mValues.get(position).lName);
//        holder.mPhoneNumView.setText(mValues.get(position).areaNumber+" "+ mValues.get(position).phoneNumber);
//
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (null != mListener) {
//                    //TODO new intent to text message
//
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//
//
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mValues.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final TextView mFirstView;
//        public final TextView mLastView;
//        public final TextView mPhoneNumView;
//
//        public FriendItem mItem;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            mFirstView = (TextView) view.findViewById(R.id.first);
//            mLastView = (TextView) view.findViewById(R.id.last);
//            mPhoneNumView = (TextView) view.findViewById(R.id.phoneNum);
//
//        }
//
////        @Override
////        public String toString() {
////            return super.toString() + " '" + mContentView.getText() + "'";
////        }
//    }
//}
