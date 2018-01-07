package com.example.seilab.bp007;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by seilab on 12/20/17.
 */

public class DetaljiAdapter extends ArrayAdapter<Objekat> {
    int resource;
    Context ctxt;

    public DetaljiAdapter(Context context, int _resource, List<Objekat> items) {
        super(context, _resource, items);
        ctxt = context;
        resource = _resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // creating + inflating
        LinearLayout newView;

        if (convertView == null) {
            // first time accessing convertView
            // create new object + inflate
            newView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, newView, true);
        }
        else {
            newView = (LinearLayout) convertView;
        }

        Objekat objekat = getItem(position);

        TextView textView = (TextView) newView.findViewById(R.id.kolona);
        textView.setText(objekat.getKolone());
        TextView textView2 = (TextView) newView.findViewById(R.id.vrijednost);
        textView2.setText(objekat.getVrijednosti());
        return newView;
    }
}
