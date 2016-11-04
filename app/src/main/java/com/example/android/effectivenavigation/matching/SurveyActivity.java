package com.example.android.effectivenavigation.matching;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.android.effectivenavigation.MainActivity;
import com.example.android.effectivenavigation.R;
import com.example.android.effectivenavigation.SignUpActivity;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SurveyActivity extends Activity {
    private int[] q_result = new int[7];
    private Button match;
    private SeekBar s1;
    private SeekBar s2;
    private SeekBar s3;
    private SeekBar s4;
    private SeekBar s5;
    private SeekBar s6;
    private SeekBar s7;
    private static String newS;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        final String name= getIntent().getStringExtra("pos");
        s1 = (SeekBar) findViewById(R.id.seekBar2);
        s2 = (SeekBar) findViewById(R.id.seekBar3);
        s3 = (SeekBar) findViewById(R.id.seekBar4);
        s4 = (SeekBar) findViewById(R.id.seekBar5);
        s5 = (SeekBar) findViewById(R.id.seekBar6);
        s6 = (SeekBar) findViewById(R.id.seekBar7);
        s7 = (SeekBar) findViewById(R.id.seekBar8);
        match = (Button) findViewById(R.id.match_button);
        match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String res = " ";
                res += (String.valueOf(s1.getProgress())+" ");
                res += (String.valueOf(s2.getProgress())+" ");
                res += (String.valueOf(s3.getProgress())+" ");
                res += (String.valueOf(s4.getProgress())+" ");
                res += (String.valueOf(s5.getProgress())+" ");
                res += (String.valueOf(s6.getProgress())+" ");
                res += (String.valueOf(s7.getProgress())+" ");


                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference(name);
                myRef.setValue(res);

                final Firebase mRef = new Firebase("https://habitbuddy-9bca7.firebaseio.com/message");
                mRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        Log.v("NEWWWW", dataSnapshot.getValue(String.class));
                        newS = dataSnapshot.getValue(String.class);
                        Log.v("NEWWWW","JKFJLDFJLKK");
                        newS = newS + " "+name;


                        mRef.setValue(newS);

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }

                });







                Intent intent = new Intent(SurveyActivity.this,MainActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("pos",name);
                intent.putExtras(mBundle);
                startActivity(intent);
                finish();

            }
        });






    }

}
