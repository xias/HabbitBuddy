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

import java.io.IOException;
import java.util.ArrayList;

import static com.example.android.effectivenavigation.summary.DiscoverFragment.decodeFromFirebaseBase64;

/**
 * Created by Xu on 2016-11-23.
 */

public class ProfileAdapter extends BaseAdapter {

    private Activity activity;
    private String[] names;
    private String[] data;
    private static LayoutInflater inflater=null;
    private ArrayList<String> list = new ArrayList<String>();

    public ProfileAdapter(Activity a, String[] s, String[] d) {
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

            vi = inflater.inflate(R.layout.item, null);

        TextView text=(TextView)vi.findViewById(R.id.text);;
        ImageView image=(ImageView)vi.findViewById(R.id.image);
        text.setText(names[position]);

        Bitmap imageBitmap = null;
        try {

            imageBitmap = decodeFromFirebaseBase64(data[position]);
            image.setImageBitmap(imageBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vi;
    }
}