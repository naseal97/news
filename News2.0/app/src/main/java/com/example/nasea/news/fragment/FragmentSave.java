package com.example.nasea.news.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.nasea.news.MainActivity;
import com.example.nasea.news.R;
import com.example.nasea.news.WebViewActivity;
import com.example.nasea.news.adapter.NewsAdapter;
import com.example.nasea.news.connection.ConnectionDetector;
import com.example.nasea.news.database.AppDatabaseFavorite;
import com.example.nasea.news.database.AppDatabaseSave;
import com.example.nasea.news.model.News;

import java.util.List;

public class FragmentSave extends BaseFragment implements NewsAdapter.ClickListener {
    private RecyclerView lvSavedNews;
    private NewsAdapter adapter;
    private List<News> data;
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_saves;
    }

    @Override
    public String getTitle() {
        return "ĐÃ LƯU";
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        Log.e(getClass().getName(),"onActivityCreated");
        getData();

        adapter.setOnItemClickListener(this);
    }
    private void initViews() {
        lvSavedNews = getActivity().findViewById(R.id.lv_saved_news);
        adapter = new NewsAdapter(getActivity());
        lvSavedNews.setAdapter(adapter);

    }
    public void getData() {
        data = AppDatabaseSave.getInstance(getContext()).getNewsDao().getAll();
        adapter.setData(data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onItemClick(int position, View v) {

    }
}
