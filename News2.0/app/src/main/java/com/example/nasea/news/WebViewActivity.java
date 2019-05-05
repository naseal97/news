package com.example.nasea.news;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nasea.news.adapter.NewsAdapter;
import com.example.nasea.news.async.DownloadAsync;
import com.example.nasea.news.connection.ConnectionDetector;
import com.example.nasea.news.database.AppDatabaseSave;
import com.example.nasea.news.manager.FileManager;
import com.example.nasea.news.model.News;
import com.example.nasea.news.ultis.DialogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener, DownloadAsync.DownloadCallback {
    public static final String EXTRA_URL = "extra.url";
    public static final String EXTRA_POSITION ="extra.position" ;
    private WebView webView;
    private FloatingActionButton fabDown;
    private NewsAdapter adapter;
    private ArrayList<News> data;
    private ArrayList<File> dataFile;
    private String currentPath;
    private ProgressBar pbDownload;
    private News news;

    private FileManager manager;
    private List<File> arr;

    private String link, path, title, desc, image, pubDate;

    private final String[] PERMISSION_LIST = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
//        manager = new FileManager();
        try {
            getSupportActionBar().hide();
            webView = findViewById(R.id.webView);
            webView.setWebViewClient(new myBrowser());
            fabDown = findViewById(R.id.fab_download);
            pbDownload = findViewById(R.id.pb_download);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showUrl(getExtra());

//        if (checkPermission()) {
//            readFile(manager.path);
//        } else {
//            requestPermissions(PERMISSION_LIST, 0);
//        }
        fabDown.setOnClickListener(this);
    }

/*    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermission()) {
            readFile(manager.path);
        } else {
            finish();
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String p : PERMISSION_LIST) {
            // PackageManager.PERMISSION_GRANTED = accept
            // PackageManager.PERMISSION_DENIED = Denied
            int accept = checkSelfPermission(p);
            if (accept == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    private void readFile(String path) {
        currentPath = path;
        arr = manager.getFile(path);
        adapter.setDataFile(arr);
    }*/


    private void showUrl(String url) {
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }

    private String getExtra() {
        Intent intent = this.getIntent();
        link = intent.getStringExtra(MainActivity.REQUEST_LINK);
        Log.i("getExtra","link "+link);
        path = intent.getStringExtra(MainActivity.REQUEST_PATH);
        Log.i("getExtra","path "+path);
        title = intent.getStringExtra(MainActivity.REQUEST_TITLE);
        Log.i("getExtra","title "+title);
        desc = intent.getStringExtra(MainActivity.REQUEST_DESC);
        Log.i("getExtra","desc "+desc);
        image = intent.getStringExtra(MainActivity.REQUEST_IMAGE);
        Log.i("getExtra","image "+image);
        pubDate = intent.getStringExtra(MainActivity.REQUEST_PUBDATE);
        Log.i("getExtra","pubDate "+pubDate);




        ConnectionDetector connectionDetector = new ConnectionDetector(this);
        if (connectionDetector.isInternetAvailble())
            return link;
        return "file:///" + path;
    }

    @Override
    public void onClick(View v) {

        String link = getExtra();
        Log.e("onClick","link: "+link);

        String path = System.currentTimeMillis()+".html";
        Log.e("onClick","path: "+path);
        DownloadAsync downloadAsync = new DownloadAsync(this);
        downloadAsync.execute(link,path);

        boolean isInsert = false;
        if(news == null){
            isInsert=true;
            news = new News();
        }
        news.setLink(link);
        news.setDesc(desc);
        news.setImage(image);
        news.setPubDate(pubDate);
        news.setTitle(title);
        if(isInsert){
            AppDatabaseSave.getInstance(this).getNewsDao().insert(news);
        }else {
            AppDatabaseSave.getInstance(this).getNewsDao().update(news);
        }
    }

    @Override
    public void updatePercent(int percent) {

    }

    @Override
    public void downloadFinish(String path) {

    }


    private class myBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String Url) {
            view.loadUrl(Url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            DialogUtils.showCancel(WebViewActivity.this);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            DialogUtils.dissmiss();
        }
    }
}
