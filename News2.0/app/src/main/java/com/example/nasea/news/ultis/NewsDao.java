package com.example.nasea.news.ultis;

import android.arch.persistence.room.Dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.nasea.news.model.News;

import java.util.List;


@Dao
public interface NewsDao {
    @Query("SELECT * FROM News")
    List<News> getAll();


    @Insert
    void insert(News... news);
    @Update
    void update(News... news);
    @Delete
    void delete(News... news);
}