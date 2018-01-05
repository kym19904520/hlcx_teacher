package com.up.study.ui.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import com.up.common.J;
import com.up.common.base.CommonAdapter;
import com.up.common.base.ViewHolder;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.AlbumUtils;
import com.up.common.utils.StudyUtils;
import com.up.common.widget.MyGridView;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.base.CallBack;
import com.up.study.model.ImgUrl;
import com.up.study.params.ImageModel;
import com.up.study.params.Params;
import com.up.study.weight.showimages.ImagePagerActivity;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * TODO:发布新任务
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/7/26.
 */

public class PublishTaskActivity extends BaseFragmentActivity {

    private EditText edtContent;
    private MyGridView gv;

    private ArrayList<String> imageList = new ArrayList<>();
    private CommonAdapter<String> adapter;

    private int position = 0;

    private List<ImageModel> list = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.act_publish_task;
    }

    @Override
    protected void initView() {
        edtContent = bind(R.id.edt_content);
        gv = bind(R.id.gv);

    }

    @Override
    protected void initData() {

        imageList.add("add");
        adapter = new CommonAdapter<String>(ctx, imageList, R.layout.item_gv_image) {
            @Override
            public void convert(ViewHolder vh, String item, int position) {
                final int index = position;
                ImageView ivDel = vh.getView(R.id.iv_del);
                if (item.equals("add")) {
                    ivDel.setVisibility(View.GONE);
//                    vh.setImageResource(R.id.iv, R.mipmap.icon_add_img);
                } else {
                    ivDel.setVisibility(View.VISIBLE);

                }
                vh.setImageByUrl(R.id.iv, item);
                ivDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imageList.remove(index);
                        if (!imageList.get(imageList.size() - 1).equals("add")) {
                            imageList.add("add");
                        }
                        adapter.NotifyDataChanged(imageList);
                    }
                });
            }
        };
        gv.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PublishTaskActivity.this.position = i;
                if (imageList.get(i).equals("add")) {//点击的是默认的添加图标，则去添加图片
                    AlbumUtils.init(ctx, 9 - (imageList.size() - 1));
                } else {//查看图片
                    ArrayList<String> temp = (ArrayList<String>) imageList;
                    Intent intent = new Intent(ctx, ImagePagerActivity.class);
                    intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, temp);
                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
                    startActivity(intent);
                }

            }
        });
        bind(R.id.btn_publish).setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<String> list = AlbumUtils.activityResult(requestCode, resultCode, data);
        if (!list.isEmpty()) {
            if (imageList.get(imageList.size() - 1).equals("add")) {
                imageList.remove(imageList.size() - 1);
            }
            imageList.addAll(list);
            if (imageList.size() < 9) {
                imageList.add("add");
            }
            adapter.NotifyDataChanged(imageList);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_publish:
                publish();
                break;
        }
    }

    private void publish() {
        final String content = edtContent.getText().toString();
        if (TextUtils.isEmpty(content) && (imageList.size()-1) <= 0) {
            showToast("请输入内容！");
            return;
        }
        if (imageList.get(imageList.size() - 1).equals("add")) {
            imageList.remove(imageList.size() - 1);
        }

        showLoading("发布中..");
        if (imageList.size() > 0) {
            StudyUtils.uploadImgUrl(imageList, ctx, new CallBack<List<ImgUrl>>() {
                @Override
                public void suc(List<ImgUrl> obj) {
                    post(obj, content);
                    /*J.http().post(Urls.PUBLISH_TASK, ctx, Params.publishTask(obj, content, null), new HttpCallback<Respond>(ctx) {
                        @Override
                        public void success(Respond res, Call call, Response response, boolean isCache) {
                            showToast(res.getMsg());
                            setResult(113);
                            hideLoading();
                            PublishTaskActivity.this.finish();

                        }
                    });*/
                }
            });
        } else {
            post(null, content);
        }
    }

    private void post(List<ImgUrl> list, String content) {
        J.http().post(Urls.PUBLISH_TASK, ctx, Params.publishTask(list, content, null), new HttpCallback<Respond>(null) {
            @Override
            public void success(Respond res, Call call, Response response, boolean isCache) {
                showToast(res.getMsg());
                setResult(113);
                hideLoading();
                PublishTaskActivity.this.finish();

            }
        });
    }
}
