package lerner.ethan.kingscamp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

public class SpecialDialog extends Dialog implements
        android.view.View.OnClickListener {

    private int num; //The position of selection
    private int imageID;
    private String description;
    private String sciname;

    enum species {MAMMAL, BIRD, REPTILE, PLANT}

    SpecialDialog(Activity a) {
        super(a);
    }

    SpecialDialog(Activity a, String name, String specie) {
        super(a);
        int index = 0;
        switch (specie) {
            case "mammals":
                index = 0;
            case "birds":
                index = 1;
            case "plants":
                index = 2;
        }
        try {

            JSONObject obj = new JSONObject(loadJSONFromAsset(a));
            JSONObject speciesArr = obj.getJSONObject(specie);
            JSONArray specieArr = speciesArr.getJSONArray(name);
            JSONObject myObj = specieArr.getJSONObject(0);

            sciname = myObj.getString("sciname");
            description = obj.getJSONObject(specie).getJSONArray(name.toLowerCase()).getJSONObject(0).getString("description");
            String img = obj.getJSONObject(specie).getJSONArray(name.toLowerCase()).getJSONObject(0).getString("imageid");
            imageID = a.getResources().getIdentifier("@drawable/" + img, "drawable", a.getPackageName());

        } catch (JSONException e) {
            e.printStackTrace();
        }

       /* switch (specie) {
            case BIRD:
                break;
            case PLANT:
                break;
            case MAMMAL:
                break;
            case REPTILE:
                break;
            default:
                break;
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_2);
        Button yes = findViewById(R.id.close_button);
        yes.setOnClickListener(this);

        ImageView imageView = findViewById(R.id.imageView);
        TextView descView = findViewById(R.id.desc_view);
        TextView sciView = findViewById(R.id.sciname_view);
        imageView.setImageResource(imageID);
        descView.setText(description);
        sciView.setText(sciname);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }


    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("imageids.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


}
