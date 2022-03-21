package com.xxyw.predictivetree.test;

import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Point;
import com.xxyw.predictivetree.bean.Graph;
import com.xxyw.predictivetree.bean.PredictiveTree;
import com.xxyw.predictivetree.common.Utils;
import com.xxyw.predictivetree.dao.MapDao;
import com.xxyw.predictivetree.dao.TrajectoryDao;

import java.util.List;
import java.util.Map;

public class MyTest {
    public static void main(String[] args) {
        Utils<Long> utils = new Utils<>();

        // 获取地图信息
        Graph<Long> graph = MapDao.getGraph();
        System.out.println(graph);
        Map<Long, Point> pointMap = MapDao.getVerticesPointMap();
        System.out.println(pointMap);

        // 创建 RTree
        RTree<Long, Point> rTree = RTree.create();

        // 把地图中所有的节点放入 RTree
        for (Graph.Vertex<Long> vertex : graph.getVertices()) {
            rTree = rTree.add(vertex.getValue(), pointMap.get(vertex.getValue()));
        }
//        System.out.println(rTree.asString());
        rTree.visualize(1000, 1000).save("target/mytree.png");

        // 获取轨迹信息
        List<Point> points = TrajectoryDao.getTrajectory();


        // 生成 trajectory buffer
        List<Long> trajectoryBuffer = utils.getTrajectoryBuffer(points, rTree, graph);
        System.out.println(trajectoryBuffer);

        // 找到当前轨迹点最近的路网上的节点
        Graph.Vertex<Long> node = utils.nearestNode(points.get(0), rTree, graph);
        System.out.println(node);

        // 对轨迹构建 Predictive-tree
        PredictiveTree<Long> predictiveTree = new PredictiveTree<>(node, 300, graph);
        System.out.println(predictiveTree);

        // 物体移动，维护 Predictive tree
        for (Point point : points) {
            node = utils.nearestNode(point, rTree, graph);
            predictiveTree.maintenance(node, 300, graph);
        }
    }
}
