package com.example.android.effectivenavigation.summary;


import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.effectivenavigation.R;

public class WallEntryActivity  extends Activity {
Button post;
    EditText com;
    EditText tit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_entry);
        post = (Button) findViewById(R.id.postButton);
        com = (EditText) findViewById(R.id.titleText);
        tit = (EditText) findViewById(R.id.actorText);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = com.getText().toString();
                String t = tit.getText().toString();
                WallEntryItem item = new WallEntryItem(BitmapFactory.decodeResource(getResources(), R.drawable.biking),m,7,"Lazy Dude",t);
                finish();
            }
        });





    }
}
