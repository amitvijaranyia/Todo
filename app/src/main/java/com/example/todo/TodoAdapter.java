package com.example.todo;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Utils.DateAndTimeUtils;
import com.example.todo.Utils.TodoUtils;
import com.example.todo.database.Todo;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoAdapterViewHolder> {

    private final Context mContext;

    private final TodoListOnClickHandler mOnClickHandler;

    private List<Todo> mTodoList;

    public TodoAdapter(Context mContext, TodoListOnClickHandler mOnClickHandler) {
        this.mContext = mContext;
        this.mOnClickHandler = mOnClickHandler;
    }

    public interface TodoListOnClickHandler{
        void onClick(int elementId);
    }

    public List<Todo> getTodoList(){
        return mTodoList;
    }

    @NonNull
    @Override
    public TodoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) parent
                .getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.list_todo, parent, false);
        return new TodoAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapterViewHolder holder, int position) {
        Todo currentTodo = mTodoList.get(position);

        holder.tvHeading.setText(currentTodo.getHeading());
        if(TextUtils.isEmpty(currentTodo.getDescription())){
            holder.tvDescription.setVisibility(View.GONE);
        }
        holder.tvDescription.setText(currentTodo.getDescription());
        holder.tvUpdatedAt.setText(DateAndTimeUtils.getDateToDisplayInMainActivity(currentTodo.getUpdatedAt()));
        holder.tvPriority.setText(TodoUtils.getPriorityText(currentTodo.getPriority()));

        int priorityBachGroundColour = TodoUtils.getPriorityBackgroundColour(mContext, currentTodo.getPriority());
        GradientDrawable drawable = (GradientDrawable) holder.tvPriority.getBackground();
        drawable.setColor(priorityBachGroundColour);
    }

    @Override
    public int getItemCount() {
        if(mTodoList == null) return 0;
        return mTodoList.size();
    }

    class TodoAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvHeading, tvDescription, tvUpdatedAt, tvPriority;
        public TodoAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHeading = itemView.findViewById(R.id.tvHeading);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvUpdatedAt = itemView.findViewById(R.id.tvLastUpdated);
            tvPriority = itemView.findViewById(R.id.tvPriority);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = mTodoList.get(getAdapterPosition()).getId();
            mOnClickHandler.onClick(id);
        }
    }

    public void setTodolist(List<Todo> todolist){
        this.mTodoList = todolist;
        notifyDataSetChanged();
    }

}
