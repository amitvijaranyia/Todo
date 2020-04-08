package com.example.todo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todo.database.AppDatabase;
import com.example.todo.database.Todo;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private LiveData<List<Todo>> todoList;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application.getApplicationContext());
        Log.d("tag", "TodoViewModel: " + "making call to database");
        todoList = db.todoDao().loadAllTodos();
    }

    public LiveData<List<Todo>> getTodoList(){return todoList;}
}
