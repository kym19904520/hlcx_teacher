package com.up.study.model;

import java.util.List;

/**
 * Created by kym on 2017/8/4.
 */

public class MyStudentDataBean {

    private List<KnowBean> know;
    private List<StructureBean> structure;

    public List<KnowBean> getKnow() {
        return know;
    }

    public void setKnow(List<KnowBean> know) {
        this.know = know;
    }

    public List<StructureBean> getStructure() {
        return structure;
    }

    public void setStructure(List<StructureBean> structure) {
        this.structure = structure;
    }

    public static class KnowBean {
        /**
         * kId : 65
         * name : 计算器与复杂的运算
         * per : 0
         */

        private int kId;
        private String name;
        private int per;

        public int getKId() {
            return kId;
        }

        public void setKId(int kId) {
            this.kId = kId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPer() {
            return per;
        }

        public void setPer(int per) {
            this.per = per;
        }
    }

    public static class StructureBean {
        /**
         * name : 第五章 三角形
         * id : 83
         * per : 0
         */

        private String name;
        private int id;
        private int per;

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

        public int getPer() {
            return per;
        }

        public void setPer(int per) {
            this.per = per;
        }
    }
}
