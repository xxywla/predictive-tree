package com.xxyw.predictivetree.dao;

import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;
import com.xxyw.predictivetree.bean.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapDao {
    public static Map<Long, Point> getVerticesPointMap() {
        Map<Long, Point> mp = new HashMap<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader("D:/data/java_predictive_tree/little/nodes.txt"));
            String str;
            while ((str = in.readLine()) != null) {
//                150547868 36.2168199 120.1951
                String[] split = str.split(" ");
                mp.put(Long.parseLong(split[0]), Geometries.pointGeographic(Double.parseDouble(split[2]), Double.parseDouble(split[1])));
            }
        } catch (IOException e) {
            System.out.println("getVertices Error !!!");
        }
        return mp;
    }

    public static List<Graph.Vertex<Long>> getVertices() {
        List<Graph.Vertex<Long>> vertices = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader("D:/data/java_predictive_tree/little/nodes.txt"));
            String str;
            while ((str = in.readLine()) != null) {
//                150547868 36.2168199 120.1951
                String[] split = str.split(" ");
                vertices.add(new Graph.Vertex<>(Long.parseLong(split[0])));
            }
        } catch (IOException e) {
            System.out.println("getVertices Error !!!");
        }
        return vertices;
    }

    public static List<Graph.Edge<Long>> getEdges() {
        List<Graph.Edge<Long>> edges = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("D:/data/java_predictive_tree/little/edges.txt");
            BufferedReader in = new BufferedReader(fileReader);
            String str;
            while ((str = in.readLine()) != null) {
//                621238220 3968811632 217
                String[] split = str.split(" ");
                edges.add(new Graph.Edge<>(Integer.parseInt(split[2]),
                        new Graph.Vertex<>(Long.parseLong(split[0])),
                        new Graph.Vertex<>(Long.parseLong(split[1]))));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return edges;
    }

    public static Graph<Long> getGraph() {

        List<Graph.Vertex<Long>> vertices = MapDao.getVertices();

        List<Graph.Edge<Long>> edges = MapDao.getEdges();

        Graph<Long> graph = new Graph<>(vertices, edges);

        return graph;
    }
}
