package com.up.study.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.up.common.J;
import com.up.study.model.ErrorModel;
import com.up.study.model.ImgUrl;
import com.up.study.weight.MyGridView;
import com.up.study.weight.showimages.ImagePagerActivity;
import com.up.teacher.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 错误原因适配器
 * @author yy_cai
 *
 * 2015年8月31日
 */
public class ErrorCauseAdapter extends BaseAdapter {

	private List<ErrorModel> dynamicLists;
	private Context context;
	public ErrorCauseAdapter(List<ErrorModel> dynamicLists,
                             Context context) {
		this.dynamicLists=dynamicLists;
		this.context=context;
	}

	@Override
	public int getCount() {
		return dynamicLists.size();
	}

	@Override
	public Object getItem(int position) {
		return dynamicLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh=null;
		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_error_cause, parent,false);
			vh = new ViewHolder();
			vh.ivImage=(ImageView)convertView.findViewById(R.id.iv_my_home_img);
			vh.iv_head=(ImageView)convertView.findViewById(R.id.iv_head);
			//vh.time=(TextView)convertView.findViewById(R.id.tv_time);
			vh.content=(TextView)convertView.findViewById(R.id.tv_content);
			vh.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			vh.gvImages=(MyGridView)convertView.findViewById(R.id.gv_my_home_imgs);
			convertView.setTag(vh);
		}
		else{
			vh=(ViewHolder)convertView.getTag();
		}
		String imgUrls =  dynamicLists.get(position).getErr_attached();
		Type type=new TypeToken<List<ImgUrl>>(){}.getType();
		List<ImgUrl> imgs = new Gson().fromJson(imgUrls,type);

		final ArrayList<String> imageShow = new ArrayList<String>();
		if(imgs!=null){
			for (int i = 0;i<imgs.size();i++){
				imageShow.add(imgs.get(i).getUrl());
			}
		}
		//List<Bitmap> imgs = dynamicLists.get(position).getAttached();
		if(imgs!=null&&imgs.size()>0){
			vh.ivImage.setVisibility(View.GONE);
			vh.gvImages.setVisibility(View.VISIBLE);
			vh.gvImages.setAdapter(new MesGridViewAdapter(imgs, context));
			vh.gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent(context, ImagePagerActivity.class);
					intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, imageShow);
					intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
					context.startActivity(intent);
				}
			});
		}
		/*else if(imgs!=null&&imgs.size()==1){
			vh.ivImage.setVisibility(View.VISIBLE);
			vh.gvImages.setVisibility(View.GONE);
			//vh.ivImage.setImageBitmap(imgs.get(0));
			J.image().load(context,imgs.get(0).getUrl(),vh.ivImage);
		}*/
		else{
			vh.gvImages.setVisibility(View.GONE);
			vh.ivImage.setVisibility(View.GONE);
		}
		//vh.time.setText(dynamicLists.get(position).getTime().toString());
		vh.content.setText(dynamicLists.get(position).getContent());
		vh.tv_name.setText(dynamicLists.get(position).getStudentName());
		J.image().loadCircle(context,dynamicLists.get(position).getHead(),vh.iv_head);
		return convertView;
	}
	private static class ViewHolder{
		ImageView ivImage;
		//TextView time;
		TextView content;
		TextView tv_name;
		ImageView iv_head;
		MyGridView gvImages;
	}

}
