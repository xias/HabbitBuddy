package com.example.android.effectivenavigation.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.effectivenavigation.R;
import com.firebase.ui.FirebaseListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static com.example.android.effectivenavigation.summary.DiscoverFragment.decodeFromFirebaseBase64;

/**
 * Created by Xu on 2016-11-25.
 */

public class FriendListAdapter extends BaseAdapter{

    private Activity activity;
    private String[] names;
    private String[] data;
    private static LayoutInflater inflater=null;
    private ArrayList<String> list = new ArrayList<String>();
    private FirebaseListAdapter listAdapter = null;


    public FriendListAdapter(Activity a, String[] s, String[] d) {
        activity = a;
        names=s;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return names.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)

            vi = inflater.inflate(R.layout.fragment_frienditem, null);

        TextView text=(TextView)vi.findViewById(R.id.text);;
        ImageView image=(ImageView)vi.findViewById(R.id.image);
        text.setText(names[position]);
        TextView textView = (TextView)vi.findViewById(R.id.text2);
        int seven=0;
        for (int k = 0; k< 7;k++){
            int random = (int )(Math.random() * 9 + 1);
            seven=seven+random*(int) Math.pow(10, k);
        }


        textView.setText("647"+"-"+seven);
//        Bitmap imageBitmap = null;
        try {

//            imageBitmap = decodeFromFirebaseBase64(data[position]);




            image.setImageBitmap(decodeFromFirebaseBase64(data[position]));
            data[position]=null;
            System.gc();
//            imageBitmap.recycle();
//            imageBitmap=null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vi;
    }
}
