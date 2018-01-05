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
import com.up.study.model.ImgUrl;
import com.up.study.model.OfflineWorkModel;
import com.up.study.weight.MyGridView;
import com.up.study.weight.showimages.ImagePagerActivity;
import com.up.teacher.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息
 * @author yy_cai
 *
 * 2015年8月31日
 */
public class OfflineWorkAdapter extends BaseAdapter {

	private List<OfflineWorkModel> dynamicLists;
	private Context context;
	public OfflineWorkAdapter(List<OfflineWorkModel> dynamicLists,
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_my_home3, parent,false);
			vh = new ViewHolder();
			vh.ivImage=(ImageView)convertView.findViewById(R.id.iv_my_home_img);
			vh.iv_head=(ImageView)convertView.findViewById(R.id.iv_head);
			//vh.time=(TextView)convertView.findViewById(R.id.tv_time);
			vh.content=(TextView)convertView.findViewById(R.id.tv_content);
			vh.tv_major=(TextView)convertView.findViewById(R.id.tv_major);
			vh.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			vh.gvImages=(MyGridView)convertView.findViewById(R.id.gv_my_home_imgs);
			convertView.setTag(vh);
		}
		else{
			vh=(ViewHolder)convertView.getTag();
		}
		List<ImgUrl> imgs = null;
		try {
			String imgUrls =  dynamicLists.get(position).getAttached();
			Type type=new TypeToken<List<ImgUrl>>(){}.getType();
			imgs = new Gson().fromJson(imgUrls,type);
		}
		catch (Exception e){
			imgs = new ArrayList<ImgUrl>();
		}


		final ArrayList<String> imageShow = new ArrayList<String>();
		if(imgs!=null) {
			for (int i = 0; i < imgs.size(); i++) {
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
			/*vh.gvImages.setClickable(false);
			vh.gvImages.setPressed(false);
			vh.gvImages.setEnabled(false);*/
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
		vh.content.setText(dynamicLists.get(position).getContent().toString());
		vh.tv_major.setText(dynamicLists.get(position).getMajorName());
		vh.tv_name.setText(dynamicLists.get(position).getUserName());

		try {
			Type type = new TypeToken<List<ImgUrl>>() {
			}.getType();
			List<ImgUrl> head = new Gson().fromJson(dynamicLists.get(position).getUserHead(), type);
			if(head!=null||head.size()>0){
				J.image().loadCircle(context,head.get(0).getUrl(),vh.iv_head);
			}
			else{
				J.image().loadCircle(context,null,vh.iv_head);
			}
		}catch (Exception e){
			J.image().loadCircle(context,dynamicLists.get(position).getUserHead(),vh.iv_head);
		}


		return convertView;
	}
	private static class ViewHolder{
		ImageView ivImage;
		//TextView time;
		TextView content;
		TextView tv_major;
		TextView tv_name;
		ImageView iv_head;
		MyGridView gvImages;
	}

}
