/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.effectivenavigation;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.effectivenavigation.messenger.FriendItem;
import com.example.android.effectivenavigation.messenger.FriendItemFragment;
import com.example.android.effectivenavigation.schedule.CalendarFragment;
import com.example.android.effectivenavigation.summary.BuddyCenterFragment;
import com.example.android.effectivenavigation.summary.DiscoverFragment;
import com.example.android.effectivenavigation.summary.PerformanceSummaryFragment;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener, FriendItemFragment.OnListFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    public static String user_name = "default";

    public static FirebaseDatabase database;
    private static BuddyCenterFragment buddyCenterFragment;
    private static Fragment cFragment=null;
    private static Fragment fFragment=null;
    private static Fragment pFragment=null;
    private static Fragment dFragment=null;

    private static Fragment discoverFragment = null;
    private Fragment mContent;
    private FragmentManager mFragmentMan;


    ViewPager mViewPager;


    public void onListFragmentInteraction(FriendItem friendItem) {
        final String number = friendItem.getPhoneString();

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to message your buddy?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private String readLogin(){
        String ret=null;
        try {
            InputStream inputStream = this.getApplicationContext().openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (IOException e) {
            Log.e("login activity", "File not found: " + e.toString());
        }
        return ret;
    }




    public static String name= "default";
    public void onCreate(Bundle savedInstanceState) {
        name= "default";
        //TODO go to login activity if not logged in
        super.onCreate(savedInstanceState);
        user_name = readLogin();
        if (user_name == null || user_name.equals("")) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
                setContentView(R.layout.activity_main);
            getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0089D0")));
            name = user_name;
    //        name= getIntent().getStringExtra("pos");


            FBHandler.getThisUser("gorett");
            FBHandler.getFriendlist("gorett");
    //       database = FirebaseDatabase.getInstance();
    //
    //        DatabaseReference databaseReference = database.getReference("habitbuddy-9bca7");


            // Create the adapter that will return a fragment for each of the three primary sections
            // of the app.
            mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

            // Set up the action bar.
            final ActionBar actionBar = getActionBar();

            // Specify that the Home/Up button should not be enabled, since there is no hierarchical
            // parent.
            actionBar.setHomeButtonEnabled(false);

            // Specify that we will be displaying tabs in the action bar.
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

//            actionBar.setStackedBackgroundDrawable(getResources().getDrawable(
//                    R.drawable.tab));
            actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#0089D0")));
            // Set up the ViewPager, attaching the adapter and setting up a listener for when the
            // user swipes between sections.
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mAppSectionsPagerAdapter);
            mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    // When swiping between different app sections, select the corresponding tab.
                    // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                    // Tab.
                    actionBar.setSelectedNavigationItem(position);
                }
            });


            // For each of the sections in the app, add a tab to the action bar.
            for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
                // Create a tab with text corresponding to the page title defined by the adapter.
                // Also specify this Activity object, which implements the TabListener interface, as the
                // listener for when this tab is selected.
                actionBar.addTab(
                        actionBar.newTab()
                                .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                                .setTabListener(this));
            }


        }
    }

//    @Override
//    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//    }
//
//    @Override
//    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//        // When the given tab is selected, switch to the corresponding page in the ViewPager.
//        // Set the color of the top bar
//
//    }

//    public void switchContent(Fragment from, Fragment to) {
//        if (!mContent.equals(to)) {
//            mContent = to;
//            FragmentTransaction transaction = mFragmentMan.beginTransaction();
//            if (!to.isAdded()) {	// 先判断是否被add过
//                transaction.hide(from).add(R.id., to).commit(); // 隐藏当前的fragment，add下一个到Activity中
//            } else {
//                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
//            }
//        }
//    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
//        tab.setCustomView(R.layout.custom);

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        public static FragmentManager fragmentManager;

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager=fm;
        }

        @Override
        public Fragment getItem(int i) {



            switch (i) {
                case 0:
                    // The first section of the app is the most interesting -- it offers
                    // a launchpad into the other demonstrations in this example application.
//                    return new LaunchpadSectionFragment();
                    if(buddyCenterFragment == null) {
                        buddyCenterFragment = new BuddyCenterFragment();
                    }
//                    fragmentManager.popBackStack();

                    return buddyCenterFragment;
                case 1:
//                    if(fFragment == null) {
//                        fFragment = new FriendItemFragment();
//                    }
////                    fragmentManager.popBackStack();
//
//                    return fFragment;
                    if (discoverFragment == null) {
                        discoverFragment = new DiscoverFragment();

                    }
//                    fragmentManager.popBackStack();

                    return discoverFragment;
                case 2:
                    if(cFragment == null) {
                        cFragment = new CalendarFragment();

                        Bundle args = new Bundle();
                        args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
                        cFragment.setArguments(args);
                    }
//                    fragmentManager.popBackStack();

                    return cFragment;


                case 3:
                    if( fFragment== null) {

                        fFragment = new FriendItemFragment();
                    }
//                    fragmentManager.popBackStack();

                    return fFragment;
                case 4:
                    if(pFragment == null) {

                        pFragment = new PerformanceSummaryFragment();
                    }
//                    fragmentManager.popBackStack();

                    return pFragment;
                default:

//                    // The other sections of the app are dummy placeholders.
                    if(dFragment == null) {

                        dFragment = new DummySectionFragment();
                        Bundle dargs = new Bundle();
                        dargs.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
                        dFragment.setArguments(dargs);
                    }
//                    fragmentManager.popBackStack();

                    return dFragment;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Habit Center";
                case 1:
                    return "Habit Hangout";
                case 2:
                    return "Habit schedule";
                case 3:
                    return "Messaging";
                case 4:
                    return "Habit Summary";
            }
            return "Section " + (position + 1);
        }
    }

    /**
     * A fragment that launches other parts of the demo application.
     */
    public static class LaunchpadSectionFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_section_launchpad, container, false);

            // Demonstration of a collection-browsing activity.
            rootView.findViewById(R.id.demo_collection_button)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), CollectionDemoActivity.class);
                            startActivity(intent);
                        }
                    });

            // Demonstration of navigating to external activities.
            rootView.findViewById(R.id.demo_external_activity)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Create an intent that asks the user to pick a photo, but using
                            // FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET, ensures that relaunching
                            // the application from the device home screen does not return
                            // to the external activity.
                            Intent externalActivityIntent = new Intent(Intent.ACTION_PICK);
                            externalActivityIntent.setType("image/*");
                            externalActivityIntent.addFlags(
                                    Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                            startActivity(externalActivityIntent);
                        }
                    });


            return rootView;
        }
    }
        /**
         * A dummy fragment representing a section of the app, but that simply displays dummy text.
         */
        public static class DummySectionFragment extends Fragment {

            public static final String ARG_SECTION_NUMBER = "section_number";

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_section_dummy, container, false);
                Bundle args = getArguments();
                ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                        getString(R.string.dummy_section_text, args.getInt(ARG_SECTION_NUMBER)));
                return rootView;
            }
        }

    public void message() {

    }


}
