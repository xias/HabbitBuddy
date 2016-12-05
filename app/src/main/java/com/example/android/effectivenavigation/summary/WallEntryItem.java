package com.example.android.effectivenavigation.summary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by J on 11/3/2016.
 */

public class WallEntryItem {
    private String pic;
    private String title;
    private int progress;
    private String content;
    public static List<WallEntryItem> wallEntryItemList = new ArrayList<>();

    public String getPic() {
        return pic;
    }



    //TODO return bitmap given the url
//    public Bitmap getPicFromFBURL(String FBURL){
//        try{
//            new AsyncTask("AsyncLoadImage").execute(FBURL);
//        }catch (Exception e){
//            Log.e("Get pic from url ","Failed at calling async class");
//        }
//    }

//    public class AsyncLoadImage extends AsyncTask<String, String, Bitmap>{
//        private final String TAG = "AsyncLoadImage";
//
//        private Bitmap result = null;
//        public Bitmap getResult() {
//            return result;
//        }
//
//
//        @Override
//        protected Bitmap doInBackground(String... params){
//            Bitmap bitmap = null;
//            try{
//                URL url = new URL(params[0]);
//                bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
//
//            }catch(IOException e){
//                Log.e("url error", "url failed: "+ params[0].toString());
//            }
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap){
//            this.result = bitmap;
//
//
//
//        }
//    }






    public String getContent() {
        return content;
    }

    public int getProgress() {
        return progress;
    }

    public String getTitle() {
        return title;
    }

    public WallEntryItem(String pic, String content, int progress, String title, String ti) {
        this.pic = pic;
        this.content = content;
        this.progress = progress;
        this.title = title +": "+ti;
        wallEntryItemList.add(this);
    }

    public WallEntryItem(){

    }

    public WallEntryItem(String pic, String content, int progress, String title) {
        this.pic = pic;
        this.content = content;
        this.progress = progress;
        this.title = title +": ";
        wallEntryItemList.add(this);
    }

}


//
//package com.example.android.effectivenavigation.summary;
//
//        import android.graphics.Bitmap;
//        import android.graphics.BitmapFactory;
//        import android.os.AsyncTask;
//        import android.util.Log;
//
//        import java.io.IOException;
//        import java.io.InputStream;
//        import java.net.URL;
//        import java.util.ArrayList;
//        import java.util.List;
//
///**
// * Created by J on 11/3/2016.
// */
//
//public class WallEntryItem {
//    private Bitmap pic;
//    private String title;
//    private int progress;
//    private String content;
//    public static List<WallEntryItem> wallEntryItemList = new ArrayList<>();
//
//    public Bitmap getPic() {
//        return pic;
//    }
//
////    public Bitmap getPicFromFBURL(String FBURL){
////        try{
////            new AsyncTask("AsyncLoadImage").execute(FBURL);
////        }catch (Exception e){
////            Log.e("Get pic from url ","Failed at calling async class");
////        }
////    }
//
//    public class AsyncLoadImage extends AsyncTask<String, String, Bitmap>{
//        private final String TAG = "AsyncLoadImage";
//
//        private Bitmap result = null;
//        public Bitmap getResult() {
//            return result;
//        }
//
//
//        @Override
//        protected Bitmap doInBackground(String... params){
//            Bitmap bitmap = null;
//            try{
//                URL url = new URL(params[0]);
//                bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
//            }catch(IOException e){
//                Log.e("url error", "url failed: "+ params[0].toString());
//            }
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap){
//            this.result = bitmap;
//        }
//    }
//
//
//
//
//
//
//    public String getContent() {
//        return content;
//    }
//
//    public int getProgress() {
//        return progress;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public WallEntryItem(Bitmap pic, String content, int progress, String title, String ti) {
//        this.pic = pic;
//        this.content = content;
//        this.progress = progress;
//        this.title = title +": "+ti;
//        wallEntryItemList.add(this);
//    }
//
//    public WallEntryItem(Bitmap pic, String content, int progress, String title) {
//        this.pic = pic;
//        this.content = content;
//        this.progress = progress;
//        this.title = title +": ";
//        wallEntryItemList.add(this);
//    }
//
//
//
//
//}
