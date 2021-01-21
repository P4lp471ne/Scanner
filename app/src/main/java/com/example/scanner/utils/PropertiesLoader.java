package com.example.scanner.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private static PropertiesLoader instance;
    private Context context;

    private PropertiesLoader() {
    }

    public static PropertiesLoader getInstance() {
        PropertiesLoader result = instance;
        if (result != null) {
            return result;
        }
        synchronized (PropertiesLoader.class) {
            if (instance == null) {
                instance = new PropertiesLoader();
            }
            return instance;
        }
    }

    public String getProperty(String key) throws IOException {
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("config.properties");
        properties.load(inputStream);
        return properties.getProperty(key);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
