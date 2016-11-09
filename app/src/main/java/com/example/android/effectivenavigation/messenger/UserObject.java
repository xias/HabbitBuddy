package com.example.android.effectivenavigation.messenger;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by Xu on 2016-11-09.
 */
@IgnoreExtraProperties

public class UserObject {

    private String name;
    private String buddy;
    private String intake;
    private List<String> friendlist;
    private String diary;
    private String schedule;

    public UserObject(String buddy, String diary, List<String> friendlist, String intake, String name, String schedule) {
        this.buddy = buddy;
        this.diary = diary;
        this.friendlist = friendlist;
        this.intake = intake;
        this.name = name;
        this.schedule = schedule;
    }

    public UserObject() {
        this.buddy = null;
        this.diary = null;
        this.friendlist = null;
        this.intake = null;
        this.name = null;
        this.schedule = null;
    }


    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuddy() {
        return buddy;
    }

    public void setBuddy(String buddy) {
        this.buddy = buddy;
    }

    public String getDiary() {
        return diary;
    }

    public void setDiary(String diary) {
        this.diary = diary;
    }

    public List<String> getFriendlist() {
        return friendlist;
    }

    public void setFriendlist(List<String> friendlist) {
        this.friendlist = friendlist;
    }

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }
}
