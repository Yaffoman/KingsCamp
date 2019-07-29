package lerner.ethan.kingscamp;

import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

public class SpecialAdapter extends ArrayAdapter {
        private boolean [] checklist;

        public SpecialAdapter(Context context, int resource, String[] items, boolean [] checklist) {
            super(context,resource,items);
            this.checklist = checklist;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            if(checklist[position])
                view.setBackgroundColor(parent.getResources().getColor(android.R.color.holo_red_light));
            return view;
        }
}

