package lerner.ethan.kingscamp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SectionIndexer;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    String[] sections;
    String SECTION_KEY = "SECTION";
    String STANDARD_SECTION_KEY = "STD_SECTION";
    String FIRST = "FIRST";
    int NUM_MAMMALS = 200;
    String ARRAY_NAME = "myArr";



    int TOTAL_MAMMALS = 43;
    int TOTAL_PLANTS = 50;
    int TOTAL_BIRDS = 333;
    int TOTAL_REPTILES = 33;
    int TOTAL_AMPHIBIANS = 19;
    int TOTAL_SPECIES = TOTAL_BIRDS + TOTAL_PLANTS + TOTAL_MAMMALS + TOTAL_REPTILES + TOTAL_AMPHIBIANS;

    int seen;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        sections = getResources().getStringArray(R.array.sections);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.reptile_progress);
        progressBar.setProgress(50);
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
    @Override
    protected void onStart() {
        super.onStart();

        int completed = 0;
        int ctr = 0;
        for (String s : sections) {
            SharedPreferences sharedPreferences = getSharedPreferences(s,MODE_PRIVATE);
            int s_completed = sharedPreferences.getInt(s + "_completed", 0);
            completed += s_completed;
            switch (ctr) {
                case 2:
                    ((ProgressBar) findViewById(R.id.mammal_progress)).setProgress(100 * s_completed / TOTAL_MAMMALS);
                    break;
                case 3:
                    ((ProgressBar) findViewById(R.id.reptile_progress)).setProgress(100 * s_completed / TOTAL_REPTILES);
                    break;
                case 1:
                    ((ProgressBar) findViewById(R.id.bird_progress)).setProgress(100 * s_completed / TOTAL_BIRDS);
                    break;
                case 0:
                    ((ProgressBar) findViewById(R.id.plant_progress)).setProgress(100 * s_completed / TOTAL_PLANTS);
                    break;
                case 4:
                    ((ProgressBar) findViewById(R.id.amphibian_progress)).setProgress(100 * s_completed / TOTAL_AMPHIBIANS);
                    break;

            }
            ctr++;

        }
        seen = completed;
        TextView percentage = findViewById(R.id.textView);
        ProgressBar progressBar = findViewById(R.id.progress);

        progressBar.setMax(TOTAL_SPECIES);
        progressBar.setProgress(completed);
        percentage.setText(String.format(Locale.getDefault(), "%d%%", 100 * completed / TOTAL_SPECIES));
        percentage.setOnClickListener(new View.OnClickListener() {
            boolean percent = true;

            @Override
            public void onClick(View view) {
                if (!percent) {
                    ((TextView) view).setText(String.format(Locale.getDefault(), "%d%%", 100 * seen / TOTAL_SPECIES));
                } else {
                    ((TextView) view).setText(String.format(Locale.getDefault(), "%d/%d", seen, TOTAL_SPECIES));
                }
                percent = !percent;
            }
        });
    }

}
