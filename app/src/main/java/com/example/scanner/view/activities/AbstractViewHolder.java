package com.example.scanner.view.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.scanner.App;
import com.example.scanner.view.ViewManager;

public abstract class AbstractViewHolder {
    protected ViewManager manager;
    protected View view;
    protected ListView listView;
    protected LayoutInflater inflater;
    protected App app;

    protected AbstractViewHolder(ViewManager viewManager) {
        this.manager = viewManager;
    }

    public View getView() {
        if (view == null) {
            makeView();
        }
        return view;
    }

    protected AppCompatActivity getApp() {
        return app.get();
    }

    public void setApp(App app) {
        this.app = app;
        inflater = LayoutInflater.from(getApp().getApplicationContext());
    }

    protected abstract void makeView();

    public void focus() {
        view.requestFocus();
    }
}
