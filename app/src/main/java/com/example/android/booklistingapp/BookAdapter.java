package com.example.android.booklistingapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lariveg on 5/06/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    static class ViewHolder {
        private TextView bookTitleTextView;
        private TextView authorTextView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        ViewHolder holder;

        // Check if the existing view is being reused, otherwise inflate the view
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
            // cache view fields into the holder
            holder = new ViewHolder();
            holder.bookTitleTextView = (TextView) listItemView.findViewById(R.id.book_title);
            holder.authorTextView = (TextView) listItemView.findViewById(R.id.book_author);
            listItemView.setTag(holder);
        }
        // view already exists, get the holder instance from the view
        else {
            holder = (ViewHolder) listItemView.getTag();
        }

        // Get the {@link Place} object located at this position in the list
        Book currentBook = getItem(position);

        //Display the book title of the current book in that TextView
        holder.bookTitleTextView.setText(currentBook.getTitle());

        //Display the book author of the current book in that TextView
        holder.authorTextView.setText(currentBook.getAuthor());

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
