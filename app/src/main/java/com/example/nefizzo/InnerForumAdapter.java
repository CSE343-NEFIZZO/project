package com.example.nefizzo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class InnerForumAdapter extends BaseAdapter {
    List<InnerForumModel> list;
    Context context;

    public InnerForumAdapter(List<InnerForumModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout = LayoutInflater.from(context).inflate(R.layout.forumlayout, parent, false);
        TextView userName = (TextView) layout.findViewById(R.id.userName);
        TextView comment = (TextView) layout.findViewById(R.id.comment);
        userName.setText(list.get(position).getName().toString());
        comment.setText(list.get(position).getComment().toString());
        return layout;
    }
}
