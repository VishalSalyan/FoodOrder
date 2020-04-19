package com.example.foodorder.activity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.R;
import com.example.foodorder.adapters.ParticularFoodAdapter;
import com.example.foodorder.data.FoodData;
import com.example.foodorder.data.FoodTypeData;
import com.example.foodorder.firebaseRepo.FireBaseRepo;
import com.example.foodorder.firebaseRepo.ServerResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.foodorder.utils.Toasts.show;

public class FoodDetailActivity extends AppCompatActivity {

    private TextView tvShopName, tvDeliveryTime, tvPromoCode, tvShopAddress, tvShopPhoneNumber,
            ivShopImage, tvFoodName, tvShopSpecification;
    private RecyclerView mRecyclerView;
    private ParticularFoodAdapter particularFoodAdapter;
    private ImageView shopImage;
    private Button btnAddToCart;
    private ArrayList<FoodData> foodList = new ArrayList<>();
    private ArrayList<FoodTypeData> foodTypeList = new ArrayList<>();

    private String id;
    private String mode;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        initializeNewCarsAdapter();

    }

    private void initView() {
        mRecyclerView = findViewById(R.id.food_detail_recyclerView);
        tvShopName = findViewById(R.id.tv_shop_name);
        tvPromoCode = findViewById(R.id.tv_promo_code);
        tvShopAddress = findViewById(R.id.tv_address);
        tvShopPhoneNumber = findViewById(R.id.tv_price);
        tvDeliveryTime = findViewById(R.id.tv_delivery_time);
        tvFoodName = findViewById(R.id.tv_foodName);
//        btnAddToCart = findViewById(R.id.add_to_bag_btn);
        tvShopSpecification = findViewById(R.id.tv_shop_specification);
        shopImage = findViewById(R.id.imageView);

    }

    private void setData(FoodData foodData) {
        Picasso.get().load(foodData.getShopImage()).into(shopImage);
        tvShopName.setText(foodData.getShopName());
        tvShopAddress.setText(" Address: " + foodData.getShopAddress());
        tvDeliveryTime.setText("Delivery Time: " + foodData.getDeliveryTime());
        tvShopSpecification.setText(foodData.getShopSpecification());
        tvPromoCode.setText(foodData.getPromoCode() + "  - Use code SAVE10  to avail this offer");
        foodTypeList.clear();
        foodTypeList.addAll(foodData.getTypesOfFood());
        particularFoodAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        id = bundle.getString("foodId");
        mode = bundle.getString("mode");
        fetchFoodDetails();
    }

    private void fetchFoodDetails() {
        FireBaseRepo.I.getFoodDetails(id, mode, new ServerResponse<FoodData>() {
            @Override
            public void onSuccess(FoodData body) {
                setData(body);
            }

            @Override
            public void onFailure(Throwable error) {
                show.longMsg(FoodDetailActivity.this, error.getMessage());
            }
        });
    }


    private void initializeNewCarsAdapter() {
        LinearLayoutManager nLayoutManager = new LinearLayoutManager(FoodDetailActivity.this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(nLayoutManager);
        particularFoodAdapter = new ParticularFoodAdapter(FoodDetailActivity.this, foodTypeList);
        mRecyclerView.setAdapter(particularFoodAdapter);

    }

}
