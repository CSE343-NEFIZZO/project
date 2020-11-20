package com.example.nefizzo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class OuterForumListAdapter extends BaseAdapter{

    List<OuterForumModel> forumList;
    Context context;

    public OuterForumListAdapter(List<OuterForumModel> forumList, Context context) {
        this.forumList = forumList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return forumList.size();
    }
    @Override
    public OuterForumModel getItem(int position) {
        return forumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout = LayoutInflater.from(context).inflate(R.layout.outer_forum_list_layout, parent, false);
        TextView forumName = (TextView) layout.findViewById(R.id.forumName);
        forumName.setText(forumList.get(position).getForumTitle());
        return layout;
    }
}
