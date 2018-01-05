package com.up.study.model;

import com.up.common.base.BaseBean;

import java.util.List;

/**
 * TODO:
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/8/10.
 */

public class QuestionsTypeModel extends BaseBean {
    private int subjectNum;//subjectNum
    private String groupName;//题目组名称
    private float scoreNum;//试卷分数
    private List<QuestionsModel> datas;

    public float getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(float scoreNum) {
        this.scoreNum = scoreNum;
    }

    public int getSubjectNum() {
        return subjectNum;
    }

    public void setSubjectNum(int subjectNum) {
        this.subjectNum = subjectNum;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<QuestionsModel> getDatas() {
        return datas;
    }

    public void setDatas(List<QuestionsModel> datas) {
        this.datas = datas;
    }
}
