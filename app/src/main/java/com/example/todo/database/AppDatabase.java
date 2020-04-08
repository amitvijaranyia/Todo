package com.example.todo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Todo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "todolist";
    private static final Object LOCK = new Object();
    private static AppDatabase db;

    public abstract TodoDao todoDao();

    public static AppDatabase getInstance(Context context){
        if(db == null){
            synchronized (LOCK){
                db = Room.databaseBuilder(
                                context,
                                AppDatabase.class,
                                DATABASE_NAME
                        )
                        .build();
            }
        }
        return db;
    }

}
