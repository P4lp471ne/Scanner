package com.example.scanner;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public interface App {
    AppCompatActivity get();
    void setBackEnabled(boolean enabled);

    Intent createIntent(Class<?> activityClass);
}
