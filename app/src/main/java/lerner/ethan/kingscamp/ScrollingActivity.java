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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class ScrollingActivity extends AppCompatActivity {
    String SECTION_KEY = "SECTION";
    String ARRAY_NAME = "myArr";
    String FIRST = "FIRST";
    int NUM_MAMMALS = 200;
    boolean[] checklist;
    String section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        section = intent.getStringExtra(SECTION_KEY);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(section);
        fillArray();
    }

    private void fillArray() {
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

        SharedPreferences sharedPref = getSharedPreferences(section, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int size = speciesArray.length;
        checklist = new boolean[size];

        if (sharedPref.getBoolean(FIRST, true)) {
            editor.putBoolean(FIRST, false);
            editor.putInt(ARRAY_NAME + "_size", speciesArray.length);
            for (int i = 0; i < size; i++)
                editor.putBoolean(ARRAY_NAME + "_" + i, false);
            editor.apply();
        } else {

            for (int i = 0; i < size; i++)
                checklist[i] = sharedPref.getBoolean(ARRAY_NAME + "_" + i, false);
        }



        LinearLayout ll = findViewById(R.id.linlayout);

        for(int i = 0; i < size; i++)
        {
            TextView tv = new TextView(this);
            tv.setText(String.format(Locale.getDefault(),"%d\t%s",i, speciesArray[i]));
            tv.setClickable(true);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Use regex to delete all non-digits, then parse to int
                    int pos = Integer.parseInt(((TextView)view).getText().toString().replaceAll("[\\D]", ""));
                    if(checklist[pos]) //Should be green already
                        view.setBackgroundColor(getResources().getColor(android.R.color.white));
                    else
                        view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));

                    checklist[pos] = !checklist[pos];

                }
            });
            if(checklist[i])
                tv.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            ll.addView(tv);
        }

    }

    @Override
    protected void onPause() {

        SharedPreferences sharedPref = getSharedPreferences(section, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int size = sharedPref.getInt(ARRAY_NAME + "_size", 0);

        for (int i = 0; i < size; i++)
            editor.putBoolean(ARRAY_NAME + "_" + i, checklist[i]);
        editor.apply();

        super.onPause();
    }
}
