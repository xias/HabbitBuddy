package com.example.android.effectivenavigation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.android.effectivenavigation.adapter.ProfileAdapter;
import com.example.android.effectivenavigation.adapter.TaskAdapter;
import com.example.android.effectivenavigation.messenger.UserObject;
import com.example.android.effectivenavigation.summary.BuddyCenterFragment;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static com.example.android.effectivenavigation.MainActivity.name;
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

    public static void GetBuddiesImages (final Activity activity, final ListView listView, String n) {
        Firebase userRef = mUsersRef.child(n).child("buddy");
        userRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
//                final ArrayList<String> list = new ArrayList<String>();

                String s = dataSnapshot.getValue(String.class);
                if (s==null||s==""){
//                    String[] a = {"no Buddies"};
//                    ProfileAdapter adapter=new ProfileAdapter(activity,a,a);
//                    listView.setAdapter(adapter);
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

    public static void Match(final String name) {
        Firebase mRef = mRootRef.child("list");
        mRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            //addValueEventListener
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                final String temp = dataSnapshot.getValue(String.class);
                final String[] names = temp.split("\\|");
                Log.v("N", Arrays.toString(names));
                final int[] myARR = new int[7];
                final int[][] compare = new int[names.length][7];
                for (int i = 0; i < names.length; i++) {


                    final Firebase nFirebase = new Firebase("https://habitbuddy-9bca7.firebaseio.com/users/"+names[i]+"/intake");
//                    Log.v("names", names[i] + "ff" + name);
                    final int finalI = i;
                    final int finalI1 = i;
                    nFirebase.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String tempV = dataSnapshot.getValue(String.class);
//                            Log.v("namesV", names[finalI] + "ff" + name);
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
                                Log.v("buddyis",names[index]);
                                final Firebase nFirebase = new Firebase("https://habitbuddy-9bca7.firebaseio.com/users/"+names[finalI]+"/buddy");
                                nFirebase.setValue(names[index]);
//
//                                match_view.setText("Your Buddy is:"+ names[index]);
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


    public static void SetSchedule(final String name, String type, String d1, String d2, String f, String s, final String s1){
        Firebase userRef = mUsersRef.child(name).child("schedule").child(type).child(s1);
        userRef.child("startDate").setValue(d1);
        userRef.child("endDate").setValue(d2);
        userRef.child("frequency").setValue(f);
        userRef.child("intensity").setValue(s);

        final Firebase userRef2 = mUsersRef.child(name).child("schedule").child(type).child("exist");
        userRef2.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                int flag = 0;
                String info = dataSnapshot.getValue(String.class);

                String result = null;
                if (info==null){

                    userRef2.setValue(s1);
                }else {
//                    Log.v("data",info);
                    String[] temp = info.split("\\|");
//                    Log.v("split",Arrays.toString(temp));
                    for (int i = 0; i<temp.length;i++) {
                        if (temp[i] == s1) {
                         flag=1;
                        }else {
                            result = temp[i];
                        }
                    }
                    if (flag != 1) {
                        if (result==null) {
                            result = s1;
                        }else {
                            result = result +"|"+s1;
                        }

                    }
                    userRef2.setValue(result);
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

    public static void checkTodaySchedule(final String name, final String cate, final Context c, final ListView listView) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        Log.v("today",currentDate);
        final String[] display = {null,null};
        final ArrayList<String> listTasks = new ArrayList<String>();
        final String[] type = {null};
        final Firebase userRef = mUsersRef.child(name).child("schedule").child(cate).child("exist");
        userRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
//                Log.v("cate",dataSnapshot.getValue(String.class));

                String s = dataSnapshot.getValue(String.class);


                type[0] =dataSnapshot.getValue(String.class);
                final String[] numberExercise = type[0].split("\\|");
                final String[] intensity = {null,null};
                for (int i = 0;i<numberExercise.length;i++) {

                    final Firebase userRef2 = mUsersRef.child(name).child("schedule").child(cate).child(numberExercise[i]);
                    final int finalI = i;
                    userRef2.child("intensity").addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                        @Override
                        public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                            intensity[finalI] =dataSnapshot.getValue(String.class);
                            final String[] day = {null,null};
                            userRef2.child("frequency").addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                                @Override
                                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                                    day[finalI] =dataSnapshot.getValue(String.class);
                                    String[] temp = day[finalI].split("\\|");
//                                    SimpleDateFormat dayInWeek = new SimpleDateFormat("u");
                                    Calendar sCalendar = Calendar.getInstance();
                                    String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                                    Log.v("Day of ",dayLongName);

                                    int currentDayInWeek = getIntFromDay(dayLongName);

                                    if (Integer.parseInt(temp[currentDayInWeek-1])==1){

                                        display[finalI] = intensity[finalI]+" "+ numberExercise[finalI];
                                        listTasks.add(display[finalI]);
                                        Log.v("Result", String.valueOf(display[finalI]));
                                    }
                                    Log.v("Result!!", String.valueOf(listTasks));
                                    TaskAdapter taskAdapter = new TaskAdapter(listTasks,c);
                                    listView.setAdapter(taskAdapter);

                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }

                            });

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }

                    });
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });






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

private static int getIntFromDay(String day){
    int res = 0;
    switch (day){
        case "Monday": res =1;break;
        case"Tuesday": res = 2;break;
        case"Wednesday": res = 3;break;
        case"Thursday": res = 4;break;
        case"Friday": res = 5;break;
        case"Saturday": res = 6;break;
        case"Sunday": res = 7;
    }
    return res;
}

    public static void checkBothSchedule(final String who, final String[] table) {
        final String[] display = {null,null};
        final ArrayList<String> listTasks = new ArrayList<String>();
        final String[] type = {null};
        final Firebase userRef = mUsersRef.child(who).child("schedule").child("exercise").child("exist");
        userRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
//                Log.v("cate",dataSnapshot.getValue(String.class));


                type[0] =dataSnapshot.getValue(String.class);
                final String[] numberExercise = type[0].split("\\|");
                final String[] intensity = {null,null};
                for (int i = 0;i<numberExercise.length;i++) {

                    final Firebase userRef2 = mUsersRef.child(who).child("schedule").child("exercise").child(numberExercise[i]);
                    final int finalI = i;
                    userRef2.child("intensity").addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                        @Override
                        public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                            intensity[finalI] =dataSnapshot.getValue(String.class);
                            final String[] day = {null,null};
                            userRef2.child("frequency").addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                                @Override
                                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                                    day[finalI] =dataSnapshot.getValue(String.class);
                                    String[] temp = day[finalI].split("\\|");
//                                    SimpleDateFormat dayInWeek = new SimpleDateFormat("u");
                                    Calendar sCalendar = Calendar.getInstance();
                                    String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                                    Log.v("Day of ",dayLongName);

                                    int currentDayInWeek = getIntFromDay(dayLongName);

                                    if (Integer.parseInt(temp[currentDayInWeek-1])==1){

                                        display[finalI] = intensity[finalI]+" "+ numberExercise[finalI];
                                        listTasks.add(display[finalI]);
                                        Log.v("Result", String.valueOf(display[finalI]));
                                    }
                                    Log.v("Result!!", String.valueOf(listTasks));
                                    for (int i = 0; i<listTasks.size();i++){
                                        table[i]=listTasks.get(i);
                                    }



                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }

                            });

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }

                    });
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });


    }
}
