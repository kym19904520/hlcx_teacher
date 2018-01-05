package com.up.study.model;

import com.up.common.base.BaseBean;

/**
 * TODO:题
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by yy_cai
 * On 2017/3/12.
 */

public class SeqModel extends BaseBean {
    private int subjectId;//试题ID
    private String answer;//答案
    private int seq;//题号
    private double mark;//该题总分数
    private String relationId;//试卷id，任务id
    private int status;//正确与否 0:正确，其他情况都视为错误,（默认正确）

    private String title;//刘全接口标题
    private String name;//黄友力接口的标题
    private int subjectType;//题型id
    private String subjectTypeName;//题型名称
    private String analysis;//解题思路
    private String content;//内容
    private String knowName;//知识点
    private String options;//选项
    private boolean select;//是否被选中
    //刘全接口
    private int allTotal;//总题数
    private int wrongTotal;//错误次数
    private int wrongRate;//错误率
    //有力接口
    private String wrongNum;//错误数
    private String per;//错误率
    private String subjectNum;//错题数

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getAnswer() {
         if (getSubjectTypeName().contains("判断")){
            String as = answer;
            as = as.replace("0","A");
            as = as.replace("1","B");
             answer = as;
        }
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;

    }

    public String getOptions() {
        if(getSubjectTypeName().contains("判断")){
           setOptions("[{\"text\":\"正确\",\"opt\":\"A\"},{\"text\":\"错误\",\"opt\":\"B\"}]");
        }
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(int subjectType) {
        this.subjectType = subjectType;
    }

    public String getSubjectTypeName() {
        return subjectTypeName;
    }

    public void setSubjectTypeName(String subjectTypeName) {
        this.subjectTypeName = subjectTypeName;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKnowName() {
        return knowName;
    }

    public void setKnowName(String knowName) {
        this.knowName = knowName;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getAllTotal() {
        return allTotal;
    }

    public void setAllTotal(int allTotal) {
        this.allTotal = allTotal;
    }

    public int getWrongTotal() {
        return wrongTotal;
    }

    public void setWrongTotal(int wrongTotal) {
        this.wrongTotal = wrongTotal;
    }

    public int getWrongRate() {
        return wrongRate;
    }

    public void setWrongRate(int wrongRate) {
        this.wrongRate = wrongRate;
    }

    public String getWrongNum() {
        return wrongNum;
    }

    public void setWrongNum(String wrongNum) {
        this.wrongNum = wrongNum;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getSubjectNum() {
        return subjectNum;
    }

    public void setSubjectNum(String subjectNum) {
        this.subjectNum = subjectNum;
    }
}
