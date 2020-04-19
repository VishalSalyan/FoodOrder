package com.example.foodorder.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodorder.R;
import com.example.foodorder.fragment.HomeFragment;
import com.example.foodorder.fragment.NearbyFragment;
import com.example.foodorder.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    HomeFragment homeFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        HomeFragment homeFragment = new HomeFragment();
        openFragment(homeFragment);
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.bottom_home:
                            homeFragment = new HomeFragment();
                            openFragment(homeFragment);
                            break;
                        case R.id.bottom_nearby:
                            NearbyFragment collectionFragment = new NearbyFragment();
                            openFragment(collectionFragment);
                            break;
//                        case R.id.bottom_wish_list:
//                            WishListFragment carWishFragment = new WishListFragment();
//                            openFragment(carWishFragment);
//                            break;
                        case R.id.bottom_user:
                            ProfileFragment searchFragment = new ProfileFragment();
                            openFragment(searchFragment);
                            break;
                    }
                    return true;
                }
            };

    public void openFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainLayout, fragment);
        fragmentTransaction.commit();
    }

}