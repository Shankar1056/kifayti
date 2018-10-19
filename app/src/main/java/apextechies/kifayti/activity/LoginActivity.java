package apextechies.kifayti.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.firebase.digitsmigrationhelpers.AuthMigrator;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;

import apextechies.kifayti.common.ClsGeneral;
import apextechies.kifayti.common.ConstantValue;
import apextechies.kifayti.R;
import apextechies.kifayti.common.Utilz;
import apextechies.kifayti.retrofitnetwork.RetrofitDataProvider;

/**
 * Created by Shankar on 4/1/2018.
 */

public class LoginActivity extends AppCompatActivity {
    public static final int RC_SIGN_IN = 111;

    private RetrofitDataProvider retrofitDataProvider;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        retrofitDataProvider = new RetrofitDataProvider(this);
        initWidgit();

    }

    private void initWidgit() {
        checkSession();
    }

    private void checkSession() {
        AuthMigrator.getInstance().migrate(true).addOnSuccessListener(this,
                new OnSuccessListener() {

                    @SuppressLint("WrongConstant")
                    @Override
                    public void onSuccess(Object o) {
                        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                        if (u != null) {
                            // StaticConfig.UID = u.getUid();
                            callApi(u.getPhoneNumber());

                        } else {
                            startActivityForResult(
                                    AuthUI.getInstance()
                                            .createSignInIntentBuilder()
                                            .setProviders(
                                                    //  Arrays.asList(
                                                    Collections.singletonList(
                                                            new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()
                                                    )
                                            )
                                            .setTheme(R.style.LoginTheme)
                                            .setLogo(R.mipmap.logo)
                                            .build(),
                                    RC_SIGN_IN);
                        }
                    }
                }).addOnFailureListener(this,
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private void callApi(String phoneNumber) {
        String mobile = phoneNumber.replace("+91", "");
        ClsGeneral.setPreferences(LoginActivity.this, ConstantValue.MOBILE, mobile);
        if (Utilz.isInternetConnected(LoginActivity.this)) {
            Utilz.showProgress(LoginActivity.this);
            ClsGeneral.setPreferences(this,ConstantValue.USERID, mobile);
            ClsGeneral.setPreferences(this,ConstantValue.MOBILE, mobile);
            if (ClsGeneral.getPreferences(this, ConstantValue.EMAIL).endsWith("")){
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }else {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

        } else {
            Utilz.displayMessageAlert(getResources().getString(R.string.nointernetconnection), LoginActivity.this);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == ResultCodes.OK) {
                FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                //StaticConfig.UID = u.getUid();
                callApi(response.getPhoneNumber());

            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Log.e("Login", "Login canceled by User");
                }
                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                }
                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Log.e("Login", "Unknown Error");
                }
            }
            Log.e("Login", "Unknown sign in response");

        }
    }

}
