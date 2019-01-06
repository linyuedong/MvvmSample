package com.example.library.binding.viewadapter;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.library.binding.command.ResponseCommand;


public class BottomNavigationViewAdater {

    @BindingAdapter(value = {"onItemSelectedCommand"},requireAll = false)
    public static void setOnNavigationItemSelectedCommand(BottomNavigationView view, final ResponseCommand<MenuItem,Boolean> command){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    return command.execute(menuItem);

            }
        });

    }
}
