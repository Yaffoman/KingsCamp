package lerner.ethan.kingscamp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_layout, null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sections = getResources().getStringArray(R.array.sections);
    }


    @Override
    public void onStart() {
        super.onStart();

        int completed = 0;
        int ctr = 0;
        for (String s : sections) {
            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(s, MODE_PRIVATE);
            int s_completed = sharedPreferences.getInt(s + "_completed", 0);
            completed += s_completed;
            switch (ctr) {
                case 2:
                    ((ProgressBar) getView().findViewById(R.id.mammal_progress)).setProgress(100 * s_completed / TOTAL_MAMMALS);
                    break;
                case 3:
                    ((ProgressBar) getView().findViewById(R.id.reptile_progress)).setProgress(100 * s_completed / TOTAL_REPTILES);
                    break;
                case 1:
                    ((ProgressBar) getView().findViewById(R.id.bird_progress)).setProgress(100 * s_completed / TOTAL_BIRDS);
                    break;
                case 0:
                    ((ProgressBar) getView().findViewById(R.id.plant_progress)).setProgress(100 * s_completed / TOTAL_PLANTS);
                    break;
                case 4:
                    ((ProgressBar) getView().findViewById(R.id.amphibian_progress)).setProgress(100 * s_completed / TOTAL_AMPHIBIANS);
                    break;

            }
            ctr++;

        }
        seen = completed;
        TextView percentage = getView().findViewById(R.id.textView);
        ProgressBar progressBar = getView().findViewById(R.id.progress);

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
