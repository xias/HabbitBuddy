package com.example.android.effectivenavigation.adapter;

/**
 * Created by Xu on 2016-11-23.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.android.effectivenavigation.R;

import java.util.ArrayList;



/**
 * Created by Xu on 2016-11-23.
 */

public class FrequencyAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private int[] flag;
    private Context context;



    public FrequencyAdapter(ArrayList<String> list,int[] flag, Context context) {
        this.list = list;
        this.context = context;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.frequency_list_layout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        ToggleButton addBtn = (ToggleButton)view.findViewById(R.id.add_btn);
        addBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.v("Toggle", String.valueOf(position)+buttonView.isChecked());
                if (buttonView.isChecked()){
                    flag[position]=1;
                }
                else {
                    flag[position]=0;
                }
            }
        });
//        addBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                //do something
//                notifyDataSetChanged();
//            }
//        });

        return view;
    }
}
