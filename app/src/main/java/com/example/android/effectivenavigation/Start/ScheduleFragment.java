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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

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
    private SeekBar s1;
    private SeekBar s2;
    private SeekBar s3;
    private SeekBar s4;
    private EditText editText;
    private int interval = 3;
    private int length = 21;
    private String s5 = "3";
    private String s6 = "21";
    private String s7 = "Easy";
    private String s51 = "1";
    private String s61 = "1";
    private String s71 = "1";
    private String title = "running";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_schedule, container, false);
        final String[] type = {null};
        Log.v("TESTTTTT","fdadads");
//        final DatePicker datePicker1 = (DatePicker) v.findViewById(R.id.datePickerStart);
//        final DatePicker datePicker2 = (DatePicker) v.findViewById(R.id.datePickerEnd);

        editText = (EditText) v.findViewById(R.id.edit_habit);


//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//        {
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch(checkedId){
//                    case R.id.radio_running:
//                        // do operations specific to this selection
//                        type[0] = "running";
//                        break;
//                    case R.id.radio_walking:
//                        // do operations specific to this selection
//                        type[0] = "walking";
//                        break;
//                }
//            }
//        });


        RadioGroup rg = (RadioGroup) v.findViewById(R.id.vector2);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_1_1:
                        // do operations specific to this selection
                        s5 = "3";
                        interval = 3;
                        s51 = "1";
                        break;
                    case R.id.radio_1_2:
                        // do operations specific to this selection
                        s5 = "2";
                        interval = 2;
                        s51 = "2";
                        break;
                    case R.id.radio_1_3:
                        // do operations specific to this selection
                        s5 = "1";
                        interval = 1;
                        s51 = "3";
                        break;
                }
            }
        });

        s1 = (SeekBar) v.findViewById(R.id.vector1);
        s2 = (SeekBar) v.findViewById(R.id.vector4);
        s3 = (SeekBar) v.findViewById(R.id.vector6);
        s4 = (SeekBar) v.findViewById(R.id.vector7);
        RadioGroup rg2 = (RadioGroup) v.findViewById(R.id.vector3);

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_2_1:
                        // do operations specific to this selection
                        s6 = "21";
                        length = 21;
                        s61 = "1";
                        break;
                    case R.id.radio_2_2:
                        // do operations specific to this selection
                        s6 = "28";
                        length = 28;
                        s61 = "2";
                        break;
                    case R.id.radio_2_3:
                        // do operations specific to this selection
                        s6 = "36";
                        s61 = "3";
                        length = 36;
                        break;
                    case R.id.radio_2_4:
                        // do operations specific to this selection
                        s6 = "50";
                        length = 50;
                        s61 = "4";
                        break;
                }
            }
        });

        RadioGroup rg3 = (RadioGroup) v.findViewById(R.id.vector5);

        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_3_1:
                        // do operations specific to this selection
                        s7 = "Easy";
                        s71 = "1";
                        break;
                    case R.id.radio_3_2:
                        // do operations specific to this selection
                        s7 = "Moderate";
                        s71 = "2";
                        break;
                    case R.id.radio_3_3:
                        // do operations specific to this selection
                        s7 = "High-Intensity";
                        s71 = "3";
                        break;
                }
            }
        });



//        ListView listView = (ListView) v.findViewById(R.id.chooseFrequency);
//        ArrayList<String> list = new ArrayList<>();
//
//        list.add("Monday");
//        list.add("Tuesday");
//
//        list.add("Wednesday");
//        list.add("Thursday");
//        list.add("Friday");
//        list.add("Saturday");
//        list.add("Sunday");

//        final int[] flag = new int[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            flag[i] = 0;
//        }
//        //instantiate custom adapter
//        FrequencyAdapter adapter = new FrequencyAdapter(list,flag, getActivity());
//        listView.setAdapter(adapter);

//        name = getActivity().getIntent().getExtras().getString("pos");


        Button finish = (Button) v.findViewById(R.id.buttonFinish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = String.valueOf(editText.getText());
                String res = " ";
                res += (String.valueOf(s1.getProgress())+" ");
                res += s51+" ";
                res += s61+" ";
                res += (String.valueOf(s2.getProgress())+" ");
                res += s71+" ";
                res += (String.valueOf(s3.getProgress())+" ");
                res += (String.valueOf(s4.getProgress())+" ");
                Log.v("String",s5+s6+s7+title);
                FBHandler.StartHabitResult(name,res,title);
                FBHandler.storeHabit(s5,s6,s7);

                String scheduleArray = null;
                for (int i = 0 ; i < length; i++) {
                    if (interval == 1) {
                        if (i == 0) {
                            scheduleArray = "1";
                        } else {
                            scheduleArray = scheduleArray + "," +"1";
                        }
                    }
                    else if (interval == 2){
                        if (i == 0) {
                            scheduleArray = "1";
                        } else if (i%2==1){
                            scheduleArray = scheduleArray + "," +"0";
                        } else {
                            scheduleArray = scheduleArray + "," +"1";
                        }
                    }else if (interval == 3){
                        if (i == 0) {
                            scheduleArray = "1";
                        } else if (i%3==1 || i%3==2){
                            scheduleArray = scheduleArray + "," +"0";
                        } else {
                            scheduleArray = scheduleArray + "," +"1";
                        }
                    }
                }

                String record = null;
                for (int i = 0 ; i < length; i++) {

                        if (i == 0) {
                            record = "0";
                        } else {
                            record = record + "," +"0";
                        }

                }
                Log.v("schedule", scheduleArray+record);
                FBHandler.setScheduleAndRecord(scheduleArray,record);
//                res += (String.valueOf(s5.getProgress())+" ");
//                res += (String.valueOf(s6.getProgress())+" ");
//                res += (String.valueOf(.getProgress())+" ");
//                Log.v("start",getDate(datePicker1));
//                Log.v("end",getDate(datePicker2));
//                //Log.v("ToggleResults", Arrays.toString(flag));
//
//                String temp = null;
//                for (int j = 0; j<flag.length; j++) {
//                    if (temp == null){
//                        temp = String.valueOf(flag[j]);
//                    } else {
//                        temp = temp+"|"+flag[j];
//                    }
//
//                }
//
//                Log.v("******",name);
//                FBHandler.SetSchedule(name,cate,getDate(datePicker1),getDate(datePicker2),temp,intensity[0],type[0]);
//
//
//
//
////                FBHandler.ActivityMatch(name,"jason");
                Intent intent = new Intent(getActivity(),MainActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("pos",name);
                intent.putExtras(mBundle);
                FBHandler.addInPool(name);
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