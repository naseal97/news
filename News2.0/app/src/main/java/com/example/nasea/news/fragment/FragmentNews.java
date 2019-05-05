package com.example.nasea.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.nasea.news.MainActivity;
import com.example.nasea.news.R;
import com.example.nasea.news.WebViewActivity;
import com.example.nasea.news.adapter.NewsAdapter;
import com.example.nasea.news.async.DownloadAsync;
import com.example.nasea.news.async.XMLAsync;
import com.example.nasea.news.database.AppDatabaseSave;
import com.example.nasea.news.manager.FileManager;
import com.example.nasea.news.model.News;
import com.example.nasea.news.ultis.DialogUtils;


import java.io.File;
import java.util.ArrayList;


public class FragmentNews extends BaseFragment implements SearchView.OnQueryTextListener, XMLAsync.ParserXMLCallback, NewsAdapter.ClickListener {
    public RecyclerView lvNews;
    private NewsAdapter adapter;
    private ArrayList<News> dataNews;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new NewsAdapter(getParent());
        lvNews = findViewById(R.id.lv_news);
        lvNews.setAdapter(adapter);

        adapter.setOnItemClickListener(this);

    }

    @Override
    public String getTitle() {
        return "TIN TỨC";
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getParent().getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.
                findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if (s.isEmpty()) {
            return false;
        }
        DialogUtils.show(getParent());
        XMLAsync async = new XMLAsync(this);
        async.execute(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void onParserFinish(ArrayList<News> arr) {
        DialogUtils.dissmiss();
        adapter.setData(arr);
        dataNews = arr;
    }


    @Override
    public void onItemClick(int position, View v) {
        Log.i("onItemClick","position "+position);
        String link = adapter.getData().get(position).getLink();
        String title = adapter.getData().get(position).getTitle();
        String desc = adapter.getData().get(position).getDesc();
        String image = adapter.getData().get(position).getImage();
        String pubDate = adapter.getData().get(position).getPubDate();

        Intent intent = new Intent(getActivity(),WebViewActivity.class);

        intent.putExtra(MainActivity.REQUEST_LINK,link);
        intent.putExtra(MainActivity.REQUEST_TITLE,title);
        intent.putExtra(MainActivity.REQUEST_DESC,desc);
        intent.putExtra(MainActivity.REQUEST_IMAGE,image);
        intent.putExtra(MainActivity.REQUEST_PUBDATE,pubDate);
        this.startActivity(intent);

//        byExtra(adapter.getData().get(position).getLink());
    }

//    public void byExtra(String link) {
//        Intent intent = new Intent(getActivity(), WebViewActivity.class);
//        intent.putExtra(MainActivity.REQUEST_LINK, link);
//        this.startActivity(intent);
//    }

}
