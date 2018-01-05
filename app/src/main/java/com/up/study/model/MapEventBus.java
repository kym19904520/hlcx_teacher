package com.up.study.model;

import java.util.Map;

/**
 * Created by kym on 2017/7/24.
 */

public class MapEventBus {
    public Map<Integer,Book> map;

    public MapEventBus(Map<Integer,Book> map){
        this.map = map;
    }
}
