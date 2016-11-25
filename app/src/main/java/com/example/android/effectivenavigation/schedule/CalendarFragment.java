package com.example.android.effectivenavigation.schedule;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.effectivenavigation.FBHandler;
import com.example.android.effectivenavigation.MainActivity;
import com.example.android.effectivenavigation.R;
import com.example.android.effectivenavigation.SignUpActivity;
import com.example.android.effectivenavigation.Start.StartActivity;
import com.example.android.effectivenavigation.matching.SurveyActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.example.android.effectivenavigation.MainActivity.name;


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
        /*
        *  int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        *  way to get day of week
        * */


        Button startHabit = (Button)mView.findViewById(R.id.startButton);
        startHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),StartActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("pos",name);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
//        Toast.makeText(getActivity(), String.valueOf(System.currentTimeMillis()), Toast.LENGTH_SHORT).show();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                final Date date = new Date(year,month,dayOfMonth);
                calendar.setTime(date);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                final ListView listView = (ListView) mView.findViewById(R.id.dailyTaskView);

//                String[] items = {null, null};
//                FBHandler.checkBothSchedule(name,items);
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
//                listView.setAdapter(adapter);
                Firebase mRef = new Firebase("https://habitbuddy-9bca7.firebaseio.com/schedule");
                Firebase cRef = mRef.child("s93").child("start_end");
                final Date[] se_dates = new Date[2];
                cRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String s = dataSnapshot.getValue(String.class);
                        String[] dates = s.split(" ");
                        String[] dmy = dates[0].split("/");
                        String[] end_dmy = dates[1].split("/");
                        se_dates[0] = new Date(Integer.valueOf(dmy[2]),Integer.valueOf(dmy[0]),Integer.valueOf(dmy[1]));
                        se_dates[1] = new Date(Integer.valueOf(end_dmy[2]),Integer.valueOf(end_dmy[0]),Integer.valueOf(end_dmy[1]));
                        if(date.after(se_dates[0]) &&  date.before(se_dates[1])   ) {
                            String[] items = {"easy run", "Rick easy walk"};
                            //TODO add items from database(saved calendar entries)
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                            listView.setAdapter(adapter);
                        }else{
                            String[] items = {"No Activity today!"};
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                            listView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }


                });


                if(se_dates[0]!=null||se_dates[1]!=null) {
                    if (date.after(se_dates[0]) && date.before(se_dates[1])) {
                        String[] items = {"easy run", "Rick easy walk"};
                        //TODO add items from database(saved calendar entries)
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    } else {
                        String[] items = {"No Activity today!"};
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
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
