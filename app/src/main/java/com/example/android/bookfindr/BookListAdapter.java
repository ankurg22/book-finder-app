package com.example.android.bookfindr;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.android.bookfindr.databinding.ListItemBookBinding;

import java.util.ArrayList;

/**
 * Created by ankurg22 on 5/8/17.
 */

public class BookListAdapter extends BaseAdapter {
    private ArrayList<Book> books;

    public BookListAdapter(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (convertView == null) {
            ListItemBookBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_book, parent, false);
            convertView = binding.getRoot();
            viewHolder = new ViewHolder(binding);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.bind(books.get(position));
        return convertView;
    }

    private class ViewHolder {
        private ListItemBookBinding itemBinding;

        public ViewHolder(ListItemBookBinding binding) {
            this.itemBinding = binding;
        }

        public void bind(Book book) {
            itemBinding.setBook(book);
            itemBinding.executePendingBindings();
        }
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int i) {
        return books.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

}
