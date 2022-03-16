package com.xxyw.rtree;

import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Point;
import com.github.davidmoten.rtree.geometry.Rectangle;

public class TestRTree {
    public static void main(String[] args) {
        RTree<String, Geometry> tree = RTree.create();
        tree = tree.add("aaa", Geometries.point(10, 20));
        Point point = Geometries.pointGeographic(103,35);
        Rectangle rectangle = Geometries.rectangleGeographic(103,35,104,36);
    }
}
