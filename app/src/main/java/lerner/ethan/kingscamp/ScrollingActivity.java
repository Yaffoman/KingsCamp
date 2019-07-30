package lerner.ethan.kingscamp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Locale;

public class ScrollingActivity extends AppCompatActivity {
    int selectedColor = android.R.color.darker_gray;
    int unselectedColor = android.R.color.white;
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


        LinearLayout outerLinear = findViewById(R.id.linlayout);
        LinearLayout innerLinear;

        for (int i = 0; i < size; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            innerLinear = new LinearLayout(outerLinear.getContext());
            innerLinear.setLayoutParams(layoutParams);
            innerLinear.setOrientation(LinearLayout.HORIZONTAL);

            CheckBox cb = new CheckBox(innerLinear.getContext());
            cb.setText(String.format(Locale.getDefault(), "\t%d\t%s", i+1, speciesArray[i]));
            cb.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Use regex to delete all non-digits, then parse to int
                    CheckBox thisCheckBox = (CheckBox) view;
                    int pos = Integer.parseInt(((TextView) view).getText().toString().replaceAll("[\\D]", ""));
                    pos--; //Because it is numbered from 1, not 0
                    if (checklist[pos]) //Should be green already
                        view.setBackgroundColor(getResources().getColor(unselectedColor));
                    else
                        view.setBackgroundColor(getResources().getColor(selectedColor));

                    checklist[pos] = !checklist[pos];

                }
            });
            //Toggle colors, every other line is gray
            if(i%2 == 0)
                cb.setBackgroundColor(getResources().getColor(selectedColor));
            else
                cb.setBackgroundColor(getResources().getColor(unselectedColor));
            cb.setChecked(checklist[i]);
            outerLinear.addView(innerLinear);

            innerLinear.addView(cb);
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
