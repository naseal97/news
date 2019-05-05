package com.example.nasea.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nasea.news.R;
import com.example.nasea.news.model.News;

import java.io.File;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder>  {
    private List<News> data;
    private List<File> dataFile;
    private LayoutInflater inflater;
    private static ClickListener clickListener;


    public NewsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<News> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setDataFile(List<File> data) {
        this.dataFile = data;
        notifyDataSetChanged();
    }

    public List<News> getData() {
        return data;
    }
    public List<File> getDataFile() {
        return dataFile;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.item_news, viewGroup, false);
        return new NewsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, int i) {
        News item = data.get(i);
        Log.e("onBindViewHolder","["+i+"]: "+item.getLink());
        newsHolder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }



    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imNews;
        private TextView tvTitle;
        private TextView tvDesc;
        private TextView tvDate;
        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imNews = itemView.findViewById(R.id.im_news);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }

        public void bindData(News item){
            tvTitle.setText(item.getTitle());
            tvDate.setText(item.getPubDate());
            tvDesc.setText(item.getDesc());
            Glide.with(imNews)
                    .load(item.getImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imNews);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        NewsAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}