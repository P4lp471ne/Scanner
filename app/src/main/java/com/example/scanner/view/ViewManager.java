package com.example.scanner.view;

import android.content.Context;

import com.example.scanner.App;
import com.example.scanner.logic.Logic;
import com.example.scanner.view.activities.AbstractViewHolder;
import com.example.scanner.view.activities.ProductsView;
import com.example.scanner.view.activities.reqList.RequestsView;

public class ViewManager {
    private Logic logic;
    private Context context;
    private App app;
    private AbstractViewHolder gui;

    public ViewManager(Context context) {
        this.context = context;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public void doublescan(boolean mode){
        logic.setDoubleScan(mode);
    }

    public void startScreen() {
        requestsView();
    }

    public void productsView(String requestId) {
        gui = new ProductsView(this, requestId);
        logic.subscribe((Consumer) gui);
        gui.setApp(app);
            ((ProductsView) gui).setUpd(this::update);
    }

    void requestsView() {
        gui = new RequestsView(this);
        update();
    }

    private void update() {
        gui.setApp(app);
        app.get().runOnUiThread(() -> {app.get().setContentView(gui.getView());});
    }

    public void requestProducts(String id, ProductsListCallback setItems) {
        logic.requestProductRequestData(id, setItems);
    }

    private App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public void requestProductRequests(ReqListCallback setItems) {
        logic.requestRequestsList(setItems);
    }

    public void cancel(String id) {
        logic.requestCancel(id, () -> {
            startScreen();
        });
    }

    public void finish(String id){logic.requestFinish(null, this::startScreen);}

    public void start(String id, Runnable r){logic.requestStart(id, r);}
}
