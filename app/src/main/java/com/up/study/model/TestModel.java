package com.up.study.model;

import com.up.common.base.BaseBean;

import java.util.List;

/**
 * TODO:试卷
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/8/10.
 */

public class TestModel extends BaseBean {
    private String gradeName;//年段名称
    private String paperType;//试卷类型
    private int subjectNum;//题目数
    private int flag;//是否存在 标准卷 0,1
    private int grade;//年段ID
    private String name;//试卷名称
    private int id;//试卷id
    private int courseId;//科目ID

    private boolean isSelected;


    private List<QuestionsTypeModel> data;

    public List<QuestionsTypeModel> getData() {
        return data;
    }

    public void setData(List<QuestionsTypeModel> data) {
        this.data = data;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public int getSubjectNum() {
        return subjectNum;
    }

    public void setSubjectNum(int subjectNum) {
        this.subjectNum = subjectNum;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
