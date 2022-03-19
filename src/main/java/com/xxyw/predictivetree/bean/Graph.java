package com.xxyw.predictivetree.bean;

import java.util.*;

public class Graph<T extends Comparable<T>> {
    private Map<T, Vertex<T>> vertexMap = new HashMap<>();
    private Map<T, List<Edge<T>>> edgesMap = new HashMap<>();
    private List<Vertex<T>> allVertices = new ArrayList<>();
    private List<Edge<T>> allEdges = new ArrayList<>();

    public Graph(Collection<Graph.Vertex<T>> vertices, Collection<Graph.Edge<T>> edges) {
        this.allVertices.addAll(vertices);
        this.allEdges.addAll(edges);

        for (Vertex<T> vertex : vertices) {
            vertexMap.put(vertex.value, vertex);
        }
        for (Edge<T> edge : edges) {
            List<Edge<T>> list = edgesMap.getOrDefault(edge.from.value, new ArrayList<>());
            list.add(edge);
            edgesMap.put(edge.from.value, list);
        }
    }

    public List<Vertex<T>> getVertices() {
        return allVertices;
    }

    public List<Edge<T>> getEdges() {
        return allEdges;
    }

    public Vertex<T> getVertice(T value) {
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
