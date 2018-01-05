package com.up.study.model;

import java.util.List;

/**
 * Created by kym on 2017/8/4.
 */

public class MyStudentAnalysisListBean {

    /**
     * powerLine : {"xAxis":["201703","201704","201705","201706","201707","201708"],"datas":[[{"name":"四年11班","countTime":"201703","value":99},{"name":"四年11班","countTime":"201704","value":0},{"name":"四年11班","countTime":"201705","value":80},{"name":"四年11班","countTime":"201706","value":0},{"name":"四年11班","countTime":"201707","value":74},{"name":"四年11班","countTime":"201708","value":88}],[{"name":"四年1班","countTime":"201703","value":0},{"name":"四年1班","countTime":"201704","value":0},{"name":"四年1班","countTime":"201705","value":0},{"name":"四年1班","countTime":"201706","value":0},{"name":"四年1班","countTime":"201707","value":0},{"name":"四年1班","countTime":"201708","value":90}]],"legend":["四年11班","四年1班"]}
     * subject : [{"difficulty":1,"name":"把0.4改写成\u201c分\u201d做位是　　）","per":33,"subjectId":94}]
     * powerInfo : {"gRank":2,"pCount":2,"pRank":2,"point":88,"gCount":2}
     * know : [{"kId":40,"name":"整数的加法和减法","per":0},{"kId":106,"name":"根据情景选择合适的计量单位","per":0},{"kId":318,"name":"横式与竖式的互换","per":0},{"kId":387,"name":"差倍问题","per":0},{"kId":56,"name":"分数的简便计算","per":0},{"kId":123,"name":"平年、闰年的判断方法","per":0},{"kId":335,"name":"位值原则","per":0},{"kId":7,"name":"倒数的认识","per":0}]
     * structure : [{"name":"第6章 可能性","structureId":249,"per":0},{"name":"第5章 平行四边形和梯形","structureId":365,"per":0},{"name":"第8章 数学广角--优化","structureId":26,"per":0},{"name":"第6章 除法","structureId":188,"per":0},{"name":"第7章 整数四则混合运算","structureId":250,"per":0},{"name":"第6章 除数是两位数的除法","structureId":372,"per":0},{"name":"第9章 总复习","structureId":27,"per":0},{"name":"第7章 生活中的负数","structureId":189,"per":0}]
     */

    private PowerLineBean powerLine;
    private PowerInfoBean powerInfo;
    private List<SubjectBean> subject;
    private List<KnowBean> know;
    private List<StructureBean> structure;

    public PowerLineBean getPowerLine() {
        return powerLine;
    }

    public void setPowerLine(PowerLineBean powerLine) {
        this.powerLine = powerLine;
    }

    public PowerInfoBean getPowerInfo() {
        return powerInfo;
    }

    public void setPowerInfo(PowerInfoBean powerInfo) {
        this.powerInfo = powerInfo;
    }

    public List<SubjectBean> getSubject() {
        return subject;
    }

    public void setSubject(List<SubjectBean> subject) {
        this.subject = subject;
    }

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

    public static class PowerLineBean {
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
             * countTime : 201703
             * value : 99
             */

            private String name;
            private String countTime;
            private int value;

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

    public static class PowerInfoBean {
        /**
         * gRank : 2
         * pCount : 2
         * pRank : 2
         * point : 88
         * gCount : 2
         */

        private int gRank;
        private int pCount;
        private int pRank;
        private int point;
        private int gCount;

        public int getGRank() {
            return gRank;
        }

        public void setGRank(int gRank) {
            this.gRank = gRank;
        }

        public int getPCount() {
            return pCount;
        }

        public void setPCount(int pCount) {
            this.pCount = pCount;
        }

        public int getPRank() {
            return pRank;
        }

        public void setPRank(int pRank) {
            this.pRank = pRank;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getGCount() {
            return gCount;
        }

        public void setGCount(int gCount) {
            this.gCount = gCount;
        }
    }

    public static class SubjectBean {
        /**
         * difficulty : 1
         * name : 把0.4改写成“分”做位是　　）
         * per : 33
         * subjectId : 94
         */

        private int difficulty;
        private String name;
        private int per;
        private int subjectId;

        public int getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(int difficulty) {
            this.difficulty = difficulty;
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

        public int getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(int subjectId) {
            this.subjectId = subjectId;
        }
    }

    public static class KnowBean {
        /**
         * kId : 40
         * name : 整数的加法和减法
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
         * name : 第6章 可能性
         * structureId : 249
         * per : 0
         */

        private String name;
        private int structureId;
        private int per;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStructureId() {
            return structureId;
        }

        public void setStructureId(int structureId) {
            this.structureId = structureId;
        }

        public int getPer() {
            return per;
        }

        public void setPer(int per) {
            this.per = per;
        }
    }
}
