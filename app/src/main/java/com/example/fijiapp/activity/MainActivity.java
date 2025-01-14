package com.example.fijiapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fijiapp.R;
import com.example.fijiapp.activity.category.CategoryManagementAdminActivity;
import com.example.fijiapp.activity.event.EventTypeManagementActivity;
import com.example.fijiapp.activity.login.LoginActivity;
import com.example.fijiapp.activity.register.ProductServiceManagementActivity;
import com.example.fijiapp.activity.register.RegistrationRequestsManagement;
import com.example.fijiapp.model.UserRole;
import com.example.fijiapp.service.OwnerService;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Button btnOpenDrawer;
    private Button btnLogOut;
    private OwnerService userService = new OwnerService();

    public MainActivity() {
    }

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
        btnLogOut = findViewById(R.id.btn_log_out);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUserAuth = mAuth.getCurrentUser();
        assert currentUserAuth != null;
        userService.getUserByEmail(currentUserAuth.getEmail()).addOnSuccessListener(extendedUser -> {
                    if (extendedUser != null) {
                        navigationView.setNavigationItemSelectedListener(item -> {
                            int id = item.getItemId();
                            if (id == R.id.nav_home) {
                                startActivity(new Intent(MainActivity.this, MainActivity.class));
                            } else if (id == R.id.product) {
                                startActivity(new Intent(MainActivity.this, PriceListActivity.class));
                            } else if (id == R.id.service) {
                                startActivity(new Intent(MainActivity.this, ServiceActivity.class));
                            } else if (id == R.id.packages) {
                                startActivity(new Intent(MainActivity.this, PackageActivity.class));
                            } else if (id == R.id.categories_management) {
                                startActivity(new Intent(MainActivity.this, CategoryManagementAdminActivity.class));
                            } else if (id == R.id.event_type_management) {
                                startActivity(new Intent(MainActivity.this, EventTypeManagementActivity.class));
                            } else if (id == R.id.owner) {
                                startActivity(new Intent(MainActivity.this, OwnerActivity.class));
                            } else if (id == R.id.create_event) {
                                startActivity(new Intent(MainActivity.this, EventActivity.class));
                            } else if (id == R.id.landing_page) {
                                startActivity(new Intent(MainActivity.this, LandingPageActivity.class));
                            } else if (id == R.id.product_service_management) {
                                startActivity(new Intent(MainActivity.this, ProductServiceManagementActivity.class));
                            } else if (id == R.id.registration_request_management) {
                                startActivity(new Intent(MainActivity.this, RegistrationRequestsManagement.class));
                            } else if (id == R.id.od_events_view) {
                                startActivity(new Intent(MainActivity.this, EventsViewActivity.class));
                            } else if (id == R.id.notification_view) {
                                startActivity(new Intent(MainActivity.this, NotificationViewActivity.class));
                            } else if (id == R.id.reservationsView) {
                                if (extendedUser.Role == UserRole.EVENT_ORGANIZER) {
                                    startActivity(new Intent(MainActivity.this, ReservationListODActivity.class));
                                }
                            } else if (id == R.id.commentsView) {
                                startActivity(new Intent(MainActivity.this, RatingCommentActivity.class));
                            }
                            drawerLayout.closeDrawers();
                            return true;
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "User data not found!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                });


        btnOpenDrawer.setOnClickListener(v -> drawerLayout.openDrawer(navigationView));
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
