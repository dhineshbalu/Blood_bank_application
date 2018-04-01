package com.example.dhina.myapplication;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.zip.Inflater;

public class CustomAdapter1 extends BaseAdapter {
    Context context;
    String[] namelist;
    String[] numberlist;
    String[] bloodlist;
    String[] locationlist;
    LayoutInflater inflter;

    public CustomAdapter1(Context applicationContext, String[] namelist, String[] numberlist,String[] bloodlist,String[] locationlist) {
        this.context = applicationContext;
        this.namelist = namelist;
        this.numberlist = numberlist;
        this.bloodlist = bloodlist;
        this.locationlist = locationlist;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return namelist.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.listview1, null);
        TextView name = (TextView) view.findViewById(R.id.textView1);
        TextView number = (TextView) view.findViewById(R.id.textView2);
        TextView blood = (TextView) view.findViewById(R.id.textView3);
        TextView location = (TextView) view.findViewById(R.id.textView4);
        name.setText(namelist[i]);
        number.setText(numberlist[i]);
        blood.setText(bloodlist[i]);
        location.setText(locationlist[i]);
        return view;
    }
}