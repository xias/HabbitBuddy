package com.example.android.effectivenavigation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;


public class CalendarFragment extends Fragment {


    CalendarView calendarView;
    ListView taskView;


    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = (CalendarView) mView.findViewById(R.id.calView);
        taskView = (ListView) mView.findViewById(R.id.dailyTaskView);
        calendarView.setDate(System.currentTimeMillis());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//TODO update listview when date is selected. include activities of yourself and buddy in different colors.
                Toast.makeText(getActivity(), "date selected"+ Integer.toString(dayOfMonth), Toast.LENGTH_SHORT).show();
            }
        });



        return mView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


}
