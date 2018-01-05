package com.up.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.up.teacher.R;

import java.util.List;

/**
 * 选择头像的adapter
 * Created by kym on 2017/7/26.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Integer> listData;
    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public GalleryAdapter(Context context, List<Integer> listData) {
        this.listData = listData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_index_gallery,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.iv_tx);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.mImg.setImageResource(listData.get(position));
        if (mOnItemClickLitener != null)
        {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.itemView,position);
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
        ImageView mImg;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    /**
     * 回调的接口
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }
}
