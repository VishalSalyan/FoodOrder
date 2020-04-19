package com.example.foodorder.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorder.R;

import java.util.Objects;

import static com.example.foodorder.utils.GoTo.go;

public class AddCardActivity extends AppCompatActivity {
    private TextView tv_amount;
    private EditText et_cvv, et_expiry_date, et_card_number, et_card_holder_name;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String totalAmount = bundle.getString("amount");
        et_cvv = findViewById(R.id.et_cvv);
        et_expiry_date = findViewById(R.id.et_expiry_date);
        et_card_number = findViewById(R.id.et_card_number);
        et_card_holder_name = findViewById(R.id.et_card_holder_name);
        tv_amount = findViewById(R.id.tv_amount);
        Button btnPay = findViewById(R.id.btn_pay);


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidate()) {
                    return;
                }
                go.to(AddCardActivity.this, OrderConfirmedActivity.class);
            }
        });
        tv_amount.setText(totalAmount);
    }

    private boolean isValidate() {
        boolean isValid = true;

        if (et_cvv.getText().toString().trim().isEmpty()) {
            isValid = false;
            et_cvv.setError("Please fill this field");
        }
        if (et_expiry_date.getText().toString().trim().isEmpty()) {
            isValid = false;
            et_expiry_date.setError("Please fill this field");
        }
        if (et_card_number.getText().toString().trim().isEmpty()) {
            isValid = false;
            et_card_number.setError("Please fill this field");
        }
        if (et_card_holder_name.getText().toString().trim().isEmpty()) {
            isValid = false;
            et_card_holder_name.setError("Please fill this field");
        }

        return isValid;
    }
}
