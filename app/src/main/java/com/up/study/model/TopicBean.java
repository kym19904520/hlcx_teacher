package com.up.study.model;

import java.util.List;

/**
 * Created by kym on 2017/9/6.
 */

public class TopicBean {

    private List<SubListBean> subList;

    public List<SubListBean> getSubList() {
        return subList;
    }

    public void setSubList(List<SubListBean> subList) {
        this.subList = subList;
    }

    public static class SubListBean {

        private int shared;
        private int upload_type;
        private int status;
        private int chapter_id;
        private String answer;
        private int difficulty;
        private int material_id;
        private String analysis;
        private int type;
        private int id;
        private Object content;
        private int cuid;
        private String title;
        private long create_date;
        private int major_id;
        private int subject_type;
        private int grade_id;
        private String options;
        private String subjectTypeName;

        public String getSubjectTypeName() {
            return subjectTypeName;
        }

        public void setSubjectTypeName(String subjectTypeName) {
            this.subjectTypeName = subjectTypeName;
        }

        public int getShared() {
            return shared;
        }

        public void setShared(int shared) {
            this.shared = shared;
        }

        public int getUpload_type() {
            return upload_type;
        }

        public void setUpload_type(int upload_type) {
            this.upload_type = upload_type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(int chapter_id) {
            this.chapter_id = chapter_id;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public int getMaterial_id() {
            return material_id;
        }

        public void setMaterial_id(int material_id) {
            this.material_id = material_id;
        }

        public String getAnalysis() {
            return analysis;
        }

        public void setAnalysis(String analysis) {
            this.analysis = analysis;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
        }

        public int getCuid() {
            return cuid;
        }

        public void setCuid(int cuid) {
            this.cuid = cuid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getCreate_date() {
            return create_date;
        }

        public void setCreate_date(long create_date) {
            this.create_date = create_date;
        }

        public int getMajor_id() {
            return major_id;
        }

        public void setMajor_id(int major_id) {
            this.major_id = major_id;
        }

        public int getSubject_type() {
            return subject_type;
        }

        public void setSubject_type(int subject_type) {
            this.subject_type = subject_type;
        }

        public int getGrade_id() {
            return grade_id;
        }

        public void setGrade_id(int grade_id) {
            this.grade_id = grade_id;
        }

        public String getOptions() {
            return options;
        }

        public void setOptions(String options) {
            this.options = options;
        }
    }
}
