package com.up.study.weight;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.up.common.base.CommonAdapter;
import com.up.common.base.ViewHolder;
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
 * On 2017/8/7.
 */

public class ChooseDialog extends Dialog {
    private Context context;
    private List<NameValue> list = new ArrayList<>();
    private CommonAdapter<NameValue> adapter;

    public ChooseDialog(Context context, List<NameValue> objects) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.list = objects;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_choose_name_value);
        ListView lv = (ListView) findViewById(R.id.lv);
        adapter = new CommonAdapter<NameValue>(context, list, R.layout.item_lv_name_value) {
            @Override
            public void convert(ViewHolder vh, NameValue item, int position) {
                vh.setText(R.id.tv_type, item.getName());
            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (null != listener)
                    listener.choose((NameValue) adapterView.getAdapter().getItem(i));
                dismiss();
            }
        });
    }

    private ChooseListener listener;

    public ChooseDialog setChooseListener(ChooseListener listener) {
        this.listener = listener;
        return this;
    }

    public interface ChooseListener {
        void choose(NameValue res);
    }
}
