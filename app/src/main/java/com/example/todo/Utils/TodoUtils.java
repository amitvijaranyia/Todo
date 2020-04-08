package com.example.todo.Utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.todo.R;

public class TodoUtils {

    private TodoUtils(){}

    private static final String HIGH_PRIORITY = "high";
    private static final String MEDIUM_PRIORITY = "medium";
    private static final String LOW_PRIORITY = "low";

    public static int getPriorityBackgroundColour(Context context, String priority){
        if(priority.equalsIgnoreCase(HIGH_PRIORITY)){
            return ContextCompat.getColor(context, R.color.colorHighPriority);
        }
        else if(priority.equalsIgnoreCase(MEDIUM_PRIORITY)){
            return ContextCompat.getColor(context, R.color.colorMediumPriority);
        }
        else{
            return ContextCompat.getColor(context, R.color.colorLowPriority);
        }
    }

    public static String getPriorityText(String priority){
        if(priority.equalsIgnoreCase(HIGH_PRIORITY)){
            return "H";
        }
        else if(priority.equalsIgnoreCase(MEDIUM_PRIORITY)){
            return "M";
        }
        else{
            return "L";
        }
    }

}
