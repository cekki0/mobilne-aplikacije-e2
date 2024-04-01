package com.example.fijiapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Button btnOpenDrawer;


    public MainActivity(){}
    public MainActivity(DrawerLayout drawerLayout, NavigationView navigationView, ActionBarDrawerToggle actionBarDrawerToggle, Button btnOpenDrawer) {
        this.drawerLayout = drawerLayout;
        this.navigationView = navigationView;
        this.actionBarDrawerToggle = actionBarDrawerToggle;
        this.btnOpenDrawer = btnOpenDrawer;
    }

    public MainActivity(int contentLayoutId, DrawerLayout drawerLayout, NavigationView navigationView, ActionBarDrawerToggle actionBarDrawerToggle, Button btnOpenDrawer) {
        super(contentLayoutId);
        this.drawerLayout = drawerLayout;
        this.navigationView = navigationView;
        this.actionBarDrawerToggle = actionBarDrawerToggle;
        this.btnOpenDrawer = btnOpenDrawer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        btnOpenDrawer = findViewById(R.id.btn_open_drawer);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id  ==   R.id.nav_home) {

                startActivity(new Intent(MainActivity.this, MainActivity.class));
            } else if (id == R.id.nav_second) {


                startActivity(new Intent(MainActivity.this, UpdateProductActivity.class));

            } else if (id == R.id.nav_third) {
                startActivity(new Intent(MainActivity.this, ServiceActivity.class));
            } else if (id == R.id.nav_fourth) {


                startActivity(new Intent(MainActivity.this, ProductsActivity.class));
            }
            else if (id == R.id.nav_fivth) {


                startActivity(new Intent(MainActivity.this, PackageActivity.class));
            }
            drawerLayout.closeDrawers();
            return true;
        });
        btnOpenDrawer.setOnClickListener(v -> drawerLayout.openDrawer(navigationView));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
