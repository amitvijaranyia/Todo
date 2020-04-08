package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.todo.database.AppDatabase;
import com.example.todo.database.Todo;

public class AddTodoActivity extends AppCompatActivity {
    private static final String TAG = "tag";

    EditText etHeading, etDescription;
    Button btnAdd;

    private static final String HIGH_PRIORITY = "high";
    private static final String MEDIUM_PRIORITY = "medium";
    private static final String LOW_PRIORITY = "low";

    private AppDatabase db;

    boolean intentHasID;
    int elementId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        etHeading = findViewById(R.id.etHeading);
        etDescription = findViewById(R.id.etTodo);
        btnAdd = findViewById(R.id.btnAdd);

        db = AppDatabase.getInstance(AddTodoActivity.this);

        Intent intentThatStartedThisActivity = getIntent();
        if(intentThatStartedThisActivity != null && intentThatStartedThisActivity.hasExtra(MainActivity.TODO_ID)){
            intentHasID = true;
            elementId = intentThatStartedThisActivity.getIntExtra(MainActivity.TODO_ID, 0);
            setUpUpdateActivity(elementId);
        }
        else{
            intentHasID = false;
        }


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etHeading.getText().toString()) && TextUtils.isEmpty(etDescription.getText().toString())){
                    Toast.makeText(
                            AddTodoActivity.this,
                            "Lazy!! You Want to do nothing.",
                            Toast.LENGTH_LONG
                    ).show();
                }
                else{
                    if(!intentHasID) {
                        insertTodoIntoDatabase();
                    }
                    else{
                        updateTodoIntoDatabase(elementId);
                    }
                }
            }
        });

    }

    private void setUpUpdateActivity(final int elementId){
        ThreadExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final Todo todoThatNeedsTobeUpdated = db.todoDao().loadTodoById(elementId);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        etHeading.setText(todoThatNeedsTobeUpdated.getHeading());
                        etDescription.setText(todoThatNeedsTobeUpdated.getDescription());
                        btnAdd.setText("Update");

                        switch (todoThatNeedsTobeUpdated.getPriority()){
                            case HIGH_PRIORITY :{
                                RadioButton rbHighPriority = findViewById(R.id.rbHighPriority);
                                rbHighPriority.setChecked(true);
                                break;
                            }
                            case MEDIUM_PRIORITY :{
                                RadioButton rbMediumPriority = findViewById(R.id.rbMediumPriority);
                                rbMediumPriority.setChecked(true);
                                break;
                            }
                            case LOW_PRIORITY :{
                                RadioButton rbLowPriority = findViewById(R.id.rbLowPriority);
                                rbLowPriority.setChecked(true);
                                break;
                            }
                        }
                    }
                });
            }
        });
    }

    private void insertTodoIntoDatabase() {
        String heading = etHeading.getText().toString();
        String description = etDescription.getText().toString();
        String priority = getPriority();
        long lastUpdatedAt = System.currentTimeMillis();
        final Todo newTodo = new Todo(heading, description, priority, lastUpdatedAt);

        ThreadExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                long id = db.todoDao().insertTodo(newTodo);
                Log.d(TAG, "insertTodoIntoDatabase: " + "id = " + id);
            }
        });
        finish();
    }

    private void updateTodoIntoDatabase(int elementId){
        String heading = etHeading.getText().toString();
        String description = etDescription.getText().toString();
        String priority = getPriority();
        long lastUpdatedAt = System.currentTimeMillis();
        final Todo newTodo = new Todo(elementId, heading, description, priority, lastUpdatedAt);

        ThreadExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int n = db.todoDao().updateTodo(newTodo);
                Log.d(TAG, "run: " + "number of todo updated = " + n);
            }
        });
        finish();
    }

    public String getPriority() {
        int id = ((RadioGroup)findViewById(R.id.radioGroup)).getCheckedRadioButtonId();

        // Check which radio button was clicked
        switch(id) {
            case R.id.rbHighPriority:
                return HIGH_PRIORITY;
            case R.id.rbMediumPriority:
                return MEDIUM_PRIORITY;
            case R.id.rbLowPriority:
                return LOW_PRIORITY;
        }

        return LOW_PRIORITY;
    }
}
