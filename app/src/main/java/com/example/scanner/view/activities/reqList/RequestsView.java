package com.example.scanner.view.activities.reqList;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.scanner.R;
import com.example.scanner.logic.datatypes.responseTypes.ShortRequestDescription;
import com.example.scanner.view.ViewManager;
import com.example.scanner.view.activities.AbstractViewHolder;
import com.example.scanner.view.activities.reqList.ReqListAdapter;

import java.util.ArrayList;
import java.util.List;

public class RequestsView extends AbstractViewHolder {
    private List<ShortRequestDescription> data = new ArrayList<>();
    private ReqListAdapter adapter;

    public RequestsView(ViewManager viewManager) {
        super(viewManager);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void makeView() {

        view = LayoutInflater.from(getApp().getApplicationContext())
                .inflate(R.layout.req_list_view_activity, null);
//        listView = view.findViewById(R.id.req_list);

        Button exit = view.findViewById(R.id.exitButton);
        exit.setOnClickListener((e) -> {
            System.exit(0);
        });

        Button refresh = view.findViewById(R.id.update);
//        refresh
        refresh.setOnClickListener(this::refresh);

//        adapter = new ReqListAdapter(getApp().getApplicationContext(),
//                R.layout.req_item, data);
//        listView.setAdapter(adapter);

        setListView();

        refresh(view);
    }

    private void refresh(View view) {
        manager.requestProductRequests(this::setItems);
    }

    private void setListView() {
        listView = inflater.inflate(R.layout.list_view, null).findViewById(R.id.listview);
        ((LinearLayout)view.findViewById(R.id.start_view)).addView(listView);
        adapter = new ReqListAdapter(getApp().getApplicationContext(),
                R.layout.req_item, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                manager.productsView(String.valueOf(data.get(position).getId()));
            }
        }
        ));
    }

    private void setItems(List<ShortRequestDescription> requestsList) {
        if (adapter == null) return;
        data.clear();
        data.addAll(requestsList);
        getApp().runOnUiThread(() -> {
            adapter.notifyDataSetChanged();
        });

    }
}
