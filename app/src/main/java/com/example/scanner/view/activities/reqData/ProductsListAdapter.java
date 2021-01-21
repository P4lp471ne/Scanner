package com.example.scanner.view.activities.reqData;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.scanner.R;
import com.example.scanner.logic.datatypes.responseTypes.ProductRequestLine;

import java.util.List;

public class ProductsListAdapter extends ArrayAdapter<ProductRequestLine> {
    public ProductsListAdapter(@NonNull Context context, int resource,
                               @NonNull List<ProductRequestLine> objects) {
        super(context, resource, objects);

    }

    @Override
    public View getView(int position, View prodView, ViewGroup parent) {
        ProductRequestLine req = getItem(position);

        if (prodView == null) {
            prodView = LayoutInflater.from(getContext())
                    .inflate(R.layout.prod_item, null);
        }

        ((TextView) prodView.findViewById(R.id.prod_name_view))
                .setText(req.getProduct().getNameView());
        ((TextView) prodView.findViewById(R.id.product_code))
                .setText(req.getProduct().getProductCode());
        ((TextView) prodView.findViewById(R.id.quantity))
                .setText(String.valueOf(req.getQuantity()));

        if (req.getQuantity() == 0){
            prodView.setBackgroundColor(Color.red(0));
        }

        return prodView;
    }
}
