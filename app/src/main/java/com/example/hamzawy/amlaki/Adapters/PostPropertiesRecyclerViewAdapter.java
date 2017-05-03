package com.example.hamzawy.amlaki.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hamzawy.amlaki.R;
import com.example.hamzawy.amlaki.models.Property;

import java.util.ArrayList;

/**
 * Created by HamzawY on 1/17/17.
 */

public class PostPropertiesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;

    ArrayList<Property> properties;
    private Activity activity;

    public PostPropertiesRecyclerViewAdapter(Activity activity, ArrayList<Property> properties) {
        this.properties = properties;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View emptyView = inflater.inflate(R.layout.property_item, parent, false);
        return new PropertyViewHolder(emptyView);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Property property = properties.get(position);
        PropertyViewHolder propertyViewHolder = (PropertyViewHolder) holder;
        propertyViewHolder.propertyName.setText(property.getTitle());
        propertyViewHolder.propertyValue.setText(property.getValue());

    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    class PropertyViewHolder extends RecyclerView.ViewHolder {
        private TextView propertyName, propertyValue;

        public PropertyViewHolder(View itemView) {
            super(itemView);
            propertyName = (TextView) itemView.findViewById(R.id.propertyName);
            propertyValue = (TextView) itemView.findViewById(R.id.propertyValue);
        }

    }
}
