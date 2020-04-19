package com.example.foodorder.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.R;
import com.example.foodorder.activity.ShopListActivity;
import com.example.foodorder.adapters.AllShopAdapter;
import com.example.foodorder.adapters.BestDealAdapter;
import com.example.foodorder.adapters.HotDealAdapter;
import com.example.foodorder.data.FoodData;
import com.example.foodorder.firebaseRepo.FireBaseRepo;
import com.example.foodorder.firebaseRepo.ServerResponse;
import com.example.foodorder.utils.Constants;

import java.util.ArrayList;

import static com.example.foodorder.utils.Toasts.show;


public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;

    private AllShopAdapter allShopAdapter;
    private BestDealAdapter bestDealAdapter;
    private HotDealAdapter hotDealAdapter;
    private ArrayList<FoodData> foodList = new ArrayList<>();
    private ArrayList<FoodData> newFoodList = new ArrayList<>();
    private CardView cvAllRestaurants, cvHotDeals, cvMostPopularDeals;
    private ProgressBar progressBar;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        cvAllRestaurants = view.findViewById(R.id.cv_all_restaurants);
        cvHotDeals = view.findViewById(R.id.cv_hot_deals);
        cvMostPopularDeals = view.findViewById(R.id.cv_most_popular_deals);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        initializeAllShopAdapter();
        clickListeners();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        cvMostPopularDeals.setEnabled(false);
        cvHotDeals.setEnabled(false);
        cvAllRestaurants.setEnabled(false);

        FireBaseRepo.I.fetchAllFoodData(new ServerResponse<String>() {
            @Override
            public void onSuccess(String body) {
                progressBar.setVisibility(View.GONE);
                allShopAdapter.notifyDataSetChanged();
                cvMostPopularDeals.setEnabled(true);
                cvHotDeals.setEnabled(true);
                cvAllRestaurants.setEnabled(true);
            }

            @Override
            public void onFailure(Throwable error) {

            }
        });
    }

    private void clickListeners() {
        cvAllRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShopListActivity.class);
                intent.putExtra("mode", Constants.ALL_RESTAURANTS);
                startActivity(intent);
            }
        });

        cvHotDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShopListActivity.class);
                intent.putExtra("mode", Constants.HOT_DEAL);
                startActivity(intent);
            }
        });

        cvMostPopularDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShopListActivity.class);
                intent.putExtra("mode", Constants.BEST_DEAL);
                startActivity(intent);
            }
        });
    }

    private void fetchHotDeal() {
        FireBaseRepo.I.fetchHotDeals(new ServerResponse<ArrayList<FoodData>>() {
            @Override
            public void onSuccess(ArrayList<FoodData> body) {
                if (body.size() == 0) {
//                    tvExploreHotDeal.setVisibility(View.GONE);
                }
                newFoodList.clear();
                newFoodList.addAll(body);
                hotDealAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                show.longMsg(getActivity(), error.toString());
            }
        });
    }

    private void fetchBestDeal() {
        FireBaseRepo.I.fetchBestDeals(new ServerResponse<ArrayList<FoodData>>() {
            @Override
            public void onSuccess(ArrayList<FoodData> body) {
//                if (body.size() == 0) {
//                    tvExploreBestDeal.setVisibility(View.GONE);
//                }
                foodList.clear();
                foodList.addAll(body);
                bestDealAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                show.longMsg(getActivity(), error.toString());
            }
        });
    }

    private void initializeHotDealAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        hotDealAdapter = new HotDealAdapter(getActivity(), newFoodList);
        recyclerView.setAdapter(hotDealAdapter);
    }

    private void initializeBestDealAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
//        mRecyclerView.setLayoutManager(linearLayoutManager);
        bestDealAdapter = new BestDealAdapter(getActivity(), foodList);
//        mRecyclerView.setAdapter(bestDealAdapter);
    }

    private void initializeAllShopAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        allShopAdapter = new AllShopAdapter(getActivity());
        recyclerView.setAdapter(allShopAdapter);
    }


}


