package com.fenixarts.nenektrivia.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenixarts.nenektrivia.R;
import com.fenixarts.nenektrivia.base.BaseActivity;
import com.fenixarts.nenektrivia.bestplayers.BestPlayersFragments;
import com.fenixarts.nenektrivia.promotions.PromotionsFragment;
import com.fenixarts.nenektrivia.utils.NenekDialog;
import com.fenixarts.nenektrivia.utils.Utils;
import com.squareup.picasso.Picasso;

@SuppressWarnings("ALL")
public class MainActivity extends BaseActivity {

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreateNenekBaseView(savedInstanceState, R.layout.activity_main);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
            if (getUser() != null){
                TextView profileMail = navigationView.getHeaderView(0).findViewById(R.id.header_email);
                TextView profileUsername = navigationView.getHeaderView(0).findViewById(R.id.header_username);
                ImageView profileImage = navigationView.getHeaderView(0).findViewById(R.id.header_image);
                profileMail.setText(getUser().getEmail());
                profileUsername.setText(getUser().getDisplayName());
                Picasso.with(this).load(getUser().getPhotoUrl()).into(profileImage);
            }
        }

        getSupportActionBar().setTitle(R.string.menu_home);
        Utils.addFragmentToActivity(getSupportFragmentManager(), MainFragment.getInstance(), R.id.main_content);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        switch (item.getItemId()) {
            case android.R.id.home: drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return false;
    }

    public void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectItem(menuItem);
                return true;
            }
        });
    }

    private void selectItem(MenuItem menuItem) {
        // Marcar item presionado
        menuItem.setChecked(true);
        // Crear nuevo fragmento
        String title = menuItem.getTitle().toString();

        Fragment fragment = null;
        switch (menuItem.getItemId()){
            /*case R.id.action_profile:
                getSupportActionBar().setSubtitle(R.string.menu_profile);
                fragment = ProfileFragment.getInstance();
                break;*/
            case R.id.action_home:
                getSupportActionBar().setSubtitle(R.string.menu_home);
                fragment = MainFragment.getInstance();
                break;
            case R.id.action_points:
                getSupportActionBar().setSubtitle(R.string.menu_honor);
                fragment = BestPlayersFragments.getInstance();
                break;
            case R.id.action_promotions:
                getSupportActionBar().setSubtitle(R.string.menu_promotions);
                fragment = PromotionsFragment.getInstance();
                break;
            /*case R.id.action_help:
                getSupportActionBar().setSubtitle(R.string.menu_help);
                fragment = HelpFragment.getInstance();
                break;*/
            case R.id.action_exit: logout();
                break;
            default:
                getSupportActionBar().setSubtitle(R.string.menu_home);
                fragment = MainFragment.getInstance();
        }

        if (fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawers(); // Cerrar drawer

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            NenekDialog dialog = new NenekDialog(this);
            dialog.setTitle("Cerrar");
            dialog.setContent("¿Deseas cerrar la aplicación?");
            dialog.setAcceptText(getString(R.string.popup_button_accept));
            dialog.setCancelText(getString(R.string.popup_button_cancel));
            dialog.setAcceptClickListener(new NenekDialog.OnClickListener() {
                @Override
                public void onClick(NenekDialog view) {
                    view.dismiss();
                    finish();
                }
            });
            dialog.setCancelClickListener(new NenekDialog.OnClickListener() {
                @Override
                public void onClick(NenekDialog view) {
                    view.dismiss();
                }
            });
            dialog.show();
        }
    }

}
