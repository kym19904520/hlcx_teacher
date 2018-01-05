package com.up.study.model;

import com.up.common.model.BaseBean;

/**
 * Created by dell on 2017/8/9.
 * 知识薄弱点
 */

public class KnowModel extends BaseBean{
    private int knowledgeId;
    private String knowledgeName;

    public int getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(int knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getKnowledgeName() {
        return knowledgeName;
    }

    public void setKnowledgeName(String knowledgeName) {
        this.knowledgeName = knowledgeName;
    }
}
