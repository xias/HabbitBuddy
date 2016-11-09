package com.example.android.effectivenavigation.messenger;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.effectivenavigation.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Arrays;


import static com.example.android.effectivenavigation.MainActivity.name;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FriendItemFragment extends Fragment {

    // TODO: inflate with friendlist and click to send a message.
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private Firebase mRef;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FriendItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FriendItemFragment newInstance(int columnCount) {
        FriendItemFragment fragment = new FriendItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    private TextView match_view;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frienditem_list, container, false);
        Button match = (Button)view.findViewById(R.id.buttonPair);
        match_view = (TextView)view.findViewById(R.id.match_view);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.buddyList);
        match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    mRef = new Firebase("https://habitbuddy-9bca7.firebaseio.com/message");
                    mRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                        //addValueEventListener
                        @Override
                        public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                            final String temp = dataSnapshot.getValue(String.class);
                            final String[] names = temp.split(" ");
                            Log.v("N", Arrays.toString(names));
                            final int[] myARR = new int[7];
                            final int[][] compare = new int[names.length][7];
                            for (int i = 0; i < names.length; i++) {


                                Firebase nFirebase = new Firebase("https://habitbuddy-9bca7.firebaseio.com/" + names[i]);
                                Log.v("names", names[i] + "ff" + name);
                                final int finalI = i;
                                final int finalI1 = i;
                                nFirebase.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        String tempV = dataSnapshot.getValue(String.class);
                                        String[] cal = tempV.split(" ");
                                        int[] results = new int[cal.length - 1];

                                        for (int j = 1; j < cal.length; j++) {
                                            try {
                                                results[j - 1] = Integer.parseInt(cal[j]);


                                                compare[finalI1][j - 1] = results[j - 1];


                                            } catch (NumberFormatException nfe) {
                                                //NOTE: write something here if you need to recover from formatting errors
                                            }
                                            ;
                                        }

                                        if (names[finalI].equals(name)) {
                                            for (int p = 0; p < 7; p++) {
                                                myARR[p] = compare[finalI][p];

                                                Log.v("Test", String.valueOf(compare[finalI][p]));

                                            }
                                            for (int q=0;q<7;q++){
                                                compare[finalI][q]=100;
                                            }
                                            int[] matchHelper = new int[names.length];
                                            for (int time= 0;time<names.length;time++){
                                                int temp = 0;
                                                for (int k = 0;k<7;k++) {
                                                   temp +=((myARR[k]-compare[time][k])*(myARR[k]-compare[time][k]));
                                                    Log.v("Temp of ***", String.valueOf(temp));
                                                }
                                                matchHelper[time] = (int)Math.sqrt((double)temp);

                                            }
                                            Log.v("DIO", Arrays.toString(matchHelper));

                                            if (matchHelper.length == 0)
                                                return;
                                            int small = matchHelper[0];
                                            int index = 0;
                                            for (int ip = 0; ip < matchHelper.length; ip++) {
                                                if (matchHelper[ip] < small)
                                                {
                                                    small = matchHelper[ip];
                                                    index = ip;
                                                }
                                                Log.v("have",names[ip]);
                                            }


                                            match_view.setText("Your Buddy is:"+ names[index]);
                                        }


                                        Log.v("DIO", Arrays.toString(myARR) + "***"+finalI + Arrays.toString(compare[finalI]));
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });
//                                mRef = new Firebase("https://habitbuddy-9bca7.firebaseio.com/" + names[i]);
//                                mRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
//                                    //addValueEventListener
//                                    @Override
//                                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
//                                        String temp = dataSnapshot.getValue(String.class);
//                                        Log.v("Find",temp);
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(FirebaseError firebaseError) {
//
//                                    }
//                                });
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }

                    });

            }
        });
        // Set the adapter

            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyFriendItemRecyclerViewAdapter(FriendItem.FriendItem_List, mListener));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(FriendItem item);

    }

}
