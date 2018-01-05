package com.up.study.weight;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.up.common.base.CommonAdapter;
import com.up.common.base.ViewHolder;
import com.up.common.widget.MyListView;
import com.up.study.model.NameValue;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO:
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/7/28.
 */

public class ChooseType2Dialog extends Dialog {

    private Context context;
    private List<NameValue> list = new ArrayList<>();
    private CommonAdapter<NameValue> adapter;

    public ChooseType2Dialog(Context context, List<NameValue> objects) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.list = objects;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_choose_type);
        ListView lv = (ListView) findViewById(R.id.lv);
        adapter = new CommonAdapter<NameValue>(context, list, R.layout.item_lv_type2) {
            @Override
            public void convert(ViewHolder vh, final NameValue item, int position) {
                TextView tv = vh.getView(R.id.tv_type);
                tv.setText(item.getName());
                tv.setSelected(item.isSelected());
                MyListView listView = vh.getView(R.id.lv2);
                final CommonAdapter<NameValue> adapter1 = new CommonAdapter<NameValue>(context, item.getChildRen(), R.layout.item_lv_type2) {
                    @Override
                    public void convert(ViewHolder vh, final NameValue item, int position) {
                        TextView tv = vh.getView(R.id.tv_type);
                        tv.setText(item.getName());
                        tv.setSelected(item.isSelected());
                        MyListView listView2 = vh.getView(R.id.lv2);
                        final CommonAdapter<NameValue> adapter2 = new CommonAdapter<NameValue>(context, item.getChildRen(), R.layout.item_lv_type2) {
                            @Override
                            public void convert(ViewHolder vh, NameValue item, int position) {
                                TextView tv = vh.getView(R.id.tv_type);
                                tv.setText(item.getName());
                                tv.setSelected(item.isSelected());
                            }
                        };
                        listView2.setAdapter(adapter2);
                        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                for (int k = 0; k < list.size(); k++) {
                                    item.getChildRen().get(k).setSelected(false);
                                }
                                item.getChildRen().get(i).setSelected(true);
                                adapter2.NotifyDataChanged(item.getChildRen());
                            }
                        });

                    }
                };
                listView.setAdapter(adapter1);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        for (int k = 0; k < list.size(); k++) {
                            item.getChildRen().get(k).setSelected(false);
                        }
                        item.getChildRen().get(i).setSelected(true);
                        adapter1.NotifyDataChanged(item.getChildRen());
                    }
                });

            }
        };
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for (int k = 0; k < list.size(); k++) {
                    list.get(k).setSelected(false);
                }
                list.get(i).setSelected(true);
                adapter.NotifyDataChanged(list);
            }
        });
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NameValue nameValue = null;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelected()) {
                        nameValue = list.get(i);
                    }
                }
                if (null == nameValue) {
                    Toast.makeText(context,
                            "请选择", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (nameValue.getChildRen() == null) {
                    if (listener != null) {
                        listener.choose(nameValue);
                    }
                    dismiss();
                } else {
                    if (nameValue.getChildRen().isEmpty()) {
                        if (listener != null) {
                            listener.choose(nameValue);
                        }
                        dismiss();
                    } else {
                        adapter.NotifyDataChanged(nameValue.getChildRen());
                    }
                }

            }
        });

    }

    private ChooseListener listener;

    public ChooseType2Dialog setChooseListener(ChooseListener listener) {
        this.listener = listener;
        return this;
    }

    public interface ChooseListener {
        void choose(NameValue nameValue);
    }
}
