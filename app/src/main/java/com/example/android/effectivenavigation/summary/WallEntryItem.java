package com.example.android.effectivenavigation.summary;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by J on 11/3/2016.
 */

public class WallEntryItem {
    private Bitmap pic;
    private String name;
    private int progress;
    private String comment;
    public static List<WallEntryItem> wallEntryItemList = new ArrayList<>();

    public Bitmap getPic() {
        return pic;
    }

    public String getComment() {
        return comment;
    }

    public int getProgress() {
        return progress;
    }

    public String getName() {
        return name;
    }

    public WallEntryItem(Bitmap pic, String comment, int progress, String name) {
        this.pic = pic;
        this.comment = comment;
        this.progress = progress;
        this.name = name;
        wallEntryItemList.add(this);
    }
}
