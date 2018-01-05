package com.up.study.model;

import java.util.List;

/**
 * Created by kym on 2017/8/3.
 */

public class MyClassChartBean {

    private List<String> xAxis;
    private List<List<DatasBean>> datas;
    private List<String> legend;

    public List<String> getXAxis() {
        return xAxis;
    }

    public void setXAxis(List<String> xAxis) {
        this.xAxis = xAxis;
    }

    public List<List<DatasBean>> getDatas() {
        return datas;
    }

    public void setDatas(List<List<DatasBean>> datas) {
        this.datas = datas;
    }

    public List<String> getLegend() {
        return legend;
    }

    public void setLegend(List<String> legend) {
        this.legend = legend;
    }

    public static class DatasBean {
        /**
         * name : 四年11班
         * countTime : 201609
         * value : 0
         */

        private String name;
        private String countTime;
        private Integer value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountTime() {
            return countTime;
        }

        public void setCountTime(String countTime) {
            this.countTime = countTime;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
