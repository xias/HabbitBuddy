package com.example.android.effectivenavigation.summary;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.effectivenavigation.Start.StartActivity;
import com.example.android.effectivenavigation.adapter.BuddiesArrayAdapter;
import com.example.android.effectivenavigation.FBHandler;
import com.example.android.effectivenavigation.R;
import com.example.android.effectivenavigation.adapter.ProfileAdapter;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static com.example.android.effectivenavigation.MainActivity.name;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuddyCenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuddyCenterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static  ArrayList<String> tasks;
    private ListView wall;
    private ListView recentTask;
    private ListView reminder;


    public BuddyCenterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuddyCenterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuddyCenterFragment newInstance(String param1, String param2) {
        BuddyCenterFragment fragment = new BuddyCenterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(WallEntryItem.wallEntryItemList.isEmpty()) {
            new WallEntryItem("gs://habitbuddy-9bca7.appspot.com/defaultDiary.jpg",
                    "I went to a great park today. Let me know if anyone wants to join next time", 30, "Rick","Queens park!");
//            new WallEntryItem(BitmapFactory.decodeResource(getResources(), R.drawable.comp),
//                    "I did not do anything today", 5, "Lazy dude","zzz");
        }

//
//        Firebase postRef = new Firebase("https://habitbuddy-9bca7.firebaseio.com/posts");
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 8;
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.comp,options);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
//        postRef.push().setValue(new WallEntryItem(imageEncoded,
//                "I went to a great park today. Let me know if anyone wants to join next time", 30, "Rick","Queens park!"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO initiate wall post content from firebase


        tasks = new ArrayList<>();







//        try {
//            latch.await();
//            Log.v("result!", String.valueOf(tasks));
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        Log.v("split!", String.valueOf(tasks));

//        ReminderAdapter taskAdapter = new ReminderAdapter(tasks, android.R.drawable.ic_input_add);
////        ArrayAdapter<String> taskAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, tasks);
//        task.setAdapter(taskAdapter);



        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buddy_center, container, false);

//        name= getActivity().getIntent().getStringExtra("pos");
        ListView list=(ListView)v.findViewById(R.id.buddies);
        FBHandler.GetBuddiesImages(getActivity(),list,name);
        Button matchB = (Button) v.findViewById(R.id.match_button);
        matchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FBHandler.Match(name);
            }
        });





//        list.add("item1");
//        list.add("item2");
//
//        //instantiate custom adapter
//        BuddiesArrayAdapter adapter = new BuddiesArrayAdapter(list, getActivity());
//
//        //handle listview and assign adapter
//        ListView listView = (ListView)v.findViewById(R.id.buddies);
//        listView.setAdapter(adapter);

        recentTask = (ListView) v.findViewById(R.id.recentTaskList);
//        ArrayList<String> listTasks = new ArrayList<String>();

        Calendar c = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE  MMM d, ''yy");
        SimpleDateFormat month = new SimpleDateFormat("MMM");
        SimpleDateFormat date = new SimpleDateFormat("dd");
//        String strDate = sdf.format(c.getTime());
        String strDate = date.format(c.getTime());
        String strMonth = month.format(c.getTime());
        TextView monthView = (TextView)v.findViewById(R.id.textMon);
        TextView dateView = (TextView)v.findViewById(R.id.textDate);
        Button startButton = (Button) v.findViewById(R.id.startNewButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),StartActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("pos",name);
                intent.putExtras(mBundle);
                startActivity(intent);
                //Log.v("Buddycente",name);
                getActivity().finish();
            }
        });
        monthView.setText(strMonth);
        dateView.setText(strDate);
        final ImageView imageView = (ImageView) v.findViewById(R.id.showImage);

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                FBHandler.GetProfileImage(name, imageView);
            }
        };
        Thread thread2 = new Thread(runnable1);
        thread2.start();
























        final List<String> rems = new ArrayList<>(1);
        rems.add("Play Pokemon Go if you need motivation. ");
