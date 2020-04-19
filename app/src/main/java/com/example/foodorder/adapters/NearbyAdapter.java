package com.example.foodorder.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.R;
import com.example.foodorder.activity.FoodDetailActivity;
import com.example.foodorder.data.FoodData;
import com.example.foodorder.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.ViewHolder> {

    private ArrayList<FoodData> foodList;
    private Context context;

    // data is passed into the constructor
    public NearbyAdapter(Context context, ArrayList<FoodData> foodList) {
        this.context = context;
        this.foodList = foodList;

    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_nearby, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       final FoodData foodData = foodList.get(position);

        holder.name.setText(foodData.getShopName());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FoodDetailActivity.class);
                intent.putExtra("foodId", foodData.getId());
                intent.putExtra("mode", Constants.NEARBY);
                context.startActivity(intent);
            }
        });

        Picasso.get().load(foodData.getShopImage()).into(holder.shopImage);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (foodList == null) {
            return 0;
        }
        return foodList.size();

    }

    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private CardView container;
        private ImageView shopImage;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_shop_name);
            container = itemView.findViewById(R.id.cv_nearby);
            shopImage = itemView.findViewById(R.id.iv_shop_image);
        }
    }

}