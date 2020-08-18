package com.example.myproject;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter extends BaseAdapter {
    private LayoutInflater articleInflater;
    private List<ArticleModel> articleList;
    Context context;


    public ArticleAdapter(Context activity, List<ArticleModel> list) {
        articleInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.articleList = list;
    }


    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public Object getItem(int i) {
        return articleList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View listview;
        listview = articleInflater.inflate(R.layout.article_list, null);
        ImageView imageview = listview.findViewById(R.id.resim);

        TextView header = listview.findViewById(R.id.header);
        TextView text = listview.findViewById(R.id.text);

        ArticleModel articleModel = articleList.get(i);

        header.setText(articleModel.getHeader());
        text.setText(articleModel.getTime() + " | " + articleModel.getAuthor());
        Picasso.get().load(Uri.parse(articleModel.getPhoto())).fit().placeholder(R.drawable.giphy).into(imageview);
        articleModel.setBody(articleModel.getBody());


        return listview;
    }
}
