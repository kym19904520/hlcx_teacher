package com.up.study.weight.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.Toast;

public class SwipeRecyclerView extends RecyclerView implements OnRefreshListener {

	SwipeRecyclerViewAdapter mAdapter;
	private LinearLayoutManager mLayoutManager;
	private int lastVisibleItem;
	private OnRefreshAndLoadListener listener;
	private boolean isLoading=false;
	public interface OnRefreshAndLoadListener{
	/**
	 * 上拉加载
	 */
	 void onUpLoad();
	 /**
	  * 下拉刷新
	  */
	 void onRefresh();
	}
	/**
	 * 完成所有加载
	 */
	public void completeAllLoad(){
		mAdapter.completeLoad();
	}
	/**
	 * 单次加载
	 */
	public void completeLoad(){
		isLoading=false;
	}
	public void setOnRefreshAndLoadListener(SwipeRefreshLayout mSwipeRefreshWidget, OnRefreshAndLoadListener listener) {
		this.listener = listener;
		mSwipeRefreshWidget.setOnRefreshListener(this);
	}
	
	public SwipeRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem + 1 == mAdapter.getItemCount()&&!mAdapter.isComplete()&&!isLoading) {
					isLoading=true;
					listener.onUpLoad();
				}
			}
			//只有数据满屏的时候才会调用该方法
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
				mAdapter.setFullScreen(true);
			}

		});
	}

	@Override
	public void setAdapter(Adapter adapter) {
		super.setAdapter(adapter);
		try{
		mAdapter = (SwipeRecyclerViewAdapter)adapter;
		}catch(Exception e){
			Toast.makeText(this.getContext(), "适配器要继承SwipeRecyclerViewAdapter", Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public void setLayoutManager(LayoutManager layout) {
		super.setLayoutManager(layout);
		try{
		mLayoutManager = (LinearLayoutManager)layout;
		}catch(Exception e){
			Toast.makeText(this.getContext(), "当前版本只支持LinearLayoutManager", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onRefresh() {
		if(mAdapter.isComplete()){//加载过
			mAdapter.refresh();//重新加载
		}
		listener.onRefresh();
	}
}
