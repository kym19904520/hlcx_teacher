package com.up.study.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by kym on 2017/7/26.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    public List<T> mDataSets;
    public LayoutInflater inflater;

    public MyBaseAdapter(Context context,List<T> mDataSets){
        this.mDataSets = mDataSets;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mDataSets != null){
            return mDataSets.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDataSets != null){
            return mDataSets.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
