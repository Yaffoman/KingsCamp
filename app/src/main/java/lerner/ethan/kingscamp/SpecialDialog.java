package lerner.ethan.kingscamp;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class SpecialDialog extends Dialog implements
        android.view.View.OnClickListener {

    private int num; //The position of selection
    private int imageID;

    enum species {MAMMAL, BIRD, REPTILE, PLANT}

    SpecialDialog(Activity a) {
        super(a);
    }

    SpecialDialog(Activity a, int s, species specie) {
        super(a);

        switch (specie) {
            case BIRD:
                break;
            case PLANT:
                break;
            case MAMMAL:
                imageID = a.getResources().obtainTypedArray(R.array.mammal_images).getResourceId(s, -1);
                break;
            case REPTILE:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup);
        Button yes = findViewById(R.id.close_button);
        yes.setOnClickListener(this);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(imageID);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
