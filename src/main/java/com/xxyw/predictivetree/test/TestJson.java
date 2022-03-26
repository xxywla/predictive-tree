package com.xxyw.predictivetree.test;

import com.xxyw.predictivetree.common.VisualUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestJson {
    public static void main(String[] args) {
        String path = "D:/data/java_predictive_tree/little/test3.json";
        Map<String, List<double[]>> mp = new HashMap<>();
        mp.put("1111", new ArrayList<double[]>() {
            {
                this.add(new double[]{119.3614, 35.1819});
                this.add(new double[]{119.3614, 35.2038});
            }
        });
        mp.put("2222", new ArrayList<double[]>() {
            {
                this.add(new double[]{119.37556, 35.22946});
                this.add(new double[]{119.39338, 35.22578});
            }
        });
        VisualUtil.saveAsJson(mp, "LineString", path);
    }
}
