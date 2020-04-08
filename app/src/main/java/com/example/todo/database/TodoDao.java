package com.example.todo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM todo")
    LiveData<List<Todo>> loadAllTodos();

    @Insert
    long insertTodo(Todo todo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateTodo(Todo todo);

    @Delete
    int deleteTodo(Todo todo);

    @Query("SELECT * FROM todo WHERE id = :id")
    Todo loadTodoById(int id);

}
