package com.example.scanner.view.activities;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.scanner.R;
import com.example.scanner.logic.datatypes.responseTypes.Product;
import com.example.scanner.logic.datatypes.responseTypes.ProductRequestLine;
import com.example.scanner.logic.datatypes.responseTypes.RequestData;
import com.example.scanner.view.Consumer;
import com.example.scanner.view.ViewManager;
import com.example.scanner.view.activities.reqData.SimpleFragment;
import com.example.scanner.view.activities.reqData.ProductsListAdapter;
import com.example.scanner.view.activities.reqData.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductsView extends AbstractViewHolder implements Consumer {
    private List<ProductRequestLine> lines = new ArrayList<>();
    private ProductsListAdapter adapter;
    private String id;
    private Switch doubleScanSwitch;
    private RequestData data;
    private Runnable upd;
    Button btn;
    private Button refresh;
    private ViewPager pager;

    public ProductsView(ViewManager viewManager, String id) {
        super(viewManager);
        this.id = id;
        refresh();
    }

    public void setUpd(Runnable upd){
        this.upd = upd;
    }

    ProductsView(ViewManager viewManager, List<Product> products) {
        super(viewManager);
    }

    @Override
    protected void makeView() {
        view = inflater.inflate(R.layout.req_data_view_activity, null)
                .findViewById(R.id.req_data_view);
        btn = view.findViewById(R.id.control);
        pager = view.findViewById(R.id.pager);
//        getInfo();
//        getListView();

        setupViewPager(pager);
        setSwitch();
        setControlButtonToStart();
        setRefresh();
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
                .setText(data.getStatus() == 0? "new":"in_progress");

        return infoView;
    }

    private void setRefresh(){
        refresh=(Button)view.findViewById(R.id.action);
        refresh.setOnClickListener(this::refreshBtnListener);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getApp().getSupportFragmentManager());
        adapter.addFragment(new SimpleFragment(getInfo()), "Info");
        adapter.addFragment(new SimpleFragment(getListView()), "List");
        viewPager.setAdapter(adapter);
    }

    private void refreshBtnListener(View view) {
        AnimationDrawable d=(AnimationDrawable) refresh.getCompoundDrawables()[0];
        d.start();
        refresh();
    }

    private void start(){
        manager.start(id, this::setControlButtonToCancel);
    }

    private void finish(){}
    private void cancel(){}

    private void setControlButtonToCancel(){
        btn.setText("Cancel");
        btn.setBackgroundColor((new Color()).parseColor("#FF0000"));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.cancel(id);
            }
        });
    }

    private void setControlButtonToStart(){
        btn.setText("Start");
        btn.setBackgroundColor((new Color()).parseColor("#00FF00"));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    private void setControlButtonToFinish(){
        btn.setText("Finish");
        btn.setBackgroundColor((new Color()).parseColor("#FFFF00"));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.finish(id);
            }
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

    private void setData(RequestData data){
        this.data = data;
        setItems(data.getLines());
        upd.run();
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

    void refresh() {
        manager.requestProducts(id, this::setData);
    }

    @Override
    public void listen(Product product) {
        for (ProductRequestLine line: lines) {
            if (line.getProduct().equals(product)){
                int quantity = line.getQuantity();
                if (quantity > 0) line.setQuantity(quantity - 1);
                if (line.getQuantity() == 0) {
                    lines.remove(line);
                    lines.add(line);
                }
                adapter.notifyDataSetChanged();
                if (lines.stream().allMatch(lin -> lin.getQuantity() == 0))
                    setControlButtonToFinish();
            }
        }
    }
}
