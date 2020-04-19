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
import com.example.foodorder.activity.CartActivity;
import com.example.foodorder.data.FoodTypeData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ParticularFoodAdapter extends RecyclerView.Adapter<ParticularFoodAdapter.ViewHolder> {

    private ArrayList<FoodTypeData> foodList;
    private Context context;

    // data is passed into the constructor
    public ParticularFoodAdapter(Context context, ArrayList<FoodTypeData> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_variety_of_food, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final FoodTypeData foodData = foodList.get(position);

        holder.name.setText(foodData.getFoodName());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CartActivity.class);
                intent.putExtra("foodName", foodData.getFoodName());
                intent.putExtra("foodImage", foodData.getFoodImage());
                intent.putExtra("foodPrice", foodData.getFoodPrice());
                intent.putExtra("foodRecipe", foodData.getFoodRecipe());
                context.startActivity(intent);
            }
        });

        Picasso.get().load(foodData.getFoodImage()).into(holder.foodImage);

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
        private ImageView foodImage;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_food_name);
            container = itemView.findViewById(R.id.cv_paricularfood);
            foodImage = itemView.findViewById(R.id.iv_food_image);
        }
    }
}