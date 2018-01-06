package com.example.seilab.bp007;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.seilab.bp007.R;

import java.util.List;

/**
 * Created by seilab on 12/20/17.
 */

public class MyArrayAdapter extends ArrayAdapter<String> {
    int resource;
    Context ctxt;

    public MyArrayAdapter(Context context, int _resource, List<String> items) {
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

        String objekat = getItem(position);

        TextView textView = (TextView) newView.findViewById(R.id.Itemname);
        textView.setText(objekat.toString());

        ImageView imageView = (ImageView) newView.findViewById(R.id.icon);
        if (objekat.equals("Tables")) {
            imageView.setImageResource(R.drawable.tabele);
        }
        else if (objekat.equals("Views")) {
            imageView.setImageResource(R.drawable.pogledi);
        }
        else if (objekat.equals("Triggers")) {
            imageView.setImageResource(R.drawable.triggeri);
        }
        else if (objekat.equals("Procedures")) {
            imageView.setImageResource(R.drawable.procedure);
        }
        else if (objekat.equals("Indexes")) {
            imageView.setImageResource(R.drawable.indeksi);
        }
        else {
            imageView.setImageResource(R.drawable.tabele);
        }
        return newView;
    }
}
