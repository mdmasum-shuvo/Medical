package com.app.shova.medical.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.shova.medical.R;
import com.app.shova.medical.appConstant.AppConstants;
import com.app.shova.medical.data.preference.AppPreference;
import com.app.shova.medical.data.preference.PrefKey;
import com.app.shova.medical.utility.ActivityUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class SignInActivity extends BaseActivity {

    private EditText eEmail, ePassword;
    private TextView tvSignupLink;
    private Button btnLogin;
    private MaterialBetterSpinner loginSpinner;
    private Activity mActivity;
    private Context mContext;
    private String userType=null;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initToolbar();
        setToolbarTitle("Sign In");
        initVariable();
        initListener();
        enableBackButton();

    }

    private void initView() {
        setContentView(R.layout.activity_sign_in);

        eEmail = findViewById(R.id.input_email);
        ePassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);
        tvSignupLink = findViewById(R.id.link_signup);
        loginSpinner = findViewById(R.id.spinner_login_person);

    }

    private void initVariable() {
        mActivity = SignInActivity.this;
        mContext=getApplicationContext();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(mActivity, MainActivity.class));
//            finish();
//        }
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, AppConstants.LOGIN_PERSON);
        loginSpinner.setAdapter(adapter);
    }

    private void initListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        tvSignupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userType=loginSpinner.getText().toString();

                if (userType.equalsIgnoreCase("doctor")){

                    ActivityUtils.getInstance().invokeActivity(mActivity, RegisterDoctorActivity.class, false);
                }
                else if (userType.equalsIgnoreCase("user")){
                    ActivityUtils.getInstance().invokeActivity(mActivity, RegisterUserActivity.class, false);
                }
                else {
                    Toast.makeText(getApplicationContext(), "please select user type", Toast.LENGTH_LONG).show();                }

            }
        });
    }

    public void login() {
        Log.d(AppConstants.TAG_LOGING_ACTIVITY, "Login");

        if (!validate()) {
           // onLoginFailed();
            return;
        }

        btnLogin.setEnabled(false);

        progressDialog = new ProgressDialog(mActivity,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = eEmail.getText().toString();
        String password = ePassword.getText().toString();

        // TODO: Implement your own authentication logic here.

        userType = loginSpinner.getText().toString();

        if (userType.equals("doctor")) {
          loginWithFireBaseAuth(email,password,progressDialog);

        } else if (userType.equals("user")) {
            // Start the Signup activity
            loginWithFireBaseAuth(email,password,progressDialog);

        } else {
            Toast.makeText(mContext,"select a user Type",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }


    }

    private void loginWithFireBaseAuth(String email,String password,ProgressDialog progressDialog){

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            // there was an error
                            onLoginFailed();

                        } else {
                            onLoginSuccess(progressDialog);

                        }
                    }
                });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.REQUEST_CODE_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }


    public void onLoginSuccess(ProgressDialog progressDialog) {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        //On complete call either onLoginSuccess or onLoginFailed
                        //onLoginSuccess();
                        //onLoginFailed();
                        progressDialog.dismiss();
                        // Start the Signup activity
                        AppPreference.getInstance(mContext).setBoolean(PrefKey.IS_LOGIN,true);
                        if (userType.equals(AppConstants.USER_TYPE_DOCTOR)){
                            AppPreference.getInstance(mContext).setString(PrefKey.PREF_USER_TYPE,AppConstants.USER_TYPE_PREF_VALUE_DOCTOR);
                            AppPreference.getInstance(mContext).setString(PrefKey.PREF_USER_EMAIL,eEmail.getText().toString());
                            Intent intent = new Intent(mActivity, MainActivity.class);
                            startActivityForResult(intent, AppConstants.REQUEST_CODE_SIGNUP);
                            finish();

                        }
                        else {
                            AppPreference.getInstance(mContext).setString(PrefKey.PREF_USER_TYPE,AppConstants.USER_TYPE_PREF_VALUE_USER);
                            AppPreference.getInstance(mContext).setString(PrefKey.PREF_USER_EMAIL,eEmail.getText().toString());
                            Intent intent = new Intent(mActivity, MainActivity.class);
                            startActivityForResult(intent, AppConstants.REQUEST_CODE_SIGNUP);
                            finish();
                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        }

                    }
                }, AppConstants.LOADING_TIME_DELAY);
        btnLogin.setEnabled(true);
    }

    public void onLoginFailed() {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        //On complete call either onLoginSuccess or onLoginFailed
                        //onLoginSuccess();
                        //onLoginFailed();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                        // Start the Signup activity

                    }
                }, AppConstants.LOADING_TIME_DELAY);
        btnLogin.setEnabled(true);

    }

    public boolean validate() {
        boolean valid = true;

        String email = eEmail.getText().toString();
        String password = ePassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            eEmail.setError("enter a valid email address");
            valid = false;
        } else {
            eEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            ePassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            ePassword.setError(null);
        }

        return valid;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
