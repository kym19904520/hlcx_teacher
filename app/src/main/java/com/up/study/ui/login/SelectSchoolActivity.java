package com.up.study.ui.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.base.BaseBean;
import com.up.common.base.CommonAdapter;
import com.up.common.base.ViewHolder;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.RegionModel;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dell on 2017/8/4.
 */

public class SelectSchoolActivity extends BaseFragmentActivity {

    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.tv_select)
    TextView tvSelect;

    private CommonAdapter<RegionModel> adapter;
    private List<RegionModel> dataList = new ArrayList<>();
    private List<RegionModel> proDataList = new ArrayList<>();
    private List<RegionModel> cityDataList = new ArrayList<>();
    private List<RegionModel> areaDataList = new ArrayList<>();
    private List<RegionModel> schoolDataList = new ArrayList<>();
    private int index;//0:选择省，1：选择市，2：选择区县,3：选择学校

    private boolean isKeywordSearch=false;//是否关键字查询
    @Override
    protected int getContentViewId() {
        return R.layout.act_select_school;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        adapter = new CommonAdapter<RegionModel>(ctx, dataList, R.layout.item_region) {
            @Override
            public void convert(ViewHolder vh, RegionModel item, int position) {
                TextView tv = vh.getView(R.id.tv_name);
                tv.setText(item.getName());
            }
        };
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (index == 0) {
                    getCityList(proDataList.get(position).getCode());
                }
                else if(index==1){
                    getAreaList(cityDataList.get(position).getCode());
                }
                else if(index==2){
                    getSchoolList(areaDataList.get(position).getCode(),null);
                }
                else if(index==3){
                    Intent intent = new Intent();
                    String passString = schoolDataList.get(position).getTrial_code();
                    intent.putExtra("code", passString);
                    setResult(RESULT_OK, intent);
                    SelectSchoolActivity.this.finish();
                }
            }
        });

        lv.setAdapter(adapter);
        getProList();
    }

    @Override
    protected void initEvent() {
        tvSelect.setOnClickListener(this);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length()>0){
                    showLog("关键字："+s.toString());
                    isKeywordSearch = true;
                    getSchoolList(null,s.toString());
                }
                else {
                    showLog("无关键字");
                    dataList.clear();
                    isKeywordSearch = false;
                    dataList.addAll(proDataList);
                    index=0;
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v==tvSelect){
            if(index>0){
                dataList.clear();
            }

            switch (index){
                case 1:
                    dataList.addAll(proDataList);
                    index--;
                    break;
                case 2:
                    dataList.addAll(cityDataList);
                    index--;
                    break;
                case 3:
                    if (isKeywordSearch){
                        dataList.addAll(proDataList);
                        index=0;
                    }
                    else {
                        dataList.addAll(areaDataList);
                        index--;
                    }
                    break;
                default:
                    break;
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void getProList() {
        proDataList.clear();
        dataList.clear();
        HttpParams params = new HttpParams();
        J.http().post(Urls.GET_REGION_LIST, ctx, params, new HttpCallback<Respond<Province>>(ctx) {
            @Override
            public void success(Respond<Province> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    index = 0;
                    proDataList.addAll(res.getData().getProvList());
                    dataList.addAll(res.getData().getProvList());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 获取城市
     */
    private void getCityList(String code) {
        dataList.clear();
        cityDataList.clear();
        HttpParams params = new HttpParams();
        params.put("city_id", code);
        J.http().post(Urls.GET_CITY_REGION_LIST, ctx, params, new HttpCallback<Respond<CityArea>>(ctx) {
            @Override
            public void success(Respond<CityArea> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    if (res.getData().getCityList() == null || res.getData().getCityList().size() == 0) {
                        showToast("当前市暂时没有学校");
                        return;
                    }
                    index = 1;
                    cityDataList.addAll(res.getData().getCityList());
                    dataList.addAll(res.getData().getCityList());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 获取区县
     * @param code
     */
    private void getAreaList(String code) {
        areaDataList.clear();
        dataList.clear();
        HttpParams params = new HttpParams();
        params.put("region_id", code);
        J.http().post(Urls.GET_CITY_REGION_LIST, ctx, params, new HttpCallback<Respond<Region>>(ctx) {
            @Override
            public void success(Respond<Region> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    if (res.getData().getRegionList() == null || res.getData().getRegionList().size() == 0) {
                        showToast("当前区县暂时没有学校");
                        return;
                    }
                    index = 2;
                    areaDataList.addAll(res.getData().getRegionList());
                    dataList.addAll(res.getData().getRegionList());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     *
     * @param county
     * @param keywords
     */
    private void getSchoolList(String county,String keywords) {
        schoolDataList.clear();
        dataList.clear();
        HttpParams params = new HttpParams();
        if(!TextUtils.isEmpty(county)){//区县查询学校
            params.put("county", county);
        }
        else{//关键字查询学校
            params.put("keywords", keywords);
        }

        J.http().post(Urls.GET_SCHOOL, ctx, params, new HttpCallback<Respond<School>>(ctx) {
            @Override
            public void success(Respond<School> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    index = 3;
                    schoolDataList.addAll(res.getData().getList());
                    dataList.addAll(res.getData().getList());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    public class Province extends BaseBean {
        private List<RegionModel> provList;

        public List<RegionModel> getProvList() {
            return provList;
        }

        public void setProvList(List<RegionModel> provList) {
            this.provList = provList;
        }
    }

    public class CityArea extends BaseBean {
        private List<RegionModel> cityList;

        public List<RegionModel> getCityList() {
            return cityList;
        }

        public void setCityList(List<RegionModel> cityList) {
            this.cityList = cityList;
        }
    }

    public class Region extends BaseBean {
        private List<RegionModel> regionList;

        public List<RegionModel> getRegionList() {
            return regionList;
        }

        public void setRegionList(List<RegionModel> regionList) {
            this.regionList = regionList;
        }
    }

    public class School extends BaseBean {
        private List<RegionModel> list;

        public List<RegionModel> getList() {
            return list;
        }

        public void setList(List<RegionModel> list) {
            this.list = list;
        }
    }

}
