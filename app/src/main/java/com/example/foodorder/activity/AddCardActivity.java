package com.example.foodorder.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorder.R;
import com.example.foodorder.firebaseRepo.FireBaseRepo;
import com.example.foodorder.firebaseRepo.ServerResponse;
import com.example.foodorder.utils.SessionData;

import java.util.Objects;

import static com.example.foodorder.utils.GoTo.go;
import static com.example.foodorder.utils.Toasts.show;

public class AddCardActivity extends AppCompatActivity {
    private TextView tvAmount, tvWalletBalance;
    private EditText et_cvv, et_expiry_date, et_card_number, et_card_holder_name, et_add_money;
    private Button addMoneyToWallet, addBalance, btnPay;
    private LinearLayout llCardLayout, ll_wallet_layout;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String totalAmount = bundle.getString("amount");
        et_cvv = findViewById(R.id.et_cvv);
        et_expiry_date = findViewById(R.id.et_expiry_date);
        et_card_number = findViewById(R.id.et_card_number);
        et_card_holder_name = findViewById(R.id.et_card_holder_name);
        tvAmount = findViewById(R.id.tv_amount);
        et_add_money = findViewById(R.id.et_add_money);
        addBalance = findViewById(R.id.add_balance);
        addMoneyToWallet = findViewById(R.id.btn_send_money_to_wallet);
        btnPay = findViewById(R.id.btn_pay);
        llCardLayout = findViewById(R.id.ll_card_layout);
        ll_wallet_layout = findViewById(R.id.ll_wallet_layout);
        tvWalletBalance = findViewById(R.id.tv_wallet_balance);

        tvAmount.setText(totalAmount);
        tvWalletBalance.setText("$" + SessionData.getInstance().getLocalData().getWalletBalance());

        clickListeners();
    }

    private void clickListeners() {
        addMoneyToWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidate()) {
                    return;
                }
                if (!et_add_money.getText().toString().trim().isEmpty()) {
                    FireBaseRepo.I.setMoneyToWallet(SessionData.getInstance().getLocalData().getEmail(),
                            SessionData.getInstance().getLocalData().getWalletBalance()
                                    + Integer.parseInt(et_add_money.getText().toString().trim()),
                            new ServerResponse<String>() {
                                @Override
                                public void onSuccess(String body) {
                                    tvWalletBalance.setText("$" + SessionData.getInstance().getLocalData().getWalletBalance());
                                    llCardLayout.setVisibility(View.GONE);
                                    ll_wallet_layout.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onFailure(Throwable error) {

                                }
                            });
                } else {
                    et_add_money.setError("Please fill this field");
                }
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int walletBalance = Integer.parseInt(tvWalletBalance.getText().toString().replace('$', ' ').trim());
                int amountToPay = Integer.parseInt(tvAmount.getText().toString().replace('$', ' ').trim());
                if (amountToPay > walletBalance) {
                    show.longMsg(AddCardActivity.this, "Insufficient Money, please add more money to your wallet");
                } else {
                    int amountValue = walletBalance - amountToPay;
                    FireBaseRepo.I.setMoneyToWallet(SessionData.getInstance().getLocalData().getEmail(),
                            amountValue,
                            new ServerResponse<String>() {
                        @Override
                        public void onSuccess(String body) {
                            go.to(AddCardActivity.this, OrderConfirmedActivity.class);
                        }

                        @Override
                        public void onFailure(Throwable error) {

                        }
                    });
                }
            }
        });

        addBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llCardLayout.setVisibility(View.VISIBLE);
                ll_wallet_layout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
