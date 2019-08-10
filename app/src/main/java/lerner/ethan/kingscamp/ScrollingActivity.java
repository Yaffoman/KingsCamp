package lerner.ethan.kingscamp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import static lerner.ethan.kingscamp.SpecialDialog.species.*;

public class ScrollingActivity extends AppCompatActivity {
    int selectedColor;
    int unselectedColor;
    String SECTION_KEY = "SECTION";
    String ARRAY_NAME = "myArr";
    CheckBox[] checklist;
    String section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        section = intent.getStringExtra(SECTION_KEY);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(section);
        unselectedColor = Color.rgb(255, 239, 229);
        selectedColor = Color.rgb(204, 173, 153);
        fillArray();
    }

    private void fillArray() {
        String[] speciesArray;
        LinearLayout outerLinear = findViewById(R.id.linlayout);

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
        checklist = new CheckBox[size];
        editor.putInt(section + "_size", size);
        editor.apply();

        for (int i = 0; i < size; i++) { //Dynamically create each checkbox

            CheckBox cb = new CheckBox(outerLinear.getContext());
            cb.setText(speciesArray[i]);
            cb.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            cb.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //int pos = Integer.parseInt(((TextView) view).getText().toString().replaceAll("[\\D]", ""));

                    Dialog popup = new SpecialDialog(ScrollingActivity.this, ((TextView) view).getText().toString(), "mammals");
                    popup.show();

                    return false;
                }
            });
            //Toggle colors, every other line is gray
            if(i%2 == 0)
                cb.setBackgroundColor(selectedColor);
            else
                cb.setBackgroundColor(unselectedColor);
            cb.setChecked(sharedPref.getBoolean(cb.getText().toString(), false));
            checklist[i] = cb;
            outerLinear.addView(cb);
        }
    }

    @Override
    protected void onPause() {

        SharedPreferences sharedPref = getSharedPreferences(section, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int size = sharedPref.getInt(section + "_size", 0);
        int total = 0;
        for (int i = 0; i < size; i++) {
            editor.putBoolean(checklist[i].getText().toString(), checklist[i].isChecked());
            if (checklist[i].isChecked())
                total++;
        }
        editor.putInt(section + "_completed", total);
        editor.apply();

        super.onPause();
    }
    @Override
    protected void onDestroy() {

        SharedPreferences sharedPref = getSharedPreferences(section, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int size = sharedPref.getInt(section + "_size", 0);
        int total = 0;
        for (int i = 0; i < size; i++) {
            editor.putBoolean(checklist[i].getText().toString(), checklist[i].isChecked());
            if (checklist[i].isChecked())
                total++;
        }
        editor.putInt(section + "_completed", total);
        editor.apply();

        super.onDestroy();
    }
}
