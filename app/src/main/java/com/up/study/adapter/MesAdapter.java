package com.up.study.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.up.common.J;
import com.up.common.utils.WidgetUtils;
import com.up.study.model.MesModel;
import com.up.study.weight.MyGridView;
import com.up.teacher.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 消息
 * @author yy_cai
 *
 * 2015年8月31日
 */
public class MesAdapter extends BaseAdapter {

	private List<MesModel> dynamicLists;
	private Context context;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	List<String> showTimes = new ArrayList<>();

	public MesAdapter(List<MesModel> dynamicLists,Context context) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_my_home, parent,false);
			vh = new ViewHolder();
			vh.ivImage=(ImageView)convertView.findViewById(R.id.iv_my_home_img);
			vh.iv_head=(ImageView)convertView.findViewById(R.id.iv_head);
			//vh.time=(TextView)convertView.findViewById(R.id.tv_time);
			vh.tv_top_time=(TextView)convertView.findViewById(R.id.tv_top_time);
			vh.content=(WebView) convertView.findViewById(R.id.wv_content);
			vh.tv_major=(TextView)convertView.findViewById(R.id.tv_major);
			vh.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			vh.gvImages=(MyGridView)convertView.findViewById(R.id.gv_my_home_imgs);
			convertView.setTag(vh);
		}
		else{
			vh=(ViewHolder)convertView.getTag();
		}
		/*String imgUrls =  dynamicLists.get(position).getAttached();
		Type type=new TypeToken<List<ImgUrl>>(){}.getType();
		List<ImgUrl> imgs = new Gson().fromJson(imgUrls,type);

		final ArrayList<String> imageShow = new ArrayList<String>();
		for (int i = 0;i<imgs.size();i++){
			imageShow.add(imgs.get(i).getUrl());
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
		*//*else if(imgs!=null&&imgs.size()==1){
			vh.ivImage.setVisibility(View.VISIBLE);
			vh.gvImages.setVisibility(View.GONE);
			//vh.ivImage.setImageBitmap(imgs.get(0));
			J.image().load(context,imgs.get(0).getUrl(),vh.ivImage);
		}*//*
		else{
			vh.gvImages.setVisibility(View.GONE);
			vh.ivImage.setVisibility(View.GONE);
		}*/
		String createTime = dynamicLists.get(position).getCreate_date();
		if (!TextUtils.isEmpty(createTime)){
			createTime  =  sdf.format(new Date(Long.parseLong(createTime)));
		}
		String topTime = "";
		if(createTime != null&&createTime.length()>=10){
			topTime = createTime;
//			if ()
		}
		if (!showTimes.contains(topTime)) {
			showTimes.add(topTime);
			vh.tv_top_time.setVisibility(View.VISIBLE);

			Date date=new Date();//取时间
			String today = sdf.format(date);

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
			date=calendar.getTime(); //这个时间就是日期往后推一天的结果
			String yesterday = sdf.format(date);

			if(today.equals(topTime)){
				vh.tv_top_time.setText("今天");
			}
			else if (yesterday.equals(topTime)){
				vh.tv_top_time.setText("昨天");
			}
			else{
				vh.tv_top_time.setText(topTime);
			}

		}

		vh.gvImages.setVisibility(View.GONE);
		vh.ivImage.setVisibility(View.GONE);
		//vh.time.setText(dynamicLists.get(position).getTime().toString());

		vh.content.setClickable(false);
		vh.content.setPressed(false);
		vh.content.setEnabled(false);
		WidgetUtils.initWebView(vh.content,dynamicLists.get(position).getContent().toString());
		vh.tv_major.setText("学校通知");
		vh.tv_name.setText("管理员");
		J.image().loadCircle(context,null,vh.iv_head);
		return convertView;
	}
	private static class ViewHolder{
		ImageView ivImage;
		TextView tv_top_time;
		//TextView time;
//		TextView content;
		WebView content;
		TextView tv_major;
		TextView tv_name;
		ImageView iv_head;
		MyGridView gvImages;
	}

}
