package com.example.android.bookfindr;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.android.bookfindr.databinding.ListItemBookBinding;

import java.util.ArrayList;

/**
 * Created by ankurg22 on 5/8/17.
 */

public class BookListAdapter extends ArrayAdapter<Book> {
    private ArrayList<Book> books;

    public BookListAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
        this.books = books;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (convertView == null) {
            ListItemBookBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_book, parent, false);
            convertView = binding.getRoot();
            viewHolder = new ViewHolder(binding, position % 2 == 0);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.bind(books.get(position));
        return convertView;
    }

    private class ViewHolder {
        private ListItemBookBinding itemBinding;
        private boolean isEven;

        public ViewHolder(ListItemBookBinding binding, boolean even) {
            this.itemBinding = binding;
            this.isEven = even;
        }

        public void bind(Book book) {
            itemBinding.setBook(book);
            itemBinding.setIsEven(isEven);
            itemBinding.executePendingBindings();
        }
    }
}
