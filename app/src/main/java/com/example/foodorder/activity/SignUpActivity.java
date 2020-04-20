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

import java.util.Objects;

import static com.example.foodorder.utils.GoTo.go;
import static com.example.foodorder.utils.Toasts.show;

public class SignUpActivity extends AppCompatActivity {

    private EditText etEmail, etUserName, etPhoneNumber, etPassword, etConfirmPassword;
    private Button btnLogin, btnSignUp;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        initViews();
        clickEvents();
    }

    private void clickEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidate()) {
                    return;
                }
                String email = etEmail.getText().toString().trim();
                String userName = etUserName.getText().toString().trim();
                String phoneNumber = etPhoneNumber.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                UserData userData = new UserData();
                userData.setEmail(email);
                userData.setName(userName);
                userData.setPhoneNumber(phoneNumber);
                userData.setPassword(password);
                userData.setWalletBalance(1000);

                if (password.equals(confirmPassword)) {
                    FireBaseRepo.I.signUp(userData, new ServerResponse<Boolean>() {
                        @Override
                        public void onSuccess(Boolean body) {
                            show.longMsg(SignUpActivity.this, "Account Created Successfully");
                            go.to(SignUpActivity.this, LoginActivity.class);
                            finish();
                        }

                        @Override
                        public void onFailure(Throwable error) {
                            show.longMsg(SignUpActivity.this, error.getMessage());
                        }
                    });
                }
            }
        });
    }

    // Id of Btn and EditText
    private void initViews() {
        etEmail = findViewById(R.id.et_email);
        etUserName = findViewById(R.id.et_username);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_Confirmpassword);

        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_signup);
    }

    private boolean isValidate() {
        boolean isValid = true;
        if (etEmail.getText().toString().trim().isEmpty()) {
            isValid = false;
            etEmail.setError("Please fill this field");
        }
        if (etUserName.getText().toString().trim().isEmpty()) {
            isValid = false;
            etUserName.setError("Please fill this field");
        }
        if (etPhoneNumber.getText().toString().trim().isEmpty()) {
            isValid = false;
            etUserName.setError("Please fill this field");
        }
        if (etPassword.getText().toString().trim().isEmpty()) {
            isValid = false;
            etPassword.setError("Please fill this field");
        }
        if (etConfirmPassword.getText().toString().trim().isEmpty()) {
            isValid = false;
            etConfirmPassword.setError("Please fill this field");
        }
        return isValid;
    }
}
