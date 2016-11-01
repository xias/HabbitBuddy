package com.example.android.effectivenavigation.schedule;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.effectivenavigation.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CalendarFragment extends Fragment {


    CalendarView calendarView;
    ListView taskView;
    ArrayList<String> data = new ArrayList<>();


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
        final View mView = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = (CalendarView) mView.findViewById(R.id.calView);
        taskView = (ListView) mView.findViewById(R.id.dailyTaskView);
        calendarView.setDate(System.currentTimeMillis());
        Toast.makeText(getActivity(), String.valueOf(System.currentTimeMillis()), Toast.LENGTH_SHORT).show();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                Date date = new Date(year,month,dayOfMonth);
                calendar.setTime(date);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                ListView listView = (ListView) mView.findViewById(R.id.dailyTaskView);

                if(dayOfWeek == 2 || dayOfWeek == 4 || dayOfWeek == 6) {
                    String[] items = {"Run 1 mile", "Lincoln run 1 mile", "Jefferson Run 1 mile", "Roosevelt Run 1 mile", "Gore Run 1 mile"};
                    //TODO add items from database(saved calendar entries)
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                    listView.setAdapter(adapter);
                }else{
                    String[] items = {"No Activity today!"};
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                    listView.setAdapter(adapter);
                }




            }
        });



        return mView;
    }

    @Override
    public void onStart(){
        super.onStart();
        calendarView.setDate(System.currentTimeMillis());
}


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


}
