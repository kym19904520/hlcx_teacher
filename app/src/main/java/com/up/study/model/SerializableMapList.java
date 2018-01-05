package com.up.study.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kym on 2017/7/25.
 */

public class SerializableMapList implements Serializable {
    private Map<Integer,Book> map;

    public Map<Integer, Book> getMap() {
        return map;
    }

    public void setMap(HashMap<Integer, Book> map) {
        this.map = map;
    }
}