//        rems.add("Bring water when you walk. ");
//        rems.add("Remember to update your friends after done walking!");
//        rems.add("Bring your umbrella if it's windy and cloudy outside. ");
//        rems.add("Maybe you can walk to get grocery today? ");

        reminder = (ListView) v.findViewById(R.id.reminderList);
        FBHandler.checkTodaySchedule(name,"exercise",getContext(),recentTask);
        final ReminderAdapter reminderAdapter = new ReminderAdapter(rems,android.R.drawable.btn_star_big_on);
        reminder.setAdapter(reminderAdapter);
        Firebase tipRef = new Firebase("https://habitbuddy-9bca7.firebaseio.com/tips");
        tipRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                rems.add(dataSnapshot.getValue(String.class));
                reminderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




//        wall = (ListView) v.findViewById(R.id.wallList);
//        Firebase postRef = new Firebase("https://habitbuddy-9bca7.firebaseio.com/posts");
//
//        FirebaseListAdapter<WallEntryItem> adapter = new FirebaseListAdapter<WallEntryItem>(this.getActivity(), WallEntryItem.class, R.layout.record_entry, postRef) {
//            @Override
//            protected void populateView(View view, WallEntryItem wallEntryItem) {
//                ImageView im = (ImageView) view.findViewById(R.id.wallPostImage);
//                TextView name = (TextView) view.findViewById(R.id.wallName);
//                TextView mes = (TextView) view.findViewById(R.id.wallMes);
//                ProgressBar p = (ProgressBar) view.findViewById(R.id.wallProgress);
//
//                String bitmapstring = wallEntryItem.getPic();
//                try {
//                    Bitmap imageBitmap = decodeFromFirebaseBase64(bitmapstring);
//                    im.setImageBitmap(imageBitmap);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                name.setText(wallEntryItem.getTitle());
//                mes.setText(wallEntryItem.getContent());
//                p.setProgress(wallEntryItem.getProgress());
//            }
//        };
//
//        wall.setAdapter(adapter);









//        WallAdapter wallAdapter = new WallAdapter();
//        wall.setAdapter(wallAdapter);


        Firebase wallRef = new Firebase("https://habitbuddy-9bca7.firebaseio.com/posts");
        wallRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });











        return v;
    }


    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }







    public class WallAdapter extends ArrayAdapter<WallEntryItem> {
        public WallAdapter() {

            super(getActivity(), R.layout.record_entry, WallEntryItem.wallEntryItemList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;
            if (row == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.record_entry, parent, false);
            }

            WallEntryItem wallEntryItem = WallEntryItem.wallEntryItemList.get(position);
            ImageView im = (ImageView) row.findViewById(R.id.wallPostImage);
            TextView name = (TextView) row.findViewById(R.id.wallName);
            TextView mes = (TextView) row.findViewById(R.id.wallMes);
            ProgressBar p = (ProgressBar) row.findViewById(R.id.wallProgress);

//            im.setImageBitmap(wallEntryItem.getPic());
            name.setText(wallEntryItem.getTitle());
            mes.setText(wallEntryItem.getContent());
            p.setProgress(wallEntryItem.getProgress());

            return row;
//            }
        }
    }











    public class ReminderAdapter extends ArrayAdapter<String> {
        List<String> it;
        int ic;
        public ReminderAdapter(List<String> items, int icon) {

            super(getActivity(), R.layout.reminder_entry, items);
            it=items;
            ic = icon;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row1 = convertView;
            if (row1 == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row1 = layoutInflater.inflate(R.layout.reminder_entry, parent, false);
            }


            TextView name1 = (TextView) row1.findViewById(R.id.reminderTextview);
            ImageView im = (ImageView) row1.findViewById(R.id.remIcon);
            name1.setText(it.get(position));
            im.setImageResource(ic);
            return row1;
//            }
        }
    }





}