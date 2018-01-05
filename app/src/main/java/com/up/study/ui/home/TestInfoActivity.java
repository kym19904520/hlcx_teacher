package com.up.study.ui.home;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.up.common.J;
import com.up.common.base.BaseBean;
import com.up.common.base.CommonAdapter;
import com.up.common.base.ViewHolder;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.FormatUtils;
import com.up.common.utils.StudyUtils;
import com.up.common.widget.MyListView;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.SeqModel;
import com.up.study.model.TestModel;
import com.up.study.params.Params;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * TODO:
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/7/28.
 */

public class TestInfoActivity extends BaseFragmentActivity {
    @Bind(R.id.tv_title_num)
    TextView tvTitleNum;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    @Bind(R.id.lv)
    ListView lv;

    private CommonAdapter<seqTypeList> adapter;
    private List<seqTypeList> list = new ArrayList<>();

    private TestModel testModel = null;

    private resData resData;
    @Override
    protected int getContentViewId() {
        return R.layout.act_test_info;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        Map<String, Object> map = getMap();
        if (map != null) {
            testModel = (TestModel) map.get("test");
            tvTitleNum.setText(testModel.getName()+"（"+testModel.getSubjectNum()+"）");
        }
        /*adapter = new CommonAdapter<QuestionsTypeModel>(ctx, list, R.layout.item_question_type_list) {
            @Override
            public void convert(ViewHolder vh, QuestionsTypeModel item, int position) {
//                LinearLayout llTitle = vh.getView(R.id.ll_title);
//                LinearLayout llChoice = vh.getView(R.id.ll_choice);
//                if (item.equals("title")) {
//                    llTitle.setVisibility(View.VISIBLE);
//                    llChoice.setVisibility(View.GONE);
//                } else {
//                    llTitle.setVisibility(View.GONE);
//                    llChoice.setVisibility(View.VISIBLE);
//                }
//                TextView tvContent = vh.getView(R.id.tv_content);
//                String content = "<font color=\"#00aeff\" size=\"13\">1/20</font>  扫地的 扫 的读音是？";
//                tvContent.setText(Html.fromHtml(content));
//                MyListView lv2 = vh.getView(R.id.lv_answer);
//                CommonAdapter<Map<String, String>> adapter1 = new CommonAdapter<Map<String, String>>(ctx, answerList, R.layout.item_topic_kgt) {
//                    @Override
//                    public void convert(ViewHolder vh, Map<String, String> item, int position) {
//                        vh.setText(R.id.tv_answer, item.get("key1"));
//                        vh.setText(R.id.tv_answer_text, item.get("key2"));
//                    }
//                };
//                lv2.setAdapter(adapter1);
                vh.setText(R.id.tv_type_name, item.getGroupName());
                vh.setText(R.id.tv_count, "(" + item.getSubjectNum() + ")");
                vh.setText(R.id.tv_score, "小计 " + item.getScoreNum() + "分");
                final int subjectNum = item.getSubjectNum();
                MyListView lvQuestion = vh.getView(R.id.lv_question);
                CommonAdapter<QuestionsModel> commonAdapter = new CommonAdapter<QuestionsModel>(ctx, item.getDatas(), R.layout.item_lv_test_info) {
                    @Override
                    public void convert(ViewHolder vh, QuestionsModel item, int position) {
                        TextView tvContent = vh.getView(R.id.tv_content);
                        String content = "<font color=\"#00aeff\" size=\"13\">" + item.getSeq() + "/" + subjectNum + "</font>" + item.getName();
                        tvContent.setText(Html.fromHtml(content));
                        TagGroup tag=vh.getView(R.id.tag_group);
                        String[] tags=item.getKnowName().split("//@");
                        tag.setTags(tags);
                        vh.setText(R.id.tv_know_name, item.getKnowName());
                        vh.setText(R.id.tv_score, item.getMark() + "分");

                        //选择题
                        MyListView lvOption = vh.getView(R.id.lv_answer);
                        CommonAdapter<QuestionsModel.Option> commonAdapter1 = new CommonAdapter<QuestionsModel.Option>(ctx, item.getOptions(), R.layout.item_topic_kgt) {
                            @Override
                            public void convert(ViewHolder vh, QuestionsModel.Option item, int position) {
                                vh.setText(R.id.tv_answer, item.getOpt());
                                vh.setText(R.id.tv_answer, item.getOpt());
                            }
                        };
                        lvOption.setAdapter(commonAdapter1);
                    }
                };
                lvQuestion.setAdapter(commonAdapter);


            }
        };*/
        adapter = new CommonAdapter<seqTypeList>(ctx, list, R.layout.item_test_subtype) {
            @Override
            public void convert(ViewHolder vh, final seqTypeList item, int position) {
                MyListView mlv = vh.getView(R.id.mlv);
                List<SeqModel> seqList = item.getDatas();
                TextView tv = vh.getView(R.id.tv_sub_type);
                tv.setText(item.getGroupName());
                TextView tv_num = vh.getView(R.id.tv_num);
                tv_num.setText("（" + seqList.size() + "）");
                TextView tv_total = vh.getView(R.id.tv_total);
                tv_total.setText("小计"+item.getScoreNum()+"分");
                CommonAdapter<SeqModel> adapter = new CommonAdapter<SeqModel>(ctx, seqList, R.layout.item_test_detail) {
                    @Override
                    public void convert(ViewHolder vh, SeqModel seq, int position) {
                        StudyUtils.initSeq(TestInfoActivity.this, vh, seq, 1);
                        TextView tv_seq = vh.getView(R.id.tv_seq);//题号/总题数
                        tv_seq.setText(seq.getSeq() + "/" + resData.getSubjectNum());
                        TextView tv_score = vh.getView(R.id.tv_score);
                        tv_score.setText(seq.getMark() + "分");
                        MyListView lv = vh.getView(R.id.mlv);
                        lv.setClickable(false);
                        lv.setPressed(false);
                        lv.setEnabled(false);
                    }
                };
                mlv.setAdapter(adapter);
            }
        };

        lv.setAdapter(adapter);
        loadTestInfo();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }


