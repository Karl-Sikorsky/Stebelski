package com.example.stebelski;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity implements CallBackListener {
    final String TAG = "facebooktag";
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    QuestionFragment questionFragment;
    FragmentTransaction fTrans;
    TextView preview;
    ProgressBar progress;
    Boolean needPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);





        Log.d(TAG, "facebook");
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut();
        questionFragment = new QuestionFragment();
        progress = (ProgressBar)findViewById(R.id.contentLoadingProgressBar);
        progress.setVisibility(View.GONE);

        needPreview = true;

        preview = (TextView)findViewById(R.id.preview);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Arimo-Regular.ttf");

        preview.setTypeface(custom_font);
        preview.setText("Войдите в систему, чтобы начать");
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile", "user_friends");

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                showProgressBar();
                handleFacebookAccessToken(loginResult.getAccessToken());
                getFriends();
               // FirebaseUser user = mAuth.getCurrentUser();
               // Toast.makeText(getApplicationContext(),"you are "+ user.getDisplayName().trim(),Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }
        });
        // [END initialize_fblogin]
    }

    private void showProgressBar() {
        progress.setVisibility(View.VISIBLE);
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
    // [END on_start_check_user]

    // [START on_activity_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    // [END on_activity_result]

    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]

        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),"you are "+ user.getDisplayName().trim(),Toast.LENGTH_SHORT).show();
                            preview.setText("");
                            needPreview = false;
                            fTrans = getFragmentManager().beginTransaction();
                            fTrans.add(R.id.question_frg, questionFragment);
                            fTrans.commit();
                            hideProgressBar();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
    }

    private void hideProgressBar() {
progress.setVisibility(View.GONE);

    }
    private void hidePreview(){
        if(!needPreview){
            preview.setVisibility(View.GONE);}
    }
    // [END auth_with_facebook]

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("needPreview",needPreview);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.needPreview = savedInstanceState.getBoolean("needPreview");
        hidePreview();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    void getFriends() {
    Log.d("responseFriends", "try to send req");
    new GraphRequest(
            AccessToken.getCurrentAccessToken(),
            "/me/friendlists",
            null,
            HttpMethod.GET,
            new GraphRequest.Callback() {

                public void onCompleted(GraphResponse response) {
                    Log.d("responseFriends", "have result");
            /* handle the result */
            Log.d("responseFriends", response.toString());
                }
            }
    ).executeAsync();
}


    @Override
    public void onCallBack(String res) {
        fTrans = getFragmentManager().beginTransaction();
        fTrans.remove(questionFragment);
        fTrans.commit();
        preview.setText(res);
    }
}