package com.xxyw.predictivetree.test;

import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Point;
import com.xxyw.predictivetree.bean.Graph;
import com.xxyw.predictivetree.common.Utils;
import com.xxyw.predictivetree.common.VisualUtil;
import com.xxyw.predictivetree.dao.MapDao;
import com.xxyw.predictivetree.dao.TrajectoryDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestVisJSON {
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

        // 把trajectory buffer保存成JSON可视化出来
        Map<String, List<double[]>> mp = new HashMap<>();
        for (Long value : trajectoryBuffer) {
            Point point = pointMap.get(value);
            double[] doubles = {point.x(), point.y()};
            mp.put(String.valueOf(value), new ArrayList<double[]>() {{
                this.add(doubles);
            }});
        }
        VisualUtil.saveAsJson(mp, "Multipoint", "D:/data/java_predictive_tree/little/tra_buffer.json");

    }
}
