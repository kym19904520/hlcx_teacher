package com.up.study.weight.refresh;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.up.teacher.R;


public abstract class SwipeRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder>{
	private boolean isComplete=false;//是否完成所有数据的加载
	private FooterViewHolder footerHolder;
	private boolean isFullScreen=false;
	protected static final int TYPE_ITEM = 0;
	private static final int TYPE_FOOTER = 1;//最后一个item
	private static final int TYPE_NULL = -1;

	public abstract int GetItemCount();
	/**
	 * @return item的viewHolder
	 */
	public abstract ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType);
	/**
	 * bind itemViewHolder
	 */
	public abstract void onBindItemViewHolder(ViewHolder holder, final int position);

	/**
	 * 完成所有加载
	 */
	public void completeLoad(){
		isComplete=true;
		footerHolder.footerRiv.stopRotate();
		footerHolder.footerRiv.setVisibility(View.GONE);
		footerHolder.footerTv.setVisibility(View.VISIBLE);
	}
	/**
	 * 是否完成所有数据的加载
	 * @return
	 */
	public boolean isComplete(){
		return isComplete;
	}
	/**
	 *  重新刷新数据
	 */
	public void refresh() {
		isComplete = false;
		footerHolder.footerRiv.setVisibility(View.VISIBLE);
		footerHolder.footerTv.setVisibility(View.GONE);
	}

	public boolean isFullScreen() {
		return isFullScreen;
	}
	public void setFullScreen(boolean isFullScreen) {
		this.isFullScreen = isFullScreen;
	}
	@Override
	public int getItemCount() {
		return GetItemCount() + 1;
	}

	@Override
	public int getItemViewType(int position) {
		if(!isFullScreen&&position + 1 == getItemCount()){//当屏幕没有占满的时候，最后一个item用空白view代替footerView
			return TYPE_NULL;
		}
		else if (isFullScreen&&position + 1 == getItemCount()) {// 最后一个item设置为footerView
			return TYPE_FOOTER;
		} 
		else {
			return TYPE_ITEM;
		}
	}
	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		if(holder instanceof FooterViewHolder){
			FooterViewHolder h = (FooterViewHolder)holder;
			if(h.footerRiv.getVisibility()== View.VISIBLE&&!isComplete){
				h.footerRiv.startRotate();
			}
		}else{
			onBindItemViewHolder(holder, position);
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == TYPE_FOOTER) {
			View view = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.footerview, null);
			view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			footerHolder = new FooterViewHolder(view);
			return footerHolder;
		}
		else if(viewType == TYPE_NULL){
			return new NullViewHolder(new TextView(parent.getContext()));
		}
		else if(viewType == TYPE_ITEM){
			return onCreateItemViewHolder(parent, viewType);
		}
		return null;
	}

	class FooterViewHolder extends ViewHolder {
		RotateImageView footerRiv;
		TextView footerTv;
		public FooterViewHolder(View view) {
			super(view);
			footerRiv = bind(view, R.id.riv_footer_load);
			footerTv = bind(view, R.id.tv_footer_load);
		}

	}
	class NullViewHolder extends ViewHolder {
		public NullViewHolder(View view) {
			super(view);
		}

	}
	
	/**
	 * 绑定ID
	 * 
	 * @param viewId
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T bind(View v, int viewId) {
		View view = v.findViewById(viewId);
		return (T) view;
	}

}
