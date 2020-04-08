package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.todo.database.AppDatabase;
import com.example.todo.database.Todo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TodoAdapter.TodoListOnClickHandler {

    RecyclerView rvTodos;
    FloatingActionButton fab;

    TodoAdapter mAdapter;

    AppDatabase db;

    static String TODO_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvTodos = findViewById(R.id.rvTodos);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivity(i);
            }
        });

        rvTodos.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        mAdapter = new TodoAdapter(this, this);
        rvTodos.setHasFixedSize(true);
        rvTodos.setAdapter(mAdapter);
        rvTodos.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));

        db = AppDatabase.getInstance(MainActivity.this);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int adapterPosition = viewHolder.getAdapterPosition();
                final List<Todo> todoList = mAdapter.getTodoList();
                ThreadExecutor.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        db.todoDao().deleteTodo(todoList.get(adapterPosition));
                    }
                });
            }
        }).attachToRecyclerView(rvTodos);

        refreshTodoList();
    }

    public void refreshTodoList(){
        Log.d("tag", "refreshTodoList: " + "refreshing list");
        TodoViewModel todoViewModel = ViewModelProviders.of(MainActivity.this).get(TodoViewModel.class);
        todoViewModel.getTodoList().observe(MainActivity.this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                mAdapter.setTodolist(todos);
            }
        });
    }

    @Override
    public void onClick(int elementId) {
        Intent i = new Intent(MainActivity.this, AddTodoActivity.class);
        i.putExtra(TODO_ID, elementId);
        startActivity(i);
    }
}
