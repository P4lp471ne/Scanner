package com.example.scanner.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;
import java.util.Properties;

import static android.content.Context.MODE_PRIVATE;

public class PropertiesLoader {
    public static final String DEFAULT_CONFIG = "config.properties";
    private String CONFIG = "preferences";
    private static PropertiesLoader instance;
    private Context context;
    private Properties properties;

    private PropertiesLoader() {
        properties = new Properties();
    }

    private void loadPropeties(){
        try {
            properties.load(new FileInputStream(CONFIG));
        }
        catch (IOException e) {
            AssetManager assetManager = context.getAssets();
            try {
                InputStream inputStream = assetManager.open(DEFAULT_CONFIG);
                properties.load(inputStream);
                properties.store(new FileOutputStream(CONFIG),"");
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public static PropertiesLoader getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (PropertiesLoader.class) {
            if (instance == null) {
                instance = new PropertiesLoader();
            }
            return instance;
        }
    }

    public String getProperty(String key) throws IOException {
        return (String) properties.get(key);
    }

    public void setProperty(String key, String value) throws IOException {
        properties.put(key, value);
        properties.store(new FileOutputStream(CONFIG),"");
    }

    public void setContext(Context context) {
        this.context = context;
        CONFIG = context.getDataDir().getAbsolutePath() + File.separator + DEFAULT_CONFIG;
    }

    public void load(){
        loadPropeties();
    }
}
