package com.up.study.ui.home;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
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
import com.up.study.params.Params;
import com.up.study.weight.showimages.ImagePagerActivity;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * TODO:
 * Created by 王剑洪
 * On 2017/7/27.
 */

public class UpTestActivity extends BaseFragmentActivity {

    private MyGridView gv;

    private ArrayList<String> imageList = new ArrayList<>();
    private CommonAdapter<String> adapter;
    private int position = 0;
    private Map<String, Object> map;
    private int testId;

    @Override
    protected int getContentViewId() {
        return R.layout.act_up_test;
    }

    @Override
    protected void initView() {
        gv = bind(R.id.gv);
        if (imageList.isEmpty()) {
            bind(R.id.ll_has).setVisibility(View.GONE);
            bind(R.id.ll_null).setVisibility(View.VISIBLE);
        } else {
            bind(R.id.ll_has).setVisibility(View.VISIBLE);
            bind(R.id.ll_null).setVisibility(View.GONE);
        }

    }

    @Override
    protected void initData() {
        map = getMap();
        testId = (int) map.get("paperId");
        adapter = new CommonAdapter<String>(ctx, imageList, R.layout.item_gv_image) {
            @Override
            public void convert(ViewHolder vh, String item, int position) {
                final int index = position;
                ImageView ivDel = vh.getView(R.id.iv_del);
                if (item.equals("add")) {
                    ivDel.setVisibility(View.GONE);
                } else {
                    ivDel.setVisibility(View.VISIBLE);
                }
                vh.setImageByUrl(R.id.iv, item);
                ivDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imageList.remove(index);
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
                ArrayList<String> temp = (ArrayList<String>) imageList;
                Intent intent = new Intent(ctx, ImagePagerActivity.class);
                intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, temp);
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
                startActivity(intent);

            }
        });
        bind(R.id.btn_next).setOnClickListener(this);
        bind(R.id.iv_right).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<String> list = AlbumUtils.activityResult(requestCode, resultCode, data);
        if (!list.isEmpty()) {
            bind(R.id.ll_has).setVisibility(View.VISIBLE);
            bind(R.id.ll_null).setVisibility(View.GONE);
            imageList.addAll(list);
            adapter.NotifyDataChanged(imageList);
        }
        if ((requestCode == 110 && resultCode == 110)) {
            setResult(111);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (imageList.isEmpty()) {
                    showToast("请选择图片");
                }
                StudyUtils.uploadImgUrl(imageList, ctx, new CallBack<List<ImgUrl>>() {
                    @Override
                    public void suc(List<ImgUrl> obj) {
                        J.http().post(Urls.UPLOAD_TEST, ctx, Params.uploadTest(obj, testId), new HttpCallback<Respond>(ctx) {
                            @Override
                            public void success(Respond res, Call call, Response response, boolean isCache) {
                                showToast(res.getMsg());
                                map.put("code", 110);
                                gotoActivityResult(ChooseClassActivity.class, map, 110);
                            }
                        });
                    }
                });


                break;
            case R.id.iv_right:
                if (imageList.size() >= 9) {
                    showToast("最多支持上传9张");
                    return;
                }
                AlbumUtils.init(ctx, 9 - imageList.size());
                break;
        }
    }
}
