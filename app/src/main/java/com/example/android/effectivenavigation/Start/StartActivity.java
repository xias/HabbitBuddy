package com.example.android.effectivenavigation.Start;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.android.effectivenavigation.R;
import com.example.android.effectivenavigation.matching.SurveyActivity;

import static com.example.android.effectivenavigation.MainActivity.name;

/**
 * Created by Xu on 2016-11-22.
 */

public class StartActivity extends Activity {
    static String cate;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#118C4E")));
//        name= getIntent().getStringExtra("pos");
        ImageButton imageButton = (ImageButton)findViewById(R.id.imageButtonExercise);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cate = "exercise";
                Bundle bundle = new Bundle();
                bundle.putString("pos",name);

                ScheduleFragment scheduleFragment = new ScheduleFragment();


                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                //scheduleFragment.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container' FrameLayout
                getFragmentManager().beginTransaction()
                        .add(R.id.schedule_container, scheduleFragment).commit();

            }
        });
        ImageButton imageButton2 = (ImageButton)findViewById(R.id.imageButton3);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cate = "sleep hygiene";
                Bundle bundle = new Bundle();
                bundle.putString("pos",name);

                ScheduleFragment scheduleFragment = new ScheduleFragment();


                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                //scheduleFragment.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container' FrameLayout
                getFragmentManager().beginTransaction()
                        .add(R.id.schedule_container, scheduleFragment).commit();

            }
        });
    }
}
