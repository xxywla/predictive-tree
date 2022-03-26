package com.xxyw.predictivetree.bean;

import java.util.*;

public class Graph<T extends Comparable<T>> {
    private final Map<T, Vertex<T>> vertexMap = new HashMap<>();
    private final Map<T, List<Edge<T>>> edgesMap = new HashMap<>();
    private final List<Vertex<T>> allVertices = new ArrayList<>();
    private final List<Edge<T>> allEdges = new ArrayList<>();

    public Graph(Collection<Graph.Vertex<T>> vertices, Collection<Graph.Edge<T>> edges) {
        this.allVertices.addAll(vertices);
        this.allEdges.addAll(edges);

        for (Vertex<T> vertex : vertices) {
            vertexMap.put(vertex.value, vertex);
        }
        for (Edge<T> edge : edges) {
            final Vertex<T> from = edge.from;
            final Vertex<T> to = edge.to;

            // 保证边的两个顶点都在顶点集合
            if (!vertexMap.containsKey(from.value) || !vertexMap.containsKey(to.value))
                continue;

            // 边 from 顶点的邻接表
            List<Edge<T>> list1 = edgesMap.getOrDefault(from.value, new ArrayList<>());
            list1.add(edge);
            edgesMap.put(from.value, list1);

            // 边 to 顶点的邻接表
            List<Edge<T>> list2 = edgesMap.getOrDefault(to.value, new ArrayList<>());
            Edge<T> reciprocal = new Edge<>(edge.cost, to, from);
            list2.add(reciprocal);
            edgesMap.put(to.value, list2);

            // 双向边也需要加入边的集合
            allEdges.add(reciprocal);
        }
    }

    public List<Vertex<T>> getVertices() {
        return allVertices;
    }

    public List<Edge<T>> getEdges() {
        return allEdges;
    }

    public Vertex<T> getVertex(T value) {
        return vertexMap.getOrDefault(value, null);
    }

    public List<Edge<T>> getToEdges(Vertex<T> vertex) {
        return edgesMap.getOrDefault(vertex.getValue(), new ArrayList<>());
    }

    public static class Vertex<T extends Comparable<T>> implements Comparable<Vertex<T>> {
        private T value = null;
        private int weight = 0;

        public Vertex(T value) {
            this.value = value;
        }

        public Vertex(T value, int weight) {
            this(value);
            this.weight = weight;
        }

        public T getValue() {
            return value;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public int compareTo(Vertex<T> v) {
            final int valueComp = this.value.compareTo(v.value);
            if (valueComp != 0)
                return valueComp;

            if (this.weight < v.weight)
                return -1;
            if (this.weight > v.weight)
                return 1;

            return 0;
        }

        @Override
        public String toString() {
            return "Value=" + value + " weight=" + weight;
        }
    }

    public static class Edge<T extends Comparable<T>> implements Comparable<Edge<T>> {
        private Graph.Vertex<T> from = null;
        private Graph.Vertex<T> to = null;
        private int cost = 0;

        public Edge(int cost, Vertex<T> from, Vertex<T> to) {
            if (from == null || to == null)
                throw (new NullPointerException("Both 'to' and 'from' vertices need to be non-NULL."));

            this.cost = cost;
            this.from = from;
            this.to = to;
        }

        public int getCost() {
            return cost;
        }

        public Vertex<T> getFromVertex() {
            return from;
        }

        public Vertex<T> getToVertex() {
            return to;
        }

        @Override
        public int compareTo(Edge<T> e) {
            if (this.cost < e.cost)
                return -1;
            if (this.cost > e.cost)
                return 1;

            final int from = this.from.compareTo(e.from);
            if (from != 0)
                return from;

            final int to = this.to.compareTo(e.to);
            if (to != 0)
                return to;

            return 0;
        }
    }
}
