package com.example.android.effectivenavigation.summary;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.effectivenavigation.CollectionDemoActivity;
import com.example.android.effectivenavigation.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    private ListView wall;
    private ListView task;
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
            new WallEntryItem(BitmapFactory.decodeResource(getResources(), R.drawable.scenery),
                    "I went to a great park today. Let me know if anyone wants to join next time", 30, "Rick","Queens park!");
            new WallEntryItem(BitmapFactory.decodeResource(getResources(), R.drawable.comp),
                    "I did not do anything today", 5, "Lazy dude","zzz");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO initiate dummy wall post content


        List<String> tasks = new ArrayList<>(3);
        tasks.add("Walk for 2 miles. ");
        tasks.add("Update my progress with Rob");
        tasks.add("Prepare for new diet plan. ");

        List<String> rems = new ArrayList<>(3);
        rems.add("Play Pokemon Go if you need motivation. ");
        rems.add("Bring water when you walk. ");
        rems.add("Remember to update your friends after done walking!");
        rems.add("Bring your umbrella if it's windy and cloudy outside. ");
        rems.add("Maybe you can walk to get grocery today? ");





        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buddy_center, container, false);
        wall = (ListView) v.findViewById(R.id.wallList);
        reminder = (ListView) v.findViewById(R.id.reminderList);
        task = (ListView) v.findViewById(R.id.recentTaskList);

        WallAdapter wallAdapter = new WallAdapter();
        ReminderAdapter reminderAdapter = new ReminderAdapter(rems,android.R.drawable.btn_star_big_on);
        ReminderAdapter taskAdapter = new ReminderAdapter(tasks, android.R.drawable.ic_input_add);
//        ArrayAdapter<String> taskAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, tasks);

        wall.setAdapter(wallAdapter);
        reminder.setAdapter(reminderAdapter);
        task.setAdapter(taskAdapter);

        Button newEnt = (Button) v.findViewById(R.id.newButton);
        newEnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WallEntryActivity.class);
                startActivity(intent);            }
        });






        return v;
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

            im.setImageBitmap(wallEntryItem.getPic());
            name.setText(wallEntryItem.getName());
            mes.setText(wallEntryItem.getComment());
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