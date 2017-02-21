package org.devnexus.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsSupportFragment;
import com.mikepenz.materialize.MaterializeBuilder;

import org.devnexus.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.menu)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        new MaterializeBuilder().withActivity(this).build();

        setupNavigationMenu();

        displayHome();
    }

    // -- Navigation ------------------------------------------------------------------------------

    @OnClick(R.id.drawer)
    void openDrawerMenu() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void setupNavigationMenu() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.home:
                        displayHome();
                        break;
                    case R.id.all_presentations:
                        displayPresentations();
                        break;
//                    case R.id.my_schedule:
//                        displayMySchedule();
//                        break;
                    case R.id.speakers:
                        displaySpeakers();
                        break;
//                    case R.id.scan_badge:
//                        displayScanBadge();
//                        break;
                    case R.id.sponsors:
                        displaySponsors();
                        break;
                    case R.id.about:
                        displayAbout();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void display(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    private void displayHome() {
        display(new HomeFragment());
    }

    private void displayPresentations() {
        display(new PresentationsFragment());
    }

//    private void displayMySchedule() {
//        display(new MyScheduleFragment());
//    }

    private void displaySpeakers() {
        display(new SpeakersFragment());
    }

//    private void displayScanBadge() {
//        display(new ScanBadgeFragment());
//    }

    private void displaySponsors() {
        display(new SponsorsFragment());
    }

    private void displayAbout() {
        LibsSupportFragment fragment = new LibsBuilder()
                .withAboutIconShown(true)
                .withAboutVersionShown(true)
                .withAboutDescription(getString(R.string.about_description))
                .supportFragment();
        display(fragment);
    }

}
