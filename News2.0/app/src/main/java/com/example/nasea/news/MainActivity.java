package com.example.nasea.news;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.nasea.news.adapter.PageAdapter;
import com.example.nasea.news.fragment.FragmentFavorite;
import com.example.nasea.news.fragment.FragmentNews;
import com.example.nasea.news.fragment.FragmentSave;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    public static final String REQUEST_LINK ="request.link" ;
    public static final String REQUEST_PATH ="request.path" ;
    public static final String REQUEST_POSITION = "request.position";
    public static final String REQUEST_TITLE = "request.title";
    public static final String REQUEST_DESC = "request.desc";
    public static final String REQUEST_IMAGE = "request.image";
    public static final String REQUEST_PUBDATE = "request.pubdate";
//this is comment test git
    private FragmentNews fmNews = new FragmentNews();
    private FragmentFavorite fmFavorite = new FragmentFavorite();
    private FragmentSave fmSave = new FragmentSave();
    private ViewPager pager;
    private PageAdapter pageAdapter;
    private TabLayout tabTitle;

    public FragmentNews getFmNews() {
        return fmNews;
    }

    public FragmentFavorite getFmFavorite() {
        return fmFavorite;
    }

    public FragmentSave getFmSave() {
        return fmSave;
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }


    private void initView() {
        pager = findViewById(R.id.pager);
        pageAdapter = new PageAdapter(getSupportFragmentManager(),fmNews,fmSave,fmFavorite);
        pager.setAdapter(pageAdapter);
        tabTitle=findViewById(R.id.tab_title);
        tabTitle.setupWithViewPager(pager);

        pager.setCurrentItem(0);
        pager.addOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        Log.e(getClass().getName(),"onPageSelected "+i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onBackPressed() {
        if(pager.getCurrentItem()==0) {

            super.onBackPressed();
        }else {
            pager.setCurrentItem(0);
        }
    }
}
