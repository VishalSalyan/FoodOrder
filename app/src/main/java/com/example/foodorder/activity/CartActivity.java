package com.example.foodorder.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorder.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class CartActivity extends AppCompatActivity {
    private TextView tvTotal, tvShipping, tvSubtotal, tvFoodName;
    private ImageView ivFoodImage;

    private Button btn_minus, btn_plus, btn_pay;
    private EditText et_quantity;

    private String foodName;
    private String foodImage;
    private String foodPrice;
    private String foodRecipe;
    private int quantity = 1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        initViewsWithData();
        clickListeners();
    }

    private void clickListeners() {
        et_quantity.setEnabled(false);
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(et_quantity.getText().toString().trim()) > 1) {
                    quantity -= 1;
                    et_quantity.setText(String.valueOf(quantity));

                    tvTotal.setText("$" + strToIntTotal(foodPrice, quantity));
                    tvSubtotal.setText("$" + strToIntFoodPrice(foodPrice, quantity));
                }
            }
        });
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(et_quantity.getText().toString().trim()) < 20) {
                    quantity += 1;
                    et_quantity.setText(String.valueOf(quantity));
                    tvTotal.setText("$" + strToIntTotal(foodPrice, quantity));
                    tvSubtotal.setText("$" + strToIntFoodPrice(foodPrice, quantity));
                }
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, AddCardActivity.class);
                intent.putExtra("amount", tvTotal.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initViewsWithData() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        foodName = bundle.getString("foodName");
        foodImage = bundle.getString("foodImage");
        foodPrice = bundle.getString("foodPrice");
        foodRecipe = bundle.getString("foodRecipe");

        tvSubtotal.setText("$" + strToIntFoodPrice(foodPrice, quantity));
        tvShipping.setText("$10");
        tvTotal.setText("$" + strToIntTotal(foodPrice, quantity));
        tvFoodName.setText(foodName);
        Picasso.get().load(foodImage).into(ivFoodImage);
    }

    private int strToIntTotal(String foodPrice, int quantity) {
        String withoutDollarSign = foodPrice.replace(foodPrice.charAt(0), ' ').trim();
        return (Integer.parseInt(withoutDollarSign) * quantity) + 10;
    }

    private int strToIntFoodPrice(String foodPrice, int quantity) {
        String withoutDollarSign = foodPrice.replace(foodPrice.charAt(0), ' ').trim();
        return Integer.parseInt(withoutDollarSign) * quantity;
    }

    private void initViews() {
        tvTotal = findViewById(R.id.tv_total);
        tvShipping = findViewById(R.id.tv_shipping);
        tvSubtotal = findViewById(R.id.tv_subtotal);
        ivFoodImage = findViewById(R.id.iv_food_image);
        tvFoodName = findViewById(R.id.tv_food_name);

        btn_minus = findViewById(R.id.btn_minus);
        et_quantity = findViewById(R.id.et_quantity);
        btn_plus = findViewById(R.id.btn_plus);
        btn_pay = findViewById(R.id.btn_pay);
    }


}
