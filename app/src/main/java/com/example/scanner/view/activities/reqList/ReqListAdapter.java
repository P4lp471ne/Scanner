package com.example.scanner.view.activities.reqList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.scanner.R;
import com.example.scanner.logic.datatypes.responseTypes.ShortRequestDescription;

import java.util.List;

public class ReqListAdapter extends ArrayAdapter<ShortRequestDescription> {
    public ReqListAdapter(@NonNull Context context, int resource,
                          @NonNull List<ShortRequestDescription> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View reqView, ViewGroup parent) {
        ShortRequestDescription req = getItem(position);

        if (reqView == null)
            reqView = LayoutInflater.from(getContext()).inflate(R.layout.req_item, null);

        ((TextView) reqView.findViewById(R.id.reqNameView))
                .setText(req.getNameView());
        ((TextView) reqView.findViewById(R.id.ID))
                .setText(String.valueOf(req.getId()));
        ((TextView) reqView.findViewById(R.id.Status))
                .setText(req.getStatus() == 0 ? "new" : req.getStatus() == 1? "in_progress": "finish");
        ((TextView) reqView.findViewById(R.id.Date))
                .setText(req.getCollectionDate().split("T")[0]);
        ((TextView) reqView.findViewById(R.id.time))
                .setText(req.getCollectionDate().split("T")[1]);

        return reqView;
    }
}
