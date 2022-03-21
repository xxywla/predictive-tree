package com.xxyw.predictivetree.common;

import com.github.davidmoten.grumpy.core.Position;
import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Point;
import com.xxyw.predictivetree.bean.Graph;
import com.xxyw.rtree.LatLongExampleTest;
import rx.Observable;
import rx.functions.Func2;

import java.util.ArrayList;
import java.util.List;

public class Utils<T extends Comparable<T>> {
    public Graph.Vertex<T> nearestNode(Point point, RTree<T, Point> rTree, Graph<T> graph) {
        final Position from = Position.create(point.y(), point.x());
        List<Entry<T, Point>> result = LatLongExampleTest.search(rTree, point, 5)
                .sorted(new Func2<Entry<T, Point>, Entry<T, Point>, Integer>() {
                    @Override
                    public Integer call(Entry<T, Point> entryA, Entry<T, Point> entryB) {
                        Point pa = entryA.geometry();
                        Point pb = entryB.geometry();
                        Position posA = Position.create(pa.y(), pa.x());
                        Position posB = Position.create(pb.y(), pb.x());
                        double t = from.getDistanceToKm(posA) - from.getDistanceToKm(posB);
                        if (t < 0) {
                            return -1;
                        }
                        return 1;
                    }
                }).toList().toBlocking().single();
//        System.out.println("RTree查询结果的个数 " + result.size());

        return graph.getVertice(result.get(0).value());
    }

    public List<T> getTrajectoryBuffer(List<Point> points, RTree<T, Point> rTree, Graph<T> graph) {
        ArrayList<T> list = new ArrayList<>();
        for (Point point : points) {
            list.add(nearestNode(point, rTree, graph).getValue());
        }
        return list;
    }
}
