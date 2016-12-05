package com.example.android.effectivenavigation.summary;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.effectivenavigation.R;
import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.android.effectivenavigation.MainActivity.name;

public class WallEntryActivity  extends Activity {
    private static final int WRITE_EXTERNAL_STORAGE = 1212;
    private static final int IMAGE_SCALE = 240;


    Button post;
    Button addPic;
    EditText com;
    EditText tit;
    boolean isPicTaken = false;
    String bitmapString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_entry);
        post = (Button) findViewById(R.id.postButton);
        tit = (EditText) findViewById(R.id.titleText);
        com = (EditText) findViewById(R.id.actorText);
        addPic = (Button) findViewById(R.id.addPicButton);
        bitmapString = null;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss");

        final String postTime = String.valueOf(Long.parseLong(simpleDateFormat.format(c.getTime())));


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = com.getText().toString();
                String t = tit.getText().toString();
                if(m.isEmpty() || t.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter title and comment!", Toast.LENGTH_LONG);
                }else{
                    Firebase postRef = new Firebase("https://habitbuddy-9bca7.firebaseio.com/users/"+name+"/post");
                    //TODO push item to firebase, upload image with url returned
                    if(bitmapString == null){
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_diary,options);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 10, baos);
                        bitmapString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                    }
                    postRef.child(postTime).setValue(new WallEntryItem(bitmapString, m, 30, t));
                    bitmapString = null;
                    finish();
                }
            }
        });

        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"clicked add pic", Toast.LENGTH_LONG);
                Log.d("pic", "pressed button");

                viewPhoto(v);

            }
        });




    }


    private static final int RESULT_GALLERY = 5;
    private static Uri imageUri;

    public void viewPhoto(View view) {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent , RESULT_GALLERY );


//            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
//            startActivityForResult(intent, TAKE_PICTURE);
//        } catch (IOException e) {
//            //ask for permission
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    WRITE_EXTERNAL_STORAGE);
//
//            e.printStackTrace();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_GALLERY:
                if (resultCode == Activity.RESULT_OK) {


                    Uri selectedImage = imageUri;
//                    getContentResolver().notifyChange(selectedImage, null);
                    imageUri = data.getData();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, bitmap.getScaledWidth(IMAGE_SCALE), bitmap.getScaledHeight(IMAGE_SCALE), false);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                        bitmapString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                        isPicTaken = true;
                        bitmap.recycle();
                        bitmap = null;
                        bitmap2.recycle();
                        bitmap2 = null;
                        baos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                }
        }
    }

}
