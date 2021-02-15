package com.example.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scanner.utils.PropertiesLoader;

import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {
    TextView token;
    TextView url;
    Button save;
    Button exit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        try {
            bindMethods();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bindMethods() throws IOException {
        token = findViewById(R.id.tokenField);
        token.setText(PropertiesLoader.getInstance().getProperty("TOKEN"));

        url = findViewById(R.id.urlField);
        url.setText(PropertiesLoader.getInstance().getProperty("URL"));

        exit = findViewById(R.id.sett_exit);
        exit.setOnClickListener((e) -> {startActivity(new Intent(SettingsActivity.this, MainActivity.class)); });

        save = findViewById(R.id.settings_save);
        save.setText("Save");
        save.setOnClickListener((e) -> {
            try {
                PropertiesLoader.getInstance().setProperty("TOKEN", token.getText().toString());
                PropertiesLoader.getInstance().setProperty("URL", url.getText().toString());
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
