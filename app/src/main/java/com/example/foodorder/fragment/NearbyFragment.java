package com.example.foodorder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.R;
import com.example.foodorder.adapters.NearbyAdapter;
import com.example.foodorder.data.FoodData;
import com.example.foodorder.firebaseRepo.FireBaseRepo;
import com.example.foodorder.firebaseRepo.ServerResponse;

import java.util.ArrayList;

import static com.example.foodorder.utils.Toasts.show;


public class NearbyFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private NearbyAdapter nearbyAdapter;
    private ArrayList<FoodData> nearByfoodList = new ArrayList<>();

    public NearbyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_nearby, container, false);
        mRecyclerView = view.findViewById(R.id.nearby_recyclerView);

        initializeCollectionAdapter();

        fetchNearBy();
        return view;
    }
    private void fetchNearBy() {
        FireBaseRepo.I.fetchNearby(new ServerResponse<ArrayList<FoodData>>() {
            @Override
            public void onSuccess(ArrayList<FoodData> body) {

                nearByfoodList.clear();
                nearByfoodList.addAll(body);
                nearbyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                show.longMsg(getActivity(), error.toString());
            }
        });
    }


    private void initializeCollectionAdapter() {
        // set up the RecyclerView in horizontal and vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // Initialize the adapter and attach it to the RecyclerView
        nearbyAdapter = new NearbyAdapter(getActivity(), nearByfoodList);
        mRecyclerView.setAdapter(nearbyAdapter);
    }


}

