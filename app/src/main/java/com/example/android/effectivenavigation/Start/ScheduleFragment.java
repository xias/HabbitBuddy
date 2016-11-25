package com.example.android.effectivenavigation.Start;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.android.effectivenavigation.FBHandler;
import com.example.android.effectivenavigation.MainActivity;
import com.example.android.effectivenavigation.R;
import com.example.android.effectivenavigation.adapter.FrequencyAdapter;
import com.example.android.effectivenavigation.matching.SurveyActivity;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.android.effectivenavigation.MainActivity.name;
import static com.example.android.effectivenavigation.Start.StartActivity.cate;

/**
 * Created by Xu on 2016-11-23.
 */


public class ScheduleFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_schedule, container, false);
        final String[] type = {null};
        Log.v("TESTTTTT","fdadads");
        final DatePicker datePicker1 = (DatePicker) v.findViewById(R.id.datePickerStart);
        final DatePicker datePicker2 = (DatePicker) v.findViewById(R.id.datePickerEnd);
        final String[] intensity = {null};


        RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.exercise_habit);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_running:
                        // do operations specific to this selection
                        type[0] = "running";
                        break;
                    case R.id.radio_walking:
                        // do operations specific to this selection
                        type[0] = "walking";
                        break;
                }
            }
        });


        RadioGroup rg = (RadioGroup) v.findViewById(R.id.radio);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_1:
                        // do operations specific to this selection
                        intensity[0] = "easy";
                        break;
                    case R.id.radio_2:
                        // do operations specific to this selection
                        intensity[0] = "moderate";
                        break;
                    case R.id.radio_3:
                        // do operations specific to this selection
                        intensity[0] = "high-intensity";
                        break;
                }
            }
        });



        ListView listView = (ListView) v.findViewById(R.id.chooseFrequency);
        ArrayList<String> list = new ArrayList<>();

        list.add("Monday");
        list.add("Tuesday");

        list.add("Wednesday");
        list.add("Thursday");
        list.add("Friday");
        list.add("Saturday");
        list.add("Sunday");

        final int[] flag = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            flag[i] = 0;
        }
        //instantiate custom adapter
        FrequencyAdapter adapter = new FrequencyAdapter(list,flag, getActivity());
        listView.setAdapter(adapter);

//        name = getActivity().getIntent().getExtras().getString("pos");


        Button finish = (Button) v.findViewById(R.id.buttonFinish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("start",getDate(datePicker1));
                Log.v("end",getDate(datePicker2));
                //Log.v("ToggleResults", Arrays.toString(flag));

                String temp = null;
                for (int j = 0; j<flag.length; j++) {
                    if (temp == null){
                        temp = String.valueOf(flag[j]);
                    } else {
                        temp = temp+"|"+flag[j];
                    }

                }

                Log.v("******",name);
                FBHandler.SetSchedule(name,cate,getDate(datePicker1),getDate(datePicker2),temp,intensity[0],type[0]);




//                FBHandler.ActivityMatch(name,"jason");
                Intent intent = new Intent(getActivity(),MainActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("pos",name);
                intent.putExtras(mBundle);
                startActivity(intent);
                getActivity().finish();


            }
        });
        return v;
    }


    private String getDate(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        return year+"-"+month+"-"+day;
    }


}