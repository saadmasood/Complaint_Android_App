package pk.com.ke.complaint;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.textfield.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pk.com.ke.complaint.model.LocationCoordinates;
import pk.com.ke.complaint.rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pk.com.ke.com.processbutton.iml.ActionProcessButton;
import pk.com.ke.complaint.model.login.Login;
import pk.com.ke.complaint.rest.ApiClient;
import pk.com.ke.complaint.utils.Encryptor;
import pk.com.ke.complaint.utils.MyActivity;
import pk.com.ke.complaint.utils.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends MyActivity implements View.OnClickListener {


    @BindView(R.id.userid)
    EditText userId;

    @BindView(R.id.pwd)
    EditText password;

    @BindView(R.id.textInputEmail)
    TextInputLayout textInputEmail;

    @BindView(R.id.textInputPassword)
    TextInputLayout textInputPassword;

    @BindView(R.id.keLoginBtn)
    ActionProcessButton login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.keLoginBtn)
    public void onClick(View v) {

        final String strUserId = userId.getText().toString(),
                strPwd = password.getText().toString();


        textInputEmail.setError(null);
        textInputPassword.setError(null);

        if (TextUtils.isEmpty(strUserId) || TextUtils.isEmpty(strPwd)) {

            if (TextUtils.isEmpty(strUserId)) {
                textInputEmail.setError("UserID can not be null");
            }

            if (TextUtils.isEmpty(strPwd)) {
                textInputPassword.setError("Password can not be null");
            }

            return;
        }


        if (strUserId.length() < 3) {
            textInputEmail.setError("Invalid UserId");
            return;
        }

        if (strPwd.length() < 3) {
            textInputPassword.setError("Invalid Password");
            return;
        }

//        Retrofit retrofit = ApiClient.getClient_JSON();
        Retrofit retrofit = ApiClient.getInstance();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

//        showDialog("Working...");

        userId.setEnabled(false);
        password.setEnabled(false);

        login.setProgress(1);

        //Call<Login> call = apiInterface.login("'AUTH'", "'" + strUserId + "'", "'" + strPwd + "'", "json");
        Call<Login> call = apiInterface.login("'AUTH'", "'" + strUserId + "'", "''", "json");

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                log("LoginAct./Login/URL/onSuccess", call.request().url().toString());

                dismissDialog();

                Login l = response.body();

                if (l != null) {

                    if (l.getD() != null) {
                        String val = l.getD().getValid();
                        if (val != null && val.startsWith("X")) {

                            userId.setEnabled(true);
                            password.setEnabled(true);

                            login.setProgress(100);

                            Toast.makeText(LoginActivity.this, "Logged In!", Toast.LENGTH_SHORT).show();

                            String name = l.getD().getUserName();

                            writeBoolean(StringUtils.PREF_IS_LOGIN, true);
                            writeString(StringUtils.PREF_USER_USER_ID, strUserId);
                            writeString(StringUtils.PREF_USER_USER_NAME_AD, name);

                            //start location tracking service
                            Intent service= new Intent(getApplicationContext(), LocationService.class);
                            service.putExtra("user",strUserId);
//                            getApplicationContext().startService(service);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                getApplicationContext().startForegroundService(service);
                            } else {
                                getApplicationContext().startService(service);
                            }


                            Bundle b = new Bundle();
                            b.putString("uid", strUserId);

                            writeString(StringUtils.PREF_USER_PWD, Encryptor.encrypt(password.getText().toString(), Encryptor.KEY_PC, Encryptor.IV_PC));
                            logUser();

                            startMyActivity(MainActivity.class, b, true);

                        } else {
                            Toast.makeText(LoginActivity.this, "UserID or Password is wrong", Toast.LENGTH_SHORT).show();

                            login.setProgress(-1);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    userId.setEnabled(true);
                                    password.setEnabled(true);

                                    login.setProgress(0);
                                }
                            }, 500);
                        }
                    } else {

                        login.setProgress(-1);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                userId.setEnabled(true);
                                password.setEnabled(true);

                                login.setProgress(0);
                            }
                        }, 500);


                        String error = getErrorBody(response);
                        log("Login Error", error);

                        if (error.length() > 200) {
                            error = error.substring(0, 200);
                        }

                        Toast.makeText(LoginActivity.this, "error:" + error, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Error


                    login.setProgress(-1);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            userId.setEnabled(true);
                            password.setEnabled(true);

                            login.setProgress(0);
                        }
                    }, 500);


                    String error = getErrorBody(response);
                    log("Login Error", error);

                    if (error.length() > 200) {
                        error = error.substring(0, 200);
                    }

                    Toast.makeText(LoginActivity.this, "error:" + error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                log("LoginAct./Login/URL/onFailure", call.request().url().toString());

                dismissDialog();

                login.setProgress(-1);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        userId.setEnabled(true);
                        password.setEnabled(true);

                        login.setProgress(0);
                    }
                }, 500);

                String error = stackTraceToString(t);

                log("LoginActivity/OnFailure", error);

                if (error.length() > 200) {
                    error = error.substring(0, 200);
                }
                toast("Error: " + t.getMessage() + "\n" + error);

            }
        });
    }

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        String strUserId = getString(StringUtils.PREF_USER_USER_ID);
        Crashlytics.setUserIdentifier(strUserId);
        Crashlytics.setUserEmail(getString(StringUtils.PREF_USER_PWD));
        Crashlytics.setUserName("User:" + strUserId);
    }

}

