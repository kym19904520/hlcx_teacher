package com.up.study.ui.home;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.up.common.J;
import com.up.study.adapter.MesGridViewAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.ImgUrl;
import com.up.study.model.OfflineWorkModel;
import com.up.study.weight.MyGridView;
import com.up.study.weight.showimages.ImagePagerActivity;
import com.up.teacher.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 线下作业详情
 */
public class OffineWorkDetailActivity extends BaseFragmentActivity {
    ImageView ivImage;
    TextView tv_time;
    TextView content;
    TextView tv_major;
    TextView tv_name;
    ImageView iv_head;
    MyGridView gvImages;

    OfflineWorkModel mesModel;
    @Override
    protected int getContentViewId() {
        return R.layout.act_officework_detail;
    }

    @Override
    protected void initView() {

        ivImage=(ImageView)this.findViewById(R.id.iv_my_home_img);
        iv_head=(ImageView)this.findViewById(R.id.iv_head);
        tv_time=(TextView)this.findViewById(R.id.tv_time);
        content=(TextView)this.findViewById(R.id.tv_content);
        tv_major=(TextView)this.findViewById(R.id.tv_major);
        tv_name=(TextView)this.findViewById(R.id.tv_name);
        gvImages=(MyGridView)this.findViewById(R.id.gv_my_home_imgs);
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData() {
        mesModel = (OfflineWorkModel)getIntent().getSerializableExtra("bean1");

        String imgUrls =  mesModel.getAttached();
        Type type=new TypeToken<List<ImgUrl>>(){}.getType();
        final List<ImgUrl> imgs = new Gson().fromJson(imgUrls,type);
        //List<Bitmap> imgs = dynamicLists.get(position).getAttached();
        final ArrayList<String> imageShow = new ArrayList<String>();
        if(imgs!=null) {
            for (int i = 0; i < imgs.size(); i++) {
                imageShow.add(imgs.get(i).getUrl());
            }
        }

        if(imgs!=null&&imgs.size()>1){
            ivImage.setVisibility(View.GONE);
            gvImages.setVisibility(View.VISIBLE);
            gvImages.setAdapter(new MesGridViewAdapter(imgs, this));
            gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(OffineWorkDetailActivity.this, ImagePagerActivity.class);
                    intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, imageShow);
                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
                    OffineWorkDetailActivity.this.startActivity(intent);
                }
            });

        }
        else if(imgs!=null&&imgs.size()==1){
            ivImage.setVisibility(View.VISIBLE);
            gvImages.setVisibility(View.GONE);
            //vh.ivImage.setImageBitmap(imgs.get(0));
            J.image().load(this,imgs.get(0).getUrl(),ivImage);
            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> temp = new ArrayList<>();
                    for(int i = 0;i<imgs.size();i++){
                        temp.add(imgs.get(i).getUrl());
                    }
                    Intent intent = new Intent(OffineWorkDetailActivity.this, ImagePagerActivity.class);
                    intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, temp);
                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
                    startActivity(intent);
                }
            });
        }
        else{
            gvImages.setVisibility(View.GONE);
            ivImage.setVisibility(View.GONE);
        }
        tv_time.setText(mesModel.getTime());
        content.setText(mesModel.getContent().toString());
        tv_major.setText(mesModel.getMajorName());
        tv_name.setText(mesModel.getUserName());

        try {
            Type type1 = new TypeToken<List<ImgUrl>>() {}.getType();
            List<ImgUrl> head = new Gson().fromJson(mesModel.getUserHead(), type1);
            if(head!=null||head.size()>0){
                J.image().loadCircle(this,head.get(0).getUrl(),iv_head);
            }
            else{
                J.image().loadCircle(this,null,iv_head);
            }
        }catch (Exception e){
            J.image().loadCircle(this,mesModel.getUserHead(),iv_head);
        }


        //J.image().loadCircle(this,mesModel.getUserHead(),iv_head);

    }

    @Override
    public void onClick(View v) {
    }

}
