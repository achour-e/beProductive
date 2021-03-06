package com.example.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.main.chat.ChatFragment;
import com.example.main.explore.ExploreFragment;
import com.example.main.post.PostFragment;
import com.example.main.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference UsersDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ChatFragment()).commit();
        }
        mAuth = FirebaseAuth.getInstance();
        UsersDb = FirebaseDatabase.getInstance().getReference().child("Users").child("AllUsers");
        String data = getIntent().getStringExtra("data");
        if(data != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new PostFragment()).commit();
        }
        String edit = getIntent().getStringExtra("edit");
        if(edit != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit();
        }

       /* String PostKey = getIntent().getStringExtra("PostKey");
        if(PostKey != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ExploreFragment()).commit();
        }*/

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_chat:
                            selectedFragment = new ChatFragment();
                            break;
                        case R.id.nav_group:
                            selectedFragment = new PostFragment();
                            break;

                        case R.id.nav_explore:
                            selectedFragment = new ExploreFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

}