package com.example.nasea.news.manager;

import android.os.Environment;

import java.io.File;
import java.util.List;
import java.util.Arrays;

public class FileManager {
    public String path = Environment.getExternalStorageDirectory().getPath();

    public List<File> getFile(String path){
        File f = new File(path);
        return Arrays.asList(f.listFiles());
    }
}
