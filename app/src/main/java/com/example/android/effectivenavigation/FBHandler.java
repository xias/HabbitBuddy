package com.example.android.effectivenavigation;

import android.provider.ContactsContract;
import android.util.Log;

import com.example.android.effectivenavigation.messenger.UserObject;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.example.android.effectivenavigation.messenger.UserObject.thisUser;

/**
 * Created by Xu on 2016-11-09.
 */

public class FBHandler {

    private DatabaseReference mDatabase;
//    private final Firebase mRef;
    FirebaseDatabase FBDatabase = FirebaseDatabase.getInstance();


//    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
//    DatabaseReference mScheduleRef = mRootRef.child("schedule");
//    DatabaseReference mUsersRef = mRootRef.child("users");
//    DatabaseReference mPostsRef = mRootRef.child("posts");
    public static Firebase meRef;

    Firebase mRootRef =  new Firebase("https://habitbuddy-9bca7.firebaseio.com/");
    Firebase mScheduleRef = mRootRef.child("schedule");
    Firebase mUsersRef = mRootRef.child("users");
    Firebase mPostsRef = mRootRef.child("posts");


    Map<String, Map<String, String>> users;
    Map<String, Map<String, String>> posts;
    Map<String, Map<String, String>> schedules;


    public FBHandler() {
//        Map<String, String> user1 = dataSnapshot.getValue(Map.class);


    }


    public void AddUser(String name){
        Firebase userRef = mUsersRef.child(name);
        userRef.child("ranking").setValue( 50 );


    }

    public static String getValueFromSnap(DataSnapshot d, String key ){
        return  d.child(key).getValue(String.class);
    }
    public static UserObject getUserFromNameString(DataSnapshot d, String name){
        String friendlist = getValueFromSnap(d, "friendlist");
        String buddy = getValueFromSnap(d, "buddy");
        String diary = getValueFromSnap(d, "diary");
        String intake = getValueFromSnap(d, "intake");
        String schedule = getValueFromSnap(d, "calendar_id");
        String[] fr = friendlist.split(" ");
        List<String> friendArray =  new ArrayList<String>(Arrays.asList(fr));

        return new UserObject(buddy, diary, friendArray, intake, name, schedule);
    }

    public static void getThisUser(final String name1){
        meRef =  new Firebase("https://habitbuddy-9bca7.firebaseio.com/users/"+name1);
        meRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                thisUser = getUserFromNameString(dataSnapshot, name1);
                Log.d("FIRE_this user result", thisUser.getBuddy()+thisUser.getIntake());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public static void getFriendlist(String myName){
        meRef =  new Firebase("https://habitbuddy-9bca7.firebaseio.com/users/");
        if(meRef!=null){
            //get my friendlist
            meRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("FIRE", "gettign friendlist");
                    for(String f:thisUser.getFriendlist()){
                        DataSnapshot newData = dataSnapshot.child(f);
                        getUserFromNameString(newData, f);
                        Log.d("FIRE", f);

                    }

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }

    }
//    public void

    /* TODO
    add user - add schedule, set calendar id
    set intake
    select preset schedule

    update activity history
    add friend
    delete friend
    set buddy
    add diary item, add diary id to user diary list
    getters



     */



}
