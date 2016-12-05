//package com.example.android.effectivenavigation.summary;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import com.example.android.effectivenavigation.FBHandler;
//import com.example.android.effectivenavigation.R;
//
//import static com.example.android.effectivenavigation.MainActivity.name;
//
///**
// * Created by Xu on 2016-12-01.
// */
//public class DefaultFragment extends Fragment {
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        Log.v("Default","dfadas");
//        View v = inflater.inflate(R.layout.default_fragment, container, false);
//
//        Log.v("Default","dfadas");
//        Button matchB = (Button) v.findViewById(R.id.match_buddy_button);
//        matchB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                RecommendFragment newFragment = new RecommendFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//// Replace whatever is in the fragment_container view with this fragment,
//// and add the transaction to the back stack if needed
//                transaction.replace(R.id.fragment_container, newFragment);
//                transaction.addToBackStack(null);
//
//// Commit the transaction
//                transaction.commit();
//                FBHandler.Match(name,v);
//            }
//        });
//        return v;
//    }
//}
