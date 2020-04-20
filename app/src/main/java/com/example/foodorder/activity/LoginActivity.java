package com.example.foodorder.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorder.R;
import com.example.foodorder.data.UserData;
import com.example.foodorder.firebaseRepo.FireBaseRepo;
import com.example.foodorder.firebaseRepo.ServerResponse;
import com.example.foodorder.utils.SessionData;

import java.util.Objects;

import static com.example.foodorder.utils.GoTo.go;
import static com.example.foodorder.utils.Toasts.show;


public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnSignUp;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SessionData.getInstance().initSharedPref(LoginActivity.this);

        if (SessionData.getInstance().isLogin()) {
            go.to(LoginActivity.this, HomeActivity.class);
            finish();
        }

        initViews();
        login();
    }

    private void login() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidate()) {
                    return;
                }

                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                FireBaseRepo.I.login(email, password, new ServerResponse<UserData>() {
                    @Override
                    public void onSuccess(UserData body) {
                        if (body != null) {
                            SessionData.getInstance().saveLocalData(body);
                            SessionData.getInstance().saveLogin(true);
                            go.to(LoginActivity.this, HomeActivity.class);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        show.longMsg(LoginActivity.this, error.getMessage());
                    }
                });
            }
        });
    }

    private boolean isValidate() {
        boolean isValid = true;
        if (etEmail.getText().toString().trim().isEmpty()) {
            isValid = false;
            etEmail.setError("Please fill this field");
        }
        if (etPassword.getText().toString().trim().isEmpty()) {
            isValid = false;
            etPassword.setError("Please fill this field");
        }

        return isValid;
    }

    // Ids of Btn and EditText
    private void initViews() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_sign_up);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go.to(LoginActivity.this, SignUpActivity.class);
            }
        });

    }
}
