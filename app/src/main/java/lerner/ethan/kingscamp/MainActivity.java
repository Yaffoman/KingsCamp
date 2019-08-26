package lerner.ethan.kingscamp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    String SECTION_KEY = "SECTION";
    String STANDARD_SECTION_KEY = "STD_SECTION";
    String FIRST = "FIRST";
    int NUM_MAMMALS = 200;
    String ARRAY_NAME = "myArr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);

        loadFragment(new HomeFragment());
    }

    public void onButtonClick(View v){
        MaterialButton button = (MaterialButton) v;
        // Button button = (Button) v;
        String section = (String) button.getText();
        CharSequence standardSection = button.getContentDescription();
        Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
        intent.putExtra(SECTION_KEY, section);
        intent.putExtra(STANDARD_SECTION_KEY, standardSection);
        this.startActivity(intent);
    }

    /*
    To add a section:
    1. add an item in @array/sections
    2. add button and progress bar components
    3. adjust constraints
    4. create new case below with index number
    5. add global total variable and adjuist totals accordingly
    6. in the new case, follow same pattern as others, but with new section
    7. in values/strings make a string array with name of section and items of animal names
     */

    private boolean loadFragment(Fragment frag) {
        if (frag != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, frag)
                    .commit();
            return true;
        } else
            return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment frag = null;

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                frag = new HomeFragment();
                break;
            case R.id.navigation_map:
                frag = new MapFragment();
                break;
            case R.id.navigation_info:
                frag = new InfoFragment();
        }

        return loadFragment(frag);
    }
}
