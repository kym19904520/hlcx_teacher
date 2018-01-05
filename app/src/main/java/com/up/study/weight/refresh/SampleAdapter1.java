package com.up.study.weight.refresh;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.up.teacher.R;

import java.util.ArrayList;
import java.util.List;

public class SampleAdapter1 extends SwipeRecyclerViewAdapter {
	private List<String> list;
	public List<String> getList() {
		return list;
	}

	public SampleAdapter1() {
		list = new ArrayList<String>();
	}

	@Override
	public int GetItemCount() {
		return list.size();
	}

	@Override
	public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
		if (viewType == TYPE_ITEM) {
			View view = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.list_item_text, null);
			view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			return new ItemViewHolder(view);
		}
		return null;
	}

	@Override
	public void onBindItemViewHolder(ViewHolder holder, int position) {
		if (holder instanceof ItemViewHolder) {
			((ItemViewHolder) holder).textView.setText(String.valueOf(list
					.get(position)));
		}
		
	}
	
	class ItemViewHolder extends ViewHolder {
		TextView textView;

		public ItemViewHolder(View view) {
			super(view);
			textView = (TextView) view.findViewById(R.id.text);
		}
	}
}