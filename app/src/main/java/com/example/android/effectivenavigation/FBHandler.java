package com.example.android.effectivenavigation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.android.effectivenavigation.adapter.ProfileAdapter;
import com.example.android.effectivenavigation.messenger.UserObject;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.example.android.effectivenavigation.messenger.UserObject.thisUser;
import static com.example.android.effectivenavigation.summary.DiscoverFragment.decodeFromFirebaseBase64;

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

    static Firebase mRootRef =  new Firebase("https://habitbuddy-9bca7.firebaseio.com/");
    Firebase mScheduleRef = mRootRef.child("schedule");
    static Firebase mUsersRef = mRootRef.child("users");
    Firebase mPostsRef = mRootRef.child("posts");


    Map<String, Map<String, String>> users;
    Map<String, Map<String, String>> posts;
    Map<String, Map<String, String>> schedules;


    public FBHandler() {
//        Map<String, String> user1 = dataSnapshot.getValue(Map.class);


    }

    public static void TestDuplicate(String name) {

        final Firebase userListRef=mRootRef.child("list");
        final String[] newS = new String[1];
        final int[] flag = {9};
        final String tempName = name;
        userListRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                newS[0] = dataSnapshot.getValue(String.class);
                if (newS[0]==null||newS[0].equals("")){
                    flag[0] = 1;
                }else {
                    flag[0] = 1;
                    String[] temp = newS[0].split("\\|");
                    Log.v("test",Arrays.toString(temp)+"*"+tempName);
                    for (int i = 0; i < temp.length; i++) {

                        if (temp[i].equals(tempName)){
                            flag[0] = 0;
                            break;

                        }

                    }
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
        Log.v("flag",String.valueOf(flag[0]));
        if (flag[0]==0) {
            name = null;
        }
    }

    public static void SignUp(final String name, String password) {
        Firebase userRef = mUsersRef.child(name);
        userRef.child("password").setValue(password);
        final Firebase userListRef=mRootRef.child("list");
        final String[] newS = new String[1];

        userListRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                newS[0] = dataSnapshot.getValue(String.class);


                if (newS[0]==null||newS[0].equals("")){
                    userListRef.setValue(name);
                }
                else {
                    newS[0] = newS[0] + "|"+name;
                    userListRef.setValue(newS[0]);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

    }

    public static void GetProfileImage (String name, final ImageView view) {
        Firebase userRef = mUsersRef.child(name).child("profileImage");
        userRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {


                try {
                    Bitmap imageBitmap = decodeFromFirebaseBase64(dataSnapshot.getValue(String.class));
                    view.setImageBitmap(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

    }

    public static void GetBuddiesImages (final Activity activity, final ListView listView, String name) {
        Firebase userRef = mUsersRef.child(name).child("buddy");
        userRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
//                final ArrayList<String> list = new ArrayList<String>();

                String s = dataSnapshot.getValue(String.class);
                if (s==null||s==""){
                    String[] a = {"no Buddies"};
                    ProfileAdapter adapter=new ProfileAdapter(activity,a,a);
                    listView.setAdapter(adapter);
                }else {
                    final String[] temp = s.split("\\|");
                    final String[] data = new String[temp.length];
                    for (int i = 0; i < temp.length; i++) {
                        Firebase buddyRef = mUsersRef.child(temp[i]).child("profileImage");
                        final int finalI = i;
                        buddyRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                            @Override
                            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                                data[finalI]=dataSnapshot.getValue(String.class);
//                                list.add(dataSnapshot.getValue(String.class));
                                if (finalI==temp.length-1) {
                                    ProfileAdapter adapter=new ProfileAdapter(activity,temp,data);
                                    listView.setAdapter(adapter);
                                }



                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }

                        });
                    }
                }




            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

    }


    public static void ActivityMatch(final String name, final String buddy) {
        final Firebase userRef = mUsersRef.child(name).child("buddy");

        final String[] newS = new String[1];

        userRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                newS[0] = dataSnapshot.getValue(String.class);


                if (newS[0]==null||newS[0].equals("")){
                    userRef.setValue(buddy);
                }
                else {
                    newS[0] = newS[0] + "|"+buddy;
                    userRef.setValue(newS[0]);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

    }



    public static void SetScheduleImage (String name, final ImageView view) {
        Firebase userRef = mUsersRef.child(name).child("profileImage");
        userRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {


                try {
                    Bitmap imageBitmap = decodeFromFirebaseBase64(dataSnapshot.getValue(String.class));
                    view.setImageBitmap(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

    }




    public static void AddUser(String name){
        Firebase userRef = mUsersRef.child(name);
        userRef.child("ranking").setValue( 50 );


    }

    public static void IntakeResult(String username, final String intake) {


        final Firebase mRef = new Firebase("https://habitbuddy-9bca7.firebaseio.com/users/"+username+"/intake");
        mRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
//                        Log.v("NEWWWW", dataSnapshot.getValue(String.class));
//                newS = dataSnapshot.getValue(String.class);
////                        Log.v("NEWWWW","JKFJLDFJLKK");
//                newS = newS + " "+name;


                mRef.setValue(intake);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
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
        String[] fr = friendlist.split("\\|");
        List<String> friendArray =  new ArrayList<String>(Arrays.asList(fr));

        return new UserObject(buddy, diary, friendArray, intake, name, schedule);
    }

    public static void getThisUser(final String name1){
        meRef =  new Firebase("https://habitbuddy-9bca7.firebaseio.com/users/"+name1);
        meRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                thisUser = getUserFromNameString(dataSnapshot, name1);
//                Log.d("FIRE_this user result", thisUser.getBuddy()+thisUser.getIntake());

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
//                    for(String f:thisUser.getFriendlist()){
//                        DataSnapshot newData = dataSnapshot.child(f);
//                        getUserFromNameString(newData, f);
//                        Log.d("FIRE", f);

//                    }

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
