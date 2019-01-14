package com.pax.mvvmsample.databinding;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.pax.mvvmsample.R;

@SuppressWarnings("ALL")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginViewModel loginViewModel = new LoginViewModel();
        activityLoginBinding.setVm(loginViewModel);




        //setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //startActivity(new Intent(LoginActivity.this,HomeActivity.class));

    }

    public  void startActivity(Class cla){
        startActivity(new Intent(LoginActivity.this,cla));
    }
}
