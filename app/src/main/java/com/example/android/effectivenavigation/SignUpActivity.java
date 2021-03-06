package com.example.android.effectivenavigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.effectivenavigation.matching.SurveyActivity;
import com.example.android.effectivenavigation.summary.WallEntryItem;
import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends Activity implements LoaderCallbacks<Cursor> {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    private static final int RESULT_GALLERY = 5;
    private static Uri imageUri;
    private static final int IMAGE_SCALE = 240;
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private String mImageFileLocation = "";
    private String GALLERY_LOCATION = "";
    private File mGalleryFolder;
    boolean isPicTaken = false;
    String bitmapString;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText rPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ImageButton openGallery;
    private ImageButton openCamera;
    private ImageView profileImage;
    private String name;
    private String pwd;
    private String pwdR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        createImageGallery();
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0089D0")));
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.sign_up);
        populateAutoComplete();
        mPasswordView = (EditText) findViewById(R.id.password);
        rPasswordView = (EditText) findViewById(R.id.passwordR);
        openGallery = (ImageButton) findViewById(R.id.gallery_button);
        openCamera = (ImageButton) findViewById(R.id.camera_button);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        bitmapString = null;

        openGallery.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

//takePhoto(v);
                OpenGallery(v);
            }
        });
        openCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("camera","camera");
                takePhoto(v);
            }
        });
//        openCamera.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                Toast.makeText(SignUpActivity.this,"camera",Toast.LENGTH_LONG).show();
////takePhoto(v);
//                OpenGallery(v);
//            }
//        });

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.intake_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();





            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void OpenGallery(View v) {
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent , RESULT_GALLERY );
    }

    public void takePhoto(View view) {
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

        startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);
    }
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
            case ACTIVITY_START_CAMERA_APP:
                if (resultCode == Activity.RESULT_OK) {
                    setReducedImageSize();
                }
                // Toast.makeText(this, "Picture taken successfully", Toast.LENGTH_SHORT).show();
                // Bundle extras = data.getExtras();
                // Bitmap photoCapturedBitmap = (Bitmap) extras.get("data");
                // mPhotoCapturedImageView.setImageBitmap(photoCapturedBitmap);
                // Bitmap photoCapturedBitmap = BitmapFactory.decodeFile(mImageFileLocation);
                // mPhotoCapturedImageView.setImageBitmap(photoCapturedBitmap);




        }
    }
    private void createImageGallery() {
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        mGalleryFolder = new File(storageDirectory, GALLERY_LOCATION);
        if(!mGalleryFolder.exists()) {
            mGalleryFolder.mkdir();
        }
    }

    File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";


        File image = File.createTempFile(imageFileName,".jpg", mGalleryFolder);
        mImageFileLocation = image.getAbsolutePath();

        return image;

    }

    void setReducedImageSize() {
        int targetImageViewWidth = profileImage.getWidth();
        int targetImageViewHeight = profileImage.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImageFileLocation, bmOptions);
        int cameraImageWidth = bmOptions.outWidth;
        int cameraImageHeight = bmOptions.outHeight;

        int scaleFactor = Math.min(cameraImageWidth/targetImageViewWidth, cameraImageHeight/targetImageViewHeight);
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inJustDecodeBounds = false;

        Bitmap photoReducedSizeBitmp = BitmapFactory.decodeFile(mImageFileLocation, bmOptions);
        profileImage.setImageBitmap(photoReducedSizeBitmp);


    }



    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {


        name = mEmailView.getEditableText().toString();


        pwd = mPasswordView.getEditableText().toString();
        pwdR = rPasswordView.getEditableText().toString();


        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        rPasswordView.setError(null);
        // Store values at the time of the login attempt.

        String nS = name;
        FBHandler.TestDuplicate(nS);
        boolean cancel = false;
        boolean photo = false;
        View focusView = null;
        Log.v("DEB",name+"*****");
        // name cannot contain capital , cannot empty
        if (name.matches(".*[A-Z].*")||name.isEmpty()) {
            mEmailView.setError(getString(R.string.error_invalid_name));
            focusView = mEmailView;
            cancel = true;
        }
        else if (TextUtils.isEmpty(pwd) || !isPasswordValid(pwd)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        else if (!pwd.equals(pwdR)) {
            rPasswordView.setError(getString(R.string.error_matched_password));
            focusView = rPasswordView;
            cancel = true;
        }
//        else if (nS.equals(null)) {
//            mEmailView.setError("Sorry, This name exist");
//            focusView = mEmailView;
//            cancel = true;
//        }
        else if (bitmapString ==null) {
            photo = true;
                }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else if (photo) {
            Toast.makeText(this,"Please select a profile image.", Toast.LENGTH_SHORT).show();
        }else
        {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, pwd);
//            mAuthTask.execute((Void) null);
            Firebase postRef = new Firebase("https://habitbuddy-9bca7.firebaseio.com/users");
            //TODO push item to firebase, upload image with url returned
            if(bitmapString == null){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_diary,options);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 1000, baos);
                bitmapString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            }
            postRef.child(name).child("profileImage").setValue(bitmapString);
            bitmapString = null;
            finish();




            FBHandler.SignUp(name,pwd);
            Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putString("pos",name);

            intent.putExtras(mBundle);
            startActivity(intent);
            finish();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(SignUpActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

