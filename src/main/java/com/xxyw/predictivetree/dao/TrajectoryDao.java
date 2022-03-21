package com.xxyw.predictivetree.dao;

import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;
import com.xxyw.predictivetree.bean.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrajectoryDao {
    public static List<Point> getTrajectory() {
        List<Point> tra = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader("D:/data/java_predictive_tree/little/tra_test.txt"));
            String str;
            while ((str = in.readLine()) != null) {
//                System.out.println(str);
//                119.37587 35.15589,119.37587 35.15589,
                String[] split = str.split(",");
                for (String poi : split) {
                    String[] strings = poi.split(" ");
                    tra.add(Geometries.pointGeographic(Double.parseDouble(strings[0]), Double.parseDouble(strings[1])));
                }
            }
        } catch (IOException e) {
            System.out.println("getTrajectory Error !!!");
        }
        return tra;
    }
}
