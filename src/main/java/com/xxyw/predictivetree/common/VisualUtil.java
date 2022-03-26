package com.xxyw.predictivetree.common;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisualUtil {
    /**
     * 把轨迹点列表转成json文件用于QGIS可视化
     *
     * @param mp   properties作为key，轨迹点double[]的列表作为Value
     * @param type 有3种：Multipoint, LineString, Point
     * @param path 保存文件的路径
     */
    public static void saveAsJson(Map<String, List<double[]>> mp, String type, String path) {
        JSONObject json = new JSONObject();
        json.put("type", "FeatureCollection");
        List<Feature> features = new ArrayList<>();
        for (Map.Entry<String, List<double[]>> entry : mp.entrySet()) {
            features.add(new Feature("Feature",
                    new Geometry(type, entry.getValue()),
                    new Properties(entry.getKey())));
        }
        json.put("features", features);
        System.out.println(json);

        try {
            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(path));
            bw.write(json.toString().getBytes());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAsJson(List<double[]> poiList, String type, String path) {
        Map<String, List<double[]>> mp = new HashMap<>();
        mp.put(path, poiList);
        saveAsJson(mp, type, path);
    }

    public static class Feature {
        @JSONField(name = "type")
        String type;
        @JSONField(name = "geometry")
        Geometry geometry;
        @JSONField(name = "properties")
        Properties properties;

        public Feature(String type, Geometry geometry, Properties properties) {
            this.type = type;
            this.geometry = geometry;
            this.properties = properties;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public Properties getProperties() {
            return properties;
        }

        public void setProperties(Properties properties) {
            this.properties = properties;
        }
    }

    public static class Geometry {
        @JSONField(name = "type")
        String type;
        @JSONField(name = "coordinates")
        List<double[]> coordinates;

        public Geometry(String type, List<double[]> coordinates) {
            this.type = type;
            this.coordinates = coordinates;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<double[]> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<double[]> coordinates) {
            this.coordinates = coordinates;
        }
    }

    public static class Properties {
        @JSONField(name = "osmId")
        String osmId;

        public Properties(String osmId) {
            this.osmId = osmId;
        }

        public String getOsmId() {
            return osmId;
        }

        public void setOsmId(String osmId) {
            this.osmId = osmId;
        }
    }
}
