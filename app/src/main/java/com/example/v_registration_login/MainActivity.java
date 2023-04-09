package com.example.v_registration_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.v_registration_login.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
     FirebaseAuth auth;
     Button logoutBtn;
     TextView textView;
     FirebaseUser user;

     BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bottomNavigationView = binding.bottonnav;

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        replaceFrament(new HomeFragment());

       }

       @Override
       public boolean onNavigationItemSelected(@NonNull MenuItem item){
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home:
                Log.d("okay","Home fragment");
                fragment = new HomeFragment();
                break;
            case R.id.dashboard:
                fragment = new DashboardFragment();
                break;
            case R.id.account:
                fragment = new AccountFragment();
                break;
            }

            if (fragment != null){
                replaceFrament(fragment);
            }

            return true;
        }


       private void replaceFrament(Fragment fragment){
           Log.d("okay","Okay here");
           getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout, fragment).commit();
       }
}