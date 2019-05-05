package com.example.nasea.news.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.nasea.news.model.News;
import com.example.nasea.news.ultis.NewsDao;

@Database(entities = {News.class},version = 1)
public abstract class AppDatabaseFavorite extends RoomDatabase {

    private static AppDatabaseFavorite instance = null;

    public static AppDatabaseFavorite getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context,AppDatabaseFavorite.class, "favorite_manager_1").allowMainThreadQueries().build();
        }
        return instance;
    }


    public abstract NewsDao getNewsDao();
}