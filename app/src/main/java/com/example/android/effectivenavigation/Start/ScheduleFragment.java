package com.example.android.effectivenavigation.Start;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.effectivenavigation.FBHandler;
import com.example.android.effectivenavigation.MainActivity;
import com.example.android.effectivenavigation.R;
import com.example.android.effectivenavigation.matching.SurveyActivity;

import static com.example.android.effectivenavigation.MainActivity.name;

/**
 * Created by Xu on 2016-11-23.
 */


public class ScheduleFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_schedule, container, false);


        name = getActivity().getIntent().getExtras().getString("pos");
        Log.v("FragmentActivity",name);
        Button finish = (Button) v.findViewById(R.id.buttonFinish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);


                FBHandler.ActivityMatch(name,"jason");
                Bundle mBundle = new Bundle();
                mBundle.putString("pos",name);
                intent.putExtras(mBundle);
                startActivity(intent);
                getActivity().finish();


            }
        });
        return v;
    }

}