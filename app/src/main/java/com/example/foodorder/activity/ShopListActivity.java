package com.example.foodorder.activity;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.R;
import com.example.foodorder.adapters.ShopListAdapter;
import com.example.foodorder.data.FoodData;
import com.example.foodorder.utils.Constants;
import com.example.foodorder.utils.SessionData;

import java.util.ArrayList;
import java.util.Objects;

public class ShopListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ShopListAdapter shopListAdapter;
    private ArrayList<FoodData> shopList = new ArrayList<>();
    private String mode;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        mode = bundle.getString("mode");
        initializeAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (mode) {
            case Constants.BEST_DEAL:
                filterList(Constants.BEST_DEAL);
                break;
            case Constants.HOT_DEAL:
                filterList(Constants.HOT_DEAL);
                break;
            case Constants.ALL_RESTAURANTS:
                shopList.addAll(SessionData.getInstance().totalFoodList);
                break;
        }
        for (int i = 0; i < SessionData.getInstance().totalFoodList.size(); i++) {

        }
//        FireBaseRepo.I.fetchAllFoodData(new ServerResponse<String>() {
//            @Override
//            public void onSuccess(String body) {
//                shopListAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Throwable error) {
//
//            }
//        });
    }

    private void filterList(String mode) {
        for (int i = 0; i < SessionData.getInstance().totalFoodList.size(); i++) {
            if (SessionData.getInstance().totalFoodList.get(i).getMode().equals(mode)) {
                shopList.add(SessionData.getInstance().totalFoodList.get(i));
            }
        }
    }

    private void initializeAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShopListActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        shopListAdapter = new ShopListAdapter(ShopListActivity.this, shopList);
        recyclerView.setAdapter(shopListAdapter);
    }
}
