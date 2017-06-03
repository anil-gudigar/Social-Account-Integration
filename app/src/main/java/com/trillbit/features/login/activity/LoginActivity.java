package com.trillbit.features.login.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.trillbit.app.R;
import com.trillbit.app.TrillbitApplication;
import com.trillbit.app.constants.AppConstants;
import com.trillbit.features.login.presenter.LoginPresenterImpl;
import com.trillbit.features.login.presenter.LoginPresenterView;
import com.trillbit.model.audio.AudioRequest;
import com.trillbit.model.audio.AudioResponse;
import com.trillbit.model.login.LoginRequest;
import com.trillbit.model.login.LoginResponse;

import java.io.FileInputStream;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.trillbit.app.constants.AppConstants.APP_TAG;

/**
 * A login screen that offers login via google/facebook.
 */
public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, LoginPresenterView {

    private static final int RC_SIGN_IN = 9001;
    private static final int RC_AUDIO_RECORD = 9002;

    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;

    @BindView(R.id.status)
    TextView mStatusTextView;
    @BindView(R.id.detail)
    TextView mDetailView;
    ProgressDialog mProgressDialog;
    @BindView(R.id.fb_login_button)
    LoginButton loginButton;
    @BindView(R.id.google_sign_in_button)
    SignInButton signInButton;
    @BindView(R.id.sign_out_button)
    Button sign_out_button;
    @BindView(R.id.disconnect_button)
    Button disconnect_button;
    @BindView(R.id.record_audio)
    Button record_audio;

    @Inject
    LoginPresenterImpl loginPresenter;

    private LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        ((TrillbitApplication) getApplication()).getNetComponent().inject(this);
        loginPresenter.attachedView(this, true);

        signInButton.setOnClickListener(this);
        sign_out_button.setOnClickListener(this);
        disconnect_button.setOnClickListener(this);
        record_audio.setOnClickListener(this);

        //Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("173605262973-7pb3pd7iatckl7tcnhvdd7vpt7i49boe.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        //Facebook
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Profile fbProfile = Profile.getCurrentProfile();
                Log.i(APP_TAG, "Login Results :" + loginResult.getAccessToken() + " fbProfile :" + fbProfile.getFirstName());
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUser_id(loginResult.getAccessToken().getUserId());
                loginRequest.setAuth(loginResult.getAccessToken().getCurrentAccessToken().getToken());
                loginRequest.setLogin_type(AppConstants.API_CONSTANTS.LOGIN_TYPE.FACEBOOK);
                mStatusTextView.setText(getString(R.string.signed_in_fmt, fbProfile.getFirstName() + " " + fbProfile.getLastName() + "  \n Data:" + " " + loginRequest.toString()));
                loginPresenter.requestLogin(loginRequest);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    //write your code here what to do when user logout
                    updateUI(false);
                }
            }
        };
        accessTokenTracker.startTracking();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(APP_TAG, "requestCode :" + requestCode);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        if (requestCode == RC_AUDIO_RECORD) {
            CallSaveAudio(data);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void CallSaveAudio(Intent data) {
        String theFilePath = data.getData().toString();
        Log.i(APP_TAG, "theFilePath :" + theFilePath);
        try {
            AssetFileDescriptor audioAsset = getContentResolver().openAssetFileDescriptor(data.getData(), "r");
            FileInputStream fis = audioAsset.createInputStream();
            Log.i(APP_TAG, "FileInputStream :" + fis.available());
            byte[] buffer = new byte[(int) fis.getChannel().size()];
            fis.read(buffer);
            Log.i(APP_TAG, "bytes :" + buffer.length);
            AudioRequest audioRequest = new AudioRequest();
            audioRequest.setAudio_data(buffer);
            if (null != loginResponse) {
                loginPresenter.saveAudio(loginResponse.getPayload().getAccess_token(), audioRequest);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(APP_TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUser_id(acct.getId());
            loginRequest.setAuth((acct.getIdToken() == null) ? "null" : acct.getIdToken());
            loginRequest.setLogin_type(AppConstants.API_CONSTANTS.LOGIN_TYPE.GOOGLE);
            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName() + "  \n Data:" + " " + loginRequest.toString()));
            loginPresenter.requestLogin(loginRequest);
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(APP_TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
        }
        if (null != mProgressDialog) {
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }

    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.fb_login_button).setVisibility(View.GONE);
            findViewById(R.id.google_sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
            record_audio.setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailView.setText("");
            record_audio.setVisibility(View.GONE);
            findViewById(R.id.fb_login_button).setVisibility(View.VISIBLE);
            findViewById(R.id.google_sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
            case R.id.record_audio:
                Intent recordIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(recordIntent, RC_AUDIO_RECORD);
                break;
        }
    }

    @Override
    public void showProgress(String message) {
        showProgressDialog(message);
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void onLoginSuccess(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
        mDetailView.setText(loginResponse.toString());
        updateUI(true);
    }

    @Override
    public void onFailure(String message) {
        mDetailView.setText(message);
    }

    @Override
    public void onSaveAudioSuccess(AudioResponse audioResponse) {
        mDetailView.setText(audioResponse.toString());
    }
}