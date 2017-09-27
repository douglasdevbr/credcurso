package com.pixelnx.sam.jobportal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.pixelnx.sam.jobportal.DTO.DummyFilterDTO;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.utils.CustomTextview;

import java.util.ArrayList;

/**
 * Created by varunverma on 25/9/17.
 */

public class AdapterFilterItem extends BaseAdapter {
    private Context mContext;
    private ArrayList<DummyFilterDTO> dummyFilterList;

    public AdapterFilterItem(Context mContext, ArrayList<DummyFilterDTO> dummyFilterList) {
        this.mContext = mContext;
        this.dummyFilterList = dummyFilterList;
    }

    @Override
    public int getCount() {
        return dummyFilterList.size();
    }

    @Override
    public Object getItem(int position) {
        return dummyFilterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final ViewHolder holder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.adapter_filter_item, null);
        holder.tvDesignation = (CustomTextview) itemView.findViewById(R.id.tvDesignation);
        holder.cbFilter = (CheckBox) itemView.findViewById(R.id.cbFilter);

        holder.tvDesignation.setText(dummyFilterList.get(position).getName());
        if (dummyFilterList.get(position).isChecked()) {
            holder.cbFilter.setChecked(true);
        } else {
            holder.cbFilter.setChecked(false);
        }

        holder.cbFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dummyFilterList.get(position).setChecked(true);

                } else {
                    dummyFilterList.get(position).setChecked(false);
                }

            }
        });
        return itemView;
    }

    static class ViewHolder {
        public CustomTextview tvDesignation;
        public CheckBox cbFilter;
    }
}