    private void loadTestInfo() {
        list.clear();
        J.http().post(Urls.TEST_INFO, ctx, Params.testInfo(testModel.getId()), new HttpCallback<Respond<resData>>(ctx) {
            @Override
            public void success(Respond<resData> res, Call call, Response response, boolean isCache) {
                if(res.getData()!=null&&res.getData().getData()!=null){
                    resData = res.getData();
                    list.addAll(res.getData().getData());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    private class resData extends BaseBean {
        private int subjectNum;
        private String name;

        private double scoreNum;
        private List<seqTypeList> data;

        public int getSubjectNum() {
            return subjectNum;
        }

        public void setSubjectNum(int subjectNum) {
            this.subjectNum = subjectNum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScoreNum() {
            return FormatUtils.double1(scoreNum);
        }

        public void setScoreNum(double scoreNum) {
            this.scoreNum = scoreNum;
        }

        public List<seqTypeList> getData() {
            return data;
        }

        public void setData(List<seqTypeList> data) {
            this.data = data;
        }
    }

    private class seqTypeList extends BaseBean {
        private String groupName;
        private int subjectNum;
        private List<SeqModel> datas;
        private double scoreNum;

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public int getSubjectNum() {
            return subjectNum;
        }

        public void setSubjectNum(int subjectNum) {
            this.subjectNum = subjectNum;
        }

        public List<SeqModel> getDatas() {
            return datas;
        }

        public void setDatas(List<SeqModel> datas) {
            this.datas = datas;
        }

        public String getScoreNum() {
            return FormatUtils.double1(scoreNum);
        }

        public void setScoreNum(double scoreNum) {
            this.scoreNum = scoreNum;
        }
    }

}
