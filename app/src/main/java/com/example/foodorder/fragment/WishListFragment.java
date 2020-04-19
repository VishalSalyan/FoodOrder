package com.example.foodorder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.R;
import com.example.foodorder.adapters.WishListFoodAdapter;
import com.example.foodorder.data.FoodData;
import com.example.foodorder.data.WishListData;
import com.example.foodorder.firebaseRepo.FireBaseRepo;
import com.example.foodorder.firebaseRepo.ServerResponse;
import com.example.foodorder.utils.SessionData;

import java.util.ArrayList;

import static com.example.foodorder.utils.Toasts.show;


public class WishListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private WishListFoodAdapter mWishListAdapter;
    private ArrayList<FoodData> foodList = new ArrayList<>();

    public WishListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_food_wishlist, container, false);
        mRecyclerView = view.findViewById(R.id.wishlist_food_recyclerView);
        initializeCollectionAdapter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        FireBaseRepo.I.wishListFood(SessionData.getInstance().getLocalData().getEmail(), new ServerResponse<ArrayList<WishListData>>() {
            @Override
            public void onSuccess(ArrayList<WishListData> body) {
                foodList.clear();
                for (WishListData item : body) {
                    for (int i = 0; i < SessionData.getInstance().totalFoodList.size(); i++) {
                        if (item.getCarId().equals(SessionData.getInstance().totalFoodList.get(i).getId())) {
                            foodList.add(SessionData.getInstance().totalFoodList.get(i));
                            break;
                        }
                    }
                }
                mWishListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                show.longMsg(getActivity(), error.getMessage());
            }
        });
    }

    private void initializeCollectionAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mWishListAdapter = new WishListFoodAdapter(getActivity(), foodList);
        mRecyclerView.setAdapter(mWishListAdapter);
    }

}

