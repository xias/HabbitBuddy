package com.example.android.effectivenavigation.messenger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by J on 10/31/2016.
 */

public class FriendItem {
    public static final List<FriendItem> FriendItem_List = new ArrayList<>();

    static {
        // TODO  add friendlist from DB
        for (int i = 1; i <= 15; i++) {
            FriendItem_List.add(new FriendItem("Bread", "Pit", 647, 9243789+i));
        }
    }



    public  String fName;
    public  String lName;
    public  int phoneNumber;
    public  int areaNumber;


    public FriendItem(String fName, String lName, int areaNumber, int phoneNumber) {
        this.fName = fName;
        this.lName = lName;
        this.phoneNumber = phoneNumber;
        this.areaNumber = areaNumber;

    }


    public String getPhoneString() {
        String phoneString = String.valueOf(this.areaNumber) + String.valueOf(this.phoneNumber);
        return phoneString;
    }
}
