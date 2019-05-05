package com.example.nasea.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.nasea.news.MainActivity;
import com.example.nasea.news.R;
import com.example.nasea.news.WebViewActivity;
import com.example.nasea.news.adapter.NewsAdapter;
import com.example.nasea.news.connection.ConnectionDetector;
import com.example.nasea.news.database.AppDatabaseFavorite;
import com.example.nasea.news.model.News;

import java.util.List;

public class FragmentFavorite extends BaseFragment  {
    private RecyclerView lvFavoriteNews;
    private NewsAdapter adapter;
    private List<News> data;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_favorite;
    }

    @Override
    public String getTitle() {
        return "YÊU THÍCH";
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvFavoriteNews = getActivity().findViewById(R.id.lv_favorite_news);
        adapter = new NewsAdapter(getContext());
        lvFavoriteNews.setAdapter(adapter);

    }


}
