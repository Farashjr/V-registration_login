package com.example.v_registration_login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.v_registration_login.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
     FirebaseAuth auth;
     Button logoutBtn;
     TextView textView;
     FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFrament(new HomeFragment()); // Fragment to show when app luanches

        binding.bottomNavigationView.setOnItemReselectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFrament(new HomeFragment());
                    break;
                case R.id.dashboard:
                    replaceFrament(new DashboardFragment());
                    break;
                case R.id.chat:
                    replaceFrament(new ChatFragment());
                    break;
                case R.id.account:
                    replaceFrament(new AccountFragment());
                    break;
            }

        });
       }

       private void replaceFrament(Fragment fragment){
           FragmentManager fragmentManager = getSupportFragmentManager();
           FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           fragmentTransaction.replace(R.id.frame_layout,fragment);
       }
}