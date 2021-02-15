package com.example.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.scanner.logic.Logic;
import com.example.scanner.utils.PropertiesLoader;
import com.example.scanner.view.ViewManager;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements App {
    ViewManager viewManager;
    Logic logic;
    boolean backEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PropertiesLoader.getInstance().setContext(getApplicationContext());
        PropertiesLoader.getInstance().load();
//        setContentView(R.layout.activity_main);

        viewManager = new ViewManager(getApplicationContext());
        viewManager.setApp(this);
        try {
            logic = new Logic();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        viewManager.setLogic(logic);

        viewManager.startScreen();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getCharacters() != null && event.getAction() == KeyEvent.ACTION_MULTIPLE)
            viewManager.focus();
            logic.scan(event.getCharacters());
        return super.dispatchKeyEvent(event);
    }

    @Override
    public AppCompatActivity get() {
        return this;
    }

    @Override
    public void setBackEnabled(boolean enabled) {
        backEnabled = enabled;
    }

    @Override
    public Intent createIntent(Class<?> activityClass) {
        return new Intent(MainActivity.this, activityClass);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onBackPressed() {
        if (backEnabled) viewManager.startScreen();
    }
}