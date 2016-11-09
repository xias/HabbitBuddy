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
        int i = 9;
            FriendItem_List.add(new FriendItem("Marvel", "Jackson", 647, 2938578+i));
            FriendItem_List.add(new FriendItem("Angelica", "Hends", 647, 9135363+i));
            FriendItem_List.add(new FriendItem("John", "Hunt", 647, 9223523+i));
            FriendItem_List.add(new FriendItem("Morphis", "Pickles", 647, 2353789+i));
            FriendItem_List.add(new FriendItem("Trinity", "Silver", 647, 2353589+i));
            FriendItem_List.add(new FriendItem("Bob", "Schneider", 647, 5352349+i));
            FriendItem_List.add(new FriendItem("Michael", "Lars", 647, 9515252+i));
            FriendItem_List.add(new FriendItem("Larry", "Timberman", 647, 5251789+i));
            FriendItem_List.add(new FriendItem("Wayne", "Schuler", 647, 94214129+i));
            FriendItem_List.add(new FriendItem("Olivia", "Bucket", 647, 51252489+i));
            FriendItem_List.add(new FriendItem("Ava", "Zhang", 647, 2512525+i));
            FriendItem_List.add(new FriendItem("Chloe", "Su", 647, 1252525));
            FriendItem_List.add(new FriendItem("Dakota", "Mick", 647, 1242789+i));
            FriendItem_List.add(new FriendItem("Madison", "Jobs", 647, 1233789+i));
            FriendItem_List.add(new FriendItem("Mia", "Waller", 647, 6362389+i));

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
