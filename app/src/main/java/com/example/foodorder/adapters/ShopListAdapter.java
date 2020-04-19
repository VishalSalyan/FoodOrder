package com.example.foodorder.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.R;
import com.example.foodorder.activity.FoodDetailActivity;
import com.example.foodorder.data.FoodData;
import com.example.foodorder.utils.SessionData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {

    private final ArrayList<FoodData> shopList;
    private Context context;

    // data is passed into the constructor
    public ShopListAdapter(Context context, ArrayList<FoodData> shopList) {

        this.context = context;
        this.shopList = shopList;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_all_shop, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final FoodData foodData = shopList.get(position);

        holder.shopName.setText(foodData.getShopName());
        holder.rating.setText(foodData.getRating());
        holder.shopSpecification.setText(foodData.getShopSpecification());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FoodDetailActivity.class);
                intent.putExtra("foodId", foodData.getId());
                intent.putExtra("mode", foodData.getMode());
                context.startActivity(intent);
            }
        });

        Picasso.get().load(foodData.getShopImage()).into(holder.shopImage);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (shopList == null) {
            return 0;
        }
        return shopList.size();
    }


    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout container;
        private ImageView shopImage;

        private TextView rating, shopSpecification, shopName;

        ViewHolder(View itemView) {
            super(itemView);
            shopName = itemView.findViewById(R.id.tv_shop_name);
            shopImage = itemView.findViewById(R.id.iv_shop_image);
            rating = itemView.findViewById(R.id.tv_rating);
            shopSpecification = itemView.findViewById(R.id.tv_shop_specification);
            container = itemView.findViewById(R.id.row_container);

        }

    }


}