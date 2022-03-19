package com.xxyw.predictivetree.common;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Point;
import com.xxyw.predictivetree.bean.Graph;
import com.xxyw.rtree.LatLongExampleTest;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

public class Utils<T extends Comparable<T>> {
    public Graph.Vertex<T> nearestNode(Point point, RTree<T, Point> rTree, Graph<T> graph) {
        List<Entry<T, Point>> result = LatLongExampleTest.search(rTree, point, 1000).toList().toBlocking().single();
        for (Graph.Vertex<T> vertex : graph.getVertices()) {
            if (vertex.getValue().equals(result.get(0).value())) {
                return vertex;
            }
        }
        return null;
    }

    public List<T> getTrajectoryBuffer(List<Point> points, RTree<T, Point> rTree, Graph<T> graph) {
        ArrayList<T> list = new ArrayList<>();
        for (Point point : points) {
            list.add(nearestNode(point, rTree, graph).getValue());
        }
        return list;
    }
}
