//package com.example.android.effectivenavigation.Start;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageButton;
//
//import com.example.android.effectivenavigation.MainActivity;
//import com.example.android.effectivenavigation.R;
//
//import static com.example.android.effectivenavigation.MainActivity.name;
//
///**
// * Created by Xu on 2016-11-23.
// */
//public class ScheduleActivity extends Activity {
//
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_schedule);
//        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#118C4E")));
//        name= getIntent().getStringExtra("pos");
//        String cate= getIntent().getStringExtra("cate");
////        ImageButton imageButton = (ImageButton)findViewById(R.id.imageButtonExercise);
////        imageButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(ScheduleActivity.this,MainActivity.class);
////                Bundle mBundle = new Bundle();
////                mBundle.putString("pos",name);
////                intent.putExtras(mBundle);
////                startActivity(intent);
////                finish();
////            }
////        });
//    }
//}
