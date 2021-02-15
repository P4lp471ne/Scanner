package com.example.scanner.view.activities.reqData;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.scanner.App;
import com.example.scanner.MainActivity;
import com.example.scanner.R;
import com.example.scanner.logic.datatypes.responseTypes.Product;
import com.example.scanner.logic.datatypes.responseTypes.ProductRequestLine;
import com.example.scanner.logic.datatypes.responseTypes.RequestData;
import com.example.scanner.view.Consumer;
import com.example.scanner.view.ViewManager;
import com.example.scanner.view.activities.AbstractViewHolder;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductsView extends AbstractViewHolder implements Consumer {
    private Button btn;
    private List<ProductRequestLine> lines = new ArrayList<>();
    private ProductsListAdapter adapter;
    private String id;
    private boolean pagerReady;
    private Switch doubleScanSwitch;
    private RequestData data;
    private Button refresh;
    private ViewPager pager;

    public ProductsView(ViewManager viewManager, String id, AppCompatActivity app) {
        super(viewManager);
        this.id = id;
        setApp(new App() {
            @Override
            public AppCompatActivity get() {
                return app;
            }

            @Override
            public void setBackEnabled(boolean enabled) {
                ((MainActivity) app).setBackEnabled(enabled);
            }

            @Override
            public Intent createIntent(Class<?> activityClass) {
                return null;
            }
        });
        inflater = (LayoutInflater) app.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        makeView();
        refresh();
        this.app.setBackEnabled(true);
    }

    ProductsView(ViewManager viewManager, List<Product> products) {
        super(viewManager);
    }

    @Override
    protected void makeView() {
        getApp().setContentView(R.layout.req_data_view_activity);
        view = getApp().findViewById(R.id.req_data_view);
        btn = view.findViewById(R.id.control);
        pager = view.findViewById(R.id.pager);
//        SimpleFragment infoFragment = new SimpleFragment(getInfo());
//        SimpleFragment listFragment = new SimpleFragment(getListView());

        setupViewPager(pager);
        setSwitch();
        setRefresh();
        view.requestFocus();
    }

    private View getInfo() {
        View infoView = inflater.inflate(R.layout.req_info_view, null)
                .findViewById(R.id.req_info_view);

        ((TextView) infoView.findViewById(R.id.req_name))
                .setText(data.getNameView());
        ((TextView) infoView.findViewById(R.id.warehouseIn))
                .setText(data.getWarehouseIn().getNameView());
        ((TextView) infoView.findViewById(R.id.warehouseOut))
                .setText(data.getWarehouseOut().getNameView());
        ((TextView) infoView.findViewById(R.id.status))
                .setText(data.getStatus() == 0 ? "new" : "in_progress");

        return infoView;
    }

    private void setRefresh() {
        refresh = (Button) view.findViewById(R.id.action);
        refresh.setOnClickListener(this::refreshBtnListener);
    }

    private void disableRefresh() {
        refresh.setEnabled(false);
        refresh.setClickable(false);
    }

    private void enableRefresh() {
        refresh.setEnabled(true);
        refresh.setClickable(true);
    }

    private void setupViewPager(ViewPager viewPager) {
        if (data == null) return;
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getApp().getSupportFragmentManager());
        adapter.addFragment(new SimpleFragment(getInfo()), "Info");
        adapter.addFragment(new SimpleFragment(getListView()), "List");
        viewPager.setAdapter(adapter);
        pagerReady = true;
    }

    private void refreshBtnListener(View view) {
        AnimationDrawable d = (AnimationDrawable) refresh.getCompoundDrawables()[0];
        d.start();
        refresh();
        d.stop();
    }

    private void start() {
        getApp();
        manager.start(id, this::setControlButtonToCancel);
        this.app.setBackEnabled(false);
    }

    private void finish() {
        manager.finish(id);
    }

    private void cancel() {
        manager.cancel(id, this::setControlButtonToStart);
        doubleScanSwitch.setChecked(false);
        refresh();
        this.app.setBackEnabled(true);
    }

    private void setControlButtonToCancel() {
        getApp().runOnUiThread(() -> {
            btn.setText("Cancel");
            btn.setBackgroundColor((new Color()).parseColor("#FF0000"));
            disableRefresh();
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel();
                }
            });
        });
    }

    private void setControlButtonToStart() {
        if (data != null && data.getStatus() == 0) getApp().runOnUiThread(() -> {
            btn.setText("Start");
            btn.setBackgroundColor((new Color()).parseColor("#00FF00"));
            enableRefresh();
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        start();
                }
            });
        });
        if (data != null && data.getStatus() != 0) {
            System.out.println(data.getStatus());
            setControlButtonToCancel();
        }
    };

    private void setControlButtonToFinish() {
        getApp().runOnUiThread(() -> {
            btn.setText("Finish");
            disableRefresh();
            btn.setBackgroundColor((new Color()).parseColor("#FFFF00"));
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manager.finish(id);
                }
            });
        });
    }

    private View getListView() {
        listView = inflater.inflate(R.layout.list_view, null).findViewById(R.id.listview);
        adapter = new ProductsListAdapter(getApp().getApplicationContext(),
                R.layout.prod_item, lines);
        listView.setAdapter(adapter);

        return listView;
    }

    private void setSwitch() {
        doubleScanSwitch = view.findViewById(R.id.doubleScanSwitch);
        doubleScanOff();
        doubleScanSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) doubleScanOn();
            else doubleScanOff();
        });
    }

    private void doubleScanOn() {
        manager.doublescan(true);
        doubleScanSwitch.setText("double\nscan");
    }

    private void doubleScanOff() {
        manager.doublescan(false);
        doubleScanSwitch.setText("single\nscan");
    }

    private void setData(RequestData data) {
        this.data = data;
        setItems(data.getLines());
        getApp().runOnUiThread(() -> {
            setControlButtonToStart();
            getApp().getWindow().setLocalFocus(true, true);
        });
        if (!pagerReady)
            getApp().runOnUiThread(() -> {
                setupViewPager(pager);
            });
//        upd.run();
    }

    private void setItems(List<ProductRequestLine> products) {
        if (adapter == null) adapter = new ProductsListAdapter(getApp().getApplicationContext(),
                R.layout.prod_item, lines);
        lines.clear();
        lines.addAll(products);
        getApp().runOnUiThread(() -> {
            adapter.notifyDataSetChanged();
        });
    }

    private void refresh() {
        manager.requestProducts(id, this::setData);
    }

    @Override
    public void notifyScan() {
        view.requestFocus();
        notify("Scan second part please");
        view.requestFocus();
    }

    @Override
    public boolean listen(@Nullable Product product) {
        if (null == product) {
            getApp().runOnUiThread(this::failDialog);
            return false;
        }
        for (ProductRequestLine line : lines) {
            if (line.getProduct().getProductCode().equals(product.getProductCode())) {
                int quantity = line.getQuantity();
                if (quantity > 0) line.decreaseQuantity();
                else {
                    getApp().runOnUiThread(this::notInListDialog);
                    return false;
                }
                if (line.getQuantity() == 0) {
                    lines.remove(line);
                    lines.add(line);
                }
                getApp().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                    getApp().runOnUiThread(() -> {
                        successDialog(product.getProductCode());});
                });
                if (lines.stream().allMatch(lin -> lin.getQuantity() == 0))
                    setControlButtonToFinish();

                return true;
            }
        }
        notInListDialog();
        return false;
    }

    private void notInListDialog() {
        notify("Product is not in list");
    }

    private void successDialog(String title) {
        notify("Scanned" + title);
    }

    private void notify(String s) {
        Toast toast = Toast.makeText(getApp().getApplicationContext(), s, Toast.LENGTH_LONG);
        toast.show();
    }

    private void failDialog() {
        notify("Fail");
    }
}
