package lerner.ethan.kingscamp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SectionIndexer;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    String [] Sections = {"Mammals", "Birds", "Plants"};
    String SECTION_KEY = "SECTION";
    String FIRST = "FIRST";
    int NUM_MAMMALS = 200;
    String ARRAY_NAME = "myArr";

    int TOTAL_SPECIES = 93;
    int TOTAL_MAMMALS = 43;
    int TOTAL_PLANTS = 50;
    int TOTAL_BIRDS;//TODO
    int TOTAL_REPTILES;//TODO
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

    }

    public void onButtonClick(View v){
        Button button = (Button) v;
        String section = (String) button.getText();
        Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
        intent.putExtra(SECTION_KEY, section);
        this.startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();

        int completed = 0;
        for(String s : Sections) {
            SharedPreferences sharedPreferences = getSharedPreferences(s,MODE_PRIVATE);
            for (int i = 0; i < sharedPreferences.getAll().size(); i++)
                if( sharedPreferences.getBoolean(ARRAY_NAME + "_" + i, false))
                    completed++;

        }
        seen = completed;
        TextView percentage = findViewById(R.id.textView);
        percentage.setText(String.format(Locale.getDefault(), "%%%.2f", 100.0 * completed / TOTAL_SPECIES));
        percentage.setOnClickListener(new View.OnClickListener() {
            boolean percent = false;

            @Override
            public void onClick(View view) {
                percent = !percent;
                if (percent)
                    ((TextView) view).setText(String.format("%d/%d", seen, TOTAL_SPECIES));
                else
                    ((TextView) view).setText(String.format(Locale.getDefault(), "%%%.2f", 100.0 * seen / TOTAL_SPECIES));

            }
        });
    }

}
