package lerner.ethan.kingscamp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

public class ScrollingActivity extends AppCompatActivity {
    String SECTION_KEY = "SECTION";
    String FIRST = "FIRST";
    int NUM_MAMMALS = 200;
    boolean[] checklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String section = intent.getStringExtra(SECTION_KEY);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(section);
        fillArray(section);
    }

    private void fillArray(String section) {
        String[] speciesArray;
        ScrollView list = findViewById(R.id.species_list);

        if (section.compareTo("Mammals") == 0)
            speciesArray = getResources().getStringArray(R.array.mammals);
        else if (section.compareTo("Plants") == 0)
            speciesArray = getResources().getStringArray(R.array.plants);
        else if (section.compareTo("Birds") == 0)
            speciesArray = getResources().getStringArray(R.array.birds);
        else
            speciesArray = getResources().getStringArray(R.array.mammals);

        SharedPreferences sharedPref = getSharedPreferences("species", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String arrayName = "myArr";
        int size = speciesArray.length;
        checklist = new boolean[size];

        if (sharedPref.getBoolean(FIRST, true)) {
            editor.putBoolean(FIRST, false);
            editor.putInt(arrayName + "_size", speciesArray.length);
            for (int i = 0; i < size; i++)
                editor.putBoolean(arrayName + "_" + i, false);
            editor.apply();
        } else {

            for (int i = 0; i < size; i++)
                checklist[i] = sharedPref.getBoolean(arrayName + "_" + i, false);
        }

        ArrayAdapter<String> speciesAdapter = new SpecialAdapter(this, android.R.layout.simple_list_item_1, speciesArray,checklist);

        list.setAdapter(speciesAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                checklist[position] = !checklist[position];
            }
        });
    }

    @Override
    protected void onDestroy() {
        SharedPreferences sharedPref = getSharedPreferences("species", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String arrayName = "myArr";
        int size = sharedPref.getInt(arrayName + "_size", 0);

        for (int i = 0; i < size; i++)
            editor.putBoolean(arrayName + "_" + i, checklist[i]);
        editor.apply();

        super.onDestroy();
    }
}
