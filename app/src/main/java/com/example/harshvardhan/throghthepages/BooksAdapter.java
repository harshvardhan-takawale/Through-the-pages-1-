package com.example.harshvardhan.throghthepages;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;



public class BooksAdapter extends ArrayAdapter<book_attributes> {

    public BooksAdapter( Context context, List<book_attributes> objects) {
        super(context, 0, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.listviewbooks, parent, false);
        }

        book_attributes currentbook = getItem(position);


        TextView Bookname = (TextView) listItemView.findViewById(R.id.booktitle_xml);
        Bookname.setText(currentbook.getBooktitle());

        TextView AuthorName = (TextView) listItemView.findViewById(R.id.author_xml);
        AuthorName.setText(currentbook.getAuthor());

        TextView pages = (TextView) listItemView.findViewById(R.id.magnitude);
        int p = (currentbook.getNo_pages());
        String l = String.valueOf(p);
        pages.setText(l);

        GradientDrawable magnitudeCircle = (GradientDrawable) pages.getBackground();
        int magnitudeColor = getMagnitudeColor(p);
        magnitudeCircle.setColor(magnitudeColor);

        return listItemView;

    }

    private int getMagnitudeColor(int page) {
        int magnitudeColorResourceId;
        page = (page/100);
        int magnitudeFloor = (int) Math.floor(page);
        switch (magnitudeFloor) {
            case 0:
                magnitudeColorResourceId = R.color.magnitude100;
                break;
            case 1:
                magnitudeColorResourceId = R.color.magnitude200;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude300;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude400;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude500;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude600;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude700;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude800;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude900;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude1000;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude1000plus;
                break;


        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }


}
