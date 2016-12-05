//package com.example.android.effectivenavigation.summary;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//
//import com.example.android.effectivenavigation.FBHandler;
//import com.example.android.effectivenavigation.MainActivity;
//import com.example.android.effectivenavigation.R;
//
///**
// * Created by Xu on 2016-12-01.
// */
//public class RecommendFragment extends Fragment {
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.recommend_fragment, container, false);
//        ListView list=(ListView)v.findViewById(R.id.buddies);
//
//        FBHandler.GetBuddiesImages(getActivity(),list, MainActivity.name,"buddyRecommend");
//    return v;
//    }
//
//}
