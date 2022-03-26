package com.xxyw.predictivetree.bean;

import javafx.util.Pair;

import java.util.*;

public class PredictiveTree<T extends Comparable<T>> {
    // the root of the Predictive Tree
    private Node<T> root;

    // Construction
    public PredictiveTree(Graph.Vertex<T> node, int timeRange, Graph<T> graph) {

        // Visited nodes list NL
        Set<Graph.Vertex<T>> visited;
        // Min-Heap MH
        PriorityQueue<Pair<Graph.Vertex<T>, Integer>> heap;

        // 初始化
        root = new Node<>(node);

        visited = new HashSet<>();
        heap = new PriorityQueue<>(Comparator.comparingInt(Pair::getValue));

        System.out.println("根节点可达的节点个数 " + graph.getToEdges(root.getPoint()).size());
        // 把和当前节点相邻的边加到 Min-Heap
        for (Graph.Edge<T> edge : graph.getToEdges(root.getPoint())) {
            heap.add(new Pair<>(edge.getToVertex(), edge.getCost()));
        }

        System.out.println("初始化后Min-Heap的大小 " + heap.size());

        // Expand the road network and create the predictive tree
        while (!heap.isEmpty() && heap.peek().getValue() <= timeRange) {
            Pair<Graph.Vertex<T>, Integer> pair = heap.poll();
            assert pair != null;
            if (!visited.contains(pair.getKey())) {
                root.addChild(new Node<>(pair.getKey()));
                visited.add(pair.getKey());
                for (Graph.Edge<T> edge : graph.getToEdges(pair.getKey())) {
                    heap.add(new Pair<>(edge.getToVertex(), pair.getValue() + edge.getCost()));
                }
            }
        }
        System.out.println("访问的节点个数 = " + visited.size());
        System.out.println("children list 的长度 " + this.root.getChildren().size());
    }

    public void maintenance(Graph.Vertex<T> node, int timeRange, Graph<T> graph) {
        System.out.println("node " + node.getValue() + " ,root " + this.root.getPoint().getValue());

        // 还在当前节点附近，直接返回
        if (node.getValue().equals(this.root.getPoint().getValue())) {
            return;
        }

        // 从 children 列表中找下一个节点
        for (Node<T> child : root.getChildren()) {
            // 找到下一个节点
            if (child.getPoint().getValue().equals(node.getValue())) {
                // Expand
                this.root = new PredictiveTree<T>(node, timeRange, graph).getRoot();
                return;
            }
        }

        // 没有找到重新建树
        System.out.println("The next position is not in children list !!!");
        this.root = new PredictiveTree<T>(node, timeRange, graph).getRoot();
    }

    public Node<T> getRoot() {
        return root;
    }

    public List<T> getChildren() {
        List<T> children = new ArrayList<>();
        for (Node<T> child : this.root.getChildren()) {
            children.add(child.getPoint().getValue());
        }
        return children;
    }

    @Override
    public String toString() {
        return "PredictiveTree{" +
                "root=" + root +
                '}';
    }
}
