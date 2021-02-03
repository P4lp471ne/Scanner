package com.example.scanner;

import androidx.appcompat.app.AppCompatActivity;

public interface App {
    AppCompatActivity get();
    void setBackEnabled(boolean enabled);
}
