package com.up.study.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.study.model.ClassMajorBean;
import com.up.study.model.ClassSubjectsBean;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by kym on 2017/8/11.
 */

public class SelectSubjectAdapter extends BaseAdapter {
    public LayoutInflater inflater;
    private Context content;
    private Map<String, String> mDataSets;
    private String gradeId;
    private List<ClassSubjectsBean> subjects;
    private List<Map.Entry<String, String>> list;
    private OptionsPickerView pvNoLinkOptions;
    private int position01;

    public SelectSubjectAdapter(Context context, Map<String, String> mDataSets, String gradeId, List<ClassSubjectsBean> subjects) {
        this.content = context;
        this.mDataSets = mDataSets;
        inflater = LayoutInflater.from(context);
        this.gradeId = gradeId;
        this.subjects = subjects;
    }

    @Override
    public int getCount() {
        return mDataSets.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_class_norisuke, null);
            viewHolder.tv_norisuke = (TextView) convertView.findViewById(R.id.tv_norisuke);
            viewHolder.tv_jf_name = (TextView) convertView.findViewById(R.id.tv_jf_name);
            viewHolder.rl_english_jf = (RelativeLayout) convertView.findViewById(R.id.rl_english_jf);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        list = new ArrayList<>(mDataSets.entrySet());

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.rl_english_jf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = list.get(position).getValue();
                HttpParams params = new HttpParams();
                params.put("majorcode", string);
                params.put("grade", gradeId);
                J.http().post(Urls.ADD_CLASS_MAJOR_URL, content, params, new HttpCallback<Respond<List<ClassMajorBean>>>(content,true,true) {
                    @Override
                    public void success(Respond<List<ClassMajorBean>> res, Call call, Response response, boolean isCache) {
                        if (Respond.SUC.equals(res.getCode())) {
                            List<ClassMajorBean> listData = res.getData();
                            if (listData.size() <= 0){
                                Toast.makeText(content,"该学科没有教辅",Toast.LENGTH_SHORT).show();
                            }else {
                                if (finalViewHolder.tv_jf_name.getText().toString().isEmpty() || finalViewHolder.tv_jf_name.getText() == null) {
//                                    showSelectJfDialog(listData, finalViewHolder.tv_jf_name);
                                    List<String> listName = new ArrayList<>();
                                    for (int i = 0;i < listData.size();i++){
                                        listName.add(listData.get(i).getName());
                                    }
                                    List<Integer> listId = new ArrayList<>();
                                    for (int i = 0;i < listData.size();i++){
                                        listId.add(listData.get(i).getId());
                                    }
                                    initNoLinkOptionsPicker(listName,listId, finalViewHolder.tv_jf_name);
                                    pvNoLinkOptions.show();
                                }else {
                                    return;
                                }
                            }
                        }
                    }
                });
            }
        });
        position01 = position;
        viewHolder.tv_norisuke.setText(list.get(position).getKey());
        return convertView;
    }

    /**
     * 暂时不用----选择教辅对话框
     */
    private SelectJfAdapter selectJfAdapter;
    private AlertDialog alertDialog;
    private Map<Integer,String> map = new HashMap<>();
    private String str = "";
    private void showSelectJfDialog(List<ClassMajorBean> listData, final TextView tv_jf_name) {
        selectJfAdapter = new SelectJfAdapter(content, listData);
        AlertDialog.Builder builder = new AlertDialog.Builder(content);
        View view = View.inflate(content, R.layout.dialog_list, null);
        builder.setView(view);
        final ListView lv_list_jf = (ListView) view.findViewById(R.id.lv_list);
        lv_list_jf.setAdapter(selectJfAdapter);
        lv_list_jf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0;i<subjects.size();i++){
                    if (subjects.get(i).getName().equals(list.get(position01).getKey())){
                        tv_jf_name.setText(selectJfAdapter.mDataSets.get(position).getName());
                    }
                }
                str +=  selectJfAdapter.mDataSets.get(position).getId() + ",";
//                map.put(str.substring(0,str.length()-1),selectJfAdapter.mDataSets.get(position).getName());
                Log.i("教辅id",str);
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    public Map<Integer,String> getMap(){
        return map;
    }

    static class ViewHolder {
        TextView tv_norisuke;
        TextView tv_jf_name;
        RelativeLayout rl_english_jf;
    }

    /**
     * 选择教辅
     * @param listName 教辅名称
     * @param listId    教辅id
     * @param tv_jf_name
     */
    private void initNoLinkOptionsPicker(final List<String> listName, final List<Integer> listId, final TextView tv_jf_name) {// 不联动的多级选项
        pvNoLinkOptions = new OptionsPickerView.Builder(content, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                for (int i = 0;i<subjects.size();i++){
                    if (subjects.get(i).getName().equals(list.get(position01).getKey())){
                        tv_jf_name.setText(listName.get(options1));
                    }
                }
                map.put(listId.get(options1),listName.get(options1));
                //设置外部遮罩颜色
            }
        }).setBackgroundId(0x00FFFFFF).build();
        pvNoLinkOptions.setNPicker(listName, null, null);
    }
}
