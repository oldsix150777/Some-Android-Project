package com.example.frag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.frag.page.a1HomeFragment;
import com.example.frag.page.a2CategoryFragment;
import com.example.frag.page.a3CartFragment;
import com.example.frag.page.a4PersonalFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabBar = findViewById(R.id.tabBar);

        tabBar.addTab(tabBar.newTab().setText("").setIcon(R.drawable.tab_item_home_focus));
        tabBar.addTab(tabBar.newTab().setText("").setIcon(R.drawable.tab_item_category_focus));
        tabBar.addTab(tabBar.newTab().setText("").setIcon(R.drawable.tab_item_cart_focus));
        tabBar.addTab(tabBar.newTab().setText("").setIcon(R.drawable.tab_item_personal_focus));

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction =
                manager.beginTransaction();
        transaction.replace(R.id.frame_01, new a1HomeFragment());
        transaction.commit();

        //检查是否登陆


        tabBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction =
                            manager.beginTransaction();
                    transaction.replace(R.id.frame_01, new a1HomeFragment());
                    transaction.commit();
                } else if (tab.getPosition() == 1) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction =
                            manager.beginTransaction();
                    transaction.replace(R.id.frame_01, new a2CategoryFragment());
                    transaction.commit();
                }else if (tab.getPosition() == 2){
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction =
                            manager.beginTransaction();
                    transaction.replace(R.id.frame_01, new a3CartFragment());
                    transaction.commit();
                }else if (tab.getPosition() == 3){
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction =
                            manager.beginTransaction();
                    transaction.replace(R.id.frame_01, new a4PersonalFragment());
                    transaction.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}